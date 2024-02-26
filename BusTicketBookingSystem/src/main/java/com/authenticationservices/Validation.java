/**
 * 
 */
package com.authenticationservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.exceptionhandling.InvalidInputException;
import com.userdetails.UserType;

/**
 * @author KARTHIPRIYA R
 *
 */
public class Validation {
	public static boolean checkUsername(String userName) throws InvalidInputException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");
        if (!pattern.matcher(userName).matches()) {
            throw new InvalidInputException("Invalid username format! Usernames must be 3-20 characters long and can contain letters, digits, underscores, and hyphens.");
        }
        return true;
    }

	public static boolean checkPassword(String password) throws InvalidInputException {
        Pattern pattern = Pattern.compile("(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[!@#$%^&*()_+]+)(?=.*[0-9]+).{8,}$");
        if (!pattern.matcher(password).matches()) {
            throw new InvalidInputException("Invalid password format! Passwords must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
        return true;
    }

	public static boolean checkEmail(String email) throws InvalidInputException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (!pattern.matcher(email).matches()) {
            throw new InvalidInputException("Invalid email format!");
        }
        return true;
    }
	public static boolean CheckValidLoginDetails(Connection con, String username, String password, String statement) {
	    try {
	        String Username = null, Password = null;
	        PreparedStatement pet = con.prepareStatement(statement);
	        ResultSet set = pet.executeQuery();
	        while (set.next()) {
	            Username = set.getString(3);
	            Password = set.getString(4);
	            if (Username!=null && Password!=null && username.equals(Username) && password.equals(Password)) {
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public static boolean validateName(String name) throws InvalidInputException {
        String regex = "[a-zA-Z]+([ '-][a-zA-Z]+)*";
        if (!name.matches(regex)) {
            throw new InvalidInputException("Invalid name format!");
        }
        return true;
    }
	public static boolean validateContactNumber(String contactNumber) throws InvalidInputException {
        String regex = "^\\d{10}$";
        if (!contactNumber.matches(regex)) {
            throw new InvalidInputException("Invalid contact number format! Contact number must be 10 digits long.");
        }
        return true;
    }
	 public static boolean validateEmail(String email) throws InvalidInputException {
	        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
	        if (!email.matches(regex)) {
	            throw new InvalidInputException("Invalid email format!");
	        }
	        return true;
	    }
	 public static boolean validateAddress(String address) throws InvalidInputException {
	        String regex = "^[#.0-9a-zA-Z\\s,-]+$";
	        if (!address.matches(regex)) {
	            throw new InvalidInputException("Invalid address format!");
	        }
	        return true;
	    }

	 public static boolean validateCity(String city) throws InvalidInputException {
	        String regex = "[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*";
	        if (!city.matches(regex)) {
	            throw new InvalidInputException("Invalid city format!");
	        }
	        return true;
	    }


	 public static boolean validateCountry(String country) throws InvalidInputException {
	        String regex = "[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*";
	        if (!country.matches(regex)) {
	            throw new InvalidInputException("Invalid country format!");
	        }
	        return true;
	    }


	 public static boolean validateNumberOfBuses(String numberOfBuses) throws InvalidInputException {
	        String regex = "^[1-9]\\d*$";
	        if (!numberOfBuses.matches(regex)) {
	            throw new InvalidInputException("Invalid number of buses format!");
	        }
	        return true;
	    }

    public static boolean validateBusRouteInput(String originCity, String destinationCity, String departureDate, String departureTime, String arrivalDate, String arrivalTime, int distance, String duration) throws InvalidInputException {
        try {
            if (originCity == null || originCity.isEmpty()) {
                throw new InvalidInputException("Origin city cannot be empty.");
            }
            if (destinationCity == null || destinationCity.isEmpty()) {
                throw new InvalidInputException("Destination city cannot be empty.");
            }
            if (!isValidDateFormat(departureDate)) {
                throw new InvalidInputException("Invalid departure date format. Please use YYYY-MM-DD format.");
            }
            if (!isValidTimeFormat(departureTime)) {
                throw new InvalidInputException("Invalid departure time format. Please use HH:MM format.");
            }
            if (!isValidDateFormat(arrivalDate)) {
                throw new InvalidInputException("Invalid arrival date format. Please use YYYY-MM-DD format.");
            }
            if (!isValidTimeFormat(arrivalTime)) {
                throw new InvalidInputException("Invalid arrival time format. Please use HH:MM format.");
            }
            LocalDate departureLocalDate = LocalDate.parse(departureDate);
            LocalDate arrivalLocalDate = LocalDate.parse(arrivalDate);
            if (departureLocalDate.isAfter(arrivalLocalDate)) {
                throw new InvalidInputException("Departure date cannot be after arrival date.");
            }
            LocalTime departureLocalTime = LocalTime.parse(departureTime);
            LocalTime arrivalLocalTime = LocalTime.parse(arrivalTime);
            if (departureLocalTime.isAfter(arrivalLocalTime)) {
                throw new InvalidInputException("Departure time cannot be after arrival time.");
            }
            if (distance <= 0) {
                throw new InvalidInputException("Distance must be greater than 0.");
            }
            LocalTime durationLocalTime = LocalTime.parse(duration);
            if (durationLocalTime.getHour() <= 0 && durationLocalTime.getMinute() <= 0 && durationLocalTime.getSecond() <= 0 && durationLocalTime.getNano() <= 0) {
                throw new InvalidInputException("Invalid duration.");
            }
            return true;
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date or time format.");
        }
    }
    public static boolean isValidDateFormat(String date) {
    	try {
	    	  LocalDate.parse(date);
	    	  return true;
	    	} catch (Exception e) {
	    	  return false;
	    	}
    	}
	public static boolean isValidTimeFormat(String time) {
		try {
			  LocalTime.parse(time);
			  return true;
			} catch (Exception e) {
			  return false;
		}
	}

    public static boolean isValidCardNumber(String cardNumber) throws InvalidInputException {
        if (!Pattern.matches("^\\d{16}$", cardNumber)) {
            throw new InvalidInputException("Invalid card number format. Please enter a 16-digit card number.");
        }
        return true;
    }

    
    public static boolean isValidExpirationDate(String expirationDate) throws InvalidInputException {
        if (!Pattern.matches("^(0[1-9]|1[0-2])/20[2-9][0-9]$", expirationDate)) {
            throw new InvalidInputException("Invalid expiration date format. Please use MM/YYYY format.");
        }
        return true;
    }
    public static boolean isValidCVV(String cvv) throws InvalidInputException {
        if (!Pattern.matches("^\\d{3}$", cvv)) {
            throw new InvalidInputException("Invalid CVV format. Please enter a 3-digit CVV.");
        }
        return true;
    }

    public static boolean isValidName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        return true;
    }
    public static boolean isValidPhoneNumber(String phoneNumber) throws InvalidInputException {
        if (!Pattern.matches("\\d{10}", phoneNumber)) {
            throw new InvalidInputException("Invalid phone number format. Please enter a 10-digit phone number.");
        }
        return true;
    }

    public static boolean isValidGender(String gender) throws InvalidInputException {
        if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female") && !gender.equalsIgnoreCase("other")) {
            throw new InvalidInputException("Invalid gender. Gender must be 'male', 'female', or 'other'.");
        }
        return true;
    }
    public static boolean isValidPostalCode(String postalcode) throws InvalidInputException {
        if (!Pattern.matches("^\\d{6}$", postalcode)) {
            throw new InvalidInputException("Invalid postal code format. Please enter a 6-digit postal code.");
        }
        return true;
    }

    public static boolean isValidDate(String dateStr) throws InvalidInputException {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$"; // Regex pattern for YYYY-MM-DD format
        if (!dateStr.matches(regex)) {
            throw new InvalidInputException("Invalid date format. Please use YYYY-MM-DD format.");
        }
        return true;
    }
    public static boolean isValidAddress(String address) throws InvalidInputException {
        // Regular expression pattern for address (allowing alphanumeric characters, spaces, commas, periods, and hyphens)
        String regex = "^[a-zA-Z0-9\\s,.\\-]*$";
        if (!address.matches(regex)) {
            throw new InvalidInputException("Invalid address format.");
        }
        return true;
    }

    public static boolean isValidCity(String city) throws InvalidInputException {
        // Regular expression pattern for city (allowing alphabetic characters and spaces)
        String regex = "^[a-zA-Z\\s]*$";
        if (!city.matches(regex)) {
            throw new InvalidInputException("Invalid city format.");
        }
        return true;
    }

    public static boolean isValidCountry(String country) throws InvalidInputException {
        // Regular expression pattern for country (allowing alphabetic characters and spaces)
        String regex = "^[a-zA-Z\\s]*$";
        if (!country.matches(regex)) {
            throw new InvalidInputException("Invalid country format.");
        }
        return true;
    }    
}
