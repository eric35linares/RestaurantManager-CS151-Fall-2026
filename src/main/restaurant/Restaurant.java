package restaurant;

import java.util.ArrayList;

// The main Restaurant class where all other classes are managed

public class Restaurant {
    public static final int MAXIMUM_INSTANCES = 100;

    // Restaurant Attributes
    private String name;
    private ArrayList<Table> tables;
    private ArrayList<Employee> employees;
    private ArrayList<Order> orders;
    private ArrayList<Reservation> reservations; 

    // Restaurant Constructor
    public Restaurant(String name) {
        this.name = name;
        this.tables = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Main methods

    

    // Setters
    public void setName(String newName) {
        if (newName == null) {
            throw new IllegalArgumentException(
                "Name cannot be null.");
        }
        this.name = newName;
    }

    // Getters

    public String getName() {
        return this.name;
    }

    public ArrayList<Table> getTables() {
        return this.tables;
    }

    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }

    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservations;
    }

    // Class methods

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Restaurant)) {
            return false;
        }
        return this.name.equals(((Restaurant) obj).name);
    }
    
}
