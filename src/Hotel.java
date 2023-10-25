import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hotel {

    private List<Complaint> complaints = new ArrayList<>();
    private static Hotel instance = null;
    private List<SeasonalPrice> seasonalPrices = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Reservation> pastReservations = new ArrayList<>();
    private List<Staff> staffMembers = new ArrayList<>();
    // private constructor to prevent instantiation
    private Hotel() {
    }

    // public method to get instance
    public static Hotel getInstance() {
        if (instance == null) {
            instance = new Hotel();
        }
        return instance;
    }

    private static List<ServiceRequest> serviceRequests = new ArrayList<>();

    public static void logServiceRequest(ServiceRequest request) {
        serviceRequests.add(request);
    }

    public void logComplaint(Guest guest, String complaintDescription) {
        int nextComplaintId = complaints.size() + 1; // Generate a unique complaint ID
        Complaint complaint = new Complaint(nextComplaintId, guest, new Date(), complaintDescription);
        complaints.add(complaint);
    }

    public List<Complaint> getUnresolvedComplaints() {
        List<Complaint> unresolved = new ArrayList<>();
        for (Complaint complaint : complaints) {
            if (!complaint.isResolved()) {
                unresolved.add(complaint);
            }
        }
        return unresolved;
    }

    public void resolveComplaint(int complaintId, String resolution) {
        for (Complaint complaint : complaints) {
            if (complaint.getComplaintId() == complaintId && !complaint.isResolved()) {
                complaint.setResolution(resolution);
                complaint.markResolved();
                System.out.println("Complaint with ID " + complaintId + " has been resolved.");
                return;
            }
        }
        System.out.println("Complaint not found or already resolved.");
    }
    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void assignStaffToRequest(ServiceRequest request) {
        Staff availableStaff = findAvailableStaff();
        if (availableStaff != null) {
            request.setStaffAssigned(availableStaff);
            availableStaff.setAvailable(false);
        } else {
            System.out.println("No available staff found.");
        }
    }



    public OccupancyReport generateOccupancyReport(Date startDate, Date endDate) {
        int totalRooms = rooms.size();
        int occupiedRoomDays = 0;

        for (Reservation reservation : reservations) {
            Date checkIn = reservation.getCheckInDate();
            Date checkOut = reservation.getCheckOutDate();
            if (checkIn.before(endDate) && checkOut.after(startDate)) {
                // Calculate the overlap days
                Date effectiveCheckIn = checkIn.after(startDate) ? checkIn : startDate;
                Date effectiveCheckOut = checkOut.before(endDate) ? checkOut : endDate;
                long days = (effectiveCheckOut.getTime() - effectiveCheckIn.getTime()) / (1000 * 60 * 60 * 24);
                occupiedRoomDays += days;
            }
        }

        double occupancyRate = (double) occupiedRoomDays / (totalRooms * ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)));
        return new OccupancyReport(startDate, endDate, occupancyRate);
    }

    public static List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }
    public void addStaffMember(Staff staff) {
        staffMembers.add(staff);
    }
    public List<Staff> getStaffMembers() {
        return staffMembers;
    }
    public void assignStaffToRoom(Staff staff, Room room) throws Exception {
        for (Room r : rooms) {
            if (r.getCleaningStaff() != null && r.getCleaningStaff().equals(staff)) {
                throw new Exception("This staff member is already assigned to another room.");
            }
        }
        room.assignCleaningStaff(staff);
    }
    public boolean hasActiveReservation(Guest guest, Date serviceDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getGuest().equals(guest) &&
                    !reservation.isCheckedOut() &&
                    (serviceDate.after(reservation.getCheckInDate()) || serviceDate.equals(reservation.getCheckInDate())) &&
                    (serviceDate.before(reservation.getCheckOutDate()) || serviceDate.equals(reservation.getCheckOutDate()))) {
                return true;
            }
        }
        return false;
    }
    public void completeServiceRequest(ServiceRequest request) {
        request.complete();
    }
    public Staff findAvailableStaff() {
        for (Staff staff : staffMembers) {
            if (staff.isAvailable()) {
                return staff;
            }
        }
        return null;  // Return null if no available staff found
    }
    public void setSeasonalPrice(Date startDate, Date endDate, double multiplier) {
        seasonalPrices.add(new SeasonalPrice(startDate, endDate, multiplier));
    }
    public double getPriceMultiplier(Date date) {
        for (SeasonalPrice seasonalPrice : seasonalPrices) {
            if (seasonalPrice.isDateWithinSeason(date)) {
                return seasonalPrice.getMultiplier();
            }
        }
        return 1.0; // default multiplier
    }
    public void addOrUpdateRoom(int roomNumber, String type, double price) {
        Room roomToUpdate = findRoomByNumber(roomNumber);
        if (roomToUpdate != null) {
            roomToUpdate.setType(type);
            roomToUpdate.setPrice(price);
            System.out.println("Room updated successfully.");
        } else {
            Room newRoom = new Room(roomNumber, type, price);
            rooms.add(newRoom);
            System.out.println("New room added to the inventory.");
        }
    }
    public List<Room> getAvailableRooms(Date checkIn, Date checkOut) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable(checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    public void bookRoom(int roomNumber, Date checkIn, Date checkOut, Guest guest) throws Exception {
        Room roomToBook = findRoomByNumber(roomNumber);
        if (roomToBook == null) {
            throw new Exception("Room not found.");
        }

        // Ensure check-in date is before check-out date
        if (!checkOut.after(checkIn)) {
            throw new Exception("Check-Out Date must be after the Check-In Date.");
        }

        // Ensure the room isn't already booked within the date range provided
        if (!roomToBook.isAvailable(checkIn, checkOut)) {
            throw new Exception("Room is already booked during the provided dates.");
        }

        try {
            Reservation newReservation = new Reservation(guest, roomToBook, checkIn, checkOut);
            reservations.add(newReservation);
            roomToBook.addReservation(newReservation);
            System.out.println("Room booked successfully.");
            System.out.printf("Total Price for the reservation: $%.2f\n", newReservation.getTotalPrice());
        } catch (Exception e) {
            System.out.println("Error while booking the room: " + e.getMessage());
        }
    }

    public Reservation checkInGuest_List(int roomNumber, String guestUsername, Date targetCheckInDate) throws Exception {
        Room roomToCheckIn = findRoomByNumber(roomNumber);
        if (roomToCheckIn == null) {
            throw new Exception("Room not found.");
        }

        Reservation targetReservation = null;
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(roomToCheckIn) &&
                    reservation.getGuest().getUsername().equals(guestUsername) &&
                    !reservation.isCheckedIn() &&
                    reservation.getCheckInDate().equals(targetCheckInDate)) {

                targetReservation = reservation;
                break;
            }
        }
        return targetReservation;

    }

    public void checkInGuest(int roomNumber, String guestUsername, Date targetCheckInDate) throws Exception {
        Room roomToCheckIn = findRoomByNumber(roomNumber);
        if (roomToCheckIn == null) {
            throw new Exception("Room not found.");
        }

        Reservation targetReservation = null;
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(roomToCheckIn) &&
                    reservation.getGuest().getUsername().equals(guestUsername) &&
                    !reservation.isCheckedIn() &&
                    reservation.getCheckInDate().equals(targetCheckInDate)) {

                targetReservation = reservation;
                break;
            }
        }

        if (targetReservation != null) {
            targetReservation.checkIn();
            // Set the room to available for cleaning status
            roomToCheckIn.setAvailableForCleaning(false);
            roomToCheckIn.setOccupied(true);  // Set the room's occupied flag to true
            System.out.println("Guest checked in successfully.");
        } else {
            throw new Exception("Matching reservation not found or guest is already checked in.");
        }
    }
    public void checkOutGuest(int roomNumber, String guestUsername, Date targetCheckInDate) throws Exception {
        Room roomToCheckOut = findRoomByNumber(roomNumber);
        if (roomToCheckOut == null) {
            throw new Exception("Room not found.");
        }

        Reservation targetReservation = null;
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(roomToCheckOut) &&
                    reservation.getGuest().getUsername().equals(guestUsername) &&
                    reservation.isCheckedIn() &&
                    reservation.getCheckInDate().equals(targetCheckInDate)) {

                targetReservation = reservation;
                break;
            }
        }

        if (targetReservation != null) {
            // Set the room to available for cleaning status
            roomToCheckOut.setAvailableForCleaning(true);
            roomToCheckOut.setOccupied(false);  // Set the room's occupied flag to false
            targetReservation.checkOut();

            // Move the reservation to pastReservations list
            reservations.remove(targetReservation);
            pastReservations.add(targetReservation);

            System.out.println("Guest checked out successfully.");
        } else {
            throw new Exception("Matching reservation not found or guest is not checked in.");
        }
    }
    public List<Reservation> getPastReservationsForGuest(Guest guest) {
        List<Reservation> guestPastReservations = new ArrayList<>();
        for (Reservation reservation : pastReservations) {
            if (reservation.getGuest().equals(guest)) {
                guestPastReservations.add(reservation);
            }
        }
        return guestPastReservations;
    }
    public List<Reservation> getAllPastReservations() {
        return pastReservations;
    }
    public List<Reservation> getReservationsForGuest(Guest guest) {
        List<Reservation> guestReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getGuest().equals(guest) && !reservation.isCheckedOut()) {
                guestReservations.add(reservation);
            }
        }
        return guestReservations;
    }
    public List<Reservation> getAllReservations() {
        return reservations;
    }
    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
    public double calculateTotalRevenue() {
        double totalRevenue = 0;

        for (Reservation reservation : reservations) {
            totalRevenue += reservation.getTotalPrice();
        }

        for (Reservation reservation : pastReservations) {
            totalRevenue += reservation.getTotalPrice();
        }

        return totalRevenue;
    }
    public void addRoom(Room room) {
        rooms.add(room);
    }
}
