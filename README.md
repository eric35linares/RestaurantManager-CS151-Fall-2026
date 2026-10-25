# Restaurant Manager

CS151 Fall 2026 - Project 2

## Overview

Our Restaurant Manager simulates the daily operations of a restaurant. Users can run their own restaurant from the console. You can set up tables, manage staff, seat parties, manage reservations, build menus, create orders, and check out customers with receipts.

## Developers

| Team Member | GitHub | Area |
|---|---|---|
| Anson | [@ansunkk1] | Orders and Checkout: Order, Receipt, Discountable |
| Emmet	| [@emmet-dobson] | Menu: Menultem (abstract), Appetizer, Entree, Dessert, Menu |
| Eric | [@eric35linares] | Restaurant Operations: Restaurant, Table, Reservation, Employee |

## Design

The program is text-based, and will look like this.

...
Welcome to restaurant simulator. Type EXIT at any time to quit.
1. Create restaurant
2. Exit
3. FOR DEMO: Default restaurant
...

## Installation Instructions

**Clone the repo**
'''sh
git clone 
''

## Usage

When you start the program, you will see a menu where you can do the following:

- **Create restaurant** starts with an empty restaurant with a name of your choice.
- **Default restaurant** loads tables, employees, a seated party, and a reservation so most features can be tested right away. 

After choosing a restaurant, the main menu has the following choices:
| Menu | What you can do |
|---|---|
| Tables and seating | View tables, add a table, seat a walk-in party, clear a table. | 
| Employees	| View, hire, and fire employees, calculate their pay, change their role or pay rate. | 
| Reservations | View, make, check in, cancel, mark a no-show, edit reservations. | 
| Orders and checkout | Viewan order, create an order for a table, add items, check out. | 
| Menu Items | View, add, edit, remove menu items. | 
| Restaurant Summary | Show the restaurant's name, tables, employees, reservations, and orders. | 
| Rename Restaurant | Change the restaurant's name. | 

**How to interact**
- Type the number of the option you want to choose and press Enter.
- If you type something invalid, you can try again.
- Type EXIT at any point to end the program.

## Contributions

| Team Member | Contributions |
|---|---|---|
| Anson | Designed and implemented "Order", "Receipt", "Discountable" interface. Built the orders and checkout screens in the UI. Wrote the JUnit tests. Contributed to the UML diagram. |
| Emmet	| [@emmet-dobson] | Designed and implemented "MenuItem" abstract class, "Appetizer", "Entree", "Dessert, "Side", "InvalidMenuItemException". Built the menu screen in the UI and menu item management in "Restaurant". Contributed to the UML diagram. |
| Eric | [@eric35linares] | Designed and implemented "Restaurant", "Table", "Employee", "Reservation", "TableUnavailableException", "DuplicateEntryException". Built the UI/UX structure and the start, main, table, employee, and reservation screens. Wrote the README. Contributed to the UML diagram. |