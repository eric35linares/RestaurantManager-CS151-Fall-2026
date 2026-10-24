package restaurant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class ReceiptJUnitTest {
    
    @Test
    public void testTax(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        Receipt receipt = new Receipt(1, items, 0 );

        assertEquals(0.0, receipt.calculateTax(), 0.001);
    }

    @Test
    public void testTotal(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        Receipt receipt = new Receipt(2, items, 0);
        receipt.addTip(5.00);

        assertEquals(5.00, receipt.calculateTotal(), 0.001);
    }

    @Test
    public void testAddTip(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        Receipt receipt = new Receipt(3, items, 0);
        receipt.addTip(5.00);
        receipt.addTip(2.00);

        assertEquals(7.00, receipt.calculateTotal(), 0.001);
    }

    @Test
    public void testNegativeTip(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
            Receipt receipt = new Receipt(4, items, 0);
            
            assertThrows(IllegalArgumentException.class, () -> {
                receipt.addTip(-5.00);
            });
        }
    @Test
    public void testPayment(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        Receipt receipt = new Receipt(5, items, 0);
        receipt.addTip(5.00);
        receipt.recordPayment(4.00);

        assertEquals(1.00, receipt.calculateBalance(), 0.001);
    }
    @Test
    public void testTaxWithItems(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        items.add(new MenuItem("Burger", 12.00));
        Receipt receipt = new Receipt(6, items, 0);

        assertEquals(1.2, receipt.calculateTax(), 0.001);
    }

    @Test 
    public void testDiscountWithITems(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        items.add(new MenuItem("Burger", 12.00));

        Receipt receipt = new Receipt(7, items, 2.00);

        assertEquals(1.00, receipt.calculateTax(), 0.001);
    }

    @Test
    public void testTotalWithItems(){
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();

        items.add(new MenuItem("Burger", 12.00));
        items.add(new MenuItem("Fries", 6.00));
        items.add(new MenuItem("Soda", 3.00));

        Receipt receipt = new Receipt(8, items, 2.00);
        receipt.addTip(5.00);

        assertEquals(25.90, receipt.calculateTotal(), 0.001);
    }
}
