package restaurant;
import exceptions.TableUnavailableException;

// A table in the restuarant that can be reserved.
// Employee and Order classes to be added later

public class Table {
    private static final String AVAILABLE = "AVAILABLE";
    private static final String RESERVED = "RESERVED";
    private static final String OCCUPIED = "OCCUPIED";
    private static final int MAX_TABLE_CAPACITY = 8;
    private static int currentTableNumber = 1;

    // Table Attributes
    private final int tableNumber;
    private int capacity, currentPartySize;
    private String status;
    private Employee server;
    private Order currentOrder;

    // Table Constructor
    public Table (int capacity) {
        if (capacity < 1 || capacity > MAX_TABLE_CAPACITY) {
            throw new IllegalArgumentException(
                "Capacity must be between 1 and " + MAX_TABLE_CAPACITY);
        }
        this.tableNumber = currentTableNumber++;
        this.capacity = capacity;
        this.status = AVAILABLE;
        this.currentPartySize = 0;
    }

    // Main methods

    // Makes table OCCUPIED
    public void seat(int size) throws TableUnavailableException {
        if (status != AVAILABLE) {
            throw new TableUnavailableException(
                "Table is " + status);
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Party size must be at least 1.");
        }
        if (size > capacity) {
            throw new TableUnavailableException(
                "Party size too large. Capacity: " + this.capacity);
        }

        currentPartySize = size;
        status = OCCUPIED;
    }

    // Makes table AVAILABLE
    public void clearTable() throws TableUnavailableException {
        if (!status.equals(OCCUPIED)) {
            throw new TableUnavailableException(
                "Table is not in use. Cannot be cleared");
        }
        // TO-DO: if table is unpaid for
        currentPartySize = 0;
        status = AVAILABLE;
        server = null;
        currentOrder = null;
    }

    // Makes table AVAILABLE
    public void cancelReservation() throws TableUnavailableException {
        if (!status.equals(RESERVED)) {
            throw new TableUnavailableException(
                "Table is not reserved. Cannot cancel reservation");
        }
        status = AVAILABLE;
    }

    // Makes table RESERVED
    public void reserve() throws TableUnavailableException {
        if (!status.equals(AVAILABLE)) {
            throw new TableUnavailableException(
                "Table is not available. Cannot reserve.");
        }
        status = RESERVED;
    }

    // Setters

    public void setCapacity(int newCapacity) {
        if (newCapacity > MAX_TABLE_CAPACITY || newCapacity < 1) {
            throw new IllegalArgumentException(
                "Capacity must be between 1 and 8.");
        }
        this.capacity = newCapacity;
    }

    public void setServer(Employee newServer) {
        if (newServer == null) {
            throw new IllegalArgumentException(
                "Employee cannot be null.");
        }
        this.server = newServer;
    }

    public void setCurrentOrder(Order newOrder) {
        this.currentOrder = newOrder;
    }

    // Getters

    public boolean isAvailable() {
        return status.equals(AVAILABLE);
    }

    public boolean isReserved() {
        return status.equals(RESERVED);
    }

    public boolean isOccupied() {
        return status.equals(OCCUPIED);
    }
    
    public int getTableNumber() {
        return this.tableNumber;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getCurrentPartySize() {
        return this.currentPartySize;
    }

    public Employee getServer() {
        return this.server;
    }

    public Order getCurrentOrder() {
        return this.currentOrder;
    }

    // Class methods

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Table)) {
            return false;
        }
        return this.tableNumber == ((Table) obj).tableNumber;
    }

    @Override 
    public String toString() {
        return "Table: " + tableNumber + " [capacity=" + capacity
            + ", status=" + status + ", party=" + currentPartySize + "]";
    }

}
