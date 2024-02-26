/**
 * 
 */
package com.implementation;

import com.userdetails.*;
import com.userdetails.UserType;
import com.notificationservices.*;
import com.authenticationservices.Validation;
import com.exceptionhandling.InvalidInputException;
import com.jdbcservice.JdbcConnection;
import com.layoutdesign.LayoutDesign;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author KARTHIPRIYA R
 *
 */
public class DriverBus {
	/**
	 * @param args
	 */
	static Connection con;
	static Scanner scanner=new Scanner(System.in);
	//Login method
	public static void login(UserType userType) throws InvalidInputException {
	    int userId = 0;
	    String username;
	    String password;
	    boolean loginSuccessful = false;
	    try {
	        while (!loginSuccessful) {
	            System.out.println("Enter the username");
	            username = scanner.nextLine();
	            if (!Validation.checkUsername(username)) {
	                System.out.println("Invalid username! Please enter valid data.");
	                continue; 
	            }

	            System.out.println("Enter the password");
	            password = scanner.nextLine();
	            if (!Validation.checkPassword(password)) {
	                System.out.println("Invalid password! Please enter valid data.");
	                continue; 
	            }

	            String selectQuery = userType.equals(UserType.ADMINISTRATOR) ?
	                                 "SELECT * FROM BUSTICKETBOOKINGSYSTEM.ADMIN" :
	                                 "SELECT * FROM BUSTICKETBOOKINGSYSTEM.CUSTOMER";

	            boolean loginStatus = Validation.CheckValidLoginDetails(con, username, password, selectQuery);
	            userId = JdbcConnection.getUserId(con, username, password);

	            if (loginStatus) {
	                System.out.println("Login Successful!");
	                if (userType.equals(UserType.ADMINISTRATOR)) {
	                    DriverBus.adminOperation(userId);
	                } else {
	                    DriverBus.customerOperation(userId);
	                }
	                loginSuccessful = true; // Exit the loop if login successful
	            } else {
	                System.out.println("Invalid login details! Please enter valid data.");
	            }
	        }
	    } catch (Exception e) {
	        throw new InvalidInputException("Invalid username or password! Please enter valid data.");
	    }
	}

	public static void register(UserType userType) throws InvalidInputException {
		try {
		boolean access=false;
		String username,password,email;
		if(userType.equals(UserType.ADMINISTRATOR)) {
			System.out.println("Enter the system admin username");
			username=scanner.nextLine();
			System.out.println("Enter the system admin password");
			password=scanner.nextLine();
			Administrator systemAdmin=new Administrator();
			if(username.equals(systemAdmin.getSystemAdminUsername()) && password.equals(systemAdmin.getSystemAdminPassword())) {
				access=true;
			}
		}
		else if(userType.equals(UserType.CUSTOMER)){
			access=true;
		}
        if(access) {
            while(true) {
	        	System.out.println("Enter the username to register");
	        	username=scanner.nextLine();
				if(!Validation.checkUsername(username)) {
					System.out.println("Invalid username Please! enter valid data");
					continue;
				}
				else {
					break;
				}
			}
			while(true) {
				System.out.println("Enter the password to register");
				password=scanner.nextLine();
				if(!Validation.checkPassword(password)) {
					System.out.println("Invalid password Please! enter valid data");
				}
				else {
					break;
				}
			}
			while(true) {
				System.out.println("Enter the Email to register");
		        email = scanner.nextLine();
				if(!Validation.checkEmail(email)) {
					System.out.println("Invalid password Please! enter valid data");
				}
				else {
					break;
				}
			}
            boolean isValidUsername = Validation.checkUsername(username);
            boolean isValidPassword = Validation.checkPassword(password);
            boolean isValidEmail = Validation.checkEmail(email);
            
            if (isValidUsername && isValidPassword && isValidEmail) {
                Account account = new Account(username, password, email, userType);

                // Insert user into the appropriate table
                boolean registrationStatus = false;
                switch (userType) {
                    case ADMINISTRATOR:
                        Administrator admin = new Administrator(account);
                        registrationStatus = JdbcConnection.registerAdmin(con, admin);
                        break;
                    case CUSTOMER:
                        Customer customer = new Customer(account);
                        registrationStatus = JdbcConnection.registerCustomer(con, customer);
                        break;
                }
                
                if (registrationStatus) {
                    System.out.println("Successfully Registered!");
                } else {
                    System.out.println("Sorry! Registration failed");
                }
            } else {
            	throw new InvalidInputException("Invalid username or password Please! enter valid data");
            }
        }else {
        	System.out.println("You are not eligible to register");
        	}
		}catch (Exception e) {
			throw new InvalidInputException("Invalid username or password Please! enter valid data");
        }
    }
	public static void adminManagement(Administrator admin,int userId) throws InvalidInputException {
		try {
		while(true) {
			LayoutDesign.topic("Admin Management");
			int subchoice=6;
			try {
				System.out.println("1.Add Admin\n2.Remove Admin\n3.Update Admin\n4.View Admin\n5.Go to Previous Page"
						+ "\n6.Exit\nPlease! Enter your choice:");
	            subchoice = scanner.nextInt();
	            scanner.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            scanner.nextLine(); // Consume invalid input
	            continue; // Continue to next iteration
	        }
			if(subchoice>=1 && subchoice<=6) {
			switch(subchoice) {
				case 1:
					admin.addMember(UserType.ADMINISTRATOR);
					break;
				case 2:
					admin.removeMember(con,UserType.ADMINISTRATOR);
					break;
				case 3:
					admin.updateProfile(con,userId,UserType.ADMINISTRATOR);
					break;
				case 4:
					admin.viewMember(con,UserType.ADMINISTRATOR);
					break;
				case 5:
					System.out.println("Now in previous page!");
					return;
				 case 6:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
				default:
					System.out.println("Invalid choice! Please try again");
					break;
			}
			if(subchoice==6) {
				break;
			}
		}else {
			throw new InvalidInputException("Invalid input! Please enter a valid number.");
		}
	}
		}
	catch(InvalidInputException e) {
		System.out.println(e.getMessage());
	}	catch (InputMismatchException e) {
	    System.out.println("Invalid input! Please enter a valid number.");
	}
		catch(Exception e) {
		e.printStackTrace();
	}
	}
	public static void userManagement(Administrator admin,int userId){
		try {
		while(true) {
			LayoutDesign.topic("User Management");
			int subchoice=6;
		    try {
		    	System.out.println("1.Add User\n2.Remove User\n3.Update User\n4.View User\n5.Go to Previous Page"
						+ "\n6.Exit\nPlease! Enter your choice:");
	            subchoice = scanner.nextInt();
	            scanner.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            scanner.nextLine(); // Consume invalid input
	            continue; // Continue to next iteration
	        }
			if(subchoice>=1 && subchoice<=6) {
			switch(subchoice) {
				case 1:
					admin.addMember(UserType.CUSTOMER);
					break;
				case 2:
					admin.removeMember(con,UserType.CUSTOMER);
					break;
				case 3:
					admin.updateProfile(con,userId,UserType.ADMINISTRATOR);
					break;
				case 4:
					admin.viewMember(con,UserType.CUSTOMER);
					break;
				case 5:
					System.out.println("Now in previous page!");
					return;
				case 6:
		        	 System.out.println("Thank you!..............");
		        	 System.exit(0);
				default:
					System.out.println("Invalid choice! Please try again");
					break;
			}
		}else {
			throw new InvalidInputException("Invalid input! Please enter a valid number.");
		}
	}
	}
	catch(InvalidInputException e) {
		System.out.println(e.getMessage());
	}
	catch (InputMismatchException e) {
	    System.out.println("Invalid input! Please enter a valid number.");
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	public static void busOperatorManagement(Administrator admin) throws SQLException{
		try {
		while(true) {
			LayoutDesign.topic("Bus Operator Management");
			int subchoice=6;
			try {
				System.out.println("1.Add Bus Operator\n2.Remove Bus Operator\n3.Update Bus Operator\n4.View Bus Operator\n5.Go to Previous Page"
						+ "\n6.Exit\nPlease! Enter your choice:");
	            subchoice = scanner.nextInt();
	            scanner.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            scanner.nextLine(); // Consume invalid input
	            continue; // Continue to next iteration
	        }
			if(subchoice>=1 && subchoice<=6) {
				switch(subchoice) {
					case 1:
						admin.addBusOperator(con);
						break;
					case 2:
						admin.removeBusOperator(con);
						break;
					case 3:
						admin.updateBusOperator(con);
						break;
					case 4:
						admin.viewBusOperator(con);
						break;
					case 5:
						System.out.println("Now in previous page!");
						return;
					 case 6:
			        	   System.out.println("Thank you!..............");
			        	   System.exit(0);
					default:
						System.out.println("Invalid choice! Please try again");
						break;
				}
		}
			else {
				throw new InvalidInputException("Invalid input! Please enter a valid number.");
			}
		}
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}	catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void busrouteManagement(Administrator admin) {
		try {
		while(true) {
			LayoutDesign.topic("Bus Route Management");
			int subchoice=6;
			try {
				System.out.println("1.Add Bus Route\n2.Remove Bus Route\n3.Update Bus Route\n4.View Bus Route\n5.Go to Previous Page"
						+ "\n6.Exit\nPlease! Enter your choice:");
	            subchoice = scanner.nextInt();
	            scanner.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            scanner.nextLine(); // Consume invalid input
	            continue; // Continue to next iteration
	        }
			if(subchoice>=1 && subchoice<=6) {
				switch(subchoice) {
					case 1:
						admin.addBusRoute(con);;
						break;
					case 2:
						admin.removeBusRoute(con);
						break;
					case 3:
						admin.updateBusRoute(con);
						break;
					case 4:
						admin.viewBusRoute(con);
						break;
					case 5:
						System.out.println("Now in previous page!");
						return;
					case 6:
			           System.out.println("Thank you!..............");
			           System.exit(0);
					default:
						System.out.println("Invalid choice! Please try again");
						break;
				}
			}
			else {
				throw new InvalidInputException("Invalid input! Please enter a valid number.");
			}
		}
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void bookingManagement(Administrator admin){
		try {
		while(true) {
	        int subchoice =6;
	        try {
	        	System.out.println("1.View Booking\n2.View Cancellation\n"
		        		+ "3.View Payment\n4.Generate Reports"
		        		+ "\n5.Go back to previous page\n6.Exit\nEnter your choice: ");
	            subchoice = scanner.nextInt();
	            scanner.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            scanner.nextLine(); // Consume invalid input
	            continue; // Continue to next iteration
	        }
		    if(subchoice>=1 && subchoice<=6) {
		        switch (subchoice) {
		            case 1:
		                JdbcConnection.viewBooking(con);
		                break;
		            case 2:
		                JdbcConnection.viewCancellation(con);
		                break;
		            case 3:
		                JdbcConnection.viewPayment(con);
		                break;
		            case 4:
	//	                generateReports(con);
		            	System.out.println("HI");
		                break;
		            case 5:
		                System.out.println("Now! in previous page");
		                return;
		            case 6:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
		            default:
		                System.out.println("Invalid choice! Please try again.");
		                break;
		        }
		        if(subchoice==6) {
		        	break;
		    }
	        }
		    else {
		    	throw new InvalidInputException("Invalid input! Please enter a valid number.");
		    }
		}
		
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void customerServiceManagement(Connection con,Administrator admin) throws SQLException {
		try {
			while(true) {
				int choice=5;
				try {
					System.out.println("1.View Feedback\n2.view FAQ\n3.Answer FAQ\n4.Go to previous page\n5.Exit\nEnter your choice:");
		            choice = scanner.nextInt();
		            scanner.nextLine();
		        } catch (InputMismatchException e) {
		            System.out.println("Invalid input! Please enter a valid number.");
		            scanner.nextLine(); // Consume invalid input
		            continue; // Continue to next iteration
		        }
				if(choice>=1 && choice<=5) {
					switch(choice) {
					case 1:
						admin.viewfeedback(con);
						break;
					case 2:
						JdbcConnection.viewFAQs(con,UserType.ADMINISTRATOR);
						break;
					case 3:
						admin.answerFAQ(con,UserType.ADMINISTRATOR);
						break;
					case 4:
						System.out.println("Now! you are in the previous page.");
						return;
					case 5:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
					default:
						System.out.println("Please! Enter the valid choice.");
						break;
					}
					if(choice==5) {
						break;
					}
				}
				else {
					throw new InvalidInputException("Invalid input! Please enter the valid input.");
				}
			}
			
		}catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number.");
		}
	}
	public static void profileManagement(Connection con,Administrator admin,int userId) throws SQLException {
		try {
			while(true) {
				int choice=4;
				try {
					System.out.println("1.Update Profile\n2.view Profile\n3.Go to previous page\n4.Exit\nEnter your choice:");
		            choice = scanner.nextInt();
		            scanner.nextLine();
		        } catch (InputMismatchException e) {
		            System.out.println("Invalid input! Please enter a valid number.");
		            scanner.nextLine(); // Consume invalid input
		            continue; // Continue to next iteration
		        }
				if(choice>=1 && choice<=4) {
					switch(choice) {
					case 1:
						admin.updateProfile(con,userId,UserType.ADMINISTRATOR);
						break;
					case 2:
		                JdbcConnection.ViewProfile(con,userId);
		                break;
					case 3:
						System.out.println("Now! you are in the previous page.");
						return;
					case 4:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
					default:
						System.out.println("Please! Enter the valid choice.");
						break;
					}
					if(choice==4) {
						break;
					}
				}
				else {
					throw new InvalidInputException("Invalid input! Please enter the valid input.");
				}
			}
			
		}catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number.");
		}
	}
	public static void adminOperation(int userId) throws SQLException {
		try {
			boolean loop=true;
			Administrator admin=new Administrator();
			while(loop) {
				LayoutDesign.topic("Administrator");
				int choice=9;
			       try {
						System.out.println("\n1.Admin Management\n2.User Management\n3.Bus Operator Management"
								+ "\n4.Bus Route Management\n5.Booking Management\n6.Customer Service Management"
								+ "\n7.Profile Management\n8.Go to Previous Page\n9.Exit\nPlease! Enter your choice");
			            choice = scanner.nextInt();
			            scanner.nextLine();
			        } catch (InputMismatchException e) {
			            System.out.println("Invalid input! Please enter a valid number.");
			            scanner.nextLine(); // Consume invalid input
			            continue; // Continue to next iteration
			        }
				if(choice>=1 && choice<=9) {
					switch(choice) {
					case 1 :
						adminManagement(admin,userId);
						break;
					case 2:
						userManagement(admin,userId);
						break;
					case 3:
						busOperatorManagement(admin);
						break;
					case 4:
						busrouteManagement(admin);
						break;
					case 5:
						bookingManagement(admin);
						break;
					case 6:
						customerServiceManagement(con,admin);
						break;
					case 7:
						profileManagement(con,admin,userId);
						break;
					case 8:
						System.out.println("Now! in previous page");
						return;
					case 9:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
					default:
						System.out.println("Invalid choice! Please try again");
						break;
				}
				if(choice==9) {
					loop=false;
					break;
				}
			}else {
				throw new InvalidInputException("Invalid input! Please enter a valid number.");
			}
		}
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}	
		catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void adminflow() {
		try {
		while(true) {
			LayoutDesign.topic("Administrator");
			int subchoice=4;
	       try {
	    	   System.out.println("1.Login\n2.Register\n3.Go to previous page\n4.Exit\nPlease! Enter your choice:");
	            subchoice = scanner.nextInt();
	            scanner.nextLine();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input! Please enter a valid number.");
	            scanner.nextLine(); // Consume invalid input
	            continue; // Continue to next iteration
	        }
			if(subchoice>=1 && subchoice<=4) {
				switch(subchoice) {
					case 1:
						login(UserType.ADMINISTRATOR);
						break;
					case 2:
						register(UserType.ADMINISTRATOR);
						break;
					case 3:
						System.out.println("Now in previous page!");
						return;
					 case 4:
			        	   System.out.println("Thank you!..............");
			        	   System.exit(0);
					default:
						System.out.println("Invalid choice! Please try again");
						break;
				}
			}
			else {
				throw new InvalidInputException("Invalid input! Please enter a valid number.");
			}
		}
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void customerflow() {
		try {
		while(true) {
			LayoutDesign.topic("Customer");
			int subchoice=4;
			   try {
				   System.out.println("1.Login\n2.Register\n3.Go to previous page\n4.Exit\nPlease! Enter your choice:");
		            subchoice = scanner.nextInt();
		            scanner.nextLine();
		        } catch (InputMismatchException e) {
		            System.out.println("Invalid input! Please enter a valid number.");
		            scanner.nextLine(); // Consume invalid input
		            continue; // Continue to next iteration
		        }
				if(subchoice>=1 && subchoice<=4) {
					switch(subchoice) {
						case 1:
							login(UserType.CUSTOMER);
							break;
						case 2:
							register(UserType.CUSTOMER);
							break;
						case 3:
							System.out.println("Now in previous page!");
							return;
						case 4:
							System.out.println("Thank you!..........");
							System.exit(0);
						default:
							System.out.println("Invalid choice! Please try again");
							break;
					}
				}
				else {
					throw new InvalidInputException("Invalid input! Please enter a valid number.");
				}
			}
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void customerOperation(int userId) {
		try {
			boolean loop=true;
			Customer customer=new Customer();
			while(loop) {
				LayoutDesign.topic("Customer");
				int choice=12;
		        try {
		        	System.out.println("1. Search Bus Routes\n2. View Bus Schedules\n3. Check Seat Availability\n" +
						    "4. Book Tickets\n5. View Booking History\n6. Cancel Booking\n" +
						    "7. Update Profile\n8.View Profile\n9. Change Password\n10.Customer Service\n11.Go to previous page" +
						    "\n12.Exit\nEnter your choice: ");
		            choice = scanner.nextInt();
		            scanner.nextLine();
		        } catch (InputMismatchException e) {
		            System.out.println("Invalid input! Please enter a valid number.");
		            scanner.nextLine(); // Consume invalid input
		            continue; // Continue to next iteration
		        }

				if(choice>=1 && choice<=12) {
					switch (choice) {
	                case 1:
	                    customer.searchBusRoutes(con);
	                    break;
	               case 2:
	            	   	customer.viewBusSchedules(con);
	            	   	break;
	               case 3:
	                    customer.checkSeatAvailability(con);
	                    break;
	               case 4:
	                    customer.booking(con,userId);
	                	break;
	               case 5:
		                customer.viewBookingHistory(con,userId);
		                break;
	               case 6:
	                    customer.cancelation(con,userId);
	                    break;
		           case 7:
		                customer.updateProfile(con,userId,UserType.CUSTOMER);
		                break;
		           case 8:
		        	   JdbcConnection.ViewProfile(con,userId);
		                break;
		           case 9:
		                customer.changePassword(con,userId,UserType.CUSTOMER);
		                break;
		           case 10:
		        	   	customerService(con,customer,userId,UserType.CUSTOMER);
		                break;
		           case 11:
	                    System.out.println("Now! you are in prevoius page");
	                    return;
		           case 12:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	                    return;
	            }
				if(choice==12) {
					loop=false;
					break;
				}
			}
			else {
				throw new InvalidInputException("Invalid input! Please enter a valid number.");
		   }
		}
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void customerService(Connection con,Customer customer,int userId,UserType usertype) throws SQLException {
		try {
			while(true) {
				int choice=6;
			    try {
			    	System.out.println("1.Give Feedback\n2.View Feedback\n3.Ask FAQ\n4.View FAQ\n5.Go to previous page\n6.Exit\nEnter your choice:");
		            choice = scanner.nextInt();
		            scanner.nextLine();
		        } catch (InputMismatchException e) {
		            System.out.println("Invalid input! Please enter a valid number.");
		            scanner.nextLine(); // Consume invalid input
		            continue; // Continue to next iteration
		        }
				if(choice>=1 && choice<=6) {
					switch(choice) {
					case 1:
						customer.givefeedback(con,userId);
						break;
					case 2:
						customer.viewfeedback(con);					
						break;
					case 3:
						customer.askFAQ(con,userId);
					case 4:
						JdbcConnection.viewFAQs(con,usertype);
						break;
					case 5:
						System.out.println("Now! you are in previous page.");
						return;
					case 6:
		        	   System.out.println("Thank you!..............");
		        	   System.exit(0);
					default:
						System.out.println("Please! Enter the valid choice.");
						break;
					}
					if(choice==6) {
						break;
					}
				}
				else {
					throw new InvalidInputException("Invalid input! Please enter the valid input.");
				}
			}
			
		}catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input! Please enter a valid number.");
		}
	}
	//Main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LayoutDesign.WelcomeNote();
			con=JdbcConnection.connectdatabase();
			boolean loop=true;
			while(loop) {
				int choice=4;
//				System.out.println("1.Administator\n2.Customer\n3.Guest\n4.Exit\nPlease! Enter your choice:");
//				int choice=scanner.nextInt();
//				scanner.nextLine();
				try {
					System.out.println("1.Administator\n2.Customer\n3.Exit\nPlease! Enter your choice:");
		            choice = scanner.nextInt();
		            scanner.nextLine();
		        } catch (InputMismatchException e) {
		            System.out.println("Invalid input! Please enter a valid number.");
		            scanner.nextLine(); // Consume invalid input
		            continue; // Continue to next iteration
		        }
				if(choice>=1 && choice<=3) {
					switch(choice) {
						case 1 :
							adminflow();
							break;
						case 2:
							customerflow();
							break;
						case 3:
							System.out.println("Thank you.................!");
							return;
						default:
							System.out.println("Invalid choice! Please try again");
							break;
					}
					if(choice==3) {
						loop=false;
						break;
					}
				}
				else {
					throw new InvalidInputException("Invalid input! Please enter a valid number.");
					
				}
			}
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("The data try to fetch is not available in database");
		}
		catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
		    System.out.println("Invalid input! Please enter a valid number.");
		}

		catch (NumberFormatException e) {
		    System.out.println("Invalid number format! Please enter a valid number.");
		}
		catch (NullPointerException e) {
		    System.out.println("Null pointer exception occurred!");
		}
		catch (ArrayIndexOutOfBoundsException e) {
		    System.out.println("Array index out of bounds!");
		}
		catch (ArithmeticException e) {
		    System.out.println("Arithmetic exception occurred!");
		}
		catch (SQLException e) {
			System.out.println("Database error! Please check your database connection and query.");
		}
		catch (Exception e) {
		    System.out.println("An unexpected error occurred!....");
		    e.printStackTrace();
		}
		finally {
	        // Close resources if needed
	        if (con != null) {
	            try {
	                con.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        if (scanner != null) {
	            scanner.close();
	        }
	    }
	}
}
