import exceptions.TableUnavailableException;

// A reservation to hold a table for a party

public class Reservation {
    private static final String PENDING = "PENDING";
    private static final String CONFIRMED = "CONFIRMED";
    private static final String SEATED = "SEATED";
    private static final String CANCELLED = "CANCELLED";
    private static final String NO_SHOW = "NO_SHOW";
    private static int currentReservationId = 0;

    // Reservation Attributes
    private final int reservationId;
    private int partySize;
    private String customerName, status;
    private Table assignedTable;

    // Reservation Constructor
    public Reservation(int partySize, String customerName) {
        reservationId = currentReservationId++;
        this.partySize = partySize;
        this.customerName = customerName;
        this.status = PENDING;
    }

    // Main methods

    // Setters

    public void setPartySize(int newPartySize) {
        if (newPartySize < 0) {
            throw new IllegalArgumentException("Party size must be at least 1.");
        }
        this.partySize = newPartySize;
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
        return "Reservation: " + reservationId;
    }

}
