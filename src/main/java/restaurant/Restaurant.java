package restaurant;

import java.util.ArrayList;
import exceptions.*;

// The main Restaurant class where all other classes are managed

public class Restaurant {
    public static final int MAXIMUM_INSTANCES = 100;

    // ---------------------- Restaurant Attributes ----------------------
    private String name;
    private int nextOrderNumber;
    private ArrayList<Table> tables;
    private ArrayList<Employee> employees;
    private ArrayList<Order> orders;
    private ArrayList<Reservation> reservations; 
    private ArrayList<MenuItem> menuItems;

    // ---------------------- Restaurant Constructor ----------------------
    public Restaurant(String name) {
        setName(name);
        this.nextOrderNumber = 1;
        this.tables = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.menuItems = new ArrayList<>();
    }

    // ---------------------- Main Methods ----------------------

    // Add/Remove tables
    public Table addTable(Table newTable) 
        throws DuplicateEntryException, LimitException {
        if (newTable == null) {
            throw new IllegalArgumentException(
                "Table cannot be null.");
        }
        if (tables.contains(newTable)) {
            throw new DuplicateEntryException(
                "Table already exist.");
        }
        checkLimit(tables.size()); // Can throw exception
        tables.add(newTable);
        return newTable;
    }

    public void removeTable(Table table) 
        throws TableUnavailableException {
        if (table == null || !tables.contains(table)) {
            throw new IllegalArgumentException(
                "Invalid table.");
        }
        if (!table.isAvailable()) {
            throw new TableUnavailableException(
                "Only available tables can be removed");
        }
        tables.remove(table);
    }

    // Hire/Fire employees
    public void hireEmployee(Employee newEmployee) 
        throws DuplicateEntryException, LimitException {
        if (newEmployee == null) {
            throw new IllegalArgumentException(
                "Employee cannot be null.");
        }
        if (employees.contains(newEmployee)) {
            throw new DuplicateEntryException(
                "Employee already works at restaurant.");
        }
        checkLimit(employees.size()); // Can throw exception
        employees.add(newEmployee);
    }

    public void removeEmployee(Employee employee) {
        if (employee == null || !employees.contains(employee)) {
            throw new IllegalArgumentException(
                "Invalid employee.");
        }
        if (!employee.getAssignedTables().isEmpty()) {
            throw new IllegalStateException(
                "Employee still has tables. Release them and try again.");
        }
        employees.remove(employee);
    }

    // ---------------------- FIX THIS ----------------------
    // Create orders
    public void createOrder(Table table) 
        throws TableUnavailableException, LimitException {
        if (table == null || !tables.contains(table)) {
            throw new IllegalArgumentException(
                "Invalid table.");
        }
        if (!table.isOccupied()) {
            throw new TableUnavailableException(
                "Table is not in use.");
        }
        if (table.getCurrentOrder() != null) {
            throw new TableUnavailableException(
                "Table already has an order made.");
        }
        checkLimit(orders.size()); // Can throw exception

        Order order = new Order(nextOrderNumber++);
        table.setCurrentOrder(order);
        orders.add(order);
    }

    // Make/Cancel reservations
    public void makeReservation(String name, int partySize) 
        throws LimitException, TableUnavailableException {
        if (name == null) {
            throw new IllegalArgumentException(
                "Reservation name cannot be null.");
        }
        if (partySize < 1) {
            throw new IllegalArgumentException(
                "Party size must be at least 1.");
        }
        checkLimit(reservations.size()); // Can throw exception

        Table table = findAvailableTable(partySize);
        table.reserve();
        Reservation reservation = new Reservation(partySize, name, table);
        reservations.add(reservation);
    }

    public void cancelReservation(Reservation reservation) 
        throws TableUnavailableException {
        if (reservation == null || !reservations.contains(reservation)) {
            throw new IllegalArgumentException(
                "Invalid reservation.");
        }
        reservation.cancelReservation();
    }

    //  ---------------------- Customer Journey ----------------------

    // Seat reservation
    public void seatReservation(String name, int partySize) 
        throws TableUnavailableException {
        Reservation reservation = findReservation(name, partySize);
        reservation.checkInReservation();
    }

    // Seat party
    public void seatWalkIn(int partySize) 
        throws TableUnavailableException {
        Table table = findAvailableTable(partySize);
        table.seat(partySize);
    }

    // Assign server to table
    public void assignServer(Table table, Employee server) {
        if (table == null || !tables.contains(table)) {
            throw new IllegalArgumentException(
                "Invalid table.");
        }
        if (server == null || !employees.contains(server)) {
            throw new IllegalArgumentException(
                "Invalid server.");
        }
        server.assignTable(table);
    }

    public void clearTable(Table table) 
        throws TableUnavailableException {
        if (table == null || !tables.contains(table)) {
            throw new IllegalArgumentException(
                "Invalid table.");
        }
        Employee server = table.getServer();
        if (server != null) {
            table.clearTable();
            server.releaseTable(table);
        }
    }

    // ---------------------- Helper Methods ----------------------

    // Find reservation
    public Reservation findReservation(String name, int partySize) {
        if (name == null) {
            throw new IllegalArgumentException(
                "Reservation name cannot be null.");
        }
        if (partySize < 1) {
            throw new IllegalArgumentException(
                "Party size must be at least 1.");
        }
        Reservation reservation = null;
        for (Reservation current : reservations) {
            if (current.getCustomerName().equals(name) && current.getPartySize() == partySize) {
                reservation = current;
            }
        }
        if (reservation == null) {
            throw new IllegalStateException(
                "No reservation found for " + name);
        }
        return reservation;
    }

    // Find available table with right capacity
    public Table findAvailableTable(int partySize) 
        throws TableUnavailableException{
        if (partySize < 1) {
            throw new IllegalArgumentException(
                "Party size must be at least 1.");
        }
        Table bestTable = null;
        for (Table table : tables) {
            if (table.isAvailable() && partySize <= table.getCapacity()
                && (bestTable == null || table.getCapacity() < bestTable.getCapacity())) {
                    bestTable = table;
            }
        }
        if (bestTable == null) {
            throw new TableUnavailableException(
                "No table currently available");
        }
        return bestTable;
    }

    private void checkLimit(int currentCount) 
        throws LimitException {
        if (currentCount >= MAXIMUM_INSTANCES) {
            throw new LimitException(
                "Limit already reached.");
        }
    }

    // ---------------------- Setters ----------------------
    public void setName(String newName) {
        if (newName == null) {
            throw new IllegalArgumentException(
                "Name cannot be null.");
        }   
        this.name = newName;
    }

    

    // ---------------------- Getters ----------------------

    public String getName() {
        return this.name;
    }

    public int getOrderNumber() {
        return this.nextOrderNumber;
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
    // --------------- Menu Item ----------------------
    public ArrayList<MenuItem> getMenuItems() {
    return new ArrayList<>(menuItems);
    }

    public void addMenuItem(MenuItem item)
        throws DuplicateEntryException, LimitException {

    if (item == null) {
        throw new IllegalArgumentException("Menu item cannot be null.");
    }

    for (MenuItem existing : menuItems) {
        if (existing.getItemId().equals(item.getItemId())) {
            throw new DuplicateEntryException("Menu item already exists.");
        }
    }

    checkLimit(menuItems.size());
    menuItems.add(item);
}

public void removeMenuItem(MenuItem item) {
    if (!menuItems.contains(item)) {
        throw new IllegalArgumentException("Menu item not found.");
    }

    menuItems.remove(item);
}

   
    // ---------------------- Class Methods ----------------------

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


    // ----------------------- Menu Item ---------------------------

     public ArrayList<MenuItem> getMenuItems(){
        return this.menuItems;
    }

    public void setMenuItem(ArrayList<MenuItem> menuItems){
        if(menuItems == null){
            throw new IllegalArgumentException("Menu item lists cannot be null");
        }
        this.menuItems = new ArrayList<MenuItem>();
        for(MenuItem m: menuItems){
                addMenuItem(m);
          
        }

    }

    public void removeMenuItem(MenuItem item){
        if(item == null || !this.menuItems.remove(item)){
            throw new IllegalArgumentException(
                "Menu item cannot be found on the menu"
            );
        }
    }



    public void addMenuItem(MenuItem item){
        if(item == null){
            throw new IllegalArgumentException("Menu item cannot be null");
        }
        for(MenuItem existing: this.menuItems){
            if(existing.equals(item) || existing.getItemId().equalsIgnoreCase(item.getItemId())){
                try {
                    throw new DuplicateEntryException(
                            "Duplicate menu item: '" + item.getName() +"' (ID: " + item.getItemId() + ") already exists."
                    );
                } catch (DuplicateEntryException ex) {
                    System.getLogger(Restaurant.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        }

        int sameTypeCount = 0;
        for(MenuItem existing : this.menuItems){
            if(existing.getClass().equals(item.getClass())){
                sameTypeCount++;
            }
        }
        if(sameTypeCount >= MAXIMUM_INSTANCES){
            try {
                throw new LimitException(
                        "Cannot add " + item.getName() + ": maximum limit of " + MAXIMUM_INSTANCES + " instances reached"
                );
            } catch (LimitException ex) {
                System.getLogger(Restaurant.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        this.menuItems.add(item);
        
    }


    
}
