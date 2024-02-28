/**
 * 
 */
package com.userdetails;
import com.notificationservices.*;
import com.bookingservices.Search;
import com.busmanagement.BusRoute;
import com.busmanagement.BusSchedule;
import com.customerservices.Feedback;
import com.exceptionhandling.InvalidInputException;
import com.jdbcservice.JdbcConnection;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;
import java.util.List;
/**
 * The Customer class that implements an application that a user of a 
 * bus ticket booking application which inherits user class
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class Customer extends User implements Search{
    /**
     * 
     */
    private boolean premiumMember;
    public Customer() {
		super();
	}
    public Customer(Account account) {
    	super(account);
    }
    /**
	 * @param username
	 * @param password
	 * @param email
	 * @param userType
	 */
	public Customer(String username, String password, String email, UserType userType) {
		super(username, password, email, userType);
	}
	/**
	 * @param username
	 * @param password
	 * @param email
	 * @param userType
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param dateOfBirth
	 * @param gender
	 * @param address
	 * @param city
	 * @param country
	 * @param postalCode
	 * @param emailVerified
	 * @param phoneNumberVerified
	 * @param premiumMember
	 */
    public Customer(String username, String password, String email, UserType userType, String firstName, String lastName,
                    String phoneNumber, String dateOfBirth, String gender, String address, String city, String country,
                    String postalCode, boolean emailVerified, boolean phoneNumberVerified, double accountBalance,
                    boolean premiumMember) {
        super(username, password, email, userType, firstName, lastName, phoneNumber, dateOfBirth, gender, address, city,
                country, postalCode, emailVerified, phoneNumberVerified);
        this.premiumMember = premiumMember;
    }
    /**
	 * @return the premiumMember
	 */
    public boolean isPremiumMember() {
        return premiumMember;
    }
    /**
	 * @param premiumMember the premiumMember to set
	 */
    public void setPremiumMember(boolean premiumMember) {
        this.premiumMember = premiumMember;
    }

    @Override
    public String toString() {
        return "Customer \nUSERID : " + getUserID() + "\naccount : " + getAccount() + "\nFIRSTNAME : " + getFirstName() +
                "\nLASTNAME : " + getLastName() + "\nPHONE NUMBER : " + getPhoneNumber() + "\nDATE OF BIRTH : " + getDateOfBirth() +
                "\nGENDER : " + getGender() + "\nADDRESS : " + getAddress() + "\nCITY : " + getCity() + "\nCOUNTRY : " + getCountry() +
                "\nPOSTALCODE : " + getPostalCode() + "\nEMAIL VERIFIED : " + isEmailVerified() + "\nPHNONE NUMBER VERIFIED : " + isPhoneNumberVerified()+"\n--------------------------------------------------";
    }
    static Scanner scanner=new Scanner(System.in);
    @Override
    public boolean searchBusRoutes(Connection con) {
    	try {
	    	System.out.println("Enter the origin:");
	    	String origin=scanner.nextLine();
	    	System.out.println("Enter the destination:");
	    	String destination=scanner.nextLine();
	        List<BusRoute> routes;
			routes = JdbcConnection.fetchAvailableBusRoutes(con, origin, destination);
			if(routes.isEmpty()) {
				System.out.println("No bus route is available");
				return false;
			}
			else {
				System.out.println("Available Bus Routes from " + origin + " to " + destination + ":");
		        for (BusRoute route : routes) {
		            System.out.println(route);
		            return true;
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
 
    }
    @Override
    public void viewBusSchedules(Connection con) {
        try {
            // Display all bus schedules
            JdbcConnection.viewSchedules(con);

            // Prompt user to enter the schedule ID
            System.out.println("Enter the Schedule ID:");
            int scheduleId = scanner.nextInt();
            scanner.nextLine();

            // View bus route for the selected schedule
            JdbcConnection.viewBusRoute(con, scheduleId);
        } catch (InputMismatchException | SQLException e) {
            System.out.println("Invalid input format. Please enter a valid integer.");
        }
    }
    @Override
    public void viewBusRoute(Connection con){
		try {
			List<BusRoute> routes;
			routes=JdbcConnection.fetchBusRoutesFromDatabase(con);
			if(routes.isEmpty()) {
				System.out.println("No bus route is available");
			}
			else {
				System.out.println("Available Bus Routes :");
		        for (BusRoute route : routes) {
		            System.out.println(route);
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    public boolean selectSeat(Connection con,int userId) throws InvalidInputException {
    	try {
    		//customer.viewBusRoute(con);
    		if(searchBusRoutes(con)) {
	        	System.out.println("Enter the route id");
	        	int routeid=scanner.nextInt();
	        	scanner.nextLine();
				if(JdbcConnection.selectSeatForBooking(con,routeid,userId)) {
					return true;
				}
				else {
					return false;
				}
    		}
    	}
		 catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    public void checkSeatAvailability(Connection con) {
        // Call the method to fetch available seats for the specified route
        try {
            System.out.println("Enter the origin city:");
            String originCity = scanner.nextLine();
            System.out.println("Enter the destination city:");
            String destinationCity = scanner.nextLine();
			JdbcConnection.checkAvailableSeat(con, originCity, destinationCity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	  public void booking(Connection con,int userId) throws InvalidInputException {
		if(selectSeat(con,userId)) {
			System.out.println("Booking is available");
		}
		else {
			System.out.println("Booking is not available");
		}
	}
    public void cancelation(Connection con,int userId) throws SQLIntegrityConstraintViolationException {
            System.out.println("Enter Booking ID to cancel:");
            int bookingId = scanner.nextInt();
            System.out.println("Enter Route ID to cancel:");
            int routeId = scanner.nextInt();
            System.out.println("Enter Number of seats to cancel:");
            int numSeats = scanner.nextInt();
            cancelBooking(con, bookingId,routeId,numSeats,userId);
    }
    public void cancelBooking(Connection connection, int bookingId, int routeId, int numSeats,int userId) {
        try {
            // Update seat occupancy status for canceled seats
            JdbcConnection.insertCancellation(connection, bookingId);
            JdbcConnection.updateBookingcancellationstatus(connection, bookingId);
            JdbcConnection.updateSeatOccupancy(connection, routeId, numSeats, false);
            // Delete the booking entry from the database
            System.out.println("Booking with ID " + bookingId + " has been canceled successfully.");
            System.out.println("\nRefunding payment...");
            String message="Your booking with ID "+bookingId+" has been canceled successfully."
            		+ " The payment refund process has been initiated."
            		+ " You will receive a confirmation email shortly..";
            JdbcConnection.notification(connection, userId, message);

//	            payment.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewBookingHistory(Connection connection,int userId) {
    	try {
    		JdbcConnection.viewHistory(connection,userId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public void changePassword(Connection connection,int userId,UserType usertype) {
    	try {
			JdbcConnection.changePass(connection,userId,usertype);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void updateProfile(Connection con,int userId,UserType usertype) throws InvalidInputException {
    	try {
			JdbcConnection.updateUserInfo(con,userId,usertype);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
//    @Override
//    public void viewFeedback(Connection con) {
//    	try {
//    		JdbcConnection.viewfeedback(con);
//    	}
//    	catch(Exception e) {
//    		e.printStackTrace();
//    	}
//    }
    public static void displayFeedback(List<Feedback> feedbackList) {
        for (Feedback feedback : feedbackList) {
            System.out.println(feedback);
        }
    }
    public static void viewfeedback(Connection connection) {
    	try {
        // Retrieve and display feedback records sorted by rating
        List<Feedback> feedbackList = JdbcConnection.getFeedbackFromDatabase(connection);
        displayFeedback(feedbackList);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public static void givefeedback(Connection connection,int userid) {
    	try {
        	Feedback feedback = JdbcConnection.getFeedbackFromUser(connection,userid);

            // Insert the new feedback record into the database
            if (feedback != null) {
                JdbcConnection.giveFeedback(connection, feedback);
                System.out.println("Feedback submitted successfully!");
            }else {
            	System.out.println("Feedback submission falied!");
            }

    }catch(Exception e) {
    	e.printStackTrace();
    }
}
    public static void askFAQ(Connection con,int userId) {
    	System.out.println("Enter the Questions:");
    	String question=scanner.nextLine();
    	System.out.println("Enter the category:");
    	String category=scanner.nextLine();
    	try {
    	JdbcConnection.addFAQ(con,question,category,userId);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public void viewFAQ(Connection connection,UserType usertype) {
    	try {
    	JdbcConnection.viewFAQs(connection,usertype);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
}


