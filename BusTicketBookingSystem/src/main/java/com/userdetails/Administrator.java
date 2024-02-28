/**
 * 
 */
package com.userdetails;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.authenticationservices.Validation;
import com.bookingservices.Search;
import com.busmanagement.BusOperator;
import com.busmanagement.BusRoute;
import com.customerservices.Feedback;
import com.exceptionhandling.InvalidInputException;
import com.implementation.DriverBus;
import com.jdbcservice.JdbcConnection;
/**
 * The Administrator class that implements an application that a user of a 
 * bus ticket booking application which inherits user class
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class Administrator extends User implements Search{
	private String systemAdminUsername="admin";
	private String systemAdminPassword="Admin@1234";
    private String adminRole;
	public Administrator() {
		super();
	}
    public Administrator(Account account) {
        super(account);
    }

    /**
	 * @param username
	 * @param password
	 * @param email
	 * @param userType
	 */
	public Administrator(String username, String password, String email, UserType userType) {
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
	 * @param adminRole
	 */
    public Administrator(String username, String password, String email, UserType userType, String firstName, String lastName,
                         String phoneNumber, String dateOfBirth, String gender, String address, String city, String country,
                         String postalCode, boolean emailVerified, boolean phoneNumberVerified, String adminRole) {
        super(username, password, email, userType, firstName, lastName, phoneNumber, dateOfBirth, gender, address, city,
                country, postalCode, emailVerified, phoneNumberVerified);
        this.adminRole = adminRole;
    }
    
    /**
	 * @return the systemAdminUsername
	 */
	public String getSystemAdminUsername() {
		return systemAdminUsername;
	}
	/**
	 * @param systemAdminUsername the systemAdminUsername to set
	 */
	public void setSystemAdminUsername(String systemAdminUsername) {
		this.systemAdminUsername = systemAdminUsername;
	}
	/**
	 * @return the systemAdminPassword
	 */
	public String getSystemAdminPassword() {
		return systemAdminPassword;
	}
	/**
	 * @param systemAdminPassword the systemAdminPassword to set
	 */
	public void setSystemAdminPassword(String systemAdminPassword) {
		this.systemAdminPassword = systemAdminPassword;
	}
	/**
	 * @return the adminRole
	 */

    public String getAdminRole() {
        return adminRole;
    }
    /**
	 * @param adminRole the adminRole to set
	 */

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    @Override
    public String toString() {
        return "Administrator \nUSER ID : " + getUserID() +"\nACCOUNT : " + getAccount() + "\nFIRSTNAME : " + getFirstName() +
        		"\nLASTNAME : " + getLastName() + "\nPHONE NUMBER : " + getPhoneNumber() + "\nDATE OF BIRTH : " + getDateOfBirth() +
        		"\nGENDER : " + getGender() + "\nADDRESS : " + getAddress() + "\nCITY : " + getCity() + "\nCOUNTRY : " + getCountry() +
        		" \nPOSTALCODE : " + getPostalCode() + "\nEMAIL VERIFIED : " + isEmailVerified() + "\nPHONE NUMBER VERIFIED : " + isPhoneNumberVerified()+"\n--------------------------------------------------";
    }
	static Scanner scanner=new Scanner(System.in);
	public void addMember(UserType usertype) {
		try {
			DriverBus.register(usertype);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void removeMember(Connection con,UserType usertype) {
		try {
			System.out.println("Enter the username");
			String username=scanner.nextLine();
			System.out.println("Enter the password");
			String password=scanner.nextLine();
			System.out.println("Enter the email");
			String email=scanner.nextLine();
			boolean isValidUsername=Validation.checkUsername(username);
			boolean isValidPassword=Validation.checkPassword(password);
			boolean isValidemail=Validation.checkEmail(email);
			boolean removedStatus=false;
			if(isValidUsername && isValidPassword && isValidemail) {
				if(usertype.equals(UserType.ADMINISTRATOR)) {
					final String DELETE="DELETE FROM BUSTICKETBOOKINGSYSTEM.ADMIN WHERE USERNAME=? AND PASSWORD=? AND EMAIL=?";
					removedStatus=JdbcConnection.removeUser(con,username,password,email,DELETE,UserType.ADMINISTRATOR); 
				}
				if(usertype.equals(UserType.CUSTOMER)) {
					final String DELETE="DELETE FROM BUSTICKETBOOKINGSYSTEM.CUSTOMER WHERE USERNAME=? AND PASSWORD=? AND EMAIL=?";
					removedStatus=JdbcConnection.removeUser(con,username,password,email,DELETE,UserType.CUSTOMER); 
				}
				if(removedStatus) {
					System.out.println("Member Removed Successfully!");
				}
				else {
					System.out.println("Member Removal failed!");
				}
			}
			else {
				System.out.println("Invalid! username,password or email Please! enter valid data");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
//	public void updateMember(Connection con,UserType usertype) {
//		try {
//			System.out.println("Enter the username");
//			String username=scanner.nextLine();
//			System.out.println("Enter the password");
//			String password=scanner.nextLine();
//			System.out.println("Enter the email");
//			String email=scanner.nextLine();
//			boolean isValidUsername=Validation.checkUsername(username);
//			boolean isValidPassword=Validation.checkPassword(password);
//			boolean isValidemail=Validation.checkEmail(email);
//			boolean updationStatus=false;
//			if(isValidUsername && isValidPassword && isValidemail) {
//				if(usertype.equals(UserType.ADMINISTRATOR)) {
//					String change=DriverBus.dispalyUpdation(UserType.ADMINISTRATOR, usertype);
//					final String UPDATE="UPDATE FROM BUSTICKETBOOKINGSYSTEM.ADMIN SET"+change+"=? WHERE USERNAME=? AND PASSWORD=? AND EMIAL=?";
//					updationStatus=JdbcConnection.UpdateUser(con,username,password,email,UPDATE,UserType.ADMINISTRATOR); 
//				}
//				if(usertype.equals(UserType.CUSTOMER)) {
//					String change=DriverBus.dispalyUpdation(UserType.ADMINISTRATOR, usertype);
//					final String UPDATE="UPDATE FROM BUSTICKETBOOKINGSYSTEM.CUSTOMER SET"+change+"=? WHERE USERNAME=? AND PASSWORD=? AND EMIAL=?";
//					updationStatus=JdbcConnection.UpdateUser(con,username,password,email,UPDATE,UserType.CUSTOMER); 
//				}
//				if(updationStatus) {
//					System.out.println("Data Updated Successfully!");
//				}
//				else {
//					System.out.println("Data Updation failed!");
//				}
//			}
//			else {
//				System.out.println("Invalid! username,password or email Please! enter valid data");
//			}
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	public void viewMember(Connection con,UserType usertype) {
		try {
		System.out.println("1.View all member\n2.View by username and password\n3.Go to previous page\nEnter your choice:");
		int choice=scanner.nextInt();
		scanner.nextLine();
		switch(choice) {
			case 1:
				if(usertype.equals(UserType.ADMINISTRATOR)) {
					final String SELECT="SELECT * FROM BUSTICKETBOOKINGSYSTEM.ADMIN";
					JdbcConnection.displayAll(con,usertype,SELECT);
				}
				if(usertype.equals(UserType.CUSTOMER)) {
					final String SELECT="SELECT * FROM BUSTICKETBOOKINGSYSTEM.CUSTOMER";
					JdbcConnection.displayAll(con,usertype,SELECT);
				}
			break;
			case 2:
				System.out.println("Enter the username");
				String username=scanner.nextLine();
				System.out.println("Enter the password");
				String password=scanner.nextLine();
				System.out.println("Enter the email");
				String email=scanner.nextLine();
				boolean isValidUsername=Validation.checkUsername(username);
				boolean isValidPassword=Validation.checkPassword(password);
				boolean isValidemail=Validation.checkEmail(email);
				boolean removedStatus=false;
				if(isValidUsername && isValidPassword && isValidemail) {
					if(usertype.equals(UserType.ADMINISTRATOR)) {
						final String SELECT="SELECT * FROM BUSTICKETBOOKINGSYSTEM.ADMIN WHERE USERNAME=? AND PASSWORD=? AND EMAIL=?";
						JdbcConnection.displaySpecific(con,username,password,email,usertype,SELECT);
						
					}
					if(usertype.equals(UserType.CUSTOMER)) {
						final String SELECT="SELECT * FROM BUSTICKETBOOKINGSYSTEM.CUSTOMER WHERE USERNAME=? AND PASSWORD=? AND EMAIL=?";
						JdbcConnection.displaySpecific(con,username,password,email,usertype,SELECT);
					}
				}
				else {
					throw new InvalidInputException("Invalid input! Please Enter the valid data.");
				}
			break;
			case 3:
				System.out.println("Now! in previous page");
				break;
			default:
				System.out.println("Please! enter the valid choice");
				break;
			
			
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	public void addBusOperator(Connection con){
		try {
        System.out.print("Enter operator name: ");
        String operatorName = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();
        System.out.print("Enter number of buses: ");
        int numberOfBuses = scanner.nextInt();
        boolean insertionStatus=false;
        if (Validation.validateName(operatorName) && Validation.validateContactNumber(contactNumber) && 
        	    Validation.validateEmail(email) && Validation.validateAddress(address) && 
        	    Validation.validateCity(city) && Validation.validateCountry(country) && 
        	    Validation.validateNumberOfBuses(String.valueOf(numberOfBuses))) {
        	final String OPERATORINSERT= "INSERT INTO BUSTICKETBOOKINGSYSTEM.BUSOPERATOR (OPERATORNAME, CONTACTNUMBER, EMAIL, ADDRESS, CITY, COUNTRY, NUMBEROFBUSES) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        	insertionStatus=JdbcConnection.insertBusOperator(con,operatorName,contactNumber,email,address,
                     city, country,numberOfBuses,OPERATORINSERT);
        	if(insertionStatus) {
        		System.out.println("Bus Operator inserted Successfully!");
        	}
        	else {
        		System.out.println("Bus Operator insertion failed!");
        	}
        	} else {
        		throw new InvalidInputException("Invalid input! Please Enter the valid data.");
        	}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void removeBusOperator(Connection con) throws SQLException{
	    System.out.print("Enter operator ID to remove: ");
	    int operatorId = scanner.nextInt();
	    boolean removeStatus=false;
	    if (JdbcConnection.isOperatorExists(con,operatorId)) {
	        String DELETE= "DELETE FROM BUSTICKETBOOKINGSYSTEM.BUSOPERATOR WHERE OPERATORID = ?";
	        removeStatus=JdbcConnection.deleteOperator(con,operatorId,DELETE);
	        if (removeStatus) {
	        	System.out.println("Bus operator removed successfully!");
	        } else {
	            System.out.println("Failed to remove bus operator!");
	        }
	    } else {
	        System.out.println("Bus operator with ID " + operatorId + " does not exist!");
	    }
	}
	public void updateBusOperator(Connection con) {
	    try {
	        System.out.print("Enter operator ID to update: ");
	        int operatorId = scanner.nextInt();
	        scanner.nextLine();
	        System.out.print("Enter new contact number: ");
	        String newContactNumber = scanner.nextLine();
	        boolean updateStatus=false;
	        if(JdbcConnection.isOperatorExists(con,operatorId) && Validation.validateContactNumber(newContactNumber) ) {
	        		updateStatus = JdbcConnection.updateBusOperator(con, operatorId, newContactNumber);
	        }
	        if (updateStatus) {
	            System.out.println("Bus Operator updated Successfully!");
	        } else {
	            System.out.println("Bus Operator update failed!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void viewBusOperator(Connection con) {
	    try {
	        JdbcConnection.getBusOperators(con);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void addBusRoute(Connection con) {
        try  {
            System.out.print("Enter origin city: ");
            String originCity = scanner.nextLine();
            System.out.print("Enter destination city: ");
            String destinationCity = scanner.nextLine();
            System.out.print("Enter departure date (YYYY-MM-DD format): ");
            String departureDate = scanner.nextLine();
            System.out.print("Enter departure time (HH:MM format): ");
            String departureTime = scanner.nextLine();
            System.out.print("Enter arrival date (YYYY-MM-DD format): ");
            String arrivalDate = scanner.nextLine();
            System.out.print("Enter arrival time (HH:MM format): ");
            String arrivalTime = scanner.nextLine();
            System.out.print("Enter distance: ");
            int distance = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter duration (HH:MM format): ");
            String duration = scanner.nextLine();
            if (Validation.validateBusRouteInput(originCity, destinationCity, departureDate, departureTime, arrivalDate, arrivalTime, distance, duration)) {
                if (JdbcConnection.insertBusRoute(con,originCity, destinationCity, departureDate, arrivalDate, departureTime, arrivalTime, distance, duration)) {
                    System.out.println("Bus route added successfully!");
                } else {
                    System.out.println("Failed to add bus route.");
                }
            }
            else {
            	throw new InvalidInputException("Invalid input! Please Enter the valid data.");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void removeBusRoute(Connection con) {
	    try {
	        System.out.print("Enter route ID to remove: ");
	        int routeId = scanner.nextInt();
	        scanner.nextLine(); // Consume newline character after nextInt()
	        boolean removalStatus=JdbcConnection.deleteBusRoute(con,routeId);
	        if (removalStatus) {
	            System.out.println("Bus route removed successfully!");
	        } else {
	            System.out.println("Bus route with ID " + routeId + " not found!");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void updateBusRoute(Connection con) {
		try {
			JdbcConnection.updateBusRoutes(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewBusRoute(Connection con) {
		try {
			List<BusRoute> routes;
			routes=JdbcConnection.fetchBusRoutesFromDatabase(con);
//			for(BusRoute route:routes) {
//				System.out.println(route);
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
	@Override
	public boolean searchBusRoutes(Connection con) {
		try {
			System.out.println("Enter the origin:");
	    	String origin=scanner.nextLine();
	    	System.out.println("Enter the destination:");
	    	String destination=scanner.nextLine();
	        List<BusRoute> routes;
			routes = JdbcConnection.fetchAvailableBusRoutes(con, origin, destination);
			System.out.println("Available Bus Routes from " + origin + " to " + destination + ":");
		    for (BusRoute route : routes) {
		         System.out.println(route);
		         return true;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public void viewBusSchedules(Connection con) {
		// TODO Auto-generated method stub
		
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
    public static void viewFAQ(Connection connection,UserType usertype) {
    	try {
    	JdbcConnection.viewFAQs(connection,usertype);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    public void answerFAQ(Connection connection,UserType usertype) {
    	
    	try {
    		viewFAQ(connection,usertype);
    		System.out.println("Enter the faqid");
    		int faqid=scanner.nextInt();
    		scanner.nextLine();
    		System.out.println("Enter the answer:");
    		String answer=scanner.nextLine();
    		JdbcConnection.answerFAQs(connection,faqid,answer);
    		}catch(Exception e) {
		e.printStackTrace();
}
    }
}
