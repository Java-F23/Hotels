import java.util.List;
import java.util.Date;

public class Manager extends User {

    public Manager(String username, String password) {
        super(username, password);
    }

    public OccupancyReport generateAndDisplayOccupancyReport(Date startDate, Date endDate) {
        OccupancyReport report = Hotel.getInstance().generateOccupancyReport(startDate, endDate);
        return report;
    }

}
