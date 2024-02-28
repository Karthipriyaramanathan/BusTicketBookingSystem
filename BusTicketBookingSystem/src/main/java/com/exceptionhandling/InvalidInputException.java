/**
 * 
 */
package com.exceptionhandling;

/**
 * The InvalidInputException class is a class that inherits the exception 
 * class used to handle the exception if the user enters the invalid data
 * or any invalid inputs to the required fields.
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
