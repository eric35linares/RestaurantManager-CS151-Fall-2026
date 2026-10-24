
package restaurant;

import java.util.ArrayList;

public class ReceiptTest {

    public static void main(String[] args) {

        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        items.add(new MenuItem("Burger", 12.00));
        items.add(new MenuItem("Fries", 5.00));

        Receipt receipt = new Receipt(1, items, 2.00);

        receipt.addTip(3.00);

        System.out.println(receipt);
    }
}
