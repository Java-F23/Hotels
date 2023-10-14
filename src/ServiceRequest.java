import java.util.Date;

public class ServiceRequest {
    private Guest guest;
    private boolean completed;

    private String serviceType;
    private Date requestTime;
    private Staff staffAssigned;

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        this.completed = true;
    }

    public ServiceRequest(Guest guest, String serviceType, Date requestTime) {
        this.guest = guest;
        this.serviceType = serviceType;
        this.requestTime = requestTime;
        this.staffAssigned = null; // Initialize as null since no staff is assigned initially.
    }

    public Staff getStaffAssigned() {
        return staffAssigned;
    }

    public void setStaffAssigned(Staff staffAssigned) {
        this.staffAssigned = staffAssigned;
    }


    // Additional getters for other fields
}
