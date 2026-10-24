package restaurant;

import java.util.ArrayList;

public class Order implements Discountable{
    private int orderNumber;
    private ArrayList<MenuItem> items;
    private boolean completed;
    private double discountPercent;

    public Order(int orderNumber){
        this.orderNumber = orderNumber;
        items = new ArrayList<MenuItem>();
        discountPercent = 0;
        completed = false;
    }

    public void addItem(MenuItem item){
        if(!completed){
            items.add(item);
        }
    }

    public void removeItem(MenuItem item){
        if(!completed){
            items.remove(item);
        }
    }

    public double calculateSubtotal(){
        double total = 0;

        for(MenuItem item : items){
            total += item.calculateCoursePrice();
        }
        return total;
    }

    @Override
    public double applyDiscount(double price, double discountPercent){
        if(discountPercent < 0 || discountPercent > 100){
            throw new IllegalArgumentException("Invalid discount");
        }

        return price - (price * discountPercent / 100);
    }

    public void setDiscount(double percent){
        if(percent < 0 || percent > 100){
            throw new IllegalArgumentException("Invalid Discount");
        }

        if(completed){
            throw new IllegalArgumentException("Cannot add discount to completed order");
        }
            discountPercent = percent;
        }

    public double calculateDiscountedSubtotal(){
        return applyDiscount(calculateSubtotal(), discountPercent);
    }

    public void completeOrder(){
        completed = true;
    }

    public Receipt generateReceipt(){
        if(!completed){
            throw new IllegalArgumentException("Order is not yet completed");
        }

        double discount = calculateSubtotal() - calculateDiscountedSubtotal();

        return new Receipt(orderNumber, items, discount);
    }

    @Override
    public String toString(){
        return "Order #" + orderNumber + "\nItems: " + items + "\nCompleted: " + completed;
    }
}
