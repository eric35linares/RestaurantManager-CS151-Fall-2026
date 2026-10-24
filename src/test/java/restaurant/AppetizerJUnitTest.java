package restaurant;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import exceptions.InvalidMenuItemException;

public class AppetizerJUnitTest {

    @Test 
    public void testIndividualCoursePrice(){
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A1", "Mozzarella Sticks", "Fried cheese", 8.00, 400, "Individual", 1, sauces, true, 1.00);
        assertEquals(8.00, app.calculateCoursePrice());
    }

    @Test 
    public void testSharableCoursePrice(){
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A2", "Nachos", "Loaded nachos", 10.00, 600, "Shareable", 2, sauces, true, 1.00);
        assertEquals(15.00, app.calculateCoursePrice(), 0.001);
    }

    @Test 
    public void testExtraDippingSauceCharge(){
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A3", "Wings", "Crispy wings", 10.00, 500, "Individual", 1, sauces, true, 1.50);

        app.addDippingSauce("Marinara");
        app.addDippingSauce("Ranch");

        assertEquals(11.50, app.calculateCoursePrice(), 0.001);

    }

    @Test
    public void testAddDippingSauceCaloriesAndAllergen() {
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A4", "Fries", "Basket of fries", 6.00, 300, "Individual", 1, sauces, true, 1.00);

        app.addDippingSauce("Garlic Aioli");

        assertEquals(350, app.getCalories());
        assertTrue(app.getAllergens().contains("Dairy"));
    }

    @Test
    public void testRemoveDippingSauce() {
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A5", "Pretzel Bites", "Warm pretzels", 7.00, 350, "Individual", 1, sauces, true, 1.00);

        app.addDippingSauce("Cheese Sauce");
        app.removeDippingSauce("Cheese Sauce");

        assertEquals(350, app.getCalories());
        assertEquals(0, app.getDippingSauces().size());
    }

    @Test
    public void testCalculateCostPerPerson() {
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A6", "Sliders", "Mini burgers", 10.00, 500, "Shareable", 2, sauces, true, 1.00);

        assertEquals(7.50, app.calculateCostPerPerson(), 0.001);
    }

    @Test
    public void testCalculatePrepTime() {
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A7", "Calamari", "Fried squid", 12.00, 450, "Individual", 1, sauces, true, 1.00);

        assertEquals(8, app.calculatePrepTime());
    }

    @Test
    public void testInvalidPortionSize() {
        ArrayList<String> sauces = new ArrayList<>();

        assertThrows(InvalidMenuItemException.class, () -> {
            new Appetizer("A8", "Bruschetta", "Toasted bread", 8.00, 250, "Giant", 4, sauces, false, 1.00);
        });
    }

    @Test
    public void testInvalidExtraSauceCharge() {
        ArrayList<String> sauces = new ArrayList<>();
        Appetizer app = new Appetizer("A9", "Spring Rolls", "Crispy rolls", 8.00, 300, "Individual", 1, sauces, true, 1.00);

        assertThrows(InvalidMenuItemException.class, () -> {
            app.setExtraSauceCharge(-2.00);
        });
    }
    
}
