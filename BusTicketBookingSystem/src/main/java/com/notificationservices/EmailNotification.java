/**
 * 
 */
package com.notificationservices;

/**
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 */
public class EmailNotification extends Notification{
	private String emailAddress;
    public EmailNotification(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    @Override
    public void sendNotification(String message) {
    	System.out.println("------------------Notification-------------------------------");
        System.out.println("Sending email to " + emailAddress + ": \n" + message);
        // To send email notification to the given email address
        System.out.println("-------------------------------------------------------------");
        
    }
}
