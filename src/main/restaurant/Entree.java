package restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Collections;

import java.util.Arrays;

public class Entree extends MenuItem {
    // private static final constants
    private static final int MAX_SIDES = 4;
    private final List<String> VALID_PROTEINS = Arrays.asList(
        "Beef", "Chicken", "Pork", "Fish", "Lamb", "Plant-Based", "Pasta");
    private static final List<String> VALID_TEMPS = Arrays.asList(
        "Rare", "Medium Rare", "Medium", "Medium Well", "Well Done");
    
    //private instance fields
    private String proteinType;
    private String cookingTemp;
    private List<String> sides;
    private double sidePrice;
    private boolean chefSpecial;
    private double chefSpecialMarkup;

    public Entree(  String itemId,
                    String name,
                    String description,
                    double basePrice,
                    int calories,
                    String proteinType,
                    String cookingTemp,
                    List<String> sides,
                    double sidePrice,
                    boolean chefSpecial,
                    double chefSpecialMarkup) {
            super(itemId, name, description, basePrice, calories);
            setProteinType(proteinType);
            this.sides = new ArrayList<>();
            if(sides != null){
                for(String side : sides){
                    String cleaned = sanitizeText(side, "Side");
                    if(this.sides.size() >= MAX_SIDES){
                        throw new InvalidMenuItemException("Cannot add more than " + MAX_SIDES + " sides to an entree");
                    }
                    this.sides.add(cleaned);
                }
            }
            this.sidePrice = sidePrice;
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
        String sidesText = sides.isEmpty() ? "no sides" : String.join(", ", sides);
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
        double totalPrice = getBasePrice() + (sidePrice * sides.size());
        if(chefSpecial){
            totalPrice += totalPrice * (chefSpecialMarkup / 100.0);
        }
        return roundCurrency(totalPrice);
    }

    //=========================================================================
    // Unique methods for Entree
    //=========================================================================

    public void customizeCookingTemp(String cookingTemp) {
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

    public void subSide(String currentSide, String newSide){
        String target = sanitizeText(currentSide, "Current Side");
        String replacement = sanitizeText(newSide, "New Side");
        if(!removeMatchingIgnoreCase(sides, target)){
            throw new IllegalArgumentException("Side \"" + target + "\" not found in the current sides list.");
        }
        int indexToReplace = -1;
        for(int i = 0; i < sides.size(); i++){
            if(sides.get(i).equalsIgnoreCase(target)){
                indexToReplace = i;
                break;
            }
        }
        if(indexToReplace == -1){
            throw new IllegalArgumentException("Unexpected error: Side \"" + target + "\" was removed but not found for replacement.");
        }
            sides.set(indexToReplace, replacement);
    }
}

