package restaurant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrderJUnitTest{

    private MenuItem createTestItem(String name, double price){
    return new Entree(
        "TEST-" + name,
        name,
        "Test food item",
        price,
        500,
        "Beef",
        "Medium",
        new java.util.ArrayList<Side>(),
        false,
        0.0
    );
}

    @Test
    public void testDiscount(){

        Order order = new Order(1);

        double result = order.applyDiscount(100,10);
        assertEquals(90.0, result, 0.001);
    }
    @Test
    public void testNoDiscount(){
        Order order = new Order(2);

        double result = order.applyDiscount(100, 0);
        assertEquals(100.0, result, 0.001);
    }

    @Test
    public void testFullDiscount(){
        Order order = new Order(3);

        double result = order.applyDiscount(100, 100);
        assertEquals(0.0, result, 0.001);
    }
    @Test
     public void testInvalidDiscount(){
        Order order = new Order(4);

        assertThrows(IllegalArgumentException.class, () -> {
            order.applyDiscount(100, 150);
        });
    }
    @Test
    public void testEmptyOrder(){
        Order order = new Order(5);

        assertEquals(0.0, order.calculateSubtotal(), 0.001);
    }

    @Test
    public void testDiscountedSubtotal(){
        Order order = new Order(6);
        order.setDiscount(10);
        assertEquals(0.0, order.calculateDiscountedSubtotal(), 0.001);
    }

    @Test
    public void testReceiptBeforeCompletion(){
        Order order = new Order(7);

        assertThrows(IllegalArgumentException.class, () -> {
            order.generateReceipt();
        });
    }

    @Test
    public void testAddItem(){
        Order order = new Order(8);

        MenuItem burger = createTestItem("Burger", 12.00);

        order.addItem(burger);

        assertEquals(12.00, order.calculateSubtotal(), 0.001);
    }

    @Test
    public void testRemoveItem(){
        Order order = new Order(9);

        MenuItem burger = createTestItem("Burger", 12.00);
        MenuItem fries = createTestItem("Fries", 5.00);

        order.addItem(burger);
        order.addItem(fries);

        order.removeItem(fries);

        assertEquals(12.00, order.calculateSubtotal(), 0.001);
    }

    @Test
    public void testMultipleItems(){
        Order order = new Order(9);

        MenuItem burger = createTestItem("Burger", 12.00);
        MenuItem fries = createTestItem("Fries", 5.00);
        MenuItem soda = createTestItem("Soda", 3.00);

        order.addItem(burger);
        order.addItem(fries);
        order.addItem(soda);

        assertEquals(20.00, order.calculateSubtotal(), 0.001);
    }
}