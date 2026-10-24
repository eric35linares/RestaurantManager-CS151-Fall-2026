package restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Entree extends MenuItem {
    // private static final constants
    private static final int INCLUDED_SIDES = 2;
    private static final int MAX_SIDES = 5;
    private final List<String> VALID_PROTEINS = Arrays.asList(
        "Beef", "Chicken", "Pork", "Fish", "Lamb", "Plant-Based", "Pasta");
    private static final List<String> VALID_TEMPS = Arrays.asList(
        "Rare", "Medium Rare", "Medium", "Medium Well", "Well Done");
    
    //private instance fields
    private String proteinType;
    private String cookingTemp;
    private final List<Side> sides;
    private boolean chefSpecial;
    private double chefSpecialMarkup;

    public Entree(  String itemId,
                    String name,
                    String description,
                    double basePrice,
                    int calories,
                    String proteinType,
                    String cookingTemp,
                    List<Side> sides,
                    boolean chefSpecial,
                    double chefSpecialMarkup) {
            super(itemId, name, description, basePrice, calories);
            setProteinType(proteinType);
            this.sides = new ArrayList<>();
            if(sides != null){
                for(Side side : sides){
                    addSide(side);
                }
            }
            this.chefSpecial = chefSpecial;
            this.chefSpecialMarkup = chefSpecialMarkup;
            customizeCookingTemp(cookingTemp);

        }

    //=========================================================================
    // Override abstract methods from MenuItem
    //=========================================================================

    @Override
    public int calculatePrepTime() {
        int basePrepTime = 14;  // Base prep time in minutes
        if(proteinType.equalsIgnoreCase("Beef") || proteinType.equalsIgnoreCase("Lamb")){
            basePrepTime += 2; // Extra time for beef and lamb
        } else if(proteinType.equalsIgnoreCase("Chicken") || proteinType.equalsIgnoreCase("Pork")){
            basePrepTime += 4; // Less time for fish
        } else if(proteinType.equalsIgnoreCase("Fish")){
            basePrepTime -= 1; // Less time for fish
        } else if(proteinType.equalsIgnoreCase("Plant-Based") || proteinType.equalsIgnoreCase("Pasta")){
            basePrepTime -= 2; // Extra time for plant-based and pasta
        }

        if(cookingTemp.equalsIgnoreCase("Well Done")){
            basePrepTime += 3; // Extra time for well-done
        } else if(cookingTemp.equalsIgnoreCase("Medium Well")){
            basePrepTime += 2; // Extra time for medium-well
        } else if(cookingTemp.equalsIgnoreCase("Medium")){
            basePrepTime += 1; // Extra time for medium
        } else if(cookingTemp.equalsIgnoreCase("Rare")){
            basePrepTime -= 1; // Less time for rare
        }

        basePrepTime += sides.size() * 2; // Add 2 minutes for each side

        return basePrepTime;
    }

    @Override
    public String getServingInstructions() {
        List<String> sideNames = new ArrayList<>();
        for(Side side : this.sides){
            sideNames.add(side.getName());
        }
        String sidesText = sideNames.isEmpty() ? "no sides" : String.join(", ", sideNames);
        String specialText = chefSpecial ? "[CHEF SPECIAL] " : "";
        return String.format(
            "%sENTREE [%s | Cooking Temp:]%s: Plate protein with %s sides.",
            specialText,
            proteinType,
            cookingTemp,
            sidesText);
    }

    @Override 
    public double calculateCoursePrice() {
        return roundCurrency(getBasePrice() + getSideUpchargeTotal() + (chefSpecial ? chefSpecialMarkup : 0.0));
    }

    //=========================================================================
    // Unique methods for Entree
    //=========================================================================

    public final void customizeCookingTemp(String cookingTemp) {
        String cleaned = sanitizeText(cookingTemp, "Cooking Temperature");
        String matched = null;
        for(String valid : VALID_TEMPS){
            if(valid.equalsIgnoreCase(cleaned)){
                matched = valid;
                break;
            }
        }
        if(matched == null){
            throw new IllegalArgumentException("Invalid cooking temperature. Valid options are: " + String.join(", ", VALID_TEMPS));
        }
        if(proteinType.equalsIgnoreCase("Chicken") && !matched.equalsIgnoreCase("Well-Done")){
            throw new IllegalArgumentException("Food safety violation: Poultry entrees must be cooked 'Well-Done' (requested: \"" + matched + "\").");
        }

        if((proteinType.equalsIgnoreCase("Plant-Based") ||
           proteinType.equalsIgnoreCase("Pasta"))
           && (matched.equalsIgnoreCase("Rare") || matched.equalsIgnoreCase("Medium Rare") || matched.equalsIgnoreCase("Medium") || matched.equalsIgnoreCase("Medium Well") || matched.equalsIgnoreCase("Well Done"))){
            throw new IllegalArgumentException(proteinType + " entrees cannot be ordered '" + matched + "'.");
        }
        this.cookingTemp = matched;
    }

    public void subSide(Side currentSide, Side newSide){
        if(currentSide == null || currentSide.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Current side cannot be null or empty");
        }
        if(newSide == null || newSide.getName().trim().isEmpty()){
            throw new IllegalArgumentException("New side cannot be null or empty");
        }
        int index = sides.indexOf(currentSide);
        if(index == -1){
            throw new IllegalArgumentException("Current side \"" + currentSide + "\" not found in the entree's sides.");
        }
        Side removed = sides.set(index, newSide);
        setCalories(Math.max(0, getCalories() - removed.getCalories() + newSide.getCalories()));
        for(String allergen: removed.getAllergens()){
            removeAllergen(allergen);
        }
        for(String allergen: newSide.getAllergens()){
            addAllergen(allergen);
        }

    }

    public final void addSide(Side newSide){
        if(newSide == null || newSide.getName().trim().isEmpty()){
            throw new IllegalArgumentException("New side cannot be null or empty");
        }
        if(sides.size() >= MAX_SIDES){
            throw new IllegalArgumentException("Cannot add more than " + MAX_SIDES + " sides to an entree.");
        }
        sides.add(newSide);
        setCalories(getCalories() + newSide.getCalories());
        for(String allergen: newSide.getAllergens()){
            addAllergen(allergen);
        }
    }

    public void configureChefSpecial(boolean special, double markup){
        if(special && (Double.isNaN(markup) || markup < 0.0 || markup > 50.0)){
            throw new IllegalArgumentException("Chef's Special markup must be between $0.00 and $50.00");
        }
        this.chefSpecial = special;
        this.chefSpecialMarkup = markup;
    }

    //===================================================================================================
    // Getters and Setters
    //===================================================================================================

    public String getProteinType(){
        return proteinType;
    }
    public final void setProteinType(String protein){
        String cleaned = sanitizeText(protein, "Protein type");
        String matched = null;
        for(String valid : VALID_PROTEINS){
            if(valid.equalsIgnoreCase(cleaned)){
                matched = valid;
                break;
            }
        }
        if(matched == null){
            throw new IllegalArgumentException(
            "Invalid protein type '" + proteinType + "'. Allowed values: " + String.join(", ", VALID_PROTEINS)
            );
        }
        this.proteinType = matched;
    }

    public String getCookingTemp(){
        return cookingTemp;
    }

    public List<Side> getSides(){
        return Collections.unmodifiableList(new ArrayList<>(sides));
    }

    public double getSideUpchargeTotal(){
        double total = 0.0;
        for(int i = INCLUDED_SIDES; i < sides.size(); i++){
            total+=sides.get(i).calculateCoursePrice();
        }
        return roundCurrency(total);
    }

    public boolean isChefSpecial(){
        return chefSpecial;
    }

    public double getChefSpecialMarkup(){
        return chefSpecialMarkup;
    }

    //===================================================================================================
    // Object Overrides
    //===================================================================================================

    @Override 
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Entree)){
            return false;
        }
        Entree entree = (Entree) o;
        if(getItemId().equalsIgnoreCase(entree.getItemId())){
            return true;
        }
        return super.equals(o)
            && proteinType.equalsIgnoreCase(entree.proteinType)
            && cookingTemp.equalsIgnoreCase(entree.cookingTemp);
    }
    
    @Override 
    public int hashCode(){
        return Objects.hash(super.hashCode(), proteinType.toLowerCase(), cookingTemp.toLowerCase());
    }

    @Override 
    public String toString(){
        List<String> sideNames = new ArrayList<>();
        for(Side side: sides){
            sideNames.add(side.getName());
        }
        return super.toString() + String.format(
            " | Protein: %s | Doneness: %s | Sides: %s (UpchargeL $%.2f", 
        proteinType,
        cookingTemp,
        sideNames.isEmpty() ? "None" : String.join(", ", sidesNames),
        getSideUpchargeTotal());
    }

}


