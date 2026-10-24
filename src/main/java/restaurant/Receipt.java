package restaurant;

import java.util.ArrayList;

public class Receipt {
    
private double subtotal;
private double taxRate;
private double tip;
private double amountPaid;
private double discount;
private ArrayList<MenuItem> items;
private int orderNumber;


public Receipt(int orderNumber, ArrayList<MenuItem> items, double discount){
    this.orderNumber = orderNumber;
    this.items = new ArrayList<MenuItem>(items);
    this.discount = discount;

    subtotal = 0;

    for(MenuItem item : items){
        subtotal += item.calculateCoursePrice();
    }

    if(discount < 0 || discount > subtotal){
        throw new IllegalArgumentException("Invalid Discount");
    }

    taxRate = 0.10;
    tip = 0;
    amountPaid = 0;
}

public double calculateTax(){
    return (subtotal - discount) * taxRate;
}

public void addTip(double amount){
    if(amount < 0){
        throw new IllegalArgumentException("Tip can not be negative");
    }
    tip = tip + amount;
}

public double calculateTotal(){
    return subtotal - discount + calculateTax() + tip;
}

public void recordPayment(double amount){
    if(amount <= 0){
        throw new IllegalArgumentException("Payment must be positive");
    }
    amountPaid = amountPaid + amount;
}

public double calculateBalance(){
    return calculateTotal() - amountPaid;
}

@Override
public String toString(){
    String 
    result = "STAR HOUSE CAFE\n";
    result += "Order #" + orderNumber + "\n";
    result += "--------------------\n";

    for(MenuItem item : items){
        result += String.format("%s - $%.2f\n", item.getName(), item.calculateCoursePrice());
    }
    result += "--------------------\n";
    result += String.format("Subtotal: $%.2f\n", subtotal);
    result += String.format("Discount: -$%.2f\n",discount);
    result += String.format("Tax: $%.2f\n", calculateTax());
    result += String.format("Tip: $%.2f\n", tip);
    result += String.format("Total $%.2f\n", calculateTotal());

    return result;
}
}
