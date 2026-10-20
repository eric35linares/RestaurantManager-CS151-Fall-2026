package restaurant;

import java.util.ArrayList;

public class Order{
    private int orderNumber;
    private ArrayList<MenuItem> items;
    private boolean completed;

    public Order(int orderNumber){
        this.orderNumber = orderNumber;
        items = new ArrayList<MenuItem>();
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
            total += item.getPrice();
        }
        return total;
    }

    public void completeOrder(){
        completed = true;
    }

    @Override
    public String toString(){
        return "Order #" + orderNumber + "\nItems: " + items + "\nCompleted: " + completed;
    }
}