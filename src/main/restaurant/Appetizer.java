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
        setPortionSize(portionSize);
        setExtraSauceCharge(extraSauceCharge);
    }

    @Override
    public int calculatePrepTime() {
        return (int) (Math.random() * (MAX_PREP_TIME - MIN_PREP_TIME + 1)) + MIN_PREP_TIME;
    }

    @Override
    public double calculateCoursePrice() {
        return getBasePrice() * (1 - getDiscountPercentage() / 100);
    }
    
}
