/**
 * 
 */
package com.paymentservices;

/**
 * The CashPayment class is the class that implements an application the
 * bus ticket booking system and this class inherits the abstract class called 
 * Payment and it implements the abstact methods of parent class
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class CashPayment extends Payment {
    public CashPayment(double amount) {
        super(amount);
    }
    @Override
    public void processPayment() {
    	System.out.println("---------------- PAYMENT ---------------------");
        System.out.println("You have chosen to pay with cash.");
        System.out.println("Please hand over Rs." + amount + ".");
        System.out.println("Payment successful.....");
        System.out.println("----------------------------------------------");
    }
    @Override
    public void cancel() {
        System.out.println("Cancelling cash payment...");
        refund();
    }
    @Override
    public void refund() {
    	System.out.println("--------------- REFUNDING ----------------------");
        System.out.println("Refunding Rs." + amount + " in cash.");
        System.out.println("Refund successful.");
        System.out.println("------------------------------------------------");
    }
}