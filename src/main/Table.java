// Employee and Order classes to be added later

// A table in the restuarant that can be reserved.
public class Table {
    private static final String AVAILABLE = "AVAILABLE";
    private static final String RESERVED = "RESERVED";
    private static final String OCCUPIED = "OCCUPIED";
    private static final int MAX_TABLE_CAPACITY = 8;

    // Table Attributes
    private final int tableNumber;
    private int capacity, currentPartySize;
    private String status;
    private Employee server;
    private Order currentOrder;

    // Table Constructor
    public Table (int tableNumber, int capatity) {
        this.tableNumber = tableNumber;
        this.capacity = capatity;
        this.status = AVAILABLE;
        this.currentPartySize = 0;
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
        this.order = newOrder;
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
        return "Table: " + tableNumber;
    }

}
