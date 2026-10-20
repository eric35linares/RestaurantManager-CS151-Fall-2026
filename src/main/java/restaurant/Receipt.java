package restaurant;

public class Receipt {
    
private double subtotal;
private double taxRate;
private double tip;
private double amountPaid;

public Receipt(double subtotal){
    if(subtotal < 0){
        throw new IllegalArgumentException("Subtotal cannot be negative");
    }
    this.subtotal = subtotal;
    this.taxRate = 0.1075; // 10.75% tax rate
    this.tip = 0;
    this.amountPaid = 0;
}

public double calculateTax(){
    return subtotal * taxRate;
}

public void addTip(double amount){
    if(amount < 0){
        throw new IllegalArgumentException("Tip can not be negative");
    }
    tip = tip + amount;
}

public double calculateTotal(){
    return subtotal + calculateTax() + tip;
}

public void recordPayment(double amount){
    if(amount < 0){
        throw new IllegalArgumentException("Payment must be positive");
    }
    amountPaid = amountPaid + amount;
}

public double calculateBalance(){
    return calculateTotal() - amountPaid;
}

@Override
public String toString(){
    return "Subtotal: $" + subtotal + "\n" +
              "Tax: $" + calculateTax() + "\n" +
              "Tip: $" + tip + "\n" +
              "Total: $" + calculateTotal() + "\n" +
              "Amount Paid: $" + amountPaid + "\n" +
              "Balance Due: $" + calculateBalance();
 }
}