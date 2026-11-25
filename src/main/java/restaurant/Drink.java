package restaurant;

public class Drink extends MenuItem {

    // Private static final constants
    private static final int MIN_VOLUME = 4;
    private static final int MAX_VOLUME = 64;
    private static final float MAX_STRENGTH = 0.15f;
    private static final double ALCOHOLIC_UPCHARGE = 2.50; //this is per oz
    private static final int ALCOHOL_CALORIES = 98;

    // Private instance fields
    private String drinkFlavor;
    private boolean isAlcoholic;
    private float alcoholicStrength;
    private float volume;

    /**
     * Default Constructor
     * 
     */

    public Drink(
            String itemId,
            String name,
            String description,
            double basePrice,
            int calories,
            float strength,
            boolean isAlcoholic, 
            String drinkFlavor,
            float volume
            ) {
        super(itemId, name, description, basePrice, calories);
        if (isAlcoholic)
            validateStrength(strength);
        validateVolume(volume);
        this.alcoholicStrength = strength;
        this.isAlcoholic = isAlcoholic;
        this.drinkFlavor = drinkFlavor;
        this.volume = volume;
        if (isAlcoholic) {
            setCalories(getCalories() + ALCOHOL_CALORIES);
        }
    }

    public Drink(
        String itemId,
        String name,
        String description,
        double basePrice,
        int calories,
        float strength,
        float volume
        ){
            this(itemId, name, description, basePrice, calories, strength, false, "None", volume);
        }

    // ===================================================================================================================
    // Overridden Abstract Methods from MenuItem
    //====================================================================================================================
    
    @Override 
    public int calculatePrepTime(){
        int prep = 1;
        if (isAlcoholic){
            prep+=2;
        }

        int volumeTime = (int) volume / 32;
        prep+=volumeTime;
        return prep;
    }

    @Override
    public double calculateCoursePrice(){
        double total = getBasePrice();
        if(isAlcoholic){

            total+= ALCOHOLIC_UPCHARGE*(alcoholicStrength+1.0f);
        }
        if (volume > 32)
            total += 2.0;
        else if (volume > 12)
            total += 1.0;
        
        total = roundCurrency(total);
        return total;
    }

    @Override 
    public String getServingInstructions(){
        int formattedStrength = Math.round(alcoholicStrength * 100);
        String alcoholText = isAlcoholic
            ? "after asking for Customer ID. 21+ only."
            : "";
    return String.format(
        "DRINK [Strength: %d.00%]: Serve %s", 
                formattedStrength,
                alcoholText);
    }

    // ===================================================================================================================
    // Unique Methods for Drink
    //======================================================================================================

    public void validateStrength(float strength) {
        
        if(strength < 0.0f || strength > MAX_STRENGTH){
            throw new IllegalArgumentException(
                "An alcoholic beverage's strength must be between 0.0 and " + MAX_STRENGTH);
        }
    }

    public void validateVolume(float volume) {
        
        if (volume < MIN_VOLUME || volume > MAX_VOLUME) {
            throw new IllegalArgumentException(
                "A beverage's volume must be between" + MIN_VOLUME + " and " + MAX_VOLUME);
        }
    }

    public String getDrinkFlavor() {
        return drinkFlavor;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public float getAlcoholicStrength() {
        return alcoholicStrength;
    }

    public float getVolume() {
        return volume;
    }


    //=========================================================================
    // Overrides
    //=========================================================================

    @Override 
    public String toString(){
        return super.toString() + String.format(
            " | Strength: %.2f | Flavor: %s | Volume: %.0f oz",
            alcoholicStrength,
            drinkFlavor,
            volume
        );
    }
}
