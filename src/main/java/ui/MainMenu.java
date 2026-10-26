package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.DuplicateEntryException;
import exceptions.LimitException;
import exceptions.TableUnavailableException;
import restaurant.Appetizer;
import restaurant.Dessert;
import restaurant.Employee;
import restaurant.Entree;
import restaurant.MenuItem;
import restaurant.Order;
import restaurant.Receipt;
import restaurant.Reservation;
import restaurant.Restaurant;
import restaurant.Side;
import restaurant.Table;

public class MainMenu {
    private final String EXIT = "EXIT";
    private static final Scanner scanner = new Scanner(System.in);
    private Restaurant restaurant;
    private boolean sampleMenuLoaded;

    public MainMenu() {
        this.restaurant = null;
        this.sampleMenuLoaded = false;
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
            System.out.println("1. View Menu");
            System.out.println("2. Inspect Menu Item");
            System.out.println("3. Add Menu Item");
            System.out.println("4. Edit Menu Item");
            System.out.println("5. Remove Menu Item");
            System.out.println("6. Load Sample Menu");
            System.out.println("7. Back to Main Menu");
            int choice = readInt("Enter a choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewMenu();
                        break;
                    case 2:
                        inspectMenuItem();
                        break;
                    case 3:
                        addMenuItem();
                        break;
                    case 4:
                        editMenuItem();
                        break;
                    case 5:
                        removeMenuItem();
                        break;
                    case 6:
                        loadSampleMenu();
                        break;
                    case 7:
                        running = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Choose 1 through 7.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                pause();
            }
        }
    }

    public void viewMenu() {
        try {
            if (restaurant.getMenuItems().isEmpty()) {
                System.out.println("There are no menu items yet.");
            } else {
                System.out.println("======= Full Menu =======");
                printMenuItems();
            }
        } catch (Exception e) {
            System.out.println("Error viewing menu: " + e.getMessage());
        }
        pause();
    }

    public void inspectMenuItem() {
        try {
            MenuItem item = pickMenuItemOrBack("Enter Item ID to inspect (or 0 to go back): ");
            if (item == null) {
                return;
            }

            System.out.println("======= Menu Item Info =======");
            System.out.println("Course       : " + item.getClass().getSimpleName());
            System.out.println("Item ID      : " + item.getItemId());
            System.out.println("Name         : " + item.getName());
            System.out.println("Description  : " + item.getDescription());
            System.out.println("Base Price   : $" + item.getBasePrice());
            System.out.println("Course Price : $" + item.calculateCoursePrice() + " (Price: $" + item.getPrice() + ")");
            System.out.println("Calories     : " + item.getCalories() + " cal");
            System.out.println("Prep Time    : " + item.calculatePrepTime() + " min");
            System.out.println("Available    : " + item.isAvailable());
            System.out.println("Times Ordered: " + item.getTimesOrdered());
            System.out.println("Dietary Tags : " + item.getDietaryTags());
            System.out.println("Allergens    : " + item.getAllergens());
            System.out.println("Instructions : " + item.getServingInstructions());

            if (item instanceof Appetizer) {
                Appetizer app = (Appetizer) item;
                System.out.println("Portion Size : " + app.getPortionSize() + " (Sharers: " + app.getRecommendedSharers() + ")");
                System.out.println("Cost/Person  : $" + app.calculateCostPerPerson());
                System.out.println("Served Hot   : " + app.isServedHot());
                System.out.println("Sauces       : " + app.getDippingSauces() + " (Extra sauce fee: $" + app.getExtraSauceCharge() + ")");
            } else if (item instanceof Entree) {
                Entree entree = (Entree) item;
                System.out.println("Protein      : " + entree.getProteinType());
                System.out.println("Cooking Temp : " + entree.getCookingTemp());
                System.out.println("Sides        : " + entree.getSides().size() + " (Extra side upcharge: $" + entree.getSideUpchargeTotal() + ")");
                System.out.println("Chef Special : " + entree.isChefSpecial() + " (Markup: $" + entree.getChefSpecialMarkup() + ")");
            } else if (item instanceof Dessert) {
                Dessert dessert = (Dessert) item;
                System.out.println("Sweetness    : " + dessert.getSweetnessLevel() + "/5");
                System.out.println("Ice Cream    : " + (dessert.isServedWithIceCream() ? dessert.getIceCreamFlavor() : "None"));
                System.out.println("Toppings     : " + dessert.getToppings());
                System.out.println("Contains Nuts: " + dessert.isContainsNuts());
            }
        } catch (Exception e) {
            System.out.println("Error inspecting item: " + e.getMessage());
        }
        pause();
    }

    public void addMenuItem() {
        try {
            System.out.println("======= Add Menu Item =======");
            System.out.println("1. Appetizer");
            System.out.println("2. Entree");
            System.out.println("3. Dessert");
            System.out.println("4. Side");
            System.out.println("5. Back");
            int typeChoice = readInt("Enter a choice: ");

            if (typeChoice == 5 || typeChoice == 0) {
                return;
            }
            if (typeChoice < 1 || typeChoice > 4) {
                throw new IllegalArgumentException("Choose 1 through 5.");
            }

            String id = readLine("Item ID (e.g. A1, E1, D1, S1): ");
            String name = readLine("Item Name: ");
            String description = readLine("Description: ");
            double basePrice = readDouble("Base Price: $");
            int calories = readInt("Calories: ");

            MenuItem newItem;
            switch (typeChoice) {
                case 1:
                    String portionSize = readLine("Portion Size (Individual, Shareable, Family): ");
                    int sharers = readInt("Recommended Sharers: ");
                    boolean servedHot = readYesNo("Served hot? (y/n): ");
                    double extraSauceCharge = readDouble("Extra dipping sauce charge: $");
                    newItem = new Appetizer(
                        id, name, description, basePrice, calories,
                        portionSize, sharers, new ArrayList<String>(), servedHot, extraSauceCharge
                    );
                    break;

                case 2:
                    String protein = readLine("Protein Type (Beef, Chicken, Pork, Fish, Lamb, Plant-Based, Pasta): ");
                    String temp = readLine("Cooking Temp (Rare, Medium Rare, Medium, Medium Well, Well Done): ");
                    boolean special = readYesNo("Chef's Special? (y/n): ");
                    double markup = special ? readDouble("Chef's Special Markup: $") : 0.0;
                    newItem = new Entree(
                        id, name, description, basePrice, calories,
                        protein, temp, new ArrayList<Side>(), special, markup
                    );
                    break;

                case 3:
                    int sweetness = readInt("Sweetness Level (1-5): ");
                    boolean hasNuts = readYesNo("Contains nuts? (y/n): ");
                    boolean withIceCream = readYesNo("Serve with ice cream? (y/n): ");
                    if (withIceCream) {
                        String flavor = readLine("Ice cream flavor: ");
                        newItem = new Dessert(id, name, description, basePrice, calories, sweetness, true, flavor, hasNuts);
                    } else {
                        newItem = new Dessert(id, name, description, basePrice, calories, sweetness, hasNuts);
                    }
                    break;

                case 4:
                    if (readYesNo("Add an allergen now? (y/n): ")) {
                        ArrayList<String> allergens = new ArrayList<String>();
                        allergens.add(readLine("Allergen name: "));
                        newItem = new Side(id, name, description, basePrice, calories, allergens);
                    } else {
                        newItem = new Side(id, name, description, basePrice, calories);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Choose 1 through 5.");
            }

            restaurant.addMenuItem(newItem);
            System.out.println("Added " + newItem.getName() + " (ID: " + newItem.getItemId() + ") to the menu.");
        } catch (Exception e) {
            System.out.println("Error adding menu item: " + e.getMessage());
        }
        pause();
    }

    public void editMenuItem() {
        try {
            MenuItem item = pickMenuItemOrBack("Enter Item ID to edit (or 0 to go back): ");
            if (item == null) {
                return;
            }

            System.out.println("======= Edit " + item.getName() + " =======");
            System.out.println("1. Change Name / Description / ID");
            System.out.println("2. Change Base Price or Adjust by %");
            System.out.println("3. Change Calories or Toggle Availability");
            System.out.println("4. Edit Dietary Tags or Allergens");
            System.out.println("5. Edit Course Details (" + item.getClass().getSimpleName() + ")");
            System.out.println("6. Back");
            int choice = readInt("Enter a choice: ");

            if (choice == 6 || choice == 0) {
                return;
            }

            switch (choice) {
                case 1:
                    int fieldChoice = readInt("1 = Name, 2 = Description, 3 = Item ID: ");
                switch (fieldChoice) {
                    case 1:
                        item.setName(readLine("New Name: "));
                        break;
                    case 2:
                        item.setDescription(readLine("New Description: "));
                        break;
                    case 3:
                        item.setItemId(readLine("New Item ID: "));
                        break;
                    default:
                        throw new IllegalArgumentException("Choose 1, 2, or 3.");
                }
                    System.out.println("Updated item info: " + item.getName() + " (ID: " + item.getItemId() + ")");
                    break;


                case 2:
                    int priceChoice = readInt("1 = Set Base Price, 2 = Adjust Base Price by %, 3 = Preview Discount: ");
                switch (priceChoice) {
                    case 1:
                        item.setBasePrice(readDouble("New Base Price: $"));
                        System.out.println("Base price set to $" + item.getBasePrice());
                        break;
                    case 2:
                        double updated = item.adjustBasePrice(readDouble("Percent change (e.g. 10 or -15): "));
                        System.out.println("Adjusted base price to $" + updated);
                        break;
                    case 3:
                        double disc = item.applyDiscount(item.calculateCoursePrice(), readDouble("Discount % (0-80): "));
                        System.out.println("Discounted price preview: $" + disc);
                        break;
                    default:
                        throw new IllegalArgumentException("Choose 1, 2, or 3.");
                }
                    break;


                case 3:
                    int statusChoice = readInt("1 = Set Calories, 2 = Toggle Availability, 3 = Record Order Count: ");
                switch (statusChoice) {
                    case 1:
                        item.setCalories(readInt("New Calories: "));
                        System.out.println("Calories set to " + item.getCalories());
                        break;
                    case 2:
                        item.updateAvailability(!item.isAvailable());
                        System.out.println("Available is now: " + item.isAvailable());
                        break;
                    case 3:
                        item.recordOrder(readInt("Order count to add: "));
                        System.out.println("Times ordered: " + item.getTimesOrdered());
                        break;
                    default:
                        throw new IllegalArgumentException("Choose 1, 2, or 3.");
                }
                    break;


                case 4:
                    int tagChoice = readInt("1 = Add Tag, 2 = Remove Tag, 3 = Add Allergen, 4 = Remove Allergen: ");
                switch (tagChoice) {
                    case 1:
                        item.addDietaryTag(readLine("Dietary Tag: "));
                        break;
                    case 2:
                        item.removeDietaryTag(readLine("Dietary Tag to remove: "));
                        break;
                    case 3:
                        item.addAllergen(readLine("Allergen: "));
                        break;
                    case 4:
                        item.removeAllergen(readLine("Allergen to remove: "));
                        break;
                    default:
                        throw new IllegalArgumentException("Choose 1 through 4.");
                }
                    System.out.println("Tags: " + item.getDietaryTags() + " | Allergens: " + item.getAllergens());
                    break;


                case 5:
                    editCourseDetails(item);
                    break;

                default:
                    throw new IllegalArgumentException("Choose 1 through 6.");
            }
        } catch (Exception e) {
            System.out.println("Error editing menu item: " + e.getMessage());
        }
        pause();
    }

    public void removeMenuItem() {
        try {
            MenuItem item = pickMenuItemOrBack("Enter Item ID to remove (or 0 to go back): ");
            if (item == null) {
                return;
            }
            restaurant.removeMenuItem(item);
            System.out.println("Removed " + item.getName() + " (ID: " + item.getItemId() + ") from the menu.");
        } catch (Exception e) {
            System.out.println("Error removing menu item: " + e.getMessage());
        }
        pause();
    }

    public void loadSampleMenu() {
        try {
            if (sampleMenuLoaded) {
                System.out.println("Sa1mple menu already loaded");
                pause();
                return;
            }
            populateSampleMenuItems();
            System.out.println("Sample menu loaded.");
        } catch (Exception e) {
            System.out.println("Error loading sample menu: " + e.getMessage());
        }
        pause();
    }

    private void populateSampleMenuItems() throws DuplicateEntryException, LimitException {
        if (sampleMenuLoaded) {
            return;
        }

        Side fries = new Side("S1", "French Fries", "Crispy salted fries", 4.00, 320);
        Side slaw = new Side("S2", "Coleslaw", "Creamy house slaw", 3.50, 180);
        restaurant.addMenuItem(fries);
        restaurant.addMenuItem(slaw);

        Appetizer nachos = new Appetizer(
            "A1", "Loaded Nachos", "Tortilla chips with queso", 10.00, 600,
            "Shareable", 2, new ArrayList<>(), true, 1.00
        );
        nachos.addDippingSauce("Salsa");
        restaurant.addMenuItem(nachos);

        ArrayList<Side> steakSides = new ArrayList<>();
        steakSides.add(fries);
        steakSides.add(slaw);
        Entree ribeye = new Entree(
            "E1", "Ribeye Steak", "12oz grilled ribeye", 26.00, 700,
            "Beef", "Medium", steakSides, true, 4.00
        );
        restaurant.addMenuItem(ribeye);

        Dessert brownie = new Dessert(
            "D1", "Warm Brownie", "Fudge brownie a la mode", 7.50, 450,
            4, true, "Vanilla", false
        );
        restaurant.addMenuItem(brownie);

        sampleMenuLoaded = true;
    }

    private void editCourseDetails(MenuItem item) {
        if (item instanceof Appetizer app) {
            int sub = readInt("1 = Portion Size, 2 = Add Sauce, 3 = Remove Sauce, 4 = Extra Sauce Charge: ");
            switch (sub) {
                case 1:
                    app.changePortionSize(readLine("New Portion Size (Individual, Shareable, Family): "));
                    break;
                case 2:
                    app.addDippingSauce(readLine("Sauce to add: "));
                    break;
                case 3:
                    app.removeDippingSauce(readLine("Sauce to remove: "));
                    break;
                case 4:
                    app.setExtraSauceCharge(readDouble("New Extra Sauce Charge: $"));
                    break;
                default:
                    throw new IllegalArgumentException("Choose 1 through 4.");
            }
            System.out.println("Updated Appetizer Course Price: $" + app.calculateCoursePrice());
        } else if (item instanceof Entree entree) {
            int sub = readInt("1 = Cooking Temp, 2 = Protein Type, 3 = Add Side, 4 = Substitute Side, 5 = Chef Special: ");
            switch (sub) {
                case 1:
                    entree.customizeCookingTemp(readLine("New Cooking Temp (Rare, Medium Rare, Medium, Medium Well, Well Done): "));
                    break;
                case 2:
                    entree.setProteinType(readLine("New Protein (Beef, Chicken, Pork, Fish, Lamb, Plant-Based, Pasta): "));
                    break;
                case 3:
                    Side s = new Side(
                            readLine("Side ID: "),
                            readLine("Side Name: "),
                            readLine("Side Description: "),
                            readDouble("Side Price: $"),
                            readInt("Side Calories: ")
                    );  entree.addSide(s);
                    break;
                case 4:
                    List<Side> currentSides = entree.getSides();
                    if (currentSides.isEmpty()) {
                        throw new IllegalStateException("This entree has no sides to substitute.");
                    }   Side replacement = new Side(
                            readLine("New Side ID: "),
                            readLine("New Side Name: "),
                            readLine("New Side Description: "),
                            readDouble("New Side Price: $"),
                            readInt("New Side Calories: ")
                    );  entree.subSide(currentSides.get(0), replacement);
                    break;
                case 5:
                    boolean special = readYesNo("Mark as Chef's Special? (y/n): ");
                    double markup = special ? readDouble("Markup amount: $") : 0.0;
                    entree.configureChefSpecial(special, markup);
                    break;
                default:
                    throw new IllegalArgumentException("Choose 1 through 5.");
            }
            System.out.println("Updated Entree Course Price: $" + entree.calculateCoursePrice());
        } else if (item instanceof Dessert dessert) {
            int sub = readInt("1 = Add Topping, 2 = Add Ice Cream, 3 = Remove Ice Cream, 4 = Sweetness Level: ");
            switch (sub) {
                case 1:
                    dessert.addTopping(
                            readLine("Topping Name: "),
                            readInt("Topping Calories: "),
                            readYesNo("Contains nuts? (y/n): ")
                    );  break;
                case 2:
                    dessert.addIceCreamScoop(readLine("Ice Cream Flavor: "));
                    break;
                case 3:
                    dessert.removeIceCreamScoop();
                    break;
                case 4:
                    dessert.adjustSweetness(readInt("Sweetness Level (1-5): "));
                    break;
                default:
                    throw new IllegalArgumentException("Choose 1 through 4.");
            }
            System.out.println("Updated Dessert Course Price: $" + dessert.calculateCoursePrice());
        } else {
            System.out.println("Side item uses standard MenuItem fields (Options 1-4).");
        }
    }

    private void printMenuItems() {
        for (MenuItem item : restaurant.getMenuItems()) {
            System.out.println(
                "[" + item.getClass().getSimpleName() + "] ID: " + item.getItemId()
                + " | " + item.getName()
                + " | $" + item.calculateCoursePrice()
                + " | " + item.getCalories() + " cal"
                + " | Available: " + item.isAvailable()
            );
        }
    }

    private MenuItem pickMenuItemOrBack(String prompt) {
        if (restaurant.getMenuItems().isEmpty()) {
            throw new IllegalStateException("There are no menu items yet.");
        }
        printMenuItems();
        String id = readLine(prompt).trim();
        if (id.isEmpty() || id.equals("0") || id.equalsIgnoreCase("back")) {
            return null;
        }
        for (MenuItem item : restaurant.getMenuItems()) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        throw new IllegalArgumentException("No menu item found with ID: " + id);
    }

    private boolean readYesNo(String prompt) {
        String ans = readLine(prompt).trim();
        return ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes");
    }

    // ---------------------- Restaurant ----------------------
    public void viewSummary() {
        System.out.println("======= Restaurant Summary =======");
        System.out.println("Name: " + restaurant.getName());
        System.out.println("Tables: " + restaurant.getTables().size());
        System.out.println("Employees: " + restaurant.getEmployees().size());
        System.out.println("Reservations: " + restaurant.getReservations().size());
        System.out.println("Orders: " + restaurant.getOrders().size());
        System.out.print("Menu: " );
        viewMenu();
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
