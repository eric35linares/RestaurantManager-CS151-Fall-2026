package restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class Appetizer extends MenuItem {
    /*
        * Private static final constants shared across all Appetizer
        instances.
    */

    private static final String PORTION_INDIVIDUAL = "Individual";
    private static final String PORTION_SHAREABLE = "Shareable";
    private static final String PORTION_FAMILY = "Family";
    private static final int MAX_DIPPING_SAUCES = 5;

    // Private instance fields
    private String portionSize;
    private int recommendedSharers;
    private List<String> dippingSauces;
    private boolean servedHot;
    private double extraSauceCharge;

    /**
     * Constructor for Appetizer class, which initializes the fields of the superclass MenuItem and sets the specific fields for Appetizer.
     * @param itemId    unique item ID
     * @param name      name of the appetizer
     * @param description   description of the appetizer
     * @param basePrice     base price of the appetizer
     * @param calories      calories in the appetizer
     * @param portionSize   portion size of the appetizer (Individual, Shareable, Family)             
     * @param recommendedSharers number of recommended sharers for the appetizer            
     * @param dippingSauces   list of dipping sauces available for the appetizer
     * @param servedHot   boolean indicating if the appetizer is served hot 
     * @param extraSauceCharge  additional charge for each additional dipping sauce  
     */
    public Appetizer(
        String itemId,
        String name,
        String description,
        double basePrice,
        int calories,
        String portionSize,
        int recommendedSharers,
        List<String> dippingSauces,
        boolean servedHot,
        double extraSauceCharge) {

            super(itemId, name, description, basePrice, calories);
            this.dippingSauces = new ArrayList<>();
            this.servedHot = servedHot;
            setPortionSizeInternal(portionSize);
            setExtraSauceCharge(extraSauceCharge);
    }
    //=========================================================================
    // Abstract method implementations
    //=========================================================================

    @Override
    public int calculatePrepTime() {
        int prepTime = 5; // Base prep time for appetizers
        if (portionSize.equalsIgnoreCase(PORTION_SHAREABLE)) {
            prepTime = 7; // Additional time for shareable portions
        } else if (portionSize.equals(PORTION_FAMILY)) {
            prepTime = 11; // Additional time for family portions
        }
        if (servedHot) {
            prepTime += 3; // Additional time for hot appetizers
        }
        if (dippingSauces.size() > 1) {
            prepTime += (dippingSauces.size() - 1); // Additional time for each extra dipping sauce
        }
        return prepTime;
    }

    @Override
    public double calculateCoursePrice() {
        double multiplier = getPortionPriceMultiplier(this.portionSize);
        int extraSauces = Math.max(0, this.dippingSauces.size() - 1);
        double totalPrice = (getBasePrice() * multiplier) + (extraSauces * this.extraSauceCharge);
        return roundCurrency(totalPrice);
    }

    @Override
    public String getServingInstructions() {
        String tempInstructions = servedHot ? "Serve on a warmed dish." : "Serve chilled.";
        String sauceInstructions = dippingSauces.isEmpty() ? "no dipping sauces" : "ramekins of " + String.join(", ", dippingSauces);
        return String.format(
            "APPETIZER [%s | %d share plates]: %s with %s",
            portionSize,
            recommendedSharers,
            tempInstructions,
            sauceInstructions
        );
    }

    //=========================================================================
    // Unique methods for Appetizer class
    //=========================================================================

    /**
     * Changes the portion size of the appetizer and adjusts the calorie count accordingly.
     * @throws InvalidMenuItemException if the new portion size is invalid
     * @param newPortionSize
     * @return void
     */
    public void changePortionSize(String newPortionSize) {
        String validated = validatePortionSize(newPortionSize);
        double oldCalorieFactor = getPortionCalorieMultiplier(this.portionSize);
        double newCalorieFactor = getPortionCalorieMultiplier(validated);
        int baseIndividualCalories = (int) Math.round(getCalories() / oldCalorieFactor);
        setCalories((int) Math.round(baseIndividualCalories * newCalorieFactor));
        setPortionSizeInternal(validated);

    }

    /**
     * Validates the portion size input and returns the standardized portion size string.
     * @param String size (Individual, Shareable, Family)
     * @return String validated portion size (Individual, Shareable, Family)
     * @throws InvalidMenuItemException if the portion size is invalid)
     */

    private String validatePortionSize(String size){
        String cleaned = sanitizeText(size, "Portion Size");
        if(cleaned.equalsIgnoreCase(PORTION_INDIVIDUAL) || cleaned.equalsIgnoreCase(PORTION_SHAREABLE) || cleaned.equalsIgnoreCase(PORTION_FAMILY)){
            return PORTION_INDIVIDUAL;
        } else if(cleaned.equalsIgnoreCase(PORTION_SHAREABLE)){
            return PORTION_SHAREABLE;
        } else if(cleaned.equalsIgnoreCase(PORTION_FAMILY)){
            return PORTION_FAMILY;
        }
            throw new InvalidMenuItemException("Portion size must be one of: " + PORTION_INDIVIDUAL + ", " + PORTION_SHAREABLE + ", " + PORTION_FAMILY);
        }
    
    /**
     * Calculates the price multiplier based on the portion size.
     * @param portionSize
     * @return double multiplier
     * @throws InvalidMenuItemException if the portion size is invalid
     */

    private double getPortionPriceMultiplier(String portionSize) {
        if (portionSize.equalsIgnoreCase(PORTION_INDIVIDUAL)) {
            return 1.0;
        } else if (portionSize.equalsIgnoreCase(PORTION_SHAREABLE)) {
            return 1.5;
        } else if (portionSize.equalsIgnoreCase(PORTION_FAMILY)) {
            return 2.25;
        }
        throw new InvalidMenuItemException("Invalid portion size: " + portionSize);
    }

    /**
     * Calculates the calorie multiplier based on the portion size.
     * @param portionSize
     * @return double multiplier
     * @throws InvalidMenuItemException if the portion size is invalid
     */
    private double getPortionCalorieMultiplier(String portionSize) {
        if (portionSize.equalsIgnoreCase(PORTION_INDIVIDUAL)) {
            return 1.0;
        } else if (portionSize.equalsIgnoreCase(PORTION_SHAREABLE)) {
            return 1.5;
        } else if (portionSize.equalsIgnoreCase(PORTION_FAMILY)) {
            return 2.25;
        }
        throw new InvalidMenuItemException("Invalid portion size: " + portionSize);
    }

    public double calculateCostPerPerson() {
        double totalPrice = calculateCoursePrice();
        return roundCurrency(totalPrice / recommendedSharers);
    }

    //getters and setters

    public String getPortionSize() {
        return portionSize;
    }
    private void setPortionSizeInternal(String portionSize) {
        String validated = validatePortionSize(portionSize);
        this.portionSize = validated;
        if(validated.equalsIgnoreCase(PORTION_INDIVIDUAL)){
            this.recommendedSharers = 1;
        } else if(validated.equalsIgnoreCase(PORTION_SHAREABLE)){
            this.recommendedSharers = 2;
        } else if(validated.equalsIgnoreCase(PORTION_FAMILY)){
            this.recommendedSharers = 4;
        }
        
    }
    public int getRecommendedSharers() {
        return recommendedSharers;
    }
    public List<String> getDippingSauces() {
        return Collections.unmodifiableList(new ArrayList<>(dippingSauces));
    }
    public boolean isServedHot() {
        return servedHot;
    }



}
