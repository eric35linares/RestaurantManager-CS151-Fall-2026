package restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class Appetizer extends MenuItem {

    public static final String PORTION_INDIVIDUAL = "Individual";
    public static final String PORTION_SHAREABLE = "Shareable";
    public static final String PORTION_FAMILY = "Family";
    public static final int MAX_DIPPING_SAUCES = 5;

    private String portionSize;
    private int recommendedSharers;
    private List<String> dippingSauces;
    private boolean servedHot;
    private double extraSauceCharge;

    public Appetizer(String itemId, String name, String description, double basePrice, int calories, String portionSize, int recommendedSharers, List<String> dippingSauces, boolean servedHot, double extraSauceCharge) {
        super(itemId, name, description, basePrice, calories);
        this.dippingSauces = new ArrayList<>();
        this.servedHot = servedHot;
        this.recommendedSharers = recommendedSharers;
        if (dippingSauces != null) {
            this.dippingSauces.addAll(dippingSauces);
        }
        setPortionSize(portionSize);
        setExtraSauceCharge(extraSauceCharge);
    }

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

    public void changePortionSize(String newPortionSize) {
        String validated = validatePortionSize(newPortionSize);
        double oldCalorieFactor = getPortionCalorieMultiplier(this.portionSize);
        double newCalorieFactor = getPortionCalorieMultiplier(validated);
        int baseIndividualCalories = (int) Math.round(getCalories() / oldCalorieFactor);
        setCalories((int) Math.round(baseIndividualCalories * newCalorieFactor));
        setPortionSize(validated);

    }

    private static String validatePortionSize(String size){
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
    }

    private static double getPortionPriceMultiplier(String portionSize) {
        if (portionSize.equalsIgnoreCase(PORTION_INDIVIDUAL)) {
            return 1.0;
        } else if (portionSize.equalsIgnoreCase(PORTION_SHAREABLE)) {
            return 1.5;
        } else if (portionSize.equalsIgnoreCase(PORTION_FAMILY)) {
            return 2.25;
        }
        throw new InvalidMenuItemException("Invalid portion size: " + portionSize);
    }
}
