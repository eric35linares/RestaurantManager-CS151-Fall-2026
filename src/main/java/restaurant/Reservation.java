package restaurant;
import exceptions.TableUnavailableException;

// A reservation to hold a table for a party

public class Reservation {
    private static final String CONFIRMED = "CONFIRMED";
    private static final String CANCELLED = "CANCELLED";
    private static final String NO_SHOW = "NO_SHOW";
    private static final String SEATED = "SEATED";
    private static int currentReservationId = 1;

    // Reservation Attributes
    private final int reservationId;
    private int partySize;
    private String customerName, status;
    private Table assignedTable;

    // Reservation Constructor
    public Reservation(int partySize, String customerName, Table assignedTable) {
        reservationId = currentReservationId++;
        this.partySize = partySize;
        this.customerName = customerName;
        this.status = CONFIRMED;
        this.assignedTable = assignedTable;
    }

    // Main methods

    public void cancelReservation() throws TableUnavailableException {
        if (!status.equals(CONFIRMED)) {
            throw new IllegalStateException(
                "Only confirmed reservations can be cancelled.");
        }
        assignedTable.cancelReservation();
        assignedTable = null;
        status = CANCELLED;
    }

    public void markNoShow() throws TableUnavailableException {
        if (!status.equals(CONFIRMED)) {
            throw new IllegalStateException(
                "Only confirmed reservations can be marked as a no-show.");
        }
        assignedTable.cancelReservation();
        assignedTable = null;
        status = NO_SHOW;
        
    }

    public void checkInReservation() throws TableUnavailableException {
        if (!status.equals(CONFIRMED)) {
            throw new IllegalStateException(
                "Only confirmed reservations can be checked in.");
        }
        assignedTable.cancelReservation();
        assignedTable.seat(this.partySize);
        status = SEATED;
    }

    // Setters

    public void setPartySize(int newPartySize) {
        if (newPartySize <= 0) {
            throw new IllegalArgumentException(
                "Party size must be at least 1.");
        }
        this.partySize = newPartySize;
    }

    public void setCustomerName(String newCustomerName) {
        if (newCustomerName == null) {
            throw new IllegalArgumentException(
                "Customer name cannot be blank.");
        }
        customerName = newCustomerName;
    }

    // Getters

    public int getReservationID() {
        return reservationId;
    }

    public int getPartySize() {
        return partySize;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public String getStatus() {
        return status;
    }

    public Table getAssignedTable() {
        return assignedTable;
    }

    // Class Methods

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Reservation)) {
            return false;
        }
        return this.reservationId == ((Reservation) obj).reservationId;
    }

    @Override 
    public String toString() {
        return "Reservation #" + reservationId + " [name=" + customerName 
            + ", party size=" + partySize + ", status=" + status + "]";
    }

}
