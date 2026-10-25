package restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DessertJUnitTest {

    @Test
    public void testBaseCoursePrice() {
        Dessert dessert = new Dessert("D1", "Cheesecake", "Classic cheesecake", 8.00, 500, 3, false);

        assertEquals(8.00, dessert.calculateCoursePrice(), 0.001);
    }

    @Test
    public void testCoursePriceWithIceCreamAndToppings() {
        Dessert dessert = new Dessert("D2", "Brownie", "Warm chocolate brownie", 7.00, 450, 4, true, "Vanilla", false);
        dessert.addTopping("Hot Fudge", 100, false);
        dessert.addTopping("Sprinkles", 50, false);

        assertEquals(11.00, dessert.calculateCoursePrice(), 0.001);
    }

    @Test
    public void testAddIceCreamScoopCaloriesAndAllergen() {
        Dessert dessert = new Dessert("D3", "Apple Pie", "Spiced apple pie", 6.50, 400, 3, false);

        dessert.addIceCreamScoop("Vanilla Bean");

        assertTrue(dessert.isServedWithIceCream());
        assertEquals(580, dessert.getCalories());
        assertTrue(dessert.getAllergens().contains("Dairy"));
    }

    @Test
    public void testRemoveIceCreamScoop() {
        Dessert dessert = new Dessert("D4", "Apple Pie", "Spiced apple pie", 6.50, 400, 3, true, "Vanilla", false);

        boolean removed = dessert.removeIceCreamScoop();

        assertTrue(removed);
        assertFalse(dessert.isServedWithIceCream());
        assertEquals(400, dessert.getCalories());
    }

    @Test
    public void testAddNutToppingUpdatesAllergens() {
        Dessert dessert = new Dessert("D5", "Sundae", "Ice cream sundae", 6.00, 350, 4, false);

        dessert.addTopping("Roasted Pecans", 120, true);

        assertTrue(dessert.isContainsNuts());
        assertTrue(dessert.getAllergens().contains("Tree Nuts"));
        assertEquals(470, dessert.getCalories());
    }

    @Test
    public void testAdjustSweetness() {
        Dessert dessert = new Dessert("D6", "Sorbet", "Lemon sorbet", 5.50, 200, 2, false);

        dessert.adjustSweetness(4);

        assertEquals(4, dessert.getSweetnessLevel());
        assertEquals(250, dessert.getCalories());
    }

    @Test
    public void testInvalidSweetness() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Dessert invalidDessert = new Dessert("D7", "Cake", "Chocolate cake", 7.00, 500, 6, false);
            assertNotNull(invalidDessert);
        });
        assertNotNull(ex.getMessage());
    }
    
    @Test
    public void testCalculatePrepTime() {
        Dessert dessert = new Dessert("D8", "Lava Cake", "Molten cake", 9.00, 550, 4, true, "Vanilla", false);
        dessert.addTopping("Berries", 30, false);

        assertEquals(8, dessert.calculatePrepTime());
    }
}
