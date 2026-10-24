package restaurant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class EntreeJUnitTest {
    
    @Test 
    public void testCoursePriceWithTwoIncludedSides(){
        ArrayList<Side> sides = new ArrayList<>();
        sides.add(new Side("S1", "Fries", "Crispy fries", 4.00, 300));
        sides.add(new Side("S2", "Coleslaw", "Fresh slaw", 3.50, 150));

        Entree entree = new Entree("E1", "Ribeye", "Grilled steak", 25.00, 700, "Beef", "Medium", sides, false, 0.0);

        assertEquals(0.0, entree.getSideUpchargeTotal(), 0.001);
        assertEquals(25.00, entree.calculateCoursePrice(), 0.001);
    }

    @Test
    public void testExtraSideUpcharge() {
        ArrayList<Side> sides = new ArrayList<>();
        sides.add(new Side("S1", "Fries", "Crispy fries", 4.00, 300));
        sides.add(new Side("S2", "Coleslaw", "Fresh slaw", 3.50, 150));

        Entree entree = new Entree("E2", "Ribeye", "Grilled steak", 25.00, 700, "Beef", "Medium", sides, false, 0.0);
        Side mac = new Side("S3", "Mac and Cheese", "Creamy pasta", 5.00, 400);
        entree.addSide(mac);

        assertEquals(5.00, entree.getSideUpchargeTotal(), 0.001);
        assertEquals(30.00, entree.calculateCoursePrice(), 0.001);
    }

    @Test
    public void testSidesContributeCaloriesAndAllergens() {
        ArrayList<Side> sides = new ArrayList<>();
        ArrayList<String> allergens = new ArrayList<>();
        allergens.add("Dairy");
        Side mashed = new Side("S1", "Mashed Potatoes", "Butter potatoes", 4.00, 250, allergens);
        sides.add(mashed);

        Entree entree = new Entree("E3", "Sirloin", "Grilled steak", 22.00, 500, "Beef", "Medium Rare", sides, false, 0.0);

        assertEquals(750, entree.getCalories());
        assertTrue(entree.getAllergens().contains("Dairy"));
    }

    @Test
    public void testSubSide() {
        ArrayList<Side> sides = new ArrayList<>();
        Side fries = new Side("S1", "Fries", "Crispy fries", 4.00, 300);
        Side salad = new Side("S2", "Side Salad", "Green salad", 4.00, 100);
        sides.add(fries);

        Entree entree = new Entree("E4", "Pork Chop", "Grilled chop", 18.00, 500, "Pork", "Medium", sides, false, 0.0);
        entree.subSide(fries, salad);

        assertEquals(600, entree.getCalories());
        assertTrue(entree.getSides().contains(salad));
    }

    @Test
    public void testChefSpecialMarkup() {
        ArrayList<Side> sides = new ArrayList<>();
        Entree entree = new Entree("E5", "Salmon", "Grilled fish", 20.00, 450, "Fish", "Medium", sides, false, 0.0);

        entree.configureChefSpecial(true, 6.00);

        assertEquals(26.00, entree.calculateCoursePrice(), 0.001);
    }

    @Test
    public void testInvalidCookingTemp() {
        ArrayList<Side> sides = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Entree("E6", "Ribeye", "Grilled steak", 25.00, 700, "Beef", "Burned", sides, false, 0.0);
        });
    }

    @Test
    public void testUnsafeChickenCookingTemp() {
        ArrayList<Side> sides = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Entree("E7", "Roast Chicken", "Herb chicken", 19.00, 550, "Chicken", "Rare", sides, false, 0.0);
        });
    }

    @Test
    public void testCalculatePrepTime() {
        ArrayList<Side> sides = new ArrayList<Side>();
        sides.add(new Side("S1", "Fries", "Crispy fries", 4.00, 300));

        Entree entree = new Entree("E8", "Ribeye", "Grilled steak", 25.00, 700, "Beef", "Medium", sides, false, 0.0);

        assertEquals(19, entree.calculatePrepTime());
    }
}
