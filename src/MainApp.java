import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainApp {
    private static Hotel hotel = Hotel.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Store guest and manager credentials in HashMap
    private static List<User> guestCredentials = new ArrayList<>();
    private static List<User> managerCredentials = new ArrayList<>();
    private static List<Staff> staffMembers = new ArrayList<>();

    public static void main(String[] args) {
        Room room = new Room(101, "Single", 100.0);
        hotel.addRoom(room);

        guestCredentials.add(new User("guest1", "password1"));
        managerCredentials.add(new User("manager1", "password1"));
        staffMembers.add(new Staff("John", 1));
        staffMembers.add(new Staff("Jane", 2));

        while (true) {
            System.out.println("\nHotel Management System:");
            System.out.println("1. Login as Manager");
            System.out.println("2. Login as Guest");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loggedInUser = managerLogin();
                    if (loggedInUser != null && loggedInUser instanceof Manager) {
                        managerMenu();
                    } else {
                        System.out.println("Invalid manager credentials.");
                    }
                    break;
                case "2":
                    loggedInUser = guestLogin();
                    if (loggedInUser != null && loggedInUser instanceof Guest) {
                        guestMenu();
                    } else {
                        System.out.println("Invalid guest credentials.");
                    }
                    break;
                case "3":
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a correct option.");
            }
        }
    }
    private static User managerLogin() {
        System.out.print("Enter Manager Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : managerCredentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return new Manager(username, password);
            }
        }
        return null;
    }

    private static User guestLogin() {
        System.out.print("Enter Guest Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : guestCredentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return new Guest(username, password);
            }
        }
        return null;
    }

    private static void managerMenu() {
        while (true) {
            System.out.println("\nManager Menu:");
            System.out.println("1. Add/Update Room");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Check-In Guest");
            System.out.println("4. Check-Out Guest");
            System.out.println("5. View Reservations");
            System.out.println("6. View Past Reservations");
            System.out.println("7. Calculate Total Revenue");
            System.out.println("8. Set Seasonal Pricing");
            System.out.println("9. Assign Cleaning Staff to Room");
            System.out.println("10. Generate Occupancy Report");
            System.out.println("11. Log Complaint");
            System.out.println("12. View Unresolved Complaints");
            System.out.println("13. Resolve Complaints");
            System.out.println("14. View Resolved Complaints");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addOrUpdateRoom();
                    break;
                case "2":
                    viewAvailableRooms();
                    break;
                case "3":
                    checkInGuest();
                    break;
                case "4":
                    checkOutGuest();
                    break;
                case "5":
                    viewReservations();
                    break;
                case "6":
                    viewPastReservations();
                    break;
                case "7":
                    displayTotalRevenue();
                    break;
                case "8":
                    setSeasonalPricing();
                    break;
                case "9":
                    assignCleaningStaffToRoom();
                    break;
                case "10":
                    generateOccupancyReport();
                    break;
                case "11":
                    logComplaint();
                    break;
                case "12":
                    viewUnresolvedComplaints();
                    break;
                case "13":
                    resolveComplaint();
                    break;
                case "14":
                    viewResolvedComplaints();
                    break;
                case "15":
                    System.out.println("Exiting manager menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a correct option.");
            }
        }
    }
    private static void displayTotalRevenue() {
        double revenue = hotel.calculateTotalRevenue();
        System.out.printf("Total revenue from room bookings: $%.2f\n", revenue);
    }
    private static void addOrUpdateRoom() {
        System.out.print("Enter Room Number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Room Type: ");
        String roomType = scanner.nextLine();
        System.out.print("Enter Room Price: ");
        double roomPrice = Double.parseDouble(scanner.nextLine());
        hotel.addOrUpdateRoom(roomNumber, roomType, roomPrice);

    }
    private static void viewAvailableRooms() {
        System.out.print("Enter Check-In Date (yyyy-MM-dd): ");
        String checkInDateStr = scanner.nextLine();
        Date checkInDate;
        try {
            checkInDate = dateFormat.parse(checkInDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        System.out.print("Enter Check-Out Date (yyyy-MM-dd): ");
        String checkOutDateStr = scanner.nextLine();
        Date checkOutDate;
        try {
            checkOutDate = dateFormat.parse(checkOutDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        List<Room> availableRooms = hotel.getAvailableRooms(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms for the given date range.");
        } else {
            System.out.println("Available Rooms:");
            for (Room room : availableRooms) {
                System.out.println(room);
            }
        }
    }
    private static void bookRoom() {
        System.out.print("Enter Room Number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Check-In Date (yyyy-MM-dd): ");
        String checkInDateStr = scanner.nextLine();
        Date checkInDate;
        try {
            checkInDate = dateFormat.parse(checkInDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        System.out.print("Enter Check-Out Date (yyyy-MM-dd): ");
        String checkOutDateStr = scanner.nextLine();
        Date checkOutDate;
        try {
            checkOutDate = dateFormat.parse(checkOutDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        try {
            hotel.bookRoom(roomNumber, checkInDate, checkOutDate, (Guest) loggedInUser);
        } catch (Exception e) {
            // Handle the exception appropriately
            System.out.println("Error while booking the room: " + e.getMessage());
        }
    }
    private static void viewReservations() {
        if (loggedInUser instanceof Guest) {
            List<Reservation> reservations = hotel.getReservationsForGuest((Guest) loggedInUser);
            if (reservations.isEmpty()) {
                System.out.println("You have no current reservations.");
            } else {
                System.out.println("Your Current Reservations:");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } else if (loggedInUser instanceof Manager) {
            List<Reservation> reservations = hotel.getAllReservations();
            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                System.out.println("All Reservations:");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        }
    }
    private static void guestMenu() {
        while (true) {
            System.out.println("\nGuest Menu:");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. View Reservations");
            System.out.println("4. View Past Reservations");
            System.out.println("5. Request Additional Service");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAvailableRooms();
                    break;
                case "2":
                    bookRoom();
                    break;
                case "3":
                    viewReservations();
                    break;
                case "4":
                    viewPastReservations();
                    break;
                case "5":
                    requestAdditionalService();
                    break;
                case "6":
                    System.out.println("Exiting guest menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a correct option.");
            }
        }
    }
    private static void generateOccupancyReport() {
        if (loggedInUser instanceof Manager) {
            System.out.print("Enter Start Date (yyyy-MM-dd): ");
            String startDateStr = scanner.nextLine();
            Date startDate;
            try {
                startDate = dateFormat.parse(startDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                return;
            }

            System.out.print("Enter End Date (yyyy-MM-dd): ");
            String endDateStr = scanner.nextLine();
            Date endDate;
            try {
                endDate = dateFormat.parse(endDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                return;
            }

            // Generate and display the occupancy report
            Manager manager = (Manager) loggedInUser;
            manager.generateAndDisplayOccupancyReport(startDate, endDate);
        } else {
            System.out.println("Only managers can generate occupancy reports.");
        }
    }
    private static void requestAdditionalService() {
        if (loggedInUser instanceof Guest) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the type of service you wish to request: ");
            String serviceType = scanner.nextLine();

            System.out.print("Enter the date for the service request (YYYY-MM-DD): ");
            String serviceDateInput = scanner.nextLine();
            Date serviceDate;
            try {
                serviceDate = new SimpleDateFormat("yyyy-MM-dd").parse(serviceDateInput);
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
                return;
            }

            Guest requestingGuest = (Guest) loggedInUser;

            if (hotel.hasActiveReservation(requestingGuest, serviceDate)) {
                ServiceRequest serviceRequest = new ServiceRequest(requestingGuest, serviceType, serviceDate);
                hotel.logServiceRequest(serviceRequest);
                System.out.println("Service request for " + serviceType + " has been submitted.");
            } else {
                System.out.println("No active reservation found for the specified date.");
            }
        } else {
            System.out.println("Only guests can request additional services.");
        }
    }
    private static void checkInGuest() {
        System.out.print("Enter Room Number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Guest Username for reservation: ");
        String guestUsername = scanner.nextLine();

        System.out.print("Enter Check-In Date of reservation (yyyy-MM-dd): ");
        String targetCheckInDateStr = scanner.nextLine();
        Date targetCheckInDate;
        try {
            targetCheckInDate = dateFormat.parse(targetCheckInDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        try {
            hotel.checkInGuest(roomNumber, guestUsername, targetCheckInDate);
        } catch (Exception e) {
            // Handle the exception appropriately
            System.out.println("Error while checking in guest: " + e.getMessage());
        }
    }
    private static void checkOutGuest() {
        System.out.print("Enter Room Number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Guest Username for reservation: ");
        String guestUsername = scanner.nextLine();

        System.out.print("Enter Check-In Date of reservation you want to check out from (yyyy-MM-dd): ");
        String targetCheckInDateStr = scanner.nextLine();
        Date targetCheckInDate;
        try {
            targetCheckInDate = dateFormat.parse(targetCheckInDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        try {
            hotel.checkOutGuest(roomNumber, guestUsername, targetCheckInDate);
        } catch (Exception e) {
            // Handle the exception appropriately
            System.out.println("Error while checking out guest: " + e.getMessage());
        }
    }
    private static void viewPastReservations() {
        if (loggedInUser instanceof Manager) {
            List<Reservation> pastRes = hotel.getAllPastReservations();
            if (pastRes.isEmpty()) {
                System.out.println("No past reservations found.");
            } else {
                System.out.println("Past Reservations:");
                for (Reservation reservation : pastRes) {
                    System.out.println(reservation);
                }
            }
        } else if (loggedInUser instanceof Guest) {
            List<Reservation> pastRes = hotel.getPastReservationsForGuest((Guest) loggedInUser);
            if (pastRes.isEmpty()) {
                System.out.println("You have no past reservations.");
            } else {
                System.out.println("Your Past Reservations:");
                for (Reservation reservation : pastRes) {
                    System.out.println(reservation);
                }
            }
        }
    }
    private static void setSeasonalPricing() {
        System.out.print("Enter Season Start Date (yyyy-MM-dd): ");
        Date startDate = getDateInput();
        System.out.print("Enter Season End Date (yyyy-MM-dd): ");
        Date endDate = getDateInput();
        System.out.print("Enter Price Multiplier (e.g., 1.5 for 50% increase): ");
        double multiplier = Double.parseDouble(scanner.nextLine());

        hotel.setSeasonalPrice(startDate, endDate, multiplier);
        System.out.println("Seasonal pricing set successfully.");
    }
    private static Date getDateInput() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        while (date == null) {
            try {
                String input = scanner.nextLine();
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
        return date;
    }
    private static void assignCleaningStaffToRoom() {
        System.out.print("Enter Staff ID: ");
        int staffId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Room Number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        Staff staff = findStaffById(staffId);
        Room room = hotel.findRoomByNumber(roomNumber);

        if (staff == null || room == null) {
            System.out.println("Invalid staff ID or room number.");
            return;
        }

        try {
            hotel.assignStaffToRoom(staff, room);
            System.out.println("Staff assigned to room successfully!");
        } catch (Exception e) {
            System.out.println("Error assigning staff: " + e.getMessage());
        }
    }

    // This function will find a staff member by ID.
    private static Staff findStaffById(int id) {
        // Assuming a list of staff members exists. Modify accordingly.
        for (Staff s : staffMembers) {
            if (s.getStaffId() == id) {
                return s;
            }
        }
        return null;
    }
    private static void logComplaint() {
        System.out.print("Enter Guest Username: ");
        String guestUsername = scanner.nextLine();
        Guest guest = findGuestByUsername(guestUsername);

        if (guest != null) {
            System.out.print("Enter Complaint Description: ");
            String complaintDescription = scanner.nextLine();
            hotel.logComplaint(guest, complaintDescription);
            System.out.println("Complaint logged successfully.");
        } else {
            System.out.println("Guest not found.");
        }
    }
    private static Guest findGuestByUsername(String username) {
        for (User user : guestCredentials) {
            if (user instanceof Guest && user.getUsername().equals(username)) {
                return (Guest) user;
            }
        }
        return null;
    }
    private static void viewUnresolvedComplaints() {
        List<Complaint> unresolvedComplaints = hotel.getUnresolvedComplaints();
        if (unresolvedComplaints.isEmpty()) {
            System.out.println("No unresolved complaints.");
        } else {
            System.out.println("Unresolved Complaints:");
            for (Complaint complaint : unresolvedComplaints) {
                System.out.println(complaint);
            }
        }
    }

    private static void resolveComplaint() {
        System.out.print("Enter Complaint ID to resolve: ");
        int complaintId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Resolution: ");
        String resolution = scanner.nextLine();

        hotel.resolveComplaint(complaintId, resolution);
    }

    private static void viewResolvedComplaints() {
        List<Complaint> resolvedComplaints = new ArrayList<>();
        for (Complaint complaint : hotel.getComplaints()) {
            if (complaint.isResolved()) {
                resolvedComplaints.add(complaint);
            }
        }

        if (resolvedComplaints.isEmpty()) {
            System.out.println("No resolved complaints.");
        } else {
            System.out.println("Resolved Complaints:");
            for (Complaint complaint : resolvedComplaints) {
                System.out.println(complaint);
            }
        }
    }
}
