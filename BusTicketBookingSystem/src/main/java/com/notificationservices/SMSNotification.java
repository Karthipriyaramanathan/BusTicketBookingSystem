/**
 * 
 */
package com.notificationservices;

/**
 * @author KARTHIPRIYA R
 *
 */
public class SMSNotification extends Notification{
    private String phoneNumber;
    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
        // Code to send SMS notification to the given phone number
    }
}
