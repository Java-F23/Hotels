import java.util.Date;

public class Complaint {
    private int complaintId;
    private Guest guest;
    private Date complaintDate;
    private String complaintDescription;
    private String resolution;
    private boolean resolved;

    public Complaint(int complaintId, Guest guest, Date complaintDate, String complaintDescription) {
        this.complaintId = complaintId;
        this.guest = guest;
        this.complaintDate = complaintDate;
        this.complaintDescription = complaintDescription;
        this.resolved = false; // Initially, the complaint is unresolved
    }

    // Getters and setters for resolution and resolved status
    public String getResolution() {
        return resolution;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void markResolved() {
        this.resolved = true;
    }

    @Override
    public String toString() {
        return "Complaint ID: " + complaintId +
                "\nGuest: " + guest.getUsername() +
                "\nComplaint Date: " + complaintDate +
                "\nDescription: " + complaintDescription +
                "\nResolution: " + (resolved ? resolution : "Not resolved yet");
    }
}
