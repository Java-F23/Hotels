import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation {
    private static int nextReservationId = 1; // A static field to generate unique reservation IDs
    private int reservationId; // The reservation ID field
    private Guest guest;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private boolean checkedIn;
    private boolean checkedOut;

    private BigDecimal totalPrice;
    public Reservation(Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this.reservationId = nextReservationId++; // Assign a unique reservation ID
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = computeTotalPrice(checkInDate, checkOutDate, room.getPrice());
        this.checkedIn = false;
        this.checkedOut = false;
    }
    private BigDecimal computeTotalPrice(Date checkInDate, Date checkOutDate, BigDecimal roomPricePerNight) {
        long differenceInMillis = checkOutDate.getTime() - checkInDate.getTime();
        long days = differenceInMillis / (1000 * 60 * 60 * 24); // convert milliseconds to days
        return roomPricePerNight.multiply(BigDecimal.valueOf(days));
    }

    public BigDecimal getTotalPrice() {
        long duration = TimeUnit.DAYS.convert(checkOutDate.getTime() - checkInDate.getTime(), TimeUnit.MILLISECONDS);
        BigDecimal basePrice = room.getPrice();
        BigDecimal seasonalMultiplier = BigDecimal.valueOf(Hotel.getInstance().getPriceMultiplier(checkInDate)); // get multiplier based on check-in date
        return basePrice.multiply(seasonalMultiplier.multiply(BigDecimal.valueOf(duration)));
    }
    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void checkIn() {
        checkedIn = true;
        checkedOut = false;
    }

    public void checkOut() {
        checkedIn = false;
        checkedOut = true;
    }
    @Override
    public String toString() {
        String status_checkin = checkedIn ? "Checked In" : "Not Checked In";
        String status_checkout = checkedOut ? "Checked Out" : "Not Checked Out";
        return "Guest: " + guest.getUsername() +
                "\nRoom Number: " + room.getRoomNumber() +
                "\nRoom Type: " + room.getType() +
                "\nRoom Price per Night: $" + room.getPrice() +
                "\nCheck-In Date: " + checkInDate +
                "\nCheck-Out Date: " + checkOutDate +
                "\nTotal Price: $" + String.format("%.2f", totalPrice) +
                "\nStatus: " + status_checkin +
                "\nStatus2: " + status_checkout +
                "\n";
    }

    public int getReservationID() {
        return reservationId;
    }
}
