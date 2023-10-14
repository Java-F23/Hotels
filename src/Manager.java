import java.util.List;
import java.util.Date;

public class Manager extends User {

    public Manager(String username, String password) {
        super(username, password);
    }

    private void displayReservations(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
    public void generateAndDisplayOccupancyReport(Date startDate, Date endDate) {
        OccupancyReport report = Hotel.getInstance().generateOccupancyReport(startDate, endDate);
        displayOccupancyReport(report);
    }

    private void displayOccupancyReport(OccupancyReport report) {
        System.out.printf("Occupancy Report from %tF to %tF:\n", report.getStartDate(), report.getEndDate());
        System.out.printf("Occupancy Rate: %.2f%%\n", report.getOccupancyRate() * 100);
    }
    // Add more manager-specific methods here
}
