package ui;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.*;
import restaurant.*;

public class MainMenu {
    private final String EXIT = "EXIT";
    private static final Scanner scanner = new Scanner(System.in);
    private Restaurant restaurant;

    public MainMenu() {
        this.restaurant = null;
        run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("Welcome to restaurant simulator. Type EXIT at any time to quit.");
            System.out.println("1. Create restaurant");
            System.out.println("2. Exit");
            System.out.println("3. FOR DEMO: Default restaurant");
            int choice = readInt("Enter a choice: ");

            switch (choice) {
                case 1:   
                    String name = readLine("Enter restaurant name: ");
                    this.restaurant = new Restaurant(name);
                    mainMenu();
                    break;
                case 2:
                    running = false;
                    exitProgram();
                    break;
                case 3:
                    this.restaurant = new Restaurant("Testaurant");
                    loadSampleData();
                    mainMenu();
                    break;
            }
        }
    }

    private void mainMenu() {
        boolean running = true;
        while (running) { 
            clearScreen();
            System.out.println("======= " + this.restaurant.getName() + " =======");
            System.out.println("1. Tables and seating");
            System.out.println("2. Employees");
            System.out.println("3. Reservations");
            System.out.println("4. Orders and checkout");
            System.out.println("5. Menu Items");
            System.out.println("6. Restaraunt Summary");
            System.out.println("7. Rename Restaurant");
            System.out.println("8. Exit");
            int choice = readInt("Enter a choice: ");

            switch (choice) {
            case 1:
                tableMenu();
                break;
            case 2:
                employeeMenu();
                break;
            case 3:
                reservationMenu();
                break;   
            case 4:
                orderMenu();
                break;  
            case 5:
                itemMenu();
                break;
            case 6:
                viewSummary();
                break;
            case 7:
                renameRestaurant();
                break;
            case 8:
                running = false;
                exitProgram();
                break;
            }
        }
    }

    // ---------------------- Tables and Seating ----------------------

    private void tableMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Tables and Seating =======");
            System.out.println("1. View Tables");
            System.out.println("2. Add a Table");
            System.out.println("3. Seat a walk-in party");
            System.out.println("4. Clear a Table");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewTables();
                        break;
                    case 2:
                        addTable();
                        break;
                    case 3:
                        seatWalkIn();
                        break;
                    case 4:
                        clearTable();
                        break;
                    case 5:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void viewTables() {
        ArrayList<Table> tables = restaurant.getTables();
        System.out.println("Tables: " + tables);
        pause();
    }

    public void addTable() 
        throws DuplicateEntryException, LimitException{
        int seats = readInt("Number of seats: ");
        Table table = new Table(1, seats);
        restaurant.addTable(table);
        System.out.println("Table " + table.getTableNumber() + " added");
        pause();
    }

    public void seatWalkIn() 
        throws TableUnavailableException {
        int partySize = readInt("Size of party: ");
        restaurant.seatWalkIn(partySize);
    }

    public void clearTable() 
        throws TableUnavailableException {
        int tableNumber = readInt("Table number to clear: ");
        restaurant.clearTable(restaurant.getTables().get(tableNumber));
    }

    // ---------------------- Employees ----------------------
    private void employeeMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Employees =======");
            System.out.println("1. View Employees");
            System.out.println("2. Hire Employee");
            System.out.println("3. Fire Employee");
            System.out.println("4. Calculate Pay");
            System.out.println("5. Change Role or Pay");
            System.out.println("6. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewEmployees();
                        break;
                    case 2:
                        hireEmployees();
                        break;
                    case 3:
                        fireEmployees();
                        break;
                    case 4:
                        calculatePay();
                        break;
                    case 5:
                        changeEmployeeDetails();
                        break;
                    case 6:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void viewEmployees() {

    }

    public void hireEmployees() {

    }

    public void fireEmployees() {

    }

    public void calculatePay() {

    }

    public void changeEmployeeDetails() {

    }

    // ---------------------- Reservations ----------------------
    private void reservationMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Reservations =======");
            System.out.println("1. Make Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Check in Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Mark No-Show");
            System.out.println("6. Edit Reservation");
            System.out.println("7. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        viewReservation();
                        break;
                    case 3:
                        checkInReservation();
                        break;
                    case 4:
                        cancelReservation();
                        break;
                    case 5:
                        markNoShow();
                        break;
                    case 6:
                        editReservation();
                        break;
                    case 7:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void makeReservation() {

    }

    public void viewReservation() {

    }

    public void checkInReservation() {

    }

    public void cancelReservation() {

    }

    public void markNoShow() {
        
    }

    public void editReservation() {

    }

    // ---------------------- Orders ---------------------- ANSON
    private void orderMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Orders =======");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        // Method 1
                        break;
                    case 2:
                        // Method 2
                        break;
                    case 3:
                        // Method 3
                        break;
                    case 4:
                        // Method 4
                        break;
                    case 5:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------------------- Menu ---------------------- EMMET
    private void itemMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Menu Items =======");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        // Method 1
                        break;
                    case 2:
                        // Method 2
                        break;
                    case 3:
                        // Method 3
                        break;
                    case 4:
                        // Method 4
                        break;
                    case 5:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------------------- Restaurant ----------------------
    public void viewSummary() {
        System.out.println("======= Restaurant Summary =======");
        System.out.println("Name: " + restaurant.getName());
        System.out.println("Tables: " + restaurant.getTables().size());
        System.out.println("Employees: " + restaurant.getEmployees().size());
        System.out.println("Reservations: " + restaurant.getReservations().size());
        System.out.println("Orders: " + restaurant.getOrders().size());
        System.out.println("Menu: ");
        pause();
    }

    public void renameRestaurant() {
        String newName = readLine("New restaurant name: ");
        restaurant.setName(newName);
        System.out.println("Restaurant renamed to " + restaurant.getName());
        pause();
    }

    public void loadSampleData() {
        try {
            restaurant.addTable(new Table(1, 2));
            restaurant.addTable(new Table(2, 4));
            restaurant.addTable(new Table(3, 8));

            restaurant.hireEmployee(new Employee("Eric Linares", "SERVER", 15.50));
            restaurant.hireEmployee(new Employee("Anson", "CHEF", 30.00));
            restaurant.hireEmployee(new Employee("Emmet", "MANAGER", 50.00));

            restaurant.seatWalkIn(2);
            //restaurant.assignServer(null, null);
            restaurant.makeReservation("Jim", 4);

        } catch (Exception e) {
            System.out.println("Could not load sample data: " + e.getMessage());
        }
    }

    // ---------------------- Helper Methods ----------------------

    private void exitProgram() {
        System.out.println("Bye.");
        scanner.close();
        System.exit(0);
    }

    private void pause() {
        readLine("Press Enter to continue...");
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line.equalsIgnoreCase(EXIT)) {
            exitProgram();
        }
        return line;
    }

    private int readInt(String prompt) {
        String line = readLine(prompt);
        int value = 0;
        try {
            value = Integer.parseInt(line);
        } catch (NumberFormatException e) { 
            System.out.println("Please enter an integer.");
        }
        return value;
    }
}
