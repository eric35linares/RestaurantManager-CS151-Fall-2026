package ui;

import java.util.Scanner;
import restaurant.Restaurant;

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
            System.out.println("Welcome to restaurant simulator. Type EXIT at any time to quit.");
            System.out.println("1. Create restaurant");
            System.out.println("2. Exit");
            System.out.println("3. Default restaurant");
            int choice = readInt("Enter 1, 2, or 3: ");

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
                    this.restaurant = new Restaurant("test");
                    mainMenu();
                    break;
            }
        }
    }

    private void mainMenu() {
        boolean running = true;
        while (running) { 
            System.out.println("======= " + this.restaurant.getName() + " =======");
            System.out.println("1. Tables and seating");
            System.out.println("2. Exit");
            int choice = readInt("Enter 1 or 2: ");

            switch (choice) {
                case 1:
                    tableMenu();
                    break;

                case 2:
                    running = false;
                    break;
            }
        }
    }

    private void tableMenu() {
        boolean running = true;
        while (running) {
            System.out.println("======= Tables and Seating =======");
            System.out.println("1. View Tables");
            System.out.println("2. Add a Table");
            System.out.println("3. Seat a walk-in party");
            System.out.println("4. Clear a Table");
            System.out.println("5. Back to Main Menu");
            int choice = readInt("Enter 1, 2, 3, 4, or 5: ");

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
        }
    }

    public void viewTables() {

    }
    public void addTable() {
        
    }
    public void seatWalkIn() {
        
    }
    public void clearTable() {
        
    }

    private void exitProgram() {
        System.out.println("Bye.");
        scanner.close();
        System.exit(0);
    }

    // Helper Methods
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
