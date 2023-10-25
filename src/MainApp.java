import java.awt.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainApp {

    static void addActionToButton(JButton button, final JPanel cardPanel, final CardLayout cardLayout, final String cardName) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, cardName);
            }
        });
    }
    private static Hotel hotel = Hotel.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Store guest and manager credentials in HashMap
    private static List<User> guestCredentials = new ArrayList<>();
    private static List<User> managerCredentials = new ArrayList<>();
    private static List<Staff> staffMembers = new ArrayList<>();

    public static void main(String[] args) {
        guestCredentials.add(new User("guest1", "password1"));
        managerCredentials.add(new User("manager1", "password1"));
        JFrame frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));

        final JPanel cardPanel = new JPanel();
        final CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);


// Welcome Card
        JPanel welcomeCard = new JPanel();
        welcomeCard.setLayout(new BoxLayout(welcomeCard, BoxLayout.PAGE_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton managerButton = new JButton("Login as Manager");
        managerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton guestButton = new JButton("Login as Guest");
        guestButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeCard.add(Box.createVerticalGlue());
        welcomeCard.add(welcomeLabel);
        welcomeCard.add(Box.createVerticalStrut(10));
        welcomeCard.add(managerButton);
        welcomeCard.add(Box.createVerticalStrut(10));
        welcomeCard.add(guestButton);
        welcomeCard.add(Box.createVerticalStrut(10));
        welcomeCard.add(exitButton);
        welcomeCard.add(Box.createVerticalGlue());



        // Manager Login Card
        JPanel managerLoginCard = new JPanel();
        JTextField managerUsername = new JTextField(20);
        JPasswordField managerPassword = new JPasswordField(20);
        JButton managerSubmit = new JButton("Submit");
        managerLoginCard.add(new JLabel("Manager Username: "));
        managerLoginCard.add(managerUsername);
        managerLoginCard.add(new JLabel("Manager Password: "));
        managerLoginCard.add(managerPassword);
        managerLoginCard.add(managerSubmit);

        // Guest Login Card
        JPanel guestLoginCard = new JPanel();
        JTextField guestUsername = new JTextField(20);
        JPasswordField guestPassword = new JPasswordField(20);
        JButton guestSubmit = new JButton("Submit");
        guestLoginCard.add(new JLabel("Guest Username: "));
        guestLoginCard.add(guestUsername);
        guestLoginCard.add(new JLabel("Guest Password: "));
        guestLoginCard.add(guestPassword);
        guestLoginCard.add(guestSubmit);

        // Manager Menu Card
        JPanel managerCard = new JPanel();

        managerCard.setLayout(new GridLayout(16, 1));
        String[] managerOptions = {
                "Add/Update Room", "View Available Rooms", "Check-In Guest", "Check-Out Guest",
                "View Reservations", "View Past Reservations", "Calculate Total Revenue",
                "Set Seasonal Pricing", "Assign Cleaning Staff", "Generate Occupancy Report",
                "Log Complaint", "View Unresolved Complaints", "Resolve Complaints", "View Resolved Complaints"
        };



// Create the card for Add/Update Room
        JPanel addOrUpdateRoomCard = new JPanel();
        JTextField roomNumberField = new JTextField(10);
        JTextField roomTypeField = new JTextField(10);
        JTextField roomPriceField = new JTextField(10);
        JButton submitRoomButton = new JButton("Submit");
        addOrUpdateRoomCard.add(new JLabel("Room Number:"));
        addOrUpdateRoomCard.add(roomNumberField);
        addOrUpdateRoomCard.add(new JLabel("Room Type:"));
        addOrUpdateRoomCard.add(roomTypeField);
        addOrUpdateRoomCard.add(new JLabel("Room Price:"));
        addOrUpdateRoomCard.add(roomPriceField);
        addOrUpdateRoomCard.add(submitRoomButton);
        JButton backButton = new JButton("Back");
        addOrUpdateRoomCard.add(backButton);

// Create the card for viewAvailableRooms
        JPanel viewAvailableRoomsCard = new JPanel();
        viewAvailableRoomsCard.setLayout(new BorderLayout());
        JLabel checkInLabel = new JLabel("Enter Check-In Date (yyyy-MM-dd):");
        JTextField checkInField = new JTextField(20);
        JLabel checkOutLabel = new JLabel("Enter Check-Out Date (yyyy-MM-dd):");
        JTextField checkOutField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JTextArea roomListArea = new JTextArea();
        roomListArea.setEditable(false);
        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        JButton backButton_viewAvailableRooms = new JButton("Back");
        inputPanel.add(checkInLabel);
        inputPanel.add(checkInField);
        inputPanel.add(checkOutLabel);
        inputPanel.add(checkOutField);
        inputPanel.add(submitButton);
        inputPanel.add(backButton_viewAvailableRooms);
        viewAvailableRoomsCard.add(inputPanel, BorderLayout.NORTH);
        viewAvailableRoomsCard.add(new JScrollPane(roomListArea), BorderLayout.CENTER);

        JPanel navPanel2 = new JPanel();
        navPanel2.add(backButton_viewAvailableRooms);
        navPanel2.add(submitButton);
        viewAvailableRoomsCard.add(navPanel2, BorderLayout.SOUTH);

// Create the card for checkInGuestCard
        JPanel checkInGuestCard = new JPanel(new BorderLayout());

// Create Input Fields and Buttons
        JTextField guestUsernameField = new JTextField(15);
        JTextField roomNumberField2 = new JTextField(5);
        JFormattedTextField checkInDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        checkInDateField.setPreferredSize(new Dimension(100, 20));
        JButton searchButton = new JButton("Search");
        JButton checkInButton = new JButton("Check In");
        JButton backButton_checkInGuest = new JButton("Back");
        checkInButton.setEnabled(false);  // Disabled by default



// Panel for Input Fields
        JPanel inputPanel2 = new JPanel();
        inputPanel2.add(new JLabel("Guest Username: "));
        inputPanel2.add(guestUsernameField);
        inputPanel2.add(new JLabel("Room Number: "));
        inputPanel2.add(roomNumberField2);
        inputPanel2.add(new JLabel("Check-In Date: "));
        checkInDateField.setText("yyyy-MM-dd");
        inputPanel2.add(checkInDateField);
        inputPanel2.add(searchButton);
        checkInGuestCard.add(inputPanel2, BorderLayout.NORTH);

// Table to Display Results
        String[] columnNames = {"Room Number", "Guest Username", "Check-In Date", "Check-Out Date"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        checkInGuestCard.add(scrollPane, BorderLayout.CENTER);

        // Add Check-In Button
        // Add the back button to a navigation panel
        JPanel navPanel = new JPanel();
        navPanel.add(backButton_checkInGuest);
        navPanel.add(checkInButton);
        checkInGuestCard.add(navPanel, BorderLayout.SOUTH);

// Handle the Search Button Click
        searchButton.addActionListener(e -> {
            if (validateInputFields(guestUsernameField, roomNumberField2, checkInDateField)) {
                // Validate the date is not in the past
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date checkInDate = dateFormat.parse(checkInDateField.getText());
                    Date currentDate = new Date();
                    if (checkInDate.before(currentDate)) {
                        JOptionPane.showMessageDialog(null, "Date is in the past.");
                        return;
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
                    return;
                }

                // Call checkInGuest_List from hotel.java and update the table with the result
                // ... your existing code to call checkInGuest_List and update the table

                checkInButton.setEnabled(true);  // Enable the Check In button if search was successful
            } else {
                JOptionPane.showMessageDialog(null, "Please enter valid information.");
                checkInButton.setEnabled(false);
            }
        });

// Handle the Check-In Button Click
        checkInButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                // Your existing code to call the check-in method in hotel.java
            } else {
                JOptionPane.showMessageDialog(null, "No reservation selected for check-in.");
            }
        });


// Create the card for checkOutGuestCard
        JPanel checkOutGuestCard = new JPanel();

        // Create the card for viewReservationsCard
        JPanel viewReservationsCard = new JPanel();

        // Create the card for viewPastReservationsCard
        JPanel viewPastReservationsCard = new JPanel();

        // Create the card for calculateTotalRevenueCard
        JPanel calculateTotalRevenueCard = new JPanel();

        // Create the card for setSeasonalPricingCard
        JPanel setSeasonalPricingCard = new JPanel();

        // Create the card for generateOccupancyReportCard
        JPanel generateOccupancyReportCard = new JPanel();

        // Create the card for logComplaintCard
        JPanel logComplaintCard = new JPanel();

        // Create the card for viewUnresolvedComplaintsCard
        JPanel viewUnresolvedComplaintsCard = new JPanel();

        // Create the card for resolveComplaintsCard
        JPanel resolveComplaintsCard = new JPanel();

        // Create the card for viewResolvedComplaintsCard
        JPanel viewResolvedComplaintsCard = new JPanel();

        for (String option : managerOptions) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                String buttonText = ((JButton) e.getSource()).getText();
                switch (buttonText) {
                    case "Add/Update Room":
                        cardLayout.show(cardPanel, "addOrUpdateRoomCard");
                        break;
                    case "View Available Rooms":
                        cardLayout.show(cardPanel, "viewAvailableRoomsCard");
                        break;
                    case "Check-In Guest":
                        cardLayout.show(cardPanel, "checkInGuestCard");
                        break;
                    case "Check-Out Guest":
                        cardLayout.show(cardPanel, "checkOutGuestCard");
                        break;
                    case "View Reservations":
                        cardLayout.show(cardPanel, "viewReservationsCard");
                        break;
                    case "View Past Reservations":
                        cardLayout.show(cardPanel, "viewPastReservationsCard");
                        break;
                    case "Calculate Total Revenue":
                        cardLayout.show(cardPanel, "calculateTotalRevenueCard");
                        break;
                    case "Set Seasonal Pricing":
                        cardLayout.show(cardPanel, "setSeasonalPricingCard");
                        break;
                    case "Assign Cleaning Staff":
                        cardLayout.show(cardPanel, "assignCleaningStaffCard");
                        break;
                    case "Generate Occupancy Report":
                        cardLayout.show(cardPanel, "generateOccupancyReportCard");
                        break;
                    case "Log Complaint":
                        cardLayout.show(cardPanel, "logComplaintCard");
                        break;
                    case "View Unresolved Complaints":
                        cardLayout.show(cardPanel, "viewUnresolvedComplaintsCard");
                        break;
                    case "Resolve Complaints":
                        cardLayout.show(cardPanel, "resolveComplaintsCard");
                        break;
                    case "View Resolved Complaints":
                        cardLayout.show(cardPanel, "viewResolvedComplaintsCard");
                        break;
                    default:
                        System.out.println("No matching action for this button");
                        break;
                }
            });
            managerCard.add(button);
        }

        JButton logoutButtonM = new JButton("Logout");
        managerCard.add(logoutButtonM);

        // Guest Menu Card
        JPanel guestCard = new JPanel();
        guestCard.setLayout(new GridLayout(7, 1));
        String[] guestOptions = {"View Available Rooms", "Book Room", "View Reservations",
                "View Past Reservations", "Request Additional Service"};

        // Create the card for Guest View Available Rooms
        JPanel Guest_viewAvailableRoomsCard = new JPanel();
        Guest_viewAvailableRoomsCard.setLayout(new BorderLayout());
        JLabel Guest_checkInLabel = new JLabel("Enter Check-In Date (yyyy-MM-dd):");
        JTextField Guest_checkInField = new JTextField(20);
        JLabel Guest_checkOutLabel = new JLabel("Enter Check-Out Date (yyyy-MM-dd):");
        JTextField Guest_checkOutField = new JTextField(20);
        JButton Guest_submitButton = new JButton("Submit");
        JTextArea Guest_roomListArea = new JTextArea();
        Guest_roomListArea.setEditable(false);
        JPanel Guest_inputPanel = new JPanel(new GridLayout(4, 1));
        JButton Guest_backButton_viewAvailableRooms = new JButton("Back");
        Guest_inputPanel.add(Guest_checkInLabel);
        Guest_inputPanel.add(Guest_checkInField);
        Guest_inputPanel.add(Guest_checkOutLabel);
        Guest_inputPanel.add(Guest_checkOutField);
        Guest_inputPanel.add(Guest_submitButton);
        Guest_inputPanel.add(Guest_backButton_viewAvailableRooms);
        Guest_viewAvailableRoomsCard.add(Guest_inputPanel, BorderLayout.NORTH);
        Guest_viewAvailableRoomsCard.add(new JScrollPane(Guest_roomListArea), BorderLayout.CENTER);

        JPanel Guest_navPanel2 = new JPanel();
        Guest_navPanel2.add(Guest_backButton_viewAvailableRooms);
        Guest_navPanel2.add(Guest_submitButton);
        Guest_viewAvailableRoomsCard.add(Guest_navPanel2, BorderLayout.SOUTH);

        // Create the card for BookRoom
        JPanel Guest_BookRoom = new JPanel();

        // Create the card for ViewReservations
        JPanel Guest_ViewReservations = new JPanel();

        // Create the card for ViewPastReservations
        JPanel Guest_ViewPastReservations = new JPanel();

        // Create the card for RequestAdditionalService
        JPanel Guest_RequestAdditionalService = new JPanel();

        for (String option : guestOptions) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                String buttonText = ((JButton) e.getSource()).getText();
                switch (buttonText) {
                    case "View Available Rooms":
                        cardLayout.show(cardPanel, "Guest_viewAvailableRoomsCard");
                        break;
                    case "Book Room":
                        cardLayout.show(cardPanel, "Guest_BookRoom");
                        break;
                    case "View Reservations":
                        cardLayout.show(cardPanel, "Guest_ViewReservations");
                        break;
                    case "View Past Reservations":
                        cardLayout.show(cardPanel, "Guest_ViewPastReservations");
                        break;
                    case "Request Additional Service":
                        cardLayout.show(cardPanel, "Guest_RequestAdditionalService");
                        break;
                    default:
                        System.out.println("No matching action for this button");
                        break;
                }
            });
            guestCard.add(button);
        }
        JButton logoutButtonG = new JButton("Logout");
        guestCard.add(logoutButtonG);

        // Adding cards to the panel
        cardPanel.add(welcomeCard, "WelcomeCard");
        cardPanel.add(managerLoginCard, "ManagerLoginCard");
        cardPanel.add(guestLoginCard, "GuestLoginCard");
        cardPanel.add(managerCard, "ManagerCard");
        cardPanel.add(guestCard, "GuestCard");
        cardPanel.add(addOrUpdateRoomCard, "addOrUpdateRoomCard");
        cardPanel.add(viewAvailableRoomsCard, "viewAvailableRoomsCard");
        cardPanel.add(checkInGuestCard, "checkInGuestCard");
        cardPanel.add(Guest_viewAvailableRoomsCard, "Guest_viewAvailableRoomsCard");

        // Button actions
        addActionToButton(managerButton, cardPanel, cardLayout, "ManagerLoginCard");
        addActionToButton(guestButton, cardPanel, cardLayout, "GuestLoginCard");
        addActionToButton(exitButton, cardPanel, cardLayout, "Exit");

        logoutButtonM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loggedInUser = null;
                managerUsername.setText("");
                managerPassword.setText("");
                cardLayout.show(cardPanel, "WelcomeCard");
            }
        });
        logoutButtonG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loggedInUser = null;
                guestUsername.setText("");
                guestPassword.setText("");
                cardLayout.show(cardPanel, "WelcomeCard");
            }
        });

        // Login validation for manager
        managerSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = managerUsername.getText();
                String password = new String(managerPassword.getPassword());
                loggedInUser = managerLogin(username, password);
                if (loggedInUser instanceof Manager) {
                    cardLayout.show(cardPanel, "ManagerCard");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid manager credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        submitButton.addActionListener(e -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date checkInDate, checkOutDate;
                    Date currentDate = new Date();
            try {
                checkInDate = dateFormat.parse(checkInField.getText());
                if (checkInDate.before(currentDate)) {
                    roomListArea.setText("Check-In date cannot be in the past.");
                    return;
                }
            } catch (ParseException ex) {
                roomListArea.setText("Invalid Check-In date format. Use yyyy-MM-dd.");
                return;
            }
            try {
                checkOutDate = dateFormat.parse(checkOutField.getText());
                if (checkOutDate.before(currentDate) || checkOutDate.before(checkInDate)) {
                    roomListArea.setText("Check-Out date cannot be in the past or before Check-In date.");
                    return;
                }
            } catch (ParseException ex) {
                roomListArea.setText("Invalid Check-Out date format. Use yyyy-MM-dd.");
                return;
            }


            List<Room> availableRooms = hotel.getAvailableRooms(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                roomListArea.setText("No available rooms for the given date range.");
            } else {
                StringBuilder sb = new StringBuilder("Available Rooms:\n");
                for (Room room : availableRooms) {
                    sb.append(room.toString()).append("\n"); // assuming Room class has a meaningful toString method
                }
                roomListArea.setText(sb.toString());
            }

        });
        Guest_submitButton.addActionListener(e -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate, checkOutDate;
            Date currentDate = new Date();
            try {
                checkInDate = dateFormat.parse(Guest_checkInField.getText());
                if (checkInDate.before(currentDate)) {
                    Guest_roomListArea.setText("Check-In date cannot be in the past.");
                    return;
                }
            } catch (ParseException ex) {
                Guest_roomListArea.setText("Invalid Check-In date format. Use yyyy-MM-dd.");
                return;
            }
            try {
                checkOutDate = dateFormat.parse(Guest_checkOutField.getText());
                if (checkOutDate.before(currentDate) || checkOutDate.before(checkInDate)) {
                    Guest_roomListArea.setText("Check-Out date cannot be in the past or before Check-In date.");
                    return;
                }
            } catch (ParseException ex) {
                Guest_roomListArea.setText("Invalid Check-Out date format. Use yyyy-MM-dd.");
                return;
            }


            List<Room> availableRooms = hotel.getAvailableRooms(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                Guest_roomListArea.setText("No available rooms for the given date range.");
            } else {
                StringBuilder sb = new StringBuilder("Available Rooms:\n");
                for (Room room : availableRooms) {
                    sb.append(room.toString()).append("\n"); // assuming Room class has a meaningful toString method
                }
                Guest_roomListArea.setText(sb.toString());
            }

        });

            // Login validation for guest
        guestSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = guestUsername.getText();
                String password = new String(guestPassword.getPassword());
                loggedInUser = guestLogin(username, password);
                if (loggedInUser instanceof Guest) {
                    cardLayout.show(cardPanel, "GuestCard");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid guest credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add action listener to submit button
        submitRoomButton.addActionListener(e -> {

            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                String roomType = roomTypeField.getText();
                double roomPrice = Double.parseDouble(roomPriceField.getText());
                hotel.addOrUpdateRoom(roomNumber, roomType, roomPrice);
            } catch (NumberFormatException Numbe) {
                // Handle the exception. For example, show a dialog to inform the user that they've entered invalid data
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for room number and room price.");
            }
        });

        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "ManagerCard");
        });
        backButton_viewAvailableRooms.addActionListener(e -> {
            roomListArea.setText("");
            checkInField.setText("");
            checkOutField.setText("");
            cardLayout.show(cardPanel, "ManagerCard");
        });
        backButton_checkInGuest.addActionListener(e -> {
            guestUsernameField.setText("");
            roomNumberField2.setText("");
            checkInDateField.setText("");
            cardLayout.show(cardPanel, "ManagerCard");
        });

        Guest_backButton_viewAvailableRooms.addActionListener(e -> {
            Guest_checkInField.setText("");
            Guest_checkOutField.setText("");
            Guest_roomListArea.setText("");
            cardLayout.show(cardPanel, "GuestCard");
        });

        // Exit the application
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(cardPanel);
        frame.pack();
        frame.setVisible(true);

    }

    // Helper Method to Validate Input Fields
    static boolean validateInputFields(JTextField guestUsernameField, JTextField roomNumberField, JFormattedTextField checkInDateField) {
        String guestUsername = guestUsernameField.getText();
        String roomNumber = roomNumberField.getText();
        String checkInDate = checkInDateField.getText();

        // Validate guest username
        if (guestUsername == null || guestUsername.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Guest Username cannot be empty.");
            return false;
        }

        // Validate room number
        if (roomNumber == null || roomNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Room Number cannot be empty.");
            return false;
        }

        try {
            Integer.parseInt(roomNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Room Number must be an integer.");
            return false;
        }

        // Validate check-in and check-out dates
        if (checkInDate == null || checkInDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Check-In Date cannot be empty.");
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedCheckInDate = dateFormat.parse(checkInDate);
            Date currentDate = new Date();
            if (parsedCheckInDate.before(currentDate)) {
                JOptionPane.showMessageDialog(null, "Date cannot be in the past.");
                return false;
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
            return false;
        }

        return true;
    }

    private static User managerLogin(String username, String password) {
        for (User user : managerCredentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return new Manager(username, password);
            }
        }
        return null;
    }

    private static User guestLogin(String username, String password) {

        for (User user : guestCredentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return new Guest(username, password);
            }
        }
        return null;
    }

    private static void displayTotalRevenue() {
        double revenue = hotel.calculateTotalRevenue();
        System.out.printf("Total revenue from room bookings: $%.2f\n", revenue);
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
