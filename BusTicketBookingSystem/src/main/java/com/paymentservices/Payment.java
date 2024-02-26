/**
 * 
 */
package com.paymentservices;

import com.exceptionhandling.InvalidInputException;

/**
 * @author KARTHIPRIYA R
 *
 */
public abstract class Payment {
	protected double amount;
    public Payment(double amount) {
        this.amount = amount;
    }
    public abstract void processPayment() throws InvalidInputException;
    public abstract void refund();
    public abstract void cancel();
}
