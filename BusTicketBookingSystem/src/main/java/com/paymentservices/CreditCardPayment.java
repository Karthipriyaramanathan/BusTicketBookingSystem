/**
 * 
 */
package com.paymentservices;

import java.util.Scanner;

import com.authenticationservices.Validation;
import com.exceptionhandling.InvalidInputException;
import com.jdbcservice.JdbcConnection;

/**
 * The CreditCardPayment class is the class that implements an application the
 * bus ticket booking system and this class inherits the abstract class called 
 * Payment and it implements the abstact methods of parent class
 * 
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private Scanner scanner;
    public CreditCardPayment(double amount, Scanner scanner) {
        super(amount);
        this.scanner = scanner;
    }
    @Override
    public void processPayment() throws InvalidInputException {
        // Get card details
        getCardDetails();
        System.out.println("---------------- PAYMENT ---------------------");
        System.out.println("Processing card payment...");
        System.out.println("Payment of Rs." + amount + " processed with credit card. Thank you!");
        System.out.println("----------------------------------------------");
    }
    private void getCardDetails() throws InvalidInputException {

        while (true) {
            System.out.print("Enter your card number: ");
            cardNumber = scanner.next();
            if (Validation.isValidCardNumber(cardNumber)) {
                break;
            } else {
                System.out.println("Invalid card number! Please enter a valid 16-digit card number.");
            }
        }
        
        // Expiration date validation
        while (true) {
            System.out.print("Enter expiration date (MM/YYYY): ");
            expirationDate = scanner.next();
            if (Validation.isValidExpirationDate(expirationDate)) {
                break;
            } else {
                System.out.println("Invalid expiration date! Please enter a valid date in MM/YYYY format.");
            }
        }
        // CVV validation
        while (true) {
            System.out.print("Enter CVV: ");
            cvv = scanner.next();
            if (Validation.isValidCVV(cvv)) {
                break;
            } else {
                System.out.println("Invalid CVV! Please enter a 3-digit CVV.");
            }
        }
        
    }
    @Override
    public void cancel() {
        System.out.println("Cancelling credit card payment...");
        refund();
    }

    @Override
    public void refund() {
    	System.out.println("--------------- REFUNDING ----------------------");
        System.out.println("Refunding Rs." + amount + " to credit card.");
        System.out.println("Refund successful.");
        System.out.println("------------------------------------------------");
    }
}
