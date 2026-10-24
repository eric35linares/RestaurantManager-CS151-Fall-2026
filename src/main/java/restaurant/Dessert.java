package restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import exceptions.*;

public class Dessert extends MenuItem{

    // Private static final constants
    private static final int MIN_SWEETNESS = 1;
    private static final int MAX_SWEETNESS = 5;
    private static final int MAX_TOPPINGS = 5;
    private static final double ICE_CREAM_UPCHARGE = 2.50;
    private static final double TOPPING_UPCHARGE = 0.75;
    private static final int ICE_CREAM_CALORIES = 180;

    // Private instance fields

    private int sweetnessLevel;
    private List<String> toppings;
    private boolean withIceCream;
    private String iceCreamFlavor;
    private boolean containsNuts;

    /**
     * Default Constructor
     * 
     */

    public Dessert(
            String itemId,
            String name,
            String description,
            double basePrice,
            int calories,
            int sweetness,
            boolean withIceCream, 
            String iceCreamFlavor,
            boolean nuts){
        super(itemId, name, description, basePrice, calories);
        validateSweetness(sweetness);
        this.sweetnessLevel = sweetness;
        this.toppings = new ArrayList<>();
        this.withIceCream = false;
        this.iceCreamFlavor = "None";
        this.containsNuts = nuts;

        if(containsNuts){
            addAllergen("Tree Nuts");
        }
        if(withIceCream){
            addIceCreamScoop(iceCreamFlavor);
        }
    }

    public Dessert(
        String itemId,
        String name,
        String description,
        double basePrice,
        int calories,
        int sweetness,
        boolean nuts){
    this(itemId, name, description, basePrice, calories, sweetness, false, "None", nuts);
        }

    // ===================================================================================================================
    // Overridden Abstract Methods from MenuItem
    //====================================================================================================================
    
    @Override 
    public int calculatePrepTime(){
        int prep = 6;
        if(withIceCream){
            prep+=1;
        }
        prep+=toppings.size();
        return prep;
    }

    @Override
    public double calculateCoursePrice(){
        double total = getBasePrice();
        if(withIceCream){
            total+=ICE_CREAM_UPCHARGE;
        }
        total+= toppings.size() * TOPPING_UPCHARGE;
        return roundCurrency(total);
    }

    @Override 
    public String getServingInstructions(){
        String aLaModeText = withIceCream
            ? "a la mode with 1 scoop of " + iceCreamFlavor + "ice cream"
            : "";
        String toppingsText = toppings.isEmpty()
            ? ""
            : "topped with " + String.join(" ,", toppings);  
    String nutWarning = containsNuts ? " [CONTAINS NUTS]" : "";
    return String.format(
        "DESSERT [Sweetness: %d/5]%s: Serve %s, %s.", 
                sweetnessLevel,
                nutWarning,
                aLaModeText,
                toppingsText);
    }
    
}
