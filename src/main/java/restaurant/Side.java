package restaurant;

public class Side extends MenuItem {
    private static final int BASE_PREP_TIME = 5; // in minutes 

    public Side(String itemId, String name, String description, double basePrice, int calories) {
        super(itemId, name, description, basePrice, calories);
    }

    public Side(String itemId, String name, String description, double basePrice, int calories, List<String> allergens){
        super(itemId, name, description, basePrice, calories);
        if(allergens != null){
            for(String allergen : allergens){
                addAllergen(allergen);
            }
        }
    }

    @Override 
    public int calculatePrepTime() {
        return BASE_PREP_TIME;
    }

    @Override 
    public String getServingInstructions() {
        return "Served on the side.";
    }

    @Override 
    public double calculateCoursePrice() {
        return getBasePrice();
    }

    //getters and setters

    public String getName() {
        return super.getName();
    }
    
}
