import java.awt.*;
import java.math.BigDecimal;
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
        hotel.addOrUpdateRoom(109,"Single", BigDecimal.valueOf(100.0));
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
        JPanel managerLoginCard = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5); // Add padding

        JTextField managerUsername = new JTextField(15);
        managerUsername.setPreferredSize(new Dimension(200, 30));
        JPasswordField managerPassword = new JPasswordField(15);
        managerPassword.setPreferredSize(new Dimension(200, 30));
        JButton managerSubmit = new JButton("Submit");
        JButton managerBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 0;
        managerLoginCard.add(new JLabel("Manager Username: "), constraints);

        constraints.gridx = 1;
        managerLoginCard.add(managerUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        managerLoginCard.add(new JLabel("Manager Password: "), constraints);

        constraints.gridx = 1;
        managerLoginCard.add(managerPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        managerLoginCard.add(managerBackButton, constraints);

        constraints.gridx = 1;
        managerLoginCard.add(managerSubmit, constraints);

// Guest Login Card
        JPanel guestLoginCard = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JTextField guestUsername = new JTextField(15);
        guestUsername.setPreferredSize(new Dimension(200, 30));
        JPasswordField guestPassword = new JPasswordField(15);
        guestPassword.setPreferredSize(new Dimension(200, 30));
        JButton guestSubmit = new JButton("Submit");
        JButton guestBackButton = new JButton("Back");

        constraints.gridx = 0;
        constraints.gridy = 0;
        guestLoginCard.add(new JLabel("Guest Username: "), constraints);

        constraints.gridx = 1;
        guestLoginCard.add(guestUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        guestLoginCard.add(new JLabel("Guest Password: "), constraints);

        constraints.gridx = 1;
        guestLoginCard.add(guestPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        guestLoginCard.add(guestBackButton, constraints);

        constraints.gridx = 1;
        guestLoginCard.add(guestSubmit, constraints);

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
        JButton backButton_addOrUpdateRoomCard = new JButton("Back");
        addOrUpdateRoomCard.add(backButton_addOrUpdateRoomCard);

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
        checkInButton.setEnabled(false); // Disabled by default

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
        String[] columnNames = {"Reservation ID", "Room Number", "Guest Username", "Check-In Date", "Check-Out Date"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames); // Update the column names
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
            // Clear the table before populating with search results
            model.setRowCount(0);

            // Get the search criteria
            String guestUsername_Checkin = guestUsernameField.getText();
            String roomNumberStr = roomNumberField2.getText();
            String checkInDateStr = checkInDateField.getText();

            // Validate the date is not in the past
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date checkInDate = dateFormat.parse(checkInDateStr);
                Date currentDate = new Date();
                if (checkInDate.before(currentDate)) {
                    JOptionPane.showMessageDialog(null, "Date is in the past.");
                    return;
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
                return;
            }

            // Call a method to search for reservations based on the criteria
            List<Reservation> searchResults = hotel.searchReservations(guestUsername_Checkin, roomNumberStr, checkInDateStr);

            // Populate the table with search results
            for (Reservation reservation : searchResults) {
                model.addRow(new Object[]{
                        reservation.getReservationID(), // Add reservation ID
                        reservation.getRoom().getRoomNumber(),
                        reservation.getGuest().getUsername(),
                        reservation.getCheckInDate(),
                        reservation.getCheckOutDate()
                });
            }
            checkInButton.setEnabled(!searchResults.isEmpty()); // Enable the Check In button if there are results
        });

// Handle the Check-In Button Click
        checkInButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int reservationID = (int) table.getValueAt(row, 0); // Assuming reservation ID is in the first column
                Reservation selectedReservation = hotel.getSelectedReservationByID(reservationID);
                try {
                    hotel.checkInGuest(selectedReservation.getRoom().getRoomNumber(), selectedReservation.getGuest().getUsername(), selectedReservation.getCheckInDate());
                    model.removeRow(row);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No reservation selected for check-in.");
            }
        });


// Create the card for CheckOutGuestCard
        JPanel checkOutGuestCard = new JPanel(new BorderLayout());

// Create Input Fields and Buttons
        JTextField guestUsernameField_CheckOut = new JTextField(15);
        JTextField roomNumberField_CheckOut = new JTextField(5);
        JFormattedTextField checkInDateField_CheckOut = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        checkInDateField_CheckOut.setPreferredSize(new Dimension(100, 20));
        JButton searchButton_CheckOut = new JButton("Search");
        JButton checkOutButton_CheckOut = new JButton("Check Out");
        JButton backButton_CheckOut = new JButton("Back");
        checkOutButton_CheckOut.setEnabled(false); // Disabled by default

// Panel for Input Fields
        JPanel inputPanel_CheckOut = new JPanel();
        inputPanel_CheckOut.add(new JLabel("Guest Username: "));
        inputPanel_CheckOut.add(guestUsernameField_CheckOut);
        inputPanel_CheckOut.add(new JLabel("Room Number: "));
        inputPanel_CheckOut.add(roomNumberField_CheckOut);
        inputPanel_CheckOut.add(new JLabel("Check-In Date (yyyy-MM-dd): "));
        checkInDateField_CheckOut.setText("yyyy-MM-dd");
        inputPanel_CheckOut.add(checkInDateField_CheckOut);
        inputPanel_CheckOut.add(searchButton_CheckOut);
        checkOutGuestCard.add(inputPanel_CheckOut, BorderLayout.NORTH);

// Table to Display Results
        String[] columnNames_CheckOut = {"Reservation ID", "Room Number", "Guest Username", "Check-In Date", "Check-Out Date"};
        DefaultTableModel model_CheckOut = new DefaultTableModel(null, columnNames_CheckOut); // Update the column names
        JTable table_CheckOut = new JTable(model_CheckOut);
        JScrollPane scrollPane_CheckOut = new JScrollPane(table_CheckOut);
        scrollPane_CheckOut.setPreferredSize(new Dimension(500, 200));
        checkOutGuestCard.add(scrollPane_CheckOut, BorderLayout.CENTER);

// Add Check Out Button
// Add the back button to a navigation panel
        JPanel navPanel_CheckOut = new JPanel();
        navPanel_CheckOut.add(backButton_CheckOut);
        navPanel_CheckOut.add(checkOutButton_CheckOut);
        checkOutGuestCard.add(navPanel_CheckOut, BorderLayout.SOUTH);

// Handle the Search Button Click for Check Out
        searchButton_CheckOut.addActionListener(e -> {
            // Clear the table before populating with search results
            model_CheckOut.setRowCount(0);

            // Get the search criteria
            String guestUsername_CheckOut = guestUsernameField_CheckOut.getText();
            String roomNumberStr_CheckOut = roomNumberField_CheckOut.getText();
            String checkInDateStr_CheckOut = checkInDateField_CheckOut.getText();

            // Validate the date is not in the past
            SimpleDateFormat dateFormat_CheckOut = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date checkInDate_CheckOut = dateFormat_CheckOut.parse(checkInDateStr_CheckOut);
                Date currentDate_CheckOut = new Date();
                if (checkInDate_CheckOut.before(currentDate_CheckOut)) {
                    JOptionPane.showMessageDialog(null, "Date is in the past.");
                    return;
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
                return;
            }

            // Call a method to search for reservations based on the criteria
            List<Reservation> searchResults_CheckOut = hotel.searchReservations(guestUsername_CheckOut, roomNumberStr_CheckOut, checkInDateStr_CheckOut);

            // Populate the table with search results
            for (Reservation reservation : searchResults_CheckOut) {
                model_CheckOut.addRow(new Object[]{
                        reservation.getReservationID(), // Add reservation ID
                        reservation.getRoom().getRoomNumber(),
                        reservation.getGuest().getUsername(),
                        reservation.getCheckInDate(),
                        reservation.getCheckOutDate()
                });
            }
            checkOutButton_CheckOut.setEnabled(!searchResults_CheckOut.isEmpty()); // Enable the Check Out button if there are results
        });

// Handle the Check Out Button Click
        checkOutButton_CheckOut.addActionListener(e -> {
            int row_CheckOut = table_CheckOut.getSelectedRow();
            if (row_CheckOut != -1) {
                int reservationID_CheckOut = (int) table_CheckOut.getValueAt(row_CheckOut, 0); // Assuming reservation ID is in the first column
                Reservation selectedReservation_CheckOut = hotel.getSelectedReservationByID(reservationID_CheckOut);
                try {
                    hotel.checkOutGuest(selectedReservation_CheckOut.getRoom().getRoomNumber(), selectedReservation_CheckOut.getGuest().getUsername(), selectedReservation_CheckOut.getCheckInDate());
                    model_CheckOut.removeRow(row_CheckOut);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No reservation selected for check-out.");
            }
        });

// Handle the Back Button Click for Check Out
        backButton_CheckOut.addActionListener(e -> {
            cardLayout.show(cardPanel, "ManagerCard"); // Navigate back to the Manager card
            // Clear input fields when going back
            guestUsernameField_CheckOut.setText("");
            roomNumberField_CheckOut.setText("");
            checkInDateField_CheckOut.setValue(null);
        });


// Create the card for viewReservations
        JPanel viewReservationsCard = new JPanel(new BorderLayout());
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        // Create the View Reservations button
        JButton viewReservationsButton = new JButton("View Reservations");

// Add the View Reservations button to the button panel
        buttonPanel.add(viewReservationsButton);

        // Create a back button
        JButton backButton_viewReservations = new JButton("Back");
        buttonPanel.add(backButton_viewReservations);
// Add the button panel to the viewReservationsCard
        viewReservationsCard.add(buttonPanel, BorderLayout.NORTH);


// Add ActionListener to the back button
        backButton_viewReservations.addActionListener(e -> {
            if (loggedInUser instanceof Manager) {
                // Switch to the Manager Menu
                cardLayout.show(cardPanel, "ManagerCard");
            } else if (loggedInUser instanceof Guest) {
                // Switch to the Guest Menu
                cardLayout.show(cardPanel, "GuestCard");
            }
        });

// Table to Display Reservations
        String[] columnNames_viewReservations =  {"Reservation ID", "Room Number", "Guest Username", "Check-In Date", "Check-Out Date", "Total Price"};
        DefaultTableModel model_viewReservations = new DefaultTableModel(null, columnNames_viewReservations);
        JTable table_viewReservations = new JTable(model_viewReservations);
        JScrollPane scrollPane_viewReservations = new JScrollPane(table_viewReservations);
        scrollPane_viewReservations.setPreferredSize(new Dimension(600, 300));
        viewReservationsCard.add(scrollPane_viewReservations, BorderLayout.CENTER);

// Handle the View Reservations Button Click
        viewReservationsButton.addActionListener(e -> {
            if (loggedInUser instanceof Guest) {
                List<Reservation> reservations = hotel.getReservationsForGuest((Guest) loggedInUser);
                model_viewReservations.setRowCount(0); // Clear the table

                if (reservations.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You have no current reservations.");
                } else {
                    for (Reservation reservation : reservations) {
                        BigDecimal totalPrice = reservation.getTotalPrice(); // Get the total price
                        model_viewReservations.addRow(new Object[]{
                                reservation.getReservationID(),
                                reservation.getRoom().getRoomNumber(),
                                reservation.getGuest().getUsername(),
                                reservation.getCheckInDate(),
                                reservation.getCheckOutDate(),
                                totalPrice // Add the total price to the table
                        });
                    }
                }
            } else if (loggedInUser instanceof Manager) {
                List<Reservation> reservations = hotel.getAllReservations();
                model_viewReservations.setRowCount(0); // Clear the table

                if (reservations.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No reservations found.");
                } else {

                    for (Reservation reservation : reservations) {
                        BigDecimal totalPrice = reservation.getTotalPrice(); // Get the total price
                        model_viewReservations.addRow(new Object[]{
                                reservation.getReservationID(),
                                reservation.getRoom().getRoomNumber(),
                                reservation.getGuest().getUsername(),
                                reservation.getCheckInDate(),
                                reservation.getCheckOutDate(),
                                totalPrice // Add the total price to the table
                        });
                    }
                }
            }
            // Enable the back button after displaying the reservations
            backButton_viewReservations.setEnabled(true);
        });


// Create the card for DisplayTotalRevenueCard
        JPanel displayTotalRevenueCard = new JPanel(new BorderLayout());

        JLabel totalRevenueLabel = new JLabel();
        JButton calculateRevenueButton = new JButton("Calculate Total Revenue");
        JButton backButton_DisplayTotalRevenue = new JButton("Back");
        JPanel buttonPanel_DisplayTotalRevenue = new JPanel();
        buttonPanel_DisplayTotalRevenue.add(calculateRevenueButton);
        buttonPanel_DisplayTotalRevenue.add(backButton_DisplayTotalRevenue); // Add the "Back" button
        displayTotalRevenueCard.add(totalRevenueLabel, BorderLayout.CENTER);
        displayTotalRevenueCard.add(buttonPanel_DisplayTotalRevenue, BorderLayout.SOUTH);

// Handle the Calculate Total Revenue Button Click
        calculateRevenueButton.addActionListener(e -> {
            // Calculate the total revenue
            BigDecimal revenue = hotel.calculateTotalRevenue();
            // Update the label with the total revenue
            totalRevenueLabel.setText("Total revenue from room bookings: $" + revenue.toString());
        });

// Handle the Back Button Click for Display Total Revenue
        backButton_DisplayTotalRevenue.addActionListener(e -> {
            cardLayout.show(cardPanel, "ManagerCard"); // Navigate back to the Manager card (or the appropriate card)
            // Clear the total revenue label when going back
            totalRevenueLabel.setText("");
        });


// Create the card for SetSeasonalPricingCard
        JPanel setSeasonalPricingCard = new JPanel(new BorderLayout());

// Create Input Fields and Button
        JFormattedTextField startDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        JFormattedTextField endDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        JTextField multiplierField = new JTextField(5);
        startDateField.setPreferredSize(new Dimension(100, 20));
        endDateField.setPreferredSize(new Dimension(100, 20));
        JButton setSeasonalPricingButton = new JButton("Set Seasonal Pricing");
        JButton backButton_SetSeasonalPricing = new JButton("Back");

// Create a panel for input fields and labels using GridBagLayout
        JPanel inputPanel_SetSeasonalPricing = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        inputPanel_SetSeasonalPricing.add(new JLabel("Season Start Date (yyyy-MM-dd): "), gbc);
        gbc.gridx = 1;
        inputPanel_SetSeasonalPricing.add(startDateField, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        inputPanel_SetSeasonalPricing.add(new JLabel("Season End Date (yyyy-MM-dd): "), gbc);
        gbc.gridx = 1;
        inputPanel_SetSeasonalPricing.add(endDateField, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        inputPanel_SetSeasonalPricing.add(new JLabel("Price Multiplier (e.g., 1.5 for 50% increase): "), gbc);
        gbc.gridx = 1;
        inputPanel_SetSeasonalPricing.add(multiplierField, gbc);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Span across two columns
        inputPanel_SetSeasonalPricing.add(setSeasonalPricingButton, gbc);

// Add the Back button to a navigation panel
        JPanel navPanel_SetSeasonalPricing = new JPanel();
        navPanel_SetSeasonalPricing.add(backButton_SetSeasonalPricing);

// Add input panel and navigation panel to setSeasonalPricingCard
        setSeasonalPricingCard.add(inputPanel_SetSeasonalPricing, BorderLayout.NORTH);
        setSeasonalPricingCard.add(navPanel_SetSeasonalPricing, BorderLayout.SOUTH);

// Handle the Set Seasonal Pricing Button Click
        setSeasonalPricingButton.addActionListener(e -> {
            // Get the input values
            Date startDate = (Date) startDateField.getValue();
            Date endDate = (Date) endDateField.getValue();
            String multiplierStr = multiplierField.getText();

            // Validate the input values
            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(null, "Please enter valid start and end dates.");
                return;
            }

            // Get the current date
            Date currentDate = new Date();

            // Check if the start date is in the past
            if (startDate.before(currentDate)) {
                JOptionPane.showMessageDialog(null, "Start date cannot be in the past.");
                return;
            }

            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(null, "Start date cannot be after end date.");
                return;
            }

            if (!isValidMultiplier(multiplierStr)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid price multiplier (e.g., 1.5 for 50% increase).");
                return;
            }

            double multiplier = Double.parseDouble(multiplierStr);

            // Call a method to set seasonal pricing
            hotel.setSeasonalPrice(startDate, endDate, multiplier);

            // Display a success message
            JOptionPane.showMessageDialog(null, "Seasonal pricing set successfully.");
        });


// Handle the Back Button Click for Set Seasonal Pricing
        backButton_SetSeasonalPricing.addActionListener(e -> {
            cardLayout.show(cardPanel, "ManagerCard"); // Navigate back to the Manager card
            // Clear input fields when going back
            startDateField.setValue(null);
            endDateField.setValue(null);
            multiplierField.setText("");
        });


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
                        cardLayout.show(cardPanel, "displayTotalRevenueCard");
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
// Create the card for searching and booking rooms
        JPanel searchAndBookRoomCard = new JPanel(new BorderLayout());

// Create Input Fields and Buttons
        JFormattedTextField checkInDateField_BookRoom = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        JFormattedTextField checkOutDateField_BookRoom = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        checkInDateField_BookRoom.setPreferredSize(new Dimension(100, 20));
        checkOutDateField_BookRoom.setPreferredSize(new Dimension(100, 20));
        JButton searchButton_BookRoom = new JButton("Search");
        JButton bookButton_BookRoom = new JButton("Book Selected Room");
        bookButton_BookRoom.setEnabled(false); // Disabled by default
        JButton backButton_BookRoom = new JButton("Back");

// Panel for Input Fields
        JPanel inputPanel_BookRoom = new JPanel();
        inputPanel_BookRoom.add(new JLabel("Check-In Date (yyyy-MM-dd): "));
        inputPanel_BookRoom.add(checkInDateField_BookRoom);
        inputPanel_BookRoom.add(new JLabel("Check-Out Date (yyyy-MM-dd): "));
        inputPanel_BookRoom.add(checkOutDateField_BookRoom);
        inputPanel_BookRoom.add(searchButton_BookRoom);

        searchAndBookRoomCard.add(inputPanel_BookRoom, BorderLayout.NORTH);

// Table to Display Search Results
        String[] columnNames_BookRoom = {"Room Number", "Room Type", "Price"};
        DefaultTableModel model_BookRoom = new DefaultTableModel(null, columnNames_BookRoom);
        JTable table_BookRoom = new JTable(model_BookRoom);
        JScrollPane scrollPane_BookRoom = new JScrollPane(table_BookRoom);
        scrollPane_BookRoom.setPreferredSize(new Dimension(500, 200));
        searchAndBookRoomCard.add(scrollPane_BookRoom, BorderLayout.CENTER);

// Add Book and Back Button
        JPanel searchAndBookRoomCard_navPanel = new JPanel();
        searchAndBookRoomCard_navPanel.add(backButton_BookRoom);
        searchAndBookRoomCard_navPanel.add(bookButton_BookRoom);
        searchAndBookRoomCard.add(searchAndBookRoomCard_navPanel, BorderLayout.SOUTH);

// Handle the Search Button Click
        searchButton_BookRoom.addActionListener(e -> {
            Date checkInDate = (Date) checkInDateField_BookRoom.getValue();
            Date checkOutDate = (Date) checkOutDateField_BookRoom.getValue();

            // Validate the selected dates
            if (checkInDate == null || checkOutDate == null || checkOutDate.before(checkInDate) || checkInDate.before(new Date())) {
                JOptionPane.showMessageDialog(null, "Please select valid check-in and check-out dates.");
                return; // Exit the method if dates are invalid
            }

            // Call a method to search for available rooms based on the date range
            List<Room> availableRooms = hotel.getAvailableRooms(checkInDate, checkOutDate);

            // Clear the table
            model_BookRoom.setRowCount(0);

            // Populate the table with search results
            for (Room room : availableRooms) {
                model_BookRoom.addRow(new Object[]{room.getRoomNumber(), room.getRoomType(), room.getPrice()});
            }

            // Enable the book button if there are available rooms
            bookButton_BookRoom.setEnabled(!availableRooms.isEmpty());
        });

// Handle the Book Button Click
        bookButton_BookRoom.addActionListener(e -> {
            int selectedRow = table_BookRoom.getSelectedRow();
            if (selectedRow != -1) {
                int roomNumber = (int) table_BookRoom.getValueAt(selectedRow, 0);
                Date checkInDate = (Date) checkInDateField_BookRoom.getValue();
                Date checkOutDate = (Date) checkOutDateField_BookRoom.getValue();

                // Validate the selected dates again
                if (checkInDate == null || checkOutDate == null || checkOutDate.before(checkInDate) || checkInDate.before(new Date())) {
                    JOptionPane.showMessageDialog(null, "Please select valid check-in and check-out dates.");
                    return; // Exit the method if dates are invalid
                }

                // Call a method to book the selected room
                boolean bookingResult = false;
                try {
                    bookingResult = hotel.bookRoom(roomNumber, checkInDate, checkOutDate, (Guest) loggedInUser);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if (bookingResult) {
                    // Display a success message in the GUI
                    JOptionPane.showMessageDialog(null, "Room booked successfully.");
                    // Update the table to remove the booked room
                    model_BookRoom.removeRow(selectedRow);
                } else {
                    // Display an error message if booking fails
                    JOptionPane.showMessageDialog(null, "Room booking failed. Please check availability.");
                }
            } else {
                // No room selected, show a message
                JOptionPane.showMessageDialog(null, "Please select a room to book.");
            }
        });

// Create the card for viewPastReservationsCard
            JPanel viewPastReservationsCard = new JPanel(new BorderLayout());

            // Create Input Fields and Buttons
            JTextField guestUsernameField_Past = new JTextField(15);
            JFormattedTextField checkInDateField_Past = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        checkInDateField_Past.setPreferredSize(new Dimension(100, 20));
            JButton searchButton_Past = new JButton("Search");
            JButton backButton_Past = new JButton("Back");

            // Panel for Input Fields
            JPanel inputPanel_Past = new JPanel();
            inputPanel_Past.add(new JLabel("Guest Username: "));
            inputPanel_Past.add(guestUsernameField_Past);
            inputPanel_Past.add(new JLabel("Check-Out Date (yyyy-MM-dd): "));
            inputPanel_Past.add(checkInDateField_Past);
            inputPanel_Past.add(searchButton_Past);
            viewPastReservationsCard.add(inputPanel_Past, BorderLayout.NORTH);

            // Table to Display Past Reservations
            String[] columnNames_Past = {"Reservation ID", "Room Number", "Guest Username", "Check-In Date", "Check-Out Date", "Total Price"};
            DefaultTableModel model_Past = new DefaultTableModel(null, columnNames_Past);
            JTable table_Past = new JTable(model_Past);
            JScrollPane scrollPane_Past = new JScrollPane(table_Past);
            scrollPane_Past.setPreferredSize(new Dimension(600, 300));
            viewPastReservationsCard.add(scrollPane_Past, BorderLayout.CENTER);

            // Add Back Button
            JPanel navPanel_Past = new JPanel();
            navPanel_Past.add(backButton_Past);
            viewPastReservationsCard.add(navPanel_Past, BorderLayout.SOUTH);

        // Handle the Search Button Click for Past Reservations
        searchButton_Past.addActionListener(e -> {
            String guestUsername_past = guestUsernameField_Past.getText();
            String checkInDateStr = checkInDateField_Past.getText();

            // Validate input fields here (e.g., check for valid date format)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Disallow lenient parsing

            try {
                if (!guestUsername_past.isEmpty() && !checkInDateStr.isEmpty()) {
                    // Search by both guest username and check-in date
                    Date checkInDate = dateFormat.parse(checkInDateStr);
                    List<Reservation> pastReservations = hotel.searchPastReservations(guestUsername_past, checkInDateStr);

                    // Clear the table
                    model_Past.setRowCount(0);

                    // Populate the table with past reservation details
                    for (Reservation pastReservation : pastReservations) {
                        model_Past.addRow(new Object[]{
                                pastReservation.getReservationID(),
                                pastReservation.getRoom().getRoomNumber(),
                                pastReservation.getGuest().getUsername(),
                                pastReservation.getCheckInDate(),
                                pastReservation.getCheckOutDate(),
                                pastReservation.getTotalPrice()
                        });
                    }
                } else if (!guestUsername_past.isEmpty()) {
                    // Search by guest username only
                    List<Reservation> pastReservations = hotel.searchPastReservationsByGuest(guestUsername_past);

                    // Clear the table
                    model_Past.setRowCount(0);

                    // Populate the table with past reservation details
                    for (Reservation pastReservation : pastReservations) {
                        model_Past.addRow(new Object[]{
                                pastReservation.getReservationID(),
                                pastReservation.getRoom().getRoomNumber(),
                                pastReservation.getGuest().getUsername(),
                                pastReservation.getCheckInDate(),
                                pastReservation.getCheckOutDate(),
                                pastReservation.getTotalPrice()
                        });
                    }
                } else if (!checkInDateStr.isEmpty()) {
                    // Search by check-in date only
                    Date checkInDate = dateFormat.parse(checkInDateStr);
                    List<Reservation> pastReservations = hotel.searchPastReservationsByDate(checkInDate);

                    // Clear the table
                    model_Past.setRowCount(0);

                    // Populate the table with past reservation details
                    for (Reservation pastReservation : pastReservations) {
                        model_Past.addRow(new Object[]{
                                pastReservation.getReservationID(),
                                pastReservation.getRoom().getRoomNumber(),
                                pastReservation.getGuest().getUsername(),
                                pastReservation.getCheckInDate(),
                                pastReservation.getCheckOutDate(),
                                pastReservation.getTotalPrice()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter guest username or check-in date.");
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
            }
        });


        // Handle the Back Button Click for Past Reservations
            backButton_Past.addActionListener(e -> {
                // Clear input fields and table
                guestUsernameField_Past.setText("");
                checkInDateField_Past.setText("");
                model_Past.setRowCount(0);

                // Navigate back to the respective menu based on the logged-in user
                if (loggedInUser instanceof Manager) {
                    cardLayout.show(cardPanel, "ManagerCard");
                } else if (loggedInUser instanceof Guest) {
                    cardLayout.show(cardPanel, "GuestCard");
                }
            });


// Create the card for RequestAdditionalService
        JPanel requestAdditionalServiceCard = new JPanel(new BorderLayout());

// Create Input Fields and Buttons
        JComboBox<String> serviceTypeComboBox = new JComboBox<>(getRelevantServiceTypes()); // Replace with a method to get relevant service types
        JFormattedTextField serviceDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        serviceDateField.setPreferredSize(new Dimension(100, 20));
        JButton requestServiceButton = new JButton("Request Service");
        JButton backButton_requestService = new JButton("Back");

// Panel for Input Fields
        JPanel inputPanel_requestService = new JPanel();
        inputPanel_requestService.add(new JLabel("Service Type: "));
        inputPanel_requestService.add(serviceTypeComboBox);
        inputPanel_requestService.add(new JLabel("Service Date (yyyy-MM-dd): "));
        inputPanel_requestService.add(serviceDateField);
        inputPanel_requestService.add(requestServiceButton);
        requestAdditionalServiceCard.add(inputPanel_requestService, BorderLayout.NORTH);

// Add Back Button
        JPanel requestAdditionalServiceCard_navPanel = new JPanel();
        requestAdditionalServiceCard_navPanel.add(backButton_requestService);
        requestAdditionalServiceCard.add(requestAdditionalServiceCard_navPanel, BorderLayout.SOUTH);


// Handle the Request Service Button Click
        requestServiceButton.addActionListener(e -> {
            String serviceType = (String) serviceTypeComboBox.getSelectedItem(); // Get the selected service type from the JComboBox
            Date serviceDate = (Date) serviceDateField.getValue();

            // Validate the selected date
            if (serviceDate == null) {
                JOptionPane.showMessageDialog(null, "Please select a valid service date.");
                return; // Exit the method if the date is invalid
            }

            if (serviceType.isEmpty() || serviceDate == null) {
                JOptionPane.showMessageDialog(null, "Please enter valid information.");
            } else {
                if (loggedInUser instanceof Guest) {
                    Guest requestingGuest = (Guest) loggedInUser;

                    if (hotel.hasActiveReservation(requestingGuest, serviceDate)) {
                        ServiceRequest serviceRequest = new ServiceRequest(requestingGuest, serviceType, serviceDate);
                        hotel.logServiceRequest(serviceRequest);
                        JOptionPane.showMessageDialog(null, "Service request for " + serviceType + " has been submitted.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No active reservation found for the specified date.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Only guests can request additional services.");
                }
            }
        });

// Handle the Back Button Click
        backButton_requestService.addActionListener(e -> {
            cardLayout.show(cardPanel, "GuestCard");
            // Clear input fields when going back
            serviceTypeComboBox.setSelectedIndex(0); // Reset the JComboBox to the first item (or initial state)
            serviceDateField.setValue(null);

        });



        for (String option : guestOptions) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                String buttonText = ((JButton) e.getSource()).getText();
                switch (buttonText) {
                    case "View Available Rooms":
                        cardLayout.show(cardPanel, "Guest_viewAvailableRoomsCard");
                        break;
                    case "Book Room":
                        cardLayout.show(cardPanel, "searchAndBookRoomCard");
                        break;
                    case "View Reservations":
                        cardLayout.show(cardPanel, "viewReservationsCard");
                        break;
                    case "View Past Reservations":
                        cardLayout.show(cardPanel, "viewPastReservationsCard");
                        break;
                    case "Request Additional Service":
                        cardLayout.show(cardPanel, "requestAdditionalServiceCard");
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
        cardPanel.add(searchAndBookRoomCard, "searchAndBookRoomCard");
        cardPanel.add(viewReservationsCard, "viewReservationsCard");
        cardPanel.add(viewPastReservationsCard, "viewPastReservationsCard");
        cardPanel.add(requestAdditionalServiceCard, "requestAdditionalServiceCard");
        cardPanel.add(checkOutGuestCard, "checkOutGuestCard");
        cardPanel.add(setSeasonalPricingCard, "setSeasonalPricingCard");
        cardPanel.add(displayTotalRevenueCard, "displayTotalRevenueCard");

        // Button actions
        addActionToButton(managerButton, cardPanel, cardLayout, "ManagerLoginCard");
        addActionToButton(guestButton, cardPanel, cardLayout, "GuestLoginCard");
        addActionToButton(exitButton, cardPanel, cardLayout, "Exit");
        addActionToButton(managerBackButton, cardPanel, cardLayout, "WelcomeCard");
        addActionToButton(guestBackButton, cardPanel, cardLayout, "WelcomeCard");

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
                hotel.addOrUpdateRoom(roomNumber, roomType, BigDecimal.valueOf(roomPrice));
            } catch (NumberFormatException Numbe) {
                // Handle the exception. For example, show a dialog to inform the user that they've entered invalid data
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for room number and room price.");
            }
        });

        backButton_BookRoom.addActionListener(e -> {
            checkInDateField_BookRoom.setText("");
            checkOutDateField_BookRoom.setText("");
            cardLayout.show(cardPanel, "GuestCard");
        });
        backButton_addOrUpdateRoomCard.addActionListener(e -> {
            roomNumberField.setText("");
            roomTypeField.setText("");
            roomPriceField.setText("");
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

    private static boolean isValidMultiplier(String multiplierStr) {
        try {
            double multiplier = Double.parseDouble(multiplierStr);
            return multiplier > 0; // Validate that the multiplier is greater than 0
        } catch (NumberFormatException e) {
            return false; // Invalid input, not a valid double
        }
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

    // Helper method to get relevant service types for the room
    private static String[] getRelevantServiceTypes() {
        // Replace this with your logic to fetch relevant service types for the room
        String[] serviceTypes = {"Cleaning", "Room Service", "Maintenance"};
        return serviceTypes;
    }
}

