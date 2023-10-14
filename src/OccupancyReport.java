import java.text.SimpleDateFormat;
import java.util.Date;

public class OccupancyReport {

    private Date startDate;
    private Date endDate;
    private double occupancyRate;

    public OccupancyReport(Date startDate, Date endDate, double occupancyRate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.occupancyRate = occupancyRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Occupancy Report from %s to %s: %.2f%% occupancy rate",
                sdf.format(startDate),
                sdf.format(endDate),
                occupancyRate * 100);
    }
}
