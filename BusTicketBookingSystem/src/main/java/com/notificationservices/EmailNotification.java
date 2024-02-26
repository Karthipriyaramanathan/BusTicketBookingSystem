/**
 * 
 */
package com.notificationservices;

/**
 * @author KARTHIPRIYA R
 *
 */
public class EmailNotification extends Notification{
	private String emailAddress;
    public EmailNotification(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending email to " + emailAddress + ": \n" + message);
        // Code to send email notification to the given email address
    }
}
