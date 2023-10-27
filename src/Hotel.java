import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

    public void addOrUpdateRoom(int roomNumber, String type, BigDecimal price) {
        Room roomToUpdate = findRoomByNumber(roomNumber);
        if (roomToUpdate != null) {
            roomToUpdate.setType(type);
            roomToUpdate.setPrice(price);
        } else {
            Room newRoom = new Room(roomNumber, type, price);
            rooms.add(newRoom);
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

    public boolean bookRoom(int roomNumber, Date checkIn, Date checkOut, Guest guest) throws Exception {
        Room roomToBook = findRoomByNumber(roomNumber);
        if (roomToBook == null) {
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
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public BigDecimal calculateTotalRevenue() {
        BigDecimal totalRevenue = BigDecimal.valueOf(0.0);

        for (Reservation reservation : reservations) {
            totalRevenue.add(reservation.getTotalPrice());
        }

        for (Reservation reservation : pastReservations) {
            totalRevenue.add(reservation.getTotalPrice());
        }

        return totalRevenue;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Reservation> searchReservations(String guestUsernameCheckin, String roomNumberStr, String checkInDateStr) {
        List<Reservation> searchResults = new ArrayList<>();

        for (Reservation reservation : reservations) {
            boolean match = true;

            // Check if guest username matches (if provided)
            if (guestUsernameCheckin != null && !guestUsernameCheckin.isEmpty()) {
                String guestUsername = reservation.getGuest().getUsername();
                if (!guestUsername.equalsIgnoreCase(guestUsernameCheckin)) {
                    match = false;
                }
            }

            // Check if room number matches (if provided)
            if (roomNumberStr != null && !roomNumberStr.isEmpty()) {
                int roomNumber = reservation.getRoom().getRoomNumber();
                try {
                    int searchRoomNumber = Integer.parseInt(roomNumberStr);
                    if (roomNumber != searchRoomNumber) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    // Invalid room number format, ignore it
                }
            }

            // Check if check-in date matches (if provided)
            if (checkInDateStr != null && !checkInDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInDate = reservation.getCheckInDate();
                try {
                    Date searchCheckInDate = dateFormat.parse(checkInDateStr);
                    if (!checkInDate.equals(searchCheckInDate)) {
                        match = false;
                    }
                } catch (ParseException e) {
                    // Invalid date format, ignore it
                }
            }

            // If all criteria match, add the reservation to search results
            if (match) {
                searchResults.add(reservation);
            }
        }

        return searchResults;
    }

    public Reservation getSelectedReservationByID(int reservationID) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationID() == reservationID) {
                return reservation;
            }
        }
        return null; // Return null if the reservation with the specified ID is not found
    }

    public List<Reservation> searchPastReservations(String guestUsernamePast, String checkInDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Disallow lenient parsing

        // Parse the provided check-in date string
        Date checkInDate = null;
        try {
            checkInDate = dateFormat.parse(checkInDateStr);
        } catch (ParseException e) {
            // Handle the invalid date format here or return an empty list
            return Collections.emptyList();
        }

        // Create a list to store matching past reservations
        List<Reservation> matchingPastReservations = new ArrayList<>();

        // Iterate through all past reservations and find matching ones
        for (Reservation reservation : pastReservations) {
            if (reservation.getCheckInDate().before(checkInDate) && reservation.getGuest().getUsername().equals(guestUsernamePast)) {
                matchingPastReservations.add(reservation);
            }
        }

        return matchingPastReservations;
    }


    public List<Reservation> searchPastReservationsByGuest(String guestUsernamePast) {
        // Create a list to store matching past reservations
        List<Reservation> matchingPastReservations = new ArrayList<>();

        // Iterate through all past reservations and find matching ones by guest username
        for (Reservation reservation : pastReservations) {
            if (reservation.getGuest().getUsername().equals(guestUsernamePast)) {
                matchingPastReservations.add(reservation);
            }
        }

        return matchingPastReservations;
    }



    public List<Reservation> searchPastReservationsByDate(Date checkInDate) {
        // Create a list to store matching past reservations
        List<Reservation> matchingPastReservations = new ArrayList<>();

        // Iterate through all past reservations and find matching ones by check-in date
        for (Reservation reservation : pastReservations) {
            if (reservation.getCheckInDate().before(checkInDate)) {
                matchingPastReservations.add(reservation);
            }
        }

        return matchingPastReservations;
    }

}
