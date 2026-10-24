package restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import exceptions.InvalidMenuItemException;

//Abstract superclass for a menu item

public abstract class MenuItem implements Discountable{

    public static final int MAXIMUM_INSTANCES = 100;
    public static final double MINIMUM_PRICE = 0.50;
    public static final double MAX_DISCOUNT_PERCENT = 80.0;

    //  private instance fields

    private String itemId;
    private String name;
    private String description;
    private double basePrice;
    private int calories;
    private boolean available;
    private final List<String> dietaryTags;
    private final List<String> allergens;
    private int timesOrdered;

    public MenuItem(String itemId, String name, String description, double basePrice, int calories){
        setItemId(itemId);
        setName(name);
        setDescription(description);
        setBasePrice(basePrice);
        setCalories(calories);
        this.available = true;
        this.dietaryTags = new ArrayList<>();
        this.allergens = new ArrayList<>();
        this.timesOrdered = 0;
    }

    //  abstract methods    
    public abstract int calculatePrepTime();
    public abstract String getServingInstructions();
    public abstract double calculateCoursePrice();

    //  method to change basePrice field by a percentage param
    public double adjustBasePrice(double percentChange){
        if(Double.isNaN(percentChange) || percentChange < -90.0 || percentChange > 500.0){
            throw new InvalidMenuItemException("Price adjustment percentage must be between -90% and +500%");
        }
        double updatedPrice = roundCurrency(this.basePrice * (1.0 + (percentChange / 100.0)));
        if(updatedPrice < MINIMUM_PRICE){
            throw new InvalidMenuItemException(
                "Adjusted price is ($" + updatedPrice + ") cannot fall below minimum price of $" + MINIMUM_PRICE
            );
        }
        this.basePrice = updatedPrice;
            return this.basePrice;

    
    }

    public void recordOrder(int num){
        if(!this.available){
            throw new InvalidMenuItemException(
                "Cannot order '" + this.name + "'because it is currently unavailable"
            );
        }
        if(num<=0){
            throw new InvalidMenuItemException(
                "Order quantity must be a positive integer"
            );
        }
        this.timesOrdered +=num;
    }

    public boolean addDietaryTag(String tag){
        String normalized = sanitizeText(tag, "Dietary Tag");
        if(normalized.equalsIgnoreCase("Vegan")
            && (containsIgnoreCase(allergens, "Eggs") ||
                containsIgnoreCase(allergens, "Dairy") ||
                containsIgnoreCase(allergens, "Shellfish") )){
                    throw new InvalidMenuItemException(
                        "Cannot tag '" + this.name + "' as Vegan because it contains animal products"
                    );

        }
        if(normalized.equalsIgnoreCase("Dairy-Free")
            && containsIgnoreCase(allergens, "Eggs")){
                    throw new InvalidMenuItemException(
                        "Cannot tag '" + this.name + "' as Dairy-Free because it contains Dairy"
                    );

        }
        if(normalized.equalsIgnoreCase("Nut-Free")
            && (containsIgnoreCase(allergens, "Peanuts") ||
                containsIgnoreCase(allergens, "Tree Nuts"))){
                    throw new InvalidMenuItemException(
                        "Cannot tag '" + this.name + "' as Nut-Free because it contains nut products"
                    );

        }
        if(containsIgnoreCase(dietaryTags,  normalized)){
            return false;
        }
        dietaryTags.add(normalized);
        return true;
    }

    public final boolean addAllergen(String allergen){
        String normalized = sanitizeText(allergen, "Allergen");
        if(containsIgnoreCase(allergens, normalized)){
            return false;
        }
        allergens.add(normalized);
        if(normalized.equalsIgnoreCase("Dairy")){
            removeMatchingIgnoreCase(dietaryTags, "Dairy-Free");
            removeMatchingIgnoreCase(dietaryTags, "Vegan");
        }
        else if(normalized.equalsIgnoreCase("Eggs")){
            removeMatchingIgnoreCase(dietaryTags, "Vegan");
        }
        else if(normalized.equalsIgnoreCase("Peanuts") || normalized.equalsIgnoreCase("Tree Nuts")){
            removeMatchingIgnoreCase(dietaryTags, "Nut-Free");
        }
        else if(normalized.equalsIgnoreCase("Shellfish")){
            removeMatchingIgnoreCase(dietaryTags, "Vegan");
        }
        else if(normalized.equalsIgnoreCase("Gluten")){
            removeMatchingIgnoreCase(dietaryTags, "Gluten-Free");
        }

        return true;
    }

    public boolean removeAllergen(String allergen){
        String normalized = sanitizeText(allergen, "Allergen");
        return removeMatchingIgnoreCase(allergens, normalized);
    }

    public boolean removeDietaryTag(String tag){
        String normalized = sanitizeText(tag, "Dietary Tag");
        return removeMatchingIgnoreCase(dietaryTags, normalized);
    }

    public void updateAvailability(boolean isAvailable){
        this.available = isAvailable;
    }

    protected boolean removeMatchingIgnoreCase(Collection<String> collection, String value){
        return collection.removeIf(item -> item.equalsIgnoreCase(value));
    }

    public static String sanitizeText(String input, String fieldName){
        if(input == null || input.trim().isEmpty()){
            throw new InvalidMenuItemException(fieldName + " cannot be null or empty");
        }
        return input.trim();
    }

    protected boolean containsIgnoreCase(Collection<String> collection, String value){
        for(String item : collection){
            if(item.equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }

    protected double roundCurrency(double amount){
        return Math.round(amount * 100.0) / 100.0;
    }

    //getters and setters
    
    public final String getItemId() {
        return itemId;
    }

    public final void setItemId(String itemId) {
        this.itemId = sanitizeText(itemId, "Item ID");
    }

    public String getName() {
        return name;
    }
    public final void setName(String name) {
        this.name = sanitizeText(name, "Name");
    }
    public final String getDescription() {
        return description;
    }
    public final void setDescription(String description) {
        this.description = sanitizeText(description, "Description");
    }
    public final double getBasePrice() {
        return basePrice;
    }
    public final void setBasePrice(double basePrice) {
        if(Double.isNaN(basePrice) || Double.isInfinite(basePrice) || basePrice < MINIMUM_PRICE){
            throw new InvalidMenuItemException("Base price must be a valid number greater than or equal to $" + MINIMUM_PRICE);
        }
        this.basePrice = roundCurrency(basePrice);
    }
    public int getCalories() {
        return calories;
    }
    public final void setCalories(int calories) {
        if(calories < 0 || calories > 10000){
            throw new InvalidMenuItemException("Calories must be between 0 and 10,000");
        }
        this.calories = calories;
    }
    public boolean isAvailable() {
        return available;
    }
    public List<String> getDietaryTags() {
        return Collections.unmodifiableList(new ArrayList<>(dietaryTags));
    }
    public List<String> getAllergens() {
        return Collections.unmodifiableList(new ArrayList<>(allergens));
    }
    public int getTimesOrdered() {
        return timesOrdered;
    }

    public double getPrice(){
        return basePrice;
    }

    @Override
    public double applyDiscount(double originalPrice, double discountPercent){
        if(discountPercent < 0.0 || discountPercent > MAX_DISCOUNT_PERCENT){
            throw new InvalidMenuItemException("Discount percentage must be between 0% and " + MAX_DISCOUNT_PERCENT + "%");
        }
        double discountedPrice = originalPrice * (1.0 - (discountPercent / 100.0));
        return roundCurrency(discountedPrice);
    }
        
}
