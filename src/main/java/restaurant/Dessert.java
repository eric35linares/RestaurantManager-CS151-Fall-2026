package restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            ? "a la mode with 1 scoop of " + iceCreamFlavor + " ice cream"
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


    //===================================================================================================================
    // UNIQUE DESSERT METHODS
    //===================================================================================================================

    public boolean addTopping(String topping, int toppingCalories, boolean hasNuts){
        String cleaned = sanitizeText(topping, "Topping");
        if(toppings.size()>= MAX_TOPPINGS){
            throw new IllegalArgumentException("Cannot add more than " + MAX_TOPPINGS + "toppings to a dessert");
        }
        if(toppingCalories < 0 || toppingCalories > 1000){
            throw new IllegalArgumentException("Topping calories must be between 0 and 1,000");
        }
        toppings.add(cleaned);
        setCalories(getCalories() + toppingCalories);

        String lower = cleaned.toLowerCase();
        if(hasNuts || lower.contains("pecan") || lower.contains("walnut")
             || lower.contains("almond") || lower.contains("hazelnut")
             || lower.contains("pistachio")){
            this.containsNuts = true;
            addAllergen("Tree Nuts");
        }
        if(lower.contains("peanut")){
            this.containsNuts = true;
            addAllergen("Peanuts");
        }
        return true;
    }

    public void addIceCreamScoop(String flavor){
        String cleaned = sanitizeText(flavor, "Ice cream flavor");
        if(cleaned.equalsIgnoreCase("None")){
            throw new IllegalArgumentException("Please specify an ice cream flavor");
        }
        if(!this.withIceCream){
            setCalories(getCalories() + ICE_CREAM_CALORIES);
        }
        this.withIceCream = true;
        this.iceCreamFlavor = cleaned;
        addAllergen("Dairy");

        String lower = cleaned.toLowerCase();
        if(lower.contains("pistachio") || lower.contains("pecan")
                    || lower.contains("almond")){
            this.containsNuts = true;
            addAllergen("Tree Nuts");
        }
        if(lower.contains("peanut")){
            this.containsNuts = true;
            addAllergen("Peanuts");
        }

    }

    public boolean removeIceCreamScoop(){
        if(!this.withIceCream){
            return false;
        }
        this.withIceCream = false;
        this.iceCreamFlavor = "None";
        setCalories(Math.max(0, getCalories() - ICE_CREAM_CALORIES));
        return true;
    }


    public void adjustSweetness(int sweetness){
        validateSweetness(sweetness);
        int delta = sweetness - this.sweetnessLevel;
        int updatedCalories = Math.max(0, getCalories() + (delta * 25));
        setCalories(updatedCalories);
        this.sweetnessLevel = sweetness;
    }

//===================================================================================================================
// Getters, Setters and Helpers
//===================================================================================================================

    private static void validateSweetness(int level){
        if(level < MIN_SWEETNESS || level > MAX_SWEETNESS){
            throw new IllegalArgumentException(
                "Sweetness level must be between " + MIN_SWEETNESS + " and " + MAX_SWEETNESS);
        }
    }

    public int getSweetnessLevel(){
        return sweetnessLevel;
    }

    public List<String> getToppings(){
        return Collections.unmodifiableList(new ArrayList<>(toppings));
    }

    public boolean isServedWithIceCream(){
        return withIceCream;
    }

    public String getIceCreamFlavor(){
        return iceCreamFlavor;
    }

    public boolean isContainsNuts(){
        return containsNuts;
    }

//=========================================================================
// Overrides
//=========================================================================

    @Override 
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Dessert)){
            return false;
        }
        Dessert dessert = (Dessert) o;
        if(getItemId().equalsIgnoreCase(dessert.getItemId())){
            return true;
        }

        return super.equals(o)
                && sweetnessLevel == dessert.sweetnessLevel
                && withIceCream == dessert.withIceCream
                && containsNuts == dessert.containsNuts
                && iceCreamFlavor.equalsIgnoreCase(dessert.iceCreamFlavor);  
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            super.hashCode(),
            sweetnessLevel,
            withIceCream,
            iceCreamFlavor.toLowerCase(),
            containsNuts
        );
    }

    @Override 
    public String toString(){
        return super.toString() + String.format(
            " | Sweetness: %d/%d | Ice Cream: %s | Toppings: %s",
            sweetnessLevel,
            MAX_SWEETNESS,
            withIceCream ? iceCreamFlavor : "No",
            toppings.isEmpty() ? "None" : String.join(", ", toppings));
    }

}
