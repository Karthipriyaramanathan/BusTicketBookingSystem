/**
 * 
 */
package com.paymentservices;

/**
 * @author KARTHIPRIYA R
 *
 */
public class CashPayment extends Payment {
    public CashPayment(double amount) {
        super(amount);
    }
    @Override
    public void processPayment() {
        System.out.println("You have chosen to pay with cash.");
        System.out.println("Please hand over Rs." + amount + ".");
        System.out.println("Payment successful.....");
    }
    @Override
    public void cancel() {
        System.out.println("Cancelling cash payment...");
        refund();
    }
    @Override
    public void refund() {
        System.out.println("Refunding Rs." + amount + " in cash.");
        System.out.println("Refund successful.");
    }
}