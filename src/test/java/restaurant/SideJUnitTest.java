package restaurant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import exceptions.InvalidMenuItemException;

public class SideJUnitTest {

    @Test
    public void testSideCoursePrice() {
        Side side = new Side("S1", "Fries", "Crispy fries", 4.50, 320);

        assertEquals(4.50, side.calculateCoursePrice(), 0.001);
    }

    @Test
    public void testSideCalories() {
        Side side = new Side("S2", "Steamed Broccoli", "Fresh broccoli", 3.50, 110);

        assertEquals(110, side.getCalories());
    }

    @Test
    public void testSidePrepTime() {
        Side side = new Side("S3", "Mashed Potatoes", "Creamy potatoes", 4.00, 260);

        assertEquals(5, side.calculatePrepTime());
    }

    @Test
    public void testSideServingInstructions() {
        Side side = new Side("S4", "Onion Rings", "Beer-battered rings", 5.00, 410);

        assertEquals("Served on the side.", side.getServingInstructions());
    }

    @Test
    public void testSideWithAllergensConstructor() {
        ArrayList<String> allergens = new ArrayList<String>();
        allergens.add("Dairy");
        allergens.add("Gluten");

        Side side = new Side("S5", "Mac and Cheese", "Cheddar pasta", 5.50, 450, allergens);

        assertTrue(side.getAllergens().contains("Dairy"));
        assertTrue(side.getAllergens().contains("Gluten"));
    }

    @Test
    public void testInvalidSidePrice() {
        assertThrows(InvalidMenuItemException.class, () -> {
            new Side("S6", "Bad Side", "Invalid price", -1.00, 200);
        });
    }
}