package restaurant;

import java.util.ArrayList;

// A restaurant employee. There are different kinds of employees. 

public class Employee {
    private static final String SERVER = "SERVER";
    private static final String CHEF = "CHEF";
    private static final String HOST = "HOST";
    private static final String MANAGER = "MANAGER";
    private static final int MAX_TABLES = 4;
    private static int currentID = 0;

    // Employee Attributes
    private final int employeeID;
    private String name, role;
    private double hourlyRate;
    private ArrayList<Table> assignedTables;

    // Employee Constructor
    public Employee(String name, String role, double hourlyRate) {
        this.employeeID = currentID++;
        this.name = name;
        this.role = role;
        this.hourlyRate = hourlyRate;
    }

    // Main methods

    public void assignTable(Table newTable) {
        if (newTable == null) {
            throw new IllegalArgumentException(
                "Table cannot be null.");
        }
        if (!this.role.equals(SERVER)) {
            throw new IllegalStateException(
                "Employee must be SERVER.");
        }
        if (assignedTables.size() >= MAX_TABLES) {
            throw new IllegalStateException(
                "Employee has maximum " + MAX_TABLES + " tables already.");
        }
        assignedTables.add(newTable);
        newTable.setServer(this);
    }

    public void releaseTable(Table newTable) {
        if (!assignedTables.contains(newTable)) {
            throw new IllegalStateException(
                "Current employee is not assigned to this table.");
        }
        assignedTables.remove(newTable);

    }

    public double calculatePay(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException(
                "Hours worked must be greater than 0.");
        }
        double totalPay = hoursWorked * hourlyRate;
        return totalPay;
    }

    // Setters

    public void setRole(String newRole) {
        if (newRole == null) {
            throw new IllegalArgumentException(
                "New role cannot be null.");
        }
        if (!newRole.equals(SERVER) && !newRole.equals(CHEF) 
            && !newRole.equals(HOST) && !newRole.equals(MANAGER)) {
            throw new IllegalArgumentException(
                "New role must be SERVER, CHEF, HOST, or MANAGER.");
        }
        this.role = newRole;
    }

    public void setHourlyRate(double newHourlyRate) {
        if (newHourlyRate <= 0) {
            throw new IllegalArgumentException(
                "Hourly rate must be greater than $0.00.");
        }
        this.hourlyRate = newHourlyRate;
    }
    
    // Getters

    public int getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public ArrayList<Table> getAssignedTables() {
        return assignedTables;
    }

    // Class Methods

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Employee)) {
            return false;
        }
        return this.name == ((Employee) obj).name;
    }

    @Override 
    public String toString() {
        return "Employee: " + name;
    }
    
}
