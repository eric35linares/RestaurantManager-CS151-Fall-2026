package restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

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
    private double discountPercentage;
    private List<String> dietaryTags;
    private List<String> allergens;
    private int timesOrdered;

    public menuItem(String itemId, String name, String description, double basePrice, int calories){
        setItemId(itemId);
        setName(name);
        setDescription(description);
        setBasePrice(basePrice);
        setCalories(calories);
        this.available = true;
        this.discountPercentage = 0.0;
        this.dietaryTags = new ArrayList<>();
        this.allergens = new ArrayList<>();
        this.timesOrdered = 0;
    }

    //  abstract methods    
    public abstract int calculatePrepTime();
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
            this.basePrice = updatedPrice;
            return this.basePrice;
        }

    
    }


}
