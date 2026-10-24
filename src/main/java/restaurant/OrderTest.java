
package restaurant;

public class OrderTest {

    public static void main(String[] args) {

        Order order = new Order(1);

        MenuItem burger = new MenuItem("Burger", 12.00);
        MenuItem fries = new MenuItem("Fries", 5.00);
        MenuItem soda = new MenuItem("Soda", 3.00);

        order.addItem(burger);
        order.addItem(fries);
        order.addItem(soda);

        order.removeItem(fries);

        order.setDiscount(150);

        order.completeOrder();

        Receipt receipt = order.generateReceipt();
        receipt.addTip(3);

        System.out.println(receipt);
    }
}
