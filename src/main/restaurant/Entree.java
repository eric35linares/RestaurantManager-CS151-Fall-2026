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
    privateString cookingTemp;
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

    }  

    

