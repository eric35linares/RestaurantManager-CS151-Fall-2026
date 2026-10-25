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
                default:
                    throw new IllegalArgumentException(
                        "Choose 1, 2, or 3.");
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
            default:
                throw new IllegalArgumentException(
                    "Choose 1 through 8.");
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
                    default:
                        throw new IllegalArgumentException(
                            "Choose 1 through 5.");
                }
            } catch (Exception e) {
                System.out.println(e);
                pause();
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
        Table table = new Table(seats);
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
                    default:
                        throw new IllegalArgumentException(
                            "Choose 1 though 6.");
                }
            } catch (Exception e) {
                System.out.println(e);
                pause();
            }
        }
    }

    public void viewEmployees() {
        if (restaurant.getEmployees().isEmpty()) {
            System.out.println("There are no employees yet.");
        } 
        else {
            printEmployees();
        }
        pause();
    }

    public void hireEmployees() 
        throws DuplicateEntryException, LimitException {
        String name = readLine("Employee Name: ");
        String role = chooseRole();
        double hourlyRate = readDouble("Hourly Rate: $");

        Employee employee = new Employee(name, role, hourlyRate);
        restaurant.hireEmployee(employee);
        System.out.println("Hired " + name + " as " + role + " ID #: " + employee.getEmployeeID());
        pause();
    }

    public void fireEmployees() {
        Employee employee = pickEmployee();
        restaurant.removeEmployee(employee);
        System.out.println(employee.getName() + " is FIRED.");
        pause();
    }

    public void calculatePay() {
        Employee employee = pickEmployee();
        double hours = readDouble("Hours worked: ");
        System.out.println(employee.getName() + " earned $" + (hours * employee.getHourlyRate()));
        pause();
    }

    public void changeEmployeeDetails() {
        Employee employee = pickEmployee();
        System.out.println("What do you want to change? (1 = role, 2 = hourly rate)");
        int choice = readInt("Enter a choice: ");

        switch (choice) {
            case 1: 
                String role = chooseRole();
                employee.setRole(role);
                System.out.println(employee.getName() + " is now a " + role);
                break;
            case 2: 
                double newPay = readDouble("New pay: $");
                employee.setHourlyRate(newPay);
                System.out.println(employee.getName() + " now makes $" + newPay);
                break;
            default:
                throw new IllegalArgumentException(
                    "Choose 1 or 2.");
        }
        pause();
    }

    // ---------------------- Reservations ----------------------
    private void reservationMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Reservations =======");
            System.out.println("1. View Reservation");
            System.out.println("2. Make Reservations");
            System.out.println("3. Check in Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Mark No-Show");
            System.out.println("6. Edit Reservation");
            System.out.println("7. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewReservations();
                        break;
                    case 2:
                        makeReservation();
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
                    default:
                        throw new IllegalArgumentException(
                            "Choose through 7.");
                }
            } catch (Exception e) {
                System.out.println(e);
                pause();
            }
        }
    }

    public void viewReservations() {
        if (restaurant.getReservations().isEmpty()) {
            System.out.println("There are no reservations yet.");
        }
        else {
            printReservations();
        }
        pause();
    }

    public void makeReservation() 
        throws TableUnavailableException, LimitException {
        String name = readLine("Customer Name: ");
        int partySize = readInt("Party Size: ");
        restaurant.makeReservation(name, partySize);
        System.out.println("Reservation made for " + name + " (party of " + partySize + ")");
        pause();
    }

    public void checkInReservation() 
        throws TableUnavailableException {
        String name = readLine("Customer Name: ");
        int partySize = readInt("Party Size: ");
        restaurant.seatReservation(name, partySize);
        System.out.println(name + "'s party has been seated.");
        pause();
    }

    public void cancelReservation() 
        throws TableUnavailableException {
        Reservation reservation = pickReservation();
        restaurant.cancelReservation(reservation);
        System.out.println(reservation.getCustomerName() + "'s reservation has been cancelled.");
        pause();
    }

    public void markNoShow() 
        throws TableUnavailableException {
        Reservation reservation = pickReservation();
        reservation.markNoShow();
        System.out.println(reservation.getCustomerName() + "'s reservation has been marked as NO-SHOW.");
        pause();
    }

    public void editReservation() {
        Reservation reservation = pickReservation();
        System.out.println("What do you want to change? (1 = name, 2 = party size)");
        int choice = readInt("Enter a choice: ");

        switch (choice) {
            case 1: 
                String name = readLine("Customer Name: ");
                reservation.setCustomerName(name);
                System.out.println("Reservation is now under " + name);
                break;
            case 2: 
                int newSize = readInt("Party Size: ");
                reservation.setPartySize(newSize);
                System.out.println(reservation.getCustomerName() + " now has party of size " + newSize);
                break;
            default:
                throw new IllegalArgumentException(
                    "Choose 1 or 2.");
        }
        pause();

    }

    // ---------------------- Orders ---------------------- ANSON
    private void orderMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Orders =======");
            System.out.println("1. View Order");
            System.out.println("2. Create Order");
            System.out.println("3. Add Item");
            System.out.println("4. Check out");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewOrders();
                        // Method 1
                        break;
                    case 2:
                        createOrder();
                        // Method 2
                        break;
                    case 3:
                        addToOrder()    ;
                        break;
                        // Method 3
                    case 4:
                        checkoutOrder();
                        // Method 4
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        throw new IllegalArgumentException(
                            "Choose 1 through 5.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                pause();
            }
        }
    }

    public void viewOrders(){
        ArrayList<Order> orders = restaurant.getOrders();

        if(orders.isEmpty()){
            System.out.println("There are no orders yet.");
        } else {
            for(Order order: orders){
                System.out.println(order);
            }
        }
        pause();
    }

    public void createOrder() throws TableUnavailableException, LimitException{
        if(restaurant.getTables().isEmpty()){
            System.out.println("There are no tables");
            pause();
            return;
        }
            for(Table table: restaurant.getTables()){
                System.out.println(table);
            }

            int tableNumber = readInt("Enter a table number: ");

            for(Table table : restaurant.getTables()){
                if(table.getTableNumber() == tableNumber){
                    restaurant.createOrder(table);
                    System.out.println("Order. created for table " + tableNumber);
                    pause();
                    return;
                }
            }

            throw new IllegalArgumentException("Table not found");
    }

    public void addToOrder() {
    ArrayList<Order> orders = restaurant.getOrders();
    ArrayList<MenuItem> menuItems = restaurant.getMenuItems();

    if (orders.isEmpty()) {
        System.out.println("No orders yet.");
        pause();
        return;
    }

    if (menuItems.isEmpty()) {
        System.out.println("There are no menu items.");
        pause();
        return;
    }

    System.out.println("======= Orders =======");

    for (int i = 0; i < orders.size(); i++) {
        System.out.println((i + 1) + ". " + orders.get(i));
    }

    int orderChoice = readInt("Select an order: ");

    if (orderChoice < 1 || orderChoice > orders.size()) {
        throw new IllegalArgumentException("Invalid order selected.");
    }

    Order order = orders.get(orderChoice - 1);

    System.out.println("======= Menu Items =======");

    for (int i = 0; i < menuItems.size(); i++) {
        MenuItem item = menuItems.get(i);
        System.out.println((i + 1) + ". " + item.getName()
                + " - $" + item.calculateCoursePrice());
    }

    int itemChoice = readInt("Select a menu item: ");

    if (itemChoice < 1 || itemChoice > menuItems.size()) {
        throw new IllegalArgumentException("Invalid menu item selected.");
    }

    MenuItem item = menuItems.get(itemChoice - 1);

    order.addItem(item);

    System.out.println(item.getName() + " added to order.");
    pause();
}
    
   
public void checkoutOrder() {
    ArrayList<Order> orders = restaurant.getOrders();

    if (orders.isEmpty()) {
        System.out.println("There are no orders.");
        pause();
        return;
    }

    System.out.println("======= Checkout =======");

    for (int i = 0; i < orders.size(); i++) {
        System.out.println((i + 1) + ". " + orders.get(i));
    }

    int choice = readInt("Select order to checkout: ");

    if (choice < 1 || choice > orders.size()) {
        throw new IllegalArgumentException("Invalid order.");
    }

    Order order = orders.get(choice - 1);

    if (order.isCompleted()) {
        throw new IllegalStateException(
            "This order has already been checked out.");
    }

    double discount = readDouble("Discount percentage (0-100): ");

    if (!Double.isFinite(discount) || discount < 0 || discount > 100) {
        throw new IllegalArgumentException("Invalid discount.");
    }

    double tip = readDouble("Tip amount: $");

    if (!Double.isFinite(tip) || tip < 0) {
        throw new IllegalArgumentException("Invalid tip.");
    }

    double subtotal = order.applyDiscount(
        order.calculateSubtotal(), discount);

    double total = subtotal + (subtotal * 0.10) + tip;

    System.out.printf("Total due: $%.2f%n", total);

    double payment = readDouble("Payment amount: $");

    if (!Double.isFinite(payment) || payment <= 0) {
        throw new IllegalArgumentException("Invalid payment.");
    }

    if (payment + 0.001 < total) {
        throw new IllegalArgumentException(
            "Payment does not cover the total.");
    }

    order.setDiscount(discount);
    order.completeOrder();

    Receipt receipt = order.generateReceipt();
    receipt.addTip(tip);
    receipt.recordPayment(payment);

    System.out.println(receipt);

    System.out.printf("Remaining balance: $%.2f%n",
        Math.max(0, receipt.calculateBalance()));

    pause();
}


    // ---------------------- Menu ---------------------- EMMET
    private void itemMenu() {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("======= Menu Items =======");
            System.out.println("1. View Menu Items");
            System.out.println("2. Add Menu Items");
            System.out.println("3. Edit Menu Items");
            System.out.println("4. Remove Menu Items");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewMenuItem();
                        break;
                    case 2:
                        addMenuItem();
                        break;
                    case 3:
                        editMenuItem();
                        break;
                    case 4:
                        removeMenuItem();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        throw new IllegalArgumentException(
                            "Choose 1 through 5.");
                }
            } catch (Exception e) {
                System.out.println(e);
                pause();
            }
        }
    }

    public void viewMenuItem(){
        if(restaurant.getMenuItems().isEmpty()){
            System.out.println("There are no menu items yet");
        } else {
            printMenuItems();
        }
        pause();
    }

    
     public void addMenuItem(){
        System.out.println("======= Select Course Type =======");
        System.out.println("1. Appetizer");
        System.out.println("2. Entree");
        System.out.println("3. Dessert");
        System.out.println("4. Side");
        int typeChoice = readInt("Enter a choice: ");

        String id = readLine("Item ID (e.g. A1, E1, D1, S1): ");
        String name = readLine("Item Name: ");
        String description = readLine("Description: ");
        double basePrice = readDouble("Base Price: $");
        int calories = readInt("Calories: ");
    

        MenuItem newItem;
        //not finsihed
    
    

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
            restaurant.addTable(new Table(2));
            restaurant.addTable(new Table(4));
            restaurant.addTable(new Table(8));

            restaurant.hireEmployee(new Employee("Eric Linares", "SERVER", 15.50));
            restaurant.hireEmployee(new Employee("Anson", "CHEF", 30.00));
            restaurant.hireEmployee(new Employee("Emmet", "MANAGER", 50.00));

            restaurant.seatWalkIn(2);
            // TO-DO restaurant.assignServer(null, null);
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

    private double readDouble(String prompt) {
        String line = readLine(prompt);
        double value = 0.0;
        try {
            value = Double.parseDouble(line);
        } catch (NumberFormatException e) { 
            System.out.println("Please enter an integer.");
        }
        return value;
    }

    private String chooseRole() {
        String role = null;
        System.out.println("======= Choose Role =======");
        System.out.println("1. SERVER");
        System.out.println("2. CHEF");
        System.out.println("3. HOST");
        System.out.println("4. MANAGER");
        int choice = readInt("Enter a choice: ");
        switch (choice) {
            case 1:
                role = "SERVER";
                break;
            case 2:
                role = "CHEF";
                break;
            case 3:
                role = "HOST";
                break;
            case 4:
                role = "MANAGER";
                break;
            default:
                throw new IllegalArgumentException(
                    "Choose 1 through 4.");
        }
        return role;
    }

    private void printEmployees() {
        for (Employee employee : restaurant.getEmployees()) {
            System.out.println("# " + employee.getEmployeeID()
                + " | " + employee.getName() 
                + " | " + employee.getRole() 
                + " | $" + employee.getHourlyRate()
                + " | Tables: " + employee.assignedTablesSize());
        }
    }

    private Employee pickEmployee() {
        if (restaurant.getEmployees().isEmpty()) {
            throw new IllegalStateException(
                " There are no employees hired yet.");
        }
        printEmployees();
        int id = readInt("Employee ID: ");
        for (Employee employee : restaurant.getEmployees()) {
            if (employee.getEmployeeID() == id) {
                return employee;
            }
        }
        throw new IllegalArgumentException(
            "No employee has this ID #.");
    }

    private void printReservations() {
        for (Reservation reservation : restaurant.getReservations()) {
            System.out.println("# " + reservation.getReservationID()
                + " | " + reservation.getCustomerName() 
                + " | Party of " + reservation.getPartySize() 
                + " | $" + reservation.getStatus()
                + " | " + reservation.getAssignedTable());
        }
    }

    private Reservation pickReservation() {
        if (restaurant.getReservations().isEmpty()) {
            throw new IllegalStateException(
                " There are no reservations made yet.");
        }
        printReservations();
        int id = readInt("Reservation ID: ");
        for (Reservation reservation : restaurant.getReservations()) {
            if (reservation.getReservationID() == id) {
                return reservation;
            }
        }
        throw new IllegalArgumentException(
            "No reservation has this ID #.");
    }

}
