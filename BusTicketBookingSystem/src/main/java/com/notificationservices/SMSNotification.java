/**
 * 
 */
package com.notificationservices;

/**
 * 
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
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
        // to send SMS notification to the given phone number
    }
}
