package restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import exceptions.InvalidMenuItemException;

public class MenuItemJUnitTest{
    @Test 
    public void testApplyDiscount(){
        MenuItem item = new Side("S1", "Fries", "Crispy fries", 10.00, 300);   
        double result = item.applyDiscount(10.00, 20.0);
        assertEquals(8.00, result, 0.001);
    }

    @Test 
    public void testNoDiscount(){
        MenuItem item = new Side("S2", "Fries", "Crispy fries", 10.00, 300);
        double result = item.applyDiscount(10.00, 0.0);
        assertEquals(10.00, result, 0.001);
    }

    @Test 
    public void testInvalidDiscount(){
        MenuItem item = new Side("S3", "Fries", "Crispy fries", 10.00, 300);
        assertThrows(InvalidMenuItemException.class, () -> {
           item.applyDiscount(10.00,  90.00);
        });
    }

    @Test 
    public void testAdjustBasePriceIncrease(){
        MenuItem item = new Side("S4", "Fries", "Crispy fries", 10.00, 300);
        double updated = item.adjustBasePrice(10.0);
        assertEquals(11.00, updated, 0.001);
    }

    @Test
    public void testAdjustBasePriceTooLow(){
        MenuItem item = new Side("S5", "Fries", "Crispy fries", 1.00, 300);
        assertThrows(InvalidMenuItemException.class, () -> {
            item.adjustBasePrice(-80.0);
        });
    }

    @Test
    public void testRecordOrder(){
        MenuItem item = new Side("S6", "Fries", "Crispy fries", 10.00, 300);
        item.recordOrder(3);
        assertEquals(3, item.getTimesOrdered());
    }

    @Test 
    public void testRecordOrderWhenUnavailable(){
        MenuItem item = new Side("S7", "Fries", "Crispy fries", 10.00, 300);
        item.updateAvailability(false);

        assertThrows(InvalidMenuItemException.class, () -> {
            item.recordOrder(1);
        });
    }

    @Test 
    public void testAddDietaryTagandAllergenConflict(){
        MenuItem item = new Side("S8", "Salad", "House salad", 6.00, 150);
        item.addDietaryTag("Vegan");
        assertTrue(item.getDietaryTags().contains("Vegan"));
        item.addAllergen("Dairy");
        assertFalse(item.getDietaryTags().contains("Vegan"));
    }

    @Test 
    public void testInvalidBasePrice(){
        assertThrows(InvalidMenuItemException.class, () -> {
            new Side("S9", "Fries", "Crispy fries", 0.1, 300);
        });
    }
    


}