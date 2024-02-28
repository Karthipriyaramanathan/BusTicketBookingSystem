/**
 * 
 */
package com.jdbcservice;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

import com.authenticationservices.Validation;
import com.bookingservices.Booking;
import com.busmanagement.BusOperator;
import com.busmanagement.BusRoute;
import com.busmanagement.BusSchedule;
import com.busmanagement.Seat;
import com.busmanagement.SeatType;
import com.notificationservices.*;
import com.paymentservices.*;
import com.userdetails.*;
import java.text.SimpleDateFormat;
import com.customerservices.*;
import com.exceptionhandling.InvalidInputException;

/**
 * The JdbcConnection class is the class that have the connection with the 
 * database and it have the behaviours of the operations.
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class JdbcConnection {
	static Scanner scanner=new Scanner(System.in);
	static String URL="jdbc:oracle:thin:@localhost:1521:xe";
	static String userName="SYSTEM";
	static String password="1234rk";
	public static Connection connectdatabase() throws ClassNotFoundException, SQLException{
		Connection con=null;
			//Step1: Register the class.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Step2 - Create Connections.
			con=DriverManager.getConnection(URL,userName,password);
			//checking connection established or not.
			if(con==null)
				System.out.println("Connection Not Established!");
			else
				System.out.println("Connection established!");
		return con;
	}
	public static int getUserId(Connection con,String username,String password) throws SQLException,SQLIntegrityConstraintViolationException  {
		String Id="SELECT USERID FROM BUSTICKETBOOKINGSYSTEM.USERS WHERE USERNAME=? AND PASSWORD=?";
		int userid=0;
		try(PreparedStatement preparedStatement=con.prepareStatement(Id)){
			preparedStatement.setString(1,username);
			preparedStatement.setString(2,password);
			ResultSet set=preparedStatement.executeQuery();
			while(set.next()) {
				userid=set.getInt(1);
			}
		}
		return userid;
	}
	public static boolean registerAdmin(Connection con, Administrator admin) throws SQLException,SQLIntegrityConstraintViolationException  {
	    boolean registrationStatus = false;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        // Insert into USERS table
	        final String USER_INSERT = "INSERT INTO BUSTICKETBOOKINGSYSTEM.USERS "
	                                  + "(USERNAME, PASSWORD, EMAIL, USERTYPE) "
	                                  + "VALUES (?, ?, ?, ?)";
	        preparedStatement = con.prepareStatement(USER_INSERT, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, admin.getAccount().getUsername());
	        preparedStatement.setString(2, admin.getAccount().getPassword());
	        preparedStatement.setString(3, admin.getAccount().getEmail());
	        preparedStatement.setString(4, admin.getAccount().getUserType().toString());
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows > 0) {
	            // Retrieve the generated userid
	        	final String select="select userid from busticketbookingsystem.users where username=?";
	        	PreparedStatement pr=con.prepareStatement(select);
	        	pr.setString(1,admin.getAccount().getUsername());
	        	ResultSet set=pr.executeQuery();
	        	int userid=0;
	        	while(set.next()) {
	        		userid=set.getInt(1);
	        	}
	            // Insert into ADMIN table
	            final String ADMIN_INSERT = "INSERT INTO BUSTICKETBOOKINGSYSTEM.ADMIN "
	                                        + "(USERID,USERNAME, PASSWORD, EMAIL, USERTYPE) "
	                                        + "VALUES (?,?, ?, ?, ?)";
	            preparedStatement = con.prepareStatement(ADMIN_INSERT);
	            preparedStatement.setInt(1, userid);
	            preparedStatement.setString(2, admin.getAccount().getUsername());
	            preparedStatement.setString(3, admin.getAccount().getPassword());
	            preparedStatement.setString(4, admin.getAccount().getEmail());
	            preparedStatement.setString(5, admin.getAccount().getUserType().toString());
	            int adminInsertCount = preparedStatement.executeUpdate();
	            // Insert into ACCOUNT table
	            if (adminInsertCount > 0) {
	                final String ACCOUNT_INSERT = "INSERT INTO busticketBookingsystem.ACCOUNT (USERID, USERROLE, ISACTIVE) "
	                                             + "VALUES (?, ?, ?)";
	                preparedStatement = con.prepareStatement(ACCOUNT_INSERT);
	                preparedStatement.setInt(1, userid);
	                preparedStatement.setString(2, admin.getAccount().getUserType().toString());// Initial balance
	                preparedStatement.setInt(3, 1); // Assuming user is active by default

	                int accountInsertCount = preparedStatement.executeUpdate();
	                if (accountInsertCount > 0) {
	                    registrationStatus = true;
	                }
	            }
	        }
	    } 
	    finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }
	    return registrationStatus;
	}
	public static boolean registerCustomer(Connection con, Customer customer) throws SQLException,SQLIntegrityConstraintViolationException  {
	    boolean registrationStatus = false;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        // Insert into USERS table
	        final String USER_INSERT = "INSERT INTO BUSTICKETBOOKINGSYSTEM.USERS "
	                                  + "(USERNAME, PASSWORD, EMAIL, USERTYPE) "
	                                  + "VALUES (?, ?, ?, ?)";

	        preparedStatement = con.prepareStatement(USER_INSERT, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, customer.getAccount().getUsername());
	        preparedStatement.setString(2, customer.getAccount().getPassword());
	        preparedStatement.setString(3, customer.getAccount().getEmail());
	        preparedStatement.setString(4, customer.getAccount().getUserType().toString());
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows > 0) {
	        	final String select="select userid from busticketbookingsystem.users where username=?";
	        	PreparedStatement pr=con.prepareStatement(select);
	        	pr.setString(1,customer.getAccount().getUsername());
	        	ResultSet set=pr.executeQuery();
	        	int userid=0;
	        	while(set.next()) {
	        		userid=set.getInt(1);
	        	}
                // Insert into CUSTOMER table
                final String CUSTOMER_INSERT = "INSERT INTO BUSTICKETBOOKINGSYSTEM.CUSTOMER "
                                               + "(USERID, USERNAME, PASSWORD, EMAIL, USERTYPE) "
                                               + "VALUES (?, ?, ?, ?, ?)";
                preparedStatement = con.prepareStatement(CUSTOMER_INSERT);
                preparedStatement.setInt(1, userid);
                preparedStatement.setString(2, customer.getAccount().getUsername());
                preparedStatement.setString(3, customer.getAccount().getPassword());
                preparedStatement.setString(4, customer.getAccount().getEmail());
                preparedStatement.setString(5, customer.getAccount().getUserType().toString());
                int customerInsertCount = preparedStatement.executeUpdate();
                if (customerInsertCount > 0) {
                    // Insert into ACCOUNT table
                    final String ACCOUNT_INSERT = "INSERT INTO busticketbookingsystem.ACCOUNT (USERID, USERROLE, ISACTIVE) "
                                                 + "VALUES (?, ?, ?)";
                    preparedStatement = con.prepareStatement(ACCOUNT_INSERT);
                    preparedStatement.setInt(1, userid);
                    preparedStatement.setString(2, customer.getAccount().getUserType().toString());
                    preparedStatement.setInt(3, 1); // Assuming user is active by default
                    int accountInsertCount = preparedStatement.executeUpdate();
                    if (accountInsertCount > 0) {
                        registrationStatus = true;
                    }
                }
            }
	    } 
	    finally {
        // Close resources
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }
	    return registrationStatus;
	}
	public static boolean AddNewUser(Connection con, String username, String password, String email, UserType userType, String statement, String typeStatement) throws SQLException,SQLIntegrityConstraintViolationException  {
	    PreparedStatement settingUser = null;
	    PreparedStatement settingAccount = null;
	   try {
	        settingUser = con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
	        settingUser.setString(1, username);
	        settingUser.setString(2, password);
	        settingUser.setString(3, email);
	        settingUser.setString(4, userType.toString());
	        int rowsUpdated = settingUser.executeUpdate();
	        if (rowsUpdated > 0) {
	            ResultSet generatedKeys = settingUser.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int userId = generatedKeys.getInt(1);

	                // Insert into the appropriate table based on UserType
	                switch (userType) {
	                    case ADMINISTRATOR:
	                    case CUSTOMER:
	                        settingAccount = con.prepareStatement(typeStatement);
	                        settingAccount.setInt(1, userId);
	                        settingAccount.setString(2, username);
	                        settingAccount.setString(3, password);
	                        settingAccount.setString(4, email);
	                        settingAccount.setString(5, userType.toString());
	                        int accountRowsUpdated = settingAccount.executeUpdate();
	                        if (accountRowsUpdated > 0) {
	                            System.out.println("User added!");
	                            return true;
	                        }
	                        break;
	                    default:
	                        System.out.println("Unsupported user type!");
	                }
	            }
	        } else {
	            System.out.println("User is not added!");
	        }
	   }finally {
	        // Close resources
	        try {
	            if (settingUser != null) {
	                settingUser.close();
	            }
	            if (settingAccount != null) {
	                settingAccount.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    }
	    return false;
	}

	public static boolean removeUser(Connection con,String username,String password,String email,String statement,UserType usertype) throws SQLException,SQLIntegrityConstraintViolationException  {

		try(PreparedStatement deletion=con.prepareStatement(statement)){
			deletion.setString(1,username);
			deletion.setString(2,password);
			deletion.setString(3,email);
			int rowsUpdated=deletion.executeUpdate();
			if(rowsUpdated>0) {
				return true;
			}
		}
		return false;
	}

	public static void displayAll(Connection con,UserType usertype,String statement) throws SQLException,SQLIntegrityConstraintViolationException  {
		try(PreparedStatement display=con.prepareStatement(statement)){
			try(ResultSet resultSet=display.executeQuery()){
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	            System.out.printf("| %-7s| %-10s| %-30s| %-20s| %-12s| %-12s| %-12s| %-12s| %-10s| %-10s| %-12s|%n", 
	                              "USERID", "USERNAME", "EMAIL", "USERTYPE", "FIRSTNAME", "LASTNAME", "PHONENUMBER",
	                              "GENDER", "ADDRESS", "CITY", "COUNTRY", "POSTALCODE");
	            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	            while (resultSet.next()) {
	                if (usertype.equals(UserType.ADMINISTRATOR)) {
	                    System.out.printf("| %-7d| %-10s| %-30s| %-20s| %-12s| %-12s| %-12s| %-12s| %-10s| %-10s| %-12s|%n",
	                                      resultSet.getInt("USERID"), resultSet.getString("USERNAME"), resultSet.getString("EMAIL"),
	                                      resultSet.getString("USERTYPE"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
	                                      resultSet.getString("PHONENUMBER"), resultSet.getString("GENDER"),
	                                      resultSet.getString("ADDRESS"), resultSet.getString("CITY"),
	                                      resultSet.getString("COUNTRY"),resultSet.getString("POSTALCODE"));
	                } else if (usertype.equals(UserType.CUSTOMER)) {
	                	  System.out.printf("| %-7d| %-10s| %-30s| %-20s| %-12s| %-12s| %-12s| %-12s| %-10s| %-10s| %-12s|%n",
                                  resultSet.getInt("USERID"), resultSet.getString("USERNAME"), resultSet.getString("EMAIL"),
                                  resultSet.getString("USERTYPE"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
                                  resultSet.getString("PHONENUMBER"), resultSet.getString("GENDER"),
                                  resultSet.getString("ADDRESS"), resultSet.getString("CITY"),
                                  resultSet.getString("COUNTRY"),resultSet.getString("POSTALCODE"));

	                }
	            }
	            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	        }
	    }
	}
	//}
	public static void displaySpecific(Connection con,String username,String password,String email,UserType usertype,String statement) throws SQLException,SQLIntegrityConstraintViolationException  {
		try(PreparedStatement display=con.prepareStatement(statement)){
			display.setString(1,username);
			display.setString(2, password);
			display.setString(3, email);
			try(ResultSet resultSet=display.executeQuery()){
					System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		            System.out.printf("| %-7s| %-10s| %-20s| %-30s| %-12s| %-12s| %-12s| %-12s| %-10s| %-10s| %-12s|%n", 
		                              "USERID", "USERNAME", "EMAIL", "USERTYPE", "FIRSTNAME", "LASTNAME", "PHONENUMBER",
		                              "GENDER", "ADDRESS", "CITY", "COUNTRY", "POSTALCODE");
		            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		            while (resultSet.next()) {
		                if (usertype.equals(UserType.ADMINISTRATOR)) {
		                    System.out.printf("| %-7d| %-10s| %-20s| %-30s| %-12s| %-12s| %-12s| %-12s| %-10s| %-10s| %-12s|%n",
		                                      resultSet.getInt("USERID"), resultSet.getString("USERNAME"), resultSet.getString("EMAIL"),
		                                      resultSet.getString("USERTYPE"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
		                                      resultSet.getString("PHONENUMBER"), resultSet.getString("GENDER"),
		                                      resultSet.getString("ADDRESS"), resultSet.getString("CITY"),
		                                      resultSet.getString("COUNTRY"),resultSet.getString("POSTALCODE"));
		                } else if (usertype.equals(UserType.CUSTOMER)) {
		                    System.out.printf("| %-7d| %-10s| %-20s| %-30s| %-12s| %-12s| %-12s| %-12s| %-10s| %-10s| %-12s|%n",
		                                      resultSet.getInt("USERID"), resultSet.getString("USERNAME"), resultSet.getString("EMAIL"),
		                                      resultSet.getString("USERTYPE"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"),
		                                      resultSet.getString("PHONENUMBER"), resultSet.getString("GENDER"),
		                                      resultSet.getString("ADDRESS"), resultSet.getString("CITY"),
		                                      resultSet.getString("COUNTRY"),resultSet.getString("POSTALCODE"));
		                }
		            }
		            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		        }
			}
		}
	public static boolean insertBusOperator(Connection con,String operatorName, String contactNumber, String email, String address,String city, String country, int numberOfBuses,String statement) throws SQLException,SQLIntegrityConstraintViolationException {
		try(PreparedStatement insert = con.prepareStatement(statement)){
			insert.setString(1, operatorName);
			insert.setString(2, contactNumber);
			insert.setString(3, email);
			insert.setString(4, address);
			insert.setString(5, city);
			insert.setString(6, country);
			insert.setInt(7, numberOfBuses);
			int rowsInserted=insert.executeUpdate();
			if(rowsInserted>0) {
				return true;
			}
		}
		return false;
	}
	public static boolean isOperatorExists(Connection con,int operatorId) throws SQLException,SQLIntegrityConstraintViolationException {
		boolean result=false;
	    String SELECT= "SELECT COUNT(*) FROM BUSTICKETBOOKINGSYSTEM.BUSOPERATOR WHERE OPERATORID = ?";
	    try(PreparedStatement exist= con.prepareStatement(SELECT)){
		    exist.setInt(1, operatorId);
		    try(ResultSet resultSet = exist.executeQuery()){
			    resultSet.next();
			    int count = resultSet.getInt(1);
			    if(count>0) {
			    	return true;
			    }
		    }
	    }
		return false;
	}
	public static boolean deleteOperator(Connection con,int operatorId,String statement) throws SQLException,SQLIntegrityConstraintViolationException  {
		try(PreparedStatement delete= con.prepareStatement(statement)){
	        delete.setInt(1, operatorId);
	        int rowsDeleted = delete.executeUpdate();
	        if (rowsDeleted > 0) {
	        	return true;
	        }
		}
		return false;
	}
	public static boolean updateBusOperator(Connection con, int operatorId, String newContactNumber) throws SQLException,SQLIntegrityConstraintViolationException  {
        final String OPERATOR_UPDATE = "UPDATE BUSTICKETBOOKINGSYSTEM.BUSOPERATOR SET CONTACTNUMBER = ? WHERE OPERATORID = ?";
        try(PreparedStatement update = con.prepareStatement(OPERATOR_UPDATE)){
	        update.setString(1, newContactNumber);
	        update.setInt(2, operatorId);
	        int rowsUpdated = update.executeUpdate();
	        if (rowsUpdated > 0) {
	            return true;
	        }
	     }
	    return false;
	}
	public static void getBusOperators(Connection con) throws SQLException,SQLIntegrityConstraintViolationException  {
        final String OPERATOR_SELECT = "SELECT * FROM BUSTICKETBOOKINGSYSTEM.BUSOPERATOR order by operatorid";
        try(Statement statement = con.createStatement()){
	        try(ResultSet resultSet = statement.executeQuery(OPERATOR_SELECT)){
//		        while (resultSet.next()) {
//		        	System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3)
//		        	+" "+resultSet.getString(4)+" "+resultSet.getString(5)+" "+resultSet.getString(6)+
//		        	" "+resultSet.getString(7)+" "+resultSet.getString(8)+" ");
//		        }
//	        }
	        	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	            System.out.printf("| %-10s | %-25s | %-15s | %-30s | %-20s | %-15s | %-15s | %-10s |\n", 
	                              "OPERATORID", "OPERATORNAME", "CONTACTNUMBER", "EMAIL", "ADDRESS", "CITY", "COUNTRY", "NUMBEROFBUSES");
	            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	            while (resultSet.next()) {
	                System.out.printf("| %-10s | %-25s | %-15s | %-30s | %-20s | %-15s | %-15s | %-10s |\n",
	                                  resultSet.getInt(1), resultSet.getString(2),
	                                  resultSet.getString(3), resultSet.getString(4),
	                                  resultSet.getString(5), resultSet.getString(6),
	                                  resultSet.getString(7), resultSet.getInt(8));
	            }
	            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	        }
	    }
	}
	public static boolean insertBusRoute(Connection con,String originCity, String destinationCity, String departureDate, String arrivalDate, String departureTime, String arrivalTime, int distance, String duration) throws SQLException,SQLIntegrityConstraintViolationException  {
        boolean inserted = false;
        String sql = "INSERT INTO BUSTICKETBOOKINGSYSTEM.BusRoute (originCity, destinationCity, departureTime, arrivalTime, distance, duration) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = con.prepareStatement(sql)){
            LocalDate departureLocalDate = LocalDate.parse(departureDate);
            LocalDate arrivalLocalDate = LocalDate.parse(arrivalDate);
            DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                    .appendPattern("[hh:mm a][HH:mm]")
                    .toFormatter();
            LocalTime departureLocalTime = LocalTime.parse(departureTime, timeFormatter);
            LocalTime arrivalLocalTime = LocalTime.parse(arrivalTime, timeFormatter);
            LocalDateTime departureDateTime = LocalDateTime.of(departureLocalDate, departureLocalTime);
            LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalLocalDate, arrivalLocalTime);
            pstmt.setString(1, originCity);
            pstmt.setString(2, destinationCity);
            pstmt.setTimestamp(3, Timestamp.valueOf(departureDateTime));
            pstmt.setTimestamp(4, Timestamp.valueOf(arrivalDateTime));
            pstmt.setInt(5, distance);
            pstmt.setString(6, duration);
            int rowsInserted = pstmt.executeUpdate();
            inserted = rowsInserted > 0;
        }
        return inserted;
    }

	public static boolean deleteBusRoute(Connection con,int routeId) throws SQLException,SQLIntegrityConstraintViolationException  {
        String DELETE= "DELETE FROM BUSTICKETBOOKINGSYSTEM.BusRoute WHERE RouteID = ?";
        try(PreparedStatement remove= con.prepareStatement(DELETE)){
	        remove.setInt(1, routeId);
	        int rowsDeleted =remove.executeUpdate();
	        if (rowsDeleted > 0) {
	            return true;
	        }
	    }
	    return false;
	}

    public static void updateBusRoutes(Connection connection) throws SQLException,SQLIntegrityConstraintViolationException  {
        String originCity;
        int routeId=0;
        String destinationCity;
        LocalDate departureDate = null;
        LocalTime departureTime;
        LocalDate arrivalDate= null;
        LocalTime arrivalTime;
        double distance = 0;
        String duration;
        int busId;
        while(true) {
        	try {
        		System.out.println("Enter the route id:");
	        	routeId = scanner.nextInt();
	        	scanner.nextLine();
	        	break;
        	}
        	catch(InputMismatchException e) {
        		System.out.println("Invalid input! Enter the numerical value.");
        		continue;
        	}
        }
        while (true) {
            System.out.println("Enter origin city: ");
            originCity = scanner.nextLine();
            if (originCity.isEmpty() || originCity.length() > 100) {
                System.out.println("Invalid input for origin city. It should not be empty and should not exceed 100 characters.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Enter destination city: ");
            destinationCity = scanner.nextLine();
            if (destinationCity.isEmpty() || destinationCity.length() > 100) {
                System.out.println("Invalid input for destination city. It should not be empty and should not exceed 100 characters.");
            } else {
                break;
            }
        }
        while (true) {
            try {
                System.out.println("Enter departure time (yyyy-MM-dd): ");
                departureDate = LocalDate.parse(scanner.nextLine());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input format for time. Please use the format yyyy-MM-dd HH:mm:ss.");
            }
        }
       
		
		while (true) {
            try {
                System.out.println("Enter departure time (HH:mm): ");
                departureTime = LocalTime.parse(scanner.nextLine());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input format for time. Please use the format yyyy-MM-dd HH:mm:ss.");
            }
        }
        while (true) {
            try {
                System.out.println("Enter arrival date (yyyy-MM-dd): ");
                arrivalDate = LocalDate.parse(scanner.nextLine());
                if (departureDate.isBefore(arrivalDate)) {
                    System.out.println("Departure is before arrival.");
                    break;
                } else {
                    System.out.println("Departure is not before arrival.");
                }
               
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input format for time. Please use the format yyyy-MM-dd HH:mm:ss.");
            }
        }
        while (true) {
            try {
                System.out.println("Enter arrival time (HH:mm): ");
                arrivalTime = LocalTime.parse(scanner.nextLine());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input format for time. Please use the format yyyy-MM-dd HH:mm:ss.");
            }
        }
        while (true) {
            System.out.println("Enter distance: ");
            if (scanner.hasNextDouble()) {
                distance = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                break;
            } else {
                System.out.println("Invalid input for distance. Please enter a valid number.");
                scanner.nextLine(); // consume invalid input
            }
        }

        while (true) {
            System.out.println("Enter duration: ");
            duration = scanner.nextLine();
            if (duration.length() > 20) {
                System.out.println("Invalid input for duration. It should not exceed 20 characters.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Enter bus ID: ");
            if (scanner.hasNextInt()) {
                busId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                break;
            } else {
                System.out.println("Invalid input for bus ID. Please enter a valid integer.");
                scanner.nextLine(); // consume invalid input
            }
        }
        LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);
        // Convert LocalDateTime to Timestamp
        Timestamp dtime = Timestamp.valueOf(departureDateTime);
        Timestamp atime= Timestamp.valueOf(arrivalDateTime);
        // Update logic for the BUSROUTE table
        String sql = "UPDATE busticketbookingsystem.BUSROUTE SET ORIGINCITY=?, DESTINATIONCITY=?, DEPARTURETIME=?, ARRIVALTIME=?, DISTANCE=?, DURATION=?, BUSID=? WHERE ROUTEID=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, originCity);
            statement.setString(2, destinationCity);
            statement.setTimestamp(3, dtime);
            statement.setTimestamp(4, atime);
            statement.setDouble(5, distance);
            statement.setString(6, duration);
            statement.setInt(7, busId);
            statement.setInt(8, routeId);
            // Execute the update statement
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Bus route information updated successfully.");
            } else {
                System.out.println("Failed to update bus route information.");
            }
        }
    }

	public static List<BusRoute> fetchAvailableBusRoutes(Connection con,String origin, String destination) throws SQLException,SQLIntegrityConstraintViolationException  {
	    List<BusRoute> routes = new ArrayList<>(); 
        String query = "SELECT * FROM BUSTICKETBOOKINGSYSTEM.busroute WHERE origincity = ? AND destinationcity = ? order by routeId";
        try(PreparedStatement statement = con.prepareStatement(query)){
            statement.setString(1, origin);
            statement.setString(2, destination);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    int routeId = resultSet.getInt(1);
                    String routeOrigin = resultSet.getString(2);
                    String routeDestination = resultSet.getString(3);
                    String departuretime=resultSet.getString(4).toString();
                    String arrivaltime=resultSet.getString(5).toString();
                    BusRoute route = new BusRoute(routeId, routeOrigin, routeDestination,departuretime,arrivaltime);
                    routes.add(route);
                }
            }
	    }
	    return routes;
	}
	public static List<BusRoute> fetchBusRoutesFromDatabase(Connection con) throws SQLException,SQLIntegrityConstraintViolationException {
		List<BusRoute> routes = new ArrayList<>();
        String sql = "SELECT * FROM BUSTICKETBOOKINGSYSTEM.BusRoute order by routeid";
        try(PreparedStatement statement = con.prepareStatement(sql)){
	        try(ResultSet resultSet = statement.executeQuery()){
		        while (resultSet.next()) {
		            int routeId = resultSet.getInt("RouteID");
		            String originCity = resultSet.getString("OriginCity");
		            String destinationCity = resultSet.getString("DestinationCity");
		            String departureTime = resultSet.getTimestamp("DepartureTime").toString();
		            String arrivalTime = resultSet.getTimestamp("ArrivalTime").toString();
		            int distance = resultSet.getInt("Distance");
		            String duration = resultSet.getString("Duration");
		            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
		            LocalTime durationTime=LocalTime.parse(duration,format);
		            BusRoute route=new BusRoute(routeId,originCity,destinationCity,departureTime,arrivalTime,distance,durationTime);
		            routes.add(route);		            
	                System.out.println("-------------------------------------------------------------------------------------------");
	                System.out.printf("| %-7s | %-12s | %-15s | %-21s | %-21s |\n", "ROUTEID", "ORIGINCITY", "DESTINATIONCITY", "DEPARTURETIME", "ARRIVALTIME");
	                System.out.println("-------------------------------------------------------------------------------------------");
	                while (resultSet.next()) {
	                    System.out.printf("| %-7d | %-12s | %-15s | %-21s | %-21s |\n",
	                                      resultSet.getInt("ROUTEID"), resultSet.getString("ORIGINCITY"),
	                                      resultSet.getString("DESTINATIONCITY"), resultSet.getString("DEPARTURETIME"),
	                                      resultSet.getString("ARRIVALTIME"));
	                }
	                System.out.println("----------------------------------------------------------------------------------------------------");
	            }
		   }
	    }
        return routes;
	}
	public static List<BusSchedule> fetchBusSchedules(Connection con,String origin, String destination) throws SQLException,SQLIntegrityConstraintViolationException  {
	    List<BusSchedule> schedules = new ArrayList<>();
        String query = "SELECT bs.* " +
                       "FROM BUSTICKETBOOKINGSYSTEM.busroute br " +
                       "JOIN BUSTICKETBOOKINGSYSTEM.busschedule bs ON br.routeid = bs.routeid " +
                       "WHERE br.origincity = ? AND br.destinationcity = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, origin);
            statement.setString(2, destination);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int scheduleId = resultSet.getInt(1);
                    String departureTime = resultSet.getString(2);
                    String arrivalTime = resultSet.getString(3);
                    // Create a BusSchedule object and add it to the list
                    BusSchedule schedule = new BusSchedule(scheduleId, departureTime, arrivalTime);
                    schedules.add(schedule);
                }
            }
        }
	    return schedules;
	}
	public static void viewSchedules(Connection con) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT * FROM busticketbookingsystem.busschedule";
        try(PreparedStatement stmt = con.prepareStatement(query)){
        	try(ResultSet rs = stmt.executeQuery()){
	        	System.out.println("----------------------------------------------------------");
	        	System.out.println("All Bus Schedules:");
	        	System.out.println("----------------------------------------------------------");
	        	System.out.printf("| %-12s | %-21s | %-21s |\n", "Schedule ID", "Departure Time", "Arrival Time");
	        	System.out.println("----------------------------------------------------------");
	        	while (rs.next()) {
	        	    int scheduleId = rs.getInt(1);
	        	    Timestamp departureTime = rs.getTimestamp(3);
	        	    Timestamp arrivalTime = rs.getTimestamp(4);
	        	    System.out.printf("| %-12d | %-21s | %-21s |\n", scheduleId, departureTime, arrivalTime);
	        	}
	        	System.out.println("----------------------------------------------------------");
	        }
	   }
	}
	public static void viewBusRoute(Connection con, int scheduleId) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT r.* FROM busticketbookingsystem.busroute r JOIN busticketbookingsystem.busschedule s ON r.routeid = s.routeid WHERE s.scheduleid = ?";
        try(PreparedStatement stmt = con.prepareStatement(query)){
	        stmt.setInt(1, scheduleId);
	        try(ResultSet rs = stmt.executeQuery()){
		        System.out.println("Bus Route for Schedule ID " + scheduleId + ":");
		        while (rs.next()) {
		            int routeId = rs.getInt("ROUTEID");
		            String source = rs.getString("origincity");
		            String destination = rs.getString("destinationcity");
		            System.out.println("Route ID: " + routeId + ", Source: " + source + ", Destination: " + destination);
		        }
	        }
	     }
	}
	public static void checkAvailableSeat(Connection connection, int routeId) throws SQLException,SQLIntegrityConstraintViolationException  {
        String availableSeatsQuery = "SELECT SEATNUMBER " +
                                     "FROM BUSTICKETBOOKINGSYSTEM.SEAT " +
                                     "WHERE BUSID = (SELECT BUSID FROM BUSTICKETBOOKINGSYSTEM.BUSSCHEDULE WHERE ROUTEID = ?) " +
                                     "AND ISOCCUPIED = 0";
        try(PreparedStatement preparedStatement = connection.prepareStatement(availableSeatsQuery)){
	        preparedStatement.setInt(1, routeId);
	        // Execute the query
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        // Print the available seats
		        System.out.println("Available Seats for Route ID " + routeId + ":");
		        while (resultSet.next()) {
		            int seatNumber = resultSet.getInt("SEATNUMBER");
		            System.out.println("Seat Number: " + seatNumber);
		        }
	        }
        }

	}
	public static void checkAvailableSeat(Connection connection, String originCity, String destinationCity) throws SQLException,SQLIntegrityConstraintViolationException  {
        String seatavailability="select routeid,busid,count(*) as a from busticketbookingsystem.seat where busid=(select busid from busticketbookingsystem.busroute where routeid=(select routeid from busticketbookingsystem.busroute where origincity=? and destinationcity=?)) and isoccupied=0";
        int routeid=0,busid=0,count=0;
        String route="select routeid from busticketbookingsystem.busroute where origincity=? and destinationcity=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(route)){
            preparedStatement.setString(1, originCity);
            preparedStatement.setString(2, destinationCity);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
	            while(resultSet.next()) {
	            	routeid=resultSet.getInt(1);
	            }
            }
        }
        String r="select busid from busticketbookingsystem.busroute where routeid=?";
        try(PreparedStatement prepared = connection.prepareStatement(r)){
            prepared.setInt(1, routeid);
            // Execute the query
            try(ResultSet Set = prepared.executeQuery()){
	            while(Set.next()) {
	            	busid=Set.getInt(1);
	            }  
            }
        }
        String c = "SELECT COUNT(*) FROM busticketbookingsystem.seat WHERE busid=? and isoccupied=0";
        try(PreparedStatement prep = connection.prepareStatement(c)){
            prep.setInt(1, busid);
            try(ResultSet resultSet1 = prep.executeQuery()){ // Execute the query and store the result
	            // Check if there are available seats for the specified route
	            if (resultSet1.next()) {
	                int availableSeats = resultSet1.getInt(1); // Get the count of available seats
	                if (availableSeats > 0) {
	                    System.out.println("Seats available for Route ID " + routeid + " (Origin: " + originCity + ", Destination: " + destinationCity + "):");
	                    System.out.println("Available Seats: " + availableSeats);
	                } 
	                else {
	                    System.out.println("No available seats for the specified route.");
	                }
	            } 
	            else {
	                System.out.println("No data found for the specified route.");
	            }
            }
        }

    }
	public static boolean selectSeatForBooking(Connection connection, int routeId,int userId) throws SQLException, InvalidInputException,SQLIntegrityConstraintViolationException {
		 boolean bol=false;
		 int count=0;  
		 List<Integer> selectedSeat=new ArrayList<>();
        // the SQL query to get available seats for the route
        String query = "SELECT SEATNUMBER,SEATTYPE,SEATFARE FROM BUSTICKETBOOKINGSYSTEM.SEAT WHERE BUSID = (SELECT BUSID FROM BUSTICKETBOOKINGSYSTEM.BUSROUTE WHERE ROUTEID = ?) AND ISOCCUPIED = 0";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, routeId);
            // Execute the query
            try(ResultSet resultSet = preparedStatement.executeQuery()){
	            int availableSeatCount=0;
	            // Display available seats
	            System.out.println("Available Seats for Route ID " + routeId + ":");
	            while (resultSet.next()) {
	                int seatNumber = resultSet.getInt("SEATNUMBER");
	                String seattype = resultSet.getString("SEATTYPE");
	                double seatfare=(double)resultSet.getInt("SEATFARE");
	                System.out.println("Seat Number: " + seatNumber+" Seat Type: "+seattype);
	                availableSeatCount++;
	            }
	            if(availableSeatCount>0) {
	            	System.out.println("Enter the number of seats to book:");
	            	int numberofseats=scanner.nextInt();
	            	scanner.nextLine();
	            	if(availableSeatCount>=numberofseats) {
		            	for(int i=0;i<numberofseats;i++) {
		            		while(true) {
		            			System.out.print("Enter the seat number you want to book: ");
			            		int selectedSeatNumber = scanner.nextInt();
			            		if (isSeatAvailable(connection, selectedSeatNumber, routeId)) {
			            			selectedSeat.add(selectedSeatNumber);
			    	                System.out.println("Seat " + selectedSeatNumber + " selected successfully for booking.");
			    	                count++;
			    	                break;
			    	            } else {
			    	                System.out.println("Seat " + selectedSeatNumber + " is not available for booking.");
			    	            }
		            		}
		            	}
		            	try {
		            		JdbcConnection.bookTicket(connection, userId, routeId,count,selectedSeat);
		            		bol=true;
		            	} catch (SQLException e) {
		            		System.out.println(e.getMessage());
		            	}
	            	}
	            	else {
	            		System.out.println("Unavailable number of seats");
	            	}

	            }
	            else {
	            	System.out.println("seats are not available");
	            }
            }
        }
        return bol;
    }

 	public static boolean isSeatAvailable(Connection con, int seatNumber, int routeId) throws SQLException,SQLIntegrityConstraintViolationException {
        String query = "SELECT COUNT(*) AS count FROM BUSTICKETBOOKINGSYSTEM.SEAT WHERE BUSID = (SELECT BUSID FROM BUSTICKETBOOKINGSYSTEM.BUSROUTE WHERE ROUTEID = ?) AND SEATNUMBER = ? AND ISOCCUPIED = 0";
        int count=0;
        try(PreparedStatement preparedStatement = con.prepareStatement(query)){
	        preparedStatement.setInt(1, routeId);
	        preparedStatement.setInt(2, seatNumber);
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        resultSet.next();
		        count = resultSet.getInt("count");
		        preparedStatement.close();
		        resultSet.close();
		    }
        }
        return count > 0;
    }
 	public static boolean isSeatAvailable(Connection con, int routeId, String busSeatType, String seatType) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT * FROM BUSTICKETBOOKINGSYSTEM.SEAT WHERE BUSID IN (SELECT BUSID FROM BUSSCHEDULE WHERE ROUTEID = ?) " +
                "AND BUSSEATTYPE = ? AND SEATTYPE = ? AND ISOCCUPIED = 0";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);
            preparedStatement.setString(2, busSeatType);
            preparedStatement.setString(3, seatType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

 	public static void bookTicket(Connection connection, int userId, int routeId, int numSeats,List<Integer> seats) throws SQLException, InvalidInputException,SQLIntegrityConstraintViolationException {
 		try {
        // Check seat availability
        boolean seatsAvailable = checkSeatAvailability(connection, routeId, numSeats);
        if (!seatsAvailable) {
            System.out.println("Sorry, the requested number of seats are not available.");
            return;
        }
        else {
	        String seatType;
	        double totalFare=0.0;
	        for(Integer x:seats) {
		        String seatquery="SELECT SEATTYPE FROM BUSTICKETBOOKINGSYSTEM.SEAT WHERE SEATNUMBER=?";
		        try(PreparedStatement preparedstatement=connection.prepareStatement(seatquery)){
			        preparedstatement.setInt(1, x);
			        try(ResultSet set=preparedstatement.executeQuery()){
				        while(set.next()) {
				        	seatType=set.getString("SEATTYPE");
				        	 double seatFare = fetchSeatFare(connection, routeId, seatType);
				        	 totalFare=totalFare+seatFare;
				        }
			        }
			    }
	        }
	        // Fetch bus seat type based on route ID
	        String busSeatType = fetchBusSeatType(connection, routeId);
	        // Fetch seat fare based on bus seat type and seat type
	        // Generate booking ID
	        int bookingId = generateBookingId(connection);
	        // Get current timestamp
	        Timestamp bookingDate = new Timestamp(System.currentTimeMillis());
	        // Insert booking details into the database
	        String paymentMethod=payment(totalFare);
	        insertBooking(connection, bookingId, userId, routeId, numSeats, totalFare, bookingDate, 1);
	        processPayment(connection, bookingId, userId, totalFare, bookingDate,paymentMethod);
	        // Active status is set to 1
	        updateSeatOccupancy(connection, routeId, numSeats,seats);
	        String message="Booking successful! Your Booking ID is "+ bookingId+".Successfully booked "+numSeats+" seats on route ID "+routeId+" for your user ID "+userId
	        		+", at "+bookingDate+". The fare for the booking is "+totalFare+".";
	        System.out.println("Booking successful! Your Booking ID is: " + bookingId);
	        notification(connection,userId,message);
	    }
 		}catch(SQLIntegrityConstraintViolationException e) {
 			System.out.println("Invalid data! Please enter valid data.");
 		}
 	}
 	public static String payment(double totalFare) throws InvalidInputException ,SQLIntegrityConstraintViolationException {
 		System.out.println("Total amount to pay: Rs." + totalFare);
        // Select payment method
 		 String paymentMethod="not specified";
        System.out.println("Select payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        System.out.println("3. Exit");
        int paymentChoice=0;
        while(true) {
	        try {
	        System.out.print("Enter your choice:");
	        paymentChoice = scanner.nextInt();
	        scanner.nextLine();
	        if(paymentChoice>=1 && paymentChoice<=3) {
		        Payment payment;
		        switch (paymentChoice) {
		            case 1:
		                payment = new CashPayment(totalFare);
		                paymentMethod="Cash";
		                payment.processPayment();
		                break;
		            case 2:
		                payment = new CreditCardPayment(totalFare, scanner);
		                paymentMethod="Credit card";
		                payment.processPayment();
		                break;
		            case 3:
		            	System.out.println("Thank you!");
		            	System.exit(0);
		            default:
		                System.out.println("Invalid choice. Payment failed.");
		                break;
		        	}
		        	break;
	        }
	        else {
	        	System.out.println("Invalid input!");
	        	continue;	
	        }
        }
        catch(InputMismatchException e) {
        	System.out.println("Invalid input!........");
        	scanner.nextLine();
        }
     }
    return paymentMethod;
 	}
	public static void processPayment(Connection connection, int bookingId, int userId, double totalFare, Timestamp bookingDate, String paymentMethod) throws SQLException,SQLIntegrityConstraintViolationException  {
	    // Generate payment ID
	    int paymentId = generatePaymentId(connection);
	    int transactionId=generateTransactionId(connection);
	    // Get current timestamp for payment date
	    Timestamp paymentDate = new Timestamp(System.currentTimeMillis());
	    // Insert payment details into the database
	    insertPayment(connection, paymentId, userId, bookingId, totalFare, paymentDate,paymentMethod,transactionId);
	}
	public static void notification(Connection connection,int userId,String message) throws SQLException,SQLIntegrityConstraintViolationException  {
		String query="SELECT EMAIL FROM BUSTICKETBOOKINGSYSTEM.USERS WHERE UserID=?";
		try(PreparedStatement preparedstatement=connection.prepareStatement(query)){
			preparedstatement.setInt(1, userId);
			try(ResultSet set=preparedstatement.executeQuery()){
				String email="Not available";
				while(set.next()) {
					email=set.getString("EMAIL");
				}
				Notification notification=new EmailNotification(email);
				notification.sendNotification(message);
			}
		}
	}
	public static void insertPayment(Connection connection, int paymentId, int userId, int bookingId, double totalAmount, Timestamp paymentDate, String paymentMethod,int transactionID) throws SQLException ,SQLIntegrityConstraintViolationException {
	    String query = "INSERT INTO busticketbookingsystem.PAYMENT (PAYMENTID, USERID, BOOKINGID, AMOUNT, PAYMENTDATE,PAYMENTMETHOD,TRANSACTIONID,PAYMENTSTATUS) VALUES (?, ?, ?, ?, ?,?,?,?)";
	    try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
		    preparedStatement.setInt(1, paymentId);
		    preparedStatement.setInt(2, userId);
		    preparedStatement.setInt(3, bookingId);
		    preparedStatement.setDouble(4, totalAmount);
		    preparedStatement.setTimestamp(5, paymentDate);
		    preparedStatement.setString(6,paymentMethod);
		    preparedStatement.setInt(7,transactionID);
		    preparedStatement.setInt(8, 1);
		    preparedStatement.executeUpdate();
		}
	}

	public static int generatePaymentId(Connection connection) throws SQLException,SQLIntegrityConstraintViolationException {
	    String query = "SELECT MAX(PAYMENTID) AS MAX_PAYMENTID FROM busticketbookingsystem.PAYMENT";
	    try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
		    try(ResultSet resultSet = preparedStatement.executeQuery()){
			    if (resultSet.next()) {
			        return resultSet.getInt("MAX_PAYMENTID") + 1;
			    }
		    }
	    }
	    return 1;
	}
	public static int generateTransactionId(Connection connection) throws SQLException,SQLIntegrityConstraintViolationException  {
	    String query = "SELECT MAX(TRANSACTIONID) AS MAX_TRANSACTIONID FROM busticketbookingsystem.PAYMENT";
	    try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
		    try(ResultSet resultSet = preparedStatement.executeQuery()){
			    if (resultSet.next()) {
			        return resultSet.getInt("MAX_TRANSACTIONID") + 1;
			    }
		    }
	    }
	    return 1;
	}
    // Method to check seat availability
    private static boolean checkSeatAvailability(Connection connection, int routeId, int numSeats) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT COUNT(*) AS total_seats FROM BUSTICKETBOOKINGSYSTEM.seat WHERE busid IN (SELECT busid FROM BUSTICKETBOOKINGSYSTEM.busroute WHERE routeid = ?) AND isoccupied = 0";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1, routeId);
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        if (resultSet.next()) {
		            int totalSeats = resultSet.getInt("total_seats");
		            return totalSeats >= numSeats;
		        }
	        }
	     }
        return false;
    }
    // Method to fetch bus seat type based on route ID using JOIN
    private static String fetchBusSeatType(Connection connection, int routeId) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT b.busseattype FROM BUSTICKETBOOKINGSYSTEM.bus b JOIN BUSTICKETBOOKINGSYSTEM.busroute r ON b.busid = r.busid WHERE r.routeid = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1, routeId);
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        if (resultSet.next()) {
		            return resultSet.getString("busseattype");
		        }
	        }
        }
        return null;
    }
    private static double fetchSeatFare(Connection connection, int routeId, String seatType) throws SQLException,SQLIntegrityConstraintViolationException  {
        double defaultFare = 0.0; // Set a default fare if no specific fare is defined
        String query = "SELECT b.busseattype FROM BUSTICKETBOOKINGSYSTEM.bus b INNER JOIN BUSTICKETBOOKINGSYSTEM.busroute r ON b.busid = r.busid WHERE r.routeid = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1, routeId);
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        if (resultSet.next()) {
		            String busSeatType = resultSet.getString("busseattype");
		            // Apply fare logic based on bus seat type and seat type
		            switch (busSeatType) {
		                case "Luxury":
		                    return calculateLuxuryFare(seatType); // Define a method to calculate luxury fare
		                case "Semi-Sleeper":
		                    return calculateSemiSleeperFare(seatType); // Define a method to calculate semi-luxury fare
		                case "Sleeper":
		                    return calculateSleeperFare(seatType); 
		                case "Standard":
		                	return calculateStandardFare(seatType);// Define a method to calculate economy fare
		                default:
		                    return defaultFare;
		            }
		        }
	        }
        }
        return defaultFare;
    }
    private static double calculateLuxuryFare(String seatType) {
        switch (seatType) {
            case "Seater":
                return 50.0; // Fare for a standard luxury seat
            case "Sleeper":
                return 60.0; // Fare for a sleeper luxury seat
            case "Window":
                return 55.0; // Fare for a window luxury seat
            case "Aisle":
                return 55.0; // Fare for an aisle luxury seat
            case "Middle":
                return 52.5; // Fare for a middle luxury seat
            default:
                return 0.0; // Default fare if seat type is not recognized
        }
    }

    private static double calculateSemiSleeperFare(String seatType) {
        switch (seatType) {
            case "Standard":
                return 40.0; // Fare for a standard semi-luxury seat
            case "Sleeper":
                return 45.0; // Fare for a sleeper semi-luxury seat
            case "Window":
                return 42.0; // Fare for a window semi-luxury seat
            case "Aisle":
                return 42.0; // Fare for an aisle semi-luxury seat
            case "Middle":
                return 41.0; // Fare for a middle semi-luxury seat
            default:
                return 0.0; // Default fare if seat type is not recognized
        }
    }
    private static double calculateSleeperFare(String seatType) {
        switch (seatType) {
            case "Seater":
                return 30.0; // Fare for a standard economy seat
            case "Sleeper":
                return 35.0; // Fare for a sleeper economy seat
            case "Window":
                return 32.0; // Fare for a window economy seat
            case "Aisle":
                return 32.0; // Fare for an aisle economy seat
            case "Middle":
                return 31.0; // Fare for a middle economy seat
            default:
                return 0.0; // Default fare if seat type is not recognized
        }
    }
    private static double calculateStandardFare(String seatType) {
        switch (seatType) {
            case "Seater":
                return 30.0; // Fare for a standard economy seat
            case "Sleeper":
                return 35.0; // Fare for a sleeper economy seat
            case "Window":
                return 32.0; // Fare for a window economy seat
            case "Aisle":
                return 32.0; // Fare for an aisle economy seat
            case "Middle":
                return 31.0; // Fare for a middle economy seat
            default:
                return 0.0; // Default fare if seat type is not recognized
        }
    }
    // Method to generate a unique booking ID
    private static int generateBookingId(Connection connection) throws SQLException ,SQLIntegrityConstraintViolationException {
        String query = "SELECT MAX(bookingid) AS max_booking_id FROM BUSTICKETBOOKINGSYSTEM.booking";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        if (resultSet.next()) {
		            return resultSet.getInt("max_booking_id") + 1;
		        }
	        }
        }
        return 1;
    }
    private static void insertBooking(Connection connection, int bookingId, int userId, int routeId, int numSeats, double totalFare, Timestamp bookingDate, int activeStatus) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "INSERT INTO BUSTICKETBOOKINGSYSTEM.BOOKING (BOOKINGID, USERID, ROUTEID, NUMSEATS, TOTALFARE, BOOKINGDATE, ACTIVESTATUS) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1, bookingId);
	        preparedStatement.setInt(2, userId);
	        preparedStatement.setInt(3, routeId);
	        preparedStatement.setInt(4, numSeats);
	        preparedStatement.setDouble(5, totalFare);
	        preparedStatement.setTimestamp(6, bookingDate);
	        preparedStatement.setInt(7, activeStatus);
	        preparedStatement.executeUpdate();
        }
    }
    // Method to update seat occupancy in the database
    private static void updateSeatOccupancy(Connection connection, int routeId, int numSeats,List<Integer> seat) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "UPDATE BUSTICKETBOOKINGSYSTEM.seat SET isoccupied = 1 WHERE seatnumber IN (SELECT seatnumber FROM BUSTICKETBOOKINGSYSTEM.seat WHERE busid IN (SELECT busid FROM BUSTICKETBOOKINGSYSTEM.busroute WHERE routeid = ?) AND isoccupied = 0 AND ROWNUM <= ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
        	preparedStatement.setInt(1, routeId);
	        for(Integer x : seat) {
	        	preparedStatement.setInt(2, x);
	        }
	        preparedStatement.executeUpdate();
        }
    }
    public static void updateSeatOccupancy(Connection connection, int routeId, int numSeats, boolean isOccupied) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "UPDATE BUSTICKETBOOKINGSYSTEM.SEAT SET ISOCCUPIED = ? WHERE SEATNUMBER IN (SELECT SEATNUMBER FROM BUSTICKETBOOKINGSYSTEM.SEAT WHERE BUSID IN (SELECT BUSID FROM BUSTICKETBOOKINGSYSTEM.BUSROUTE WHERE ROUTEID = ?) AND ISOCCUPIED = ? AND ROWNUM <= ?)";
    	try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1, isOccupied ? 1 : 0);
	        preparedStatement.setInt(2, routeId);
	        preparedStatement.setInt(3, isOccupied ? 0 : 1);
	        preparedStatement.setInt(4, numSeats);
	        preparedStatement.executeUpdate();
    	}
    }
    public static int generateCancellationId(Connection connection) throws SQLException {
        String query = "SELECT MAX(CANCELLATIONID) AS max_cancellation_id FROM BUSTICKETBOOKINGSYSTEM.CANCELLATION";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        if (resultSet.next()) {
		            int maxCancellationId = resultSet.getInt("max_cancellation_id");
		            return maxCancellationId + 1;
		        }
	        }
        }
        return 1; // Return 1 if there are no existing cancellation IDs
    }
    public static void insertCancellation(Connection connection, int bookingId) throws SQLException ,SQLIntegrityConstraintViolationException {
        // the query to insert cancellation record
        String query = "INSERT INTO BUSTICKETBOOKINGSYSTEM.CANCELLATION (CANCELLATIONID, BOOKINGID, USERID, ROUTEID, CANCELLATIONDATE, REFUNDAMOUNT) VALUES (?,?,?,?,?,?)";
        String select= "SELECT userid,routeid,totalfare from busticketbookingsystem.booking where bookingid=?";
        try(PreparedStatement preparedStatements=connection.prepareStatement(select)){
	        preparedStatements.setInt(1, bookingId);
	        preparedStatements.executeQuery();
	        try(ResultSet set=preparedStatements.executeQuery()){
		        int userid=0,routeid=0;
		        double refundAmount=0.0;
		        while(set.next()) {
		        	userid=set.getInt("USERID");
		        	routeid=set.getInt("ROUTEID");
		        	refundAmount=set.getDouble("TOTALFARE");
		        	
		        }
		        // Get current timestamp
		        Timestamp cancellationDate = new Timestamp(System.currentTimeMillis());
		        // Create a prepared statement
		        PreparedStatement preparedStatement = connection.prepareStatement(query);
		        // Set values for parameters
		        preparedStatement.setInt(1, generateCancellationId(connection)); // Assuming cancellation ID is generated
		        preparedStatement.setInt(2, bookingId);
		        preparedStatement.setInt(3, userid); // Assuming user ID is not implemented in cancellation
		        preparedStatement.setInt(4, routeid); 
		        preparedStatement.setTimestamp(5, cancellationDate);
		        preparedStatement.setDouble(6, refundAmount);
		        // Execute the query to insert cancellation record
		        preparedStatement.executeUpdate();
	        }
        }
    }
    public static void updateBookingcancellationstatus(Connection connection, int bookingId) throws SQLException ,SQLIntegrityConstraintViolationException {
        String query = "Update BUSTICKETBOOKINGSYSTEM.booking  set activestatus=? WHERE bookingid = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1,0);
	        preparedStatement.setInt(2,bookingId);
	        preparedStatement.executeUpdate();
        }
    }
	public static Booking fetchBookingDetails(Connection connection, int bookingId) throws SQLException ,SQLIntegrityConstraintViolationException {
	    String query = "SELECT * FROM busticketsystem.booking WHERE bookingid = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
		    preparedStatement.setInt(1, bookingId);
		    try(ResultSet resultSet = preparedStatement.executeQuery()){
		        if (resultSet.next()) {
		            int userId = resultSet.getInt("userid");
		            int totalfare=resultSet.getInt("totalfare");
		        // Fetch other booking details as needed
		        return new Booking(bookingId, userId ,totalfare);
		      }
		    }
		}
	    return null;
	} 
	public static double calculatePaymentAmount(Booking booking) {
	    // Implement your payment calculation logic based on booking details
	    return booking.getTotalFare();
	}
	public static void processPayment(Connection connection, int userId, int bookingId, double amount) throws SQLException {
	    // Insert payment details into the payment table
		String insertQuery = "INSERT INTO busticketbookingsystem.payment (userid, bookingid, amount, paymentdate) VALUES (?, ?, ?, SYSTIMESTAMP)";
    	try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setInt(2, bookingId);
	        preparedStatement.setDouble(3, amount);
	        //preparedStatement.setString(4,);
	        preparedStatement.executeUpdate();
    	}
	}
	public static void viewBooking(Connection connection) throws SQLException ,SQLIntegrityConstraintViolationException {
        String query = "SELECT * FROM busticketbookingsystem.booking order by bookingid";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        while (resultSet.next()) {
		            int bookingId = resultSet.getInt("BOOKINGID");
		            int userId = resultSet.getInt("USERID");
		            int routeId = resultSet.getInt("ROUTEID");
		            int numSeats = resultSet.getInt("NUMSEATS");
		            double totalFare = resultSet.getDouble("TOTALFARE");
		            Timestamp bookingDate = resultSet.getTimestamp("BOOKINGDATE");
		            int activeStatus = resultSet.getInt("ACTIVESTATUS");
		
		            System.out.println("Booking ID: " + bookingId);
		            System.out.println("User ID: " + userId);
		            System.out.println("Route ID: " + routeId);
		            System.out.println("Number of Seats: " + numSeats);
		            System.out.println("Total Fare: " + totalFare);
		            System.out.println("Booking Date: " + bookingDate);
		            System.out.println("Active Status: " + activeStatus);
		            System.out.println("---------------------------------------------");
		        }
	        }
        }
	}
	public static void viewCancellation(Connection connection) throws SQLException ,SQLIntegrityConstraintViolationException {
        String query = "SELECT * FROM busticketbookingsystem.cancellation order by cancellationid";
	    try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
		    try(ResultSet resultSet = preparedStatement.executeQuery()){
			    while (resultSet.next()) {
			        int cancellationId = resultSet.getInt("CANCELLATIONID");
			        int bookingId = resultSet.getInt("BOOKINGID");
			        int userId = resultSet.getInt("USERID");
			        int routeId = resultSet.getInt("ROUTEID");
			        Timestamp cancellationDate = resultSet.getTimestamp("CANCELLATIONDATE");
			        double refundAmount = resultSet.getDouble("REFUNDAMOUNT");
			
			        System.out.println("Cancellation ID: " + cancellationId);
			        System.out.println("Booking ID: " + bookingId);
			        System.out.println("User ID: " + userId);
			        System.out.println("Route ID: " + routeId);
			        System.out.println("Cancellation Date: " + cancellationDate);
			        System.out.println("Refund Amount: " + refundAmount);
			        System.out.println("---------------------------------------------");
			    }
		    }
        }
	}
	public static void viewPayment(Connection connection) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT * FROM busticketbookingsystem.payment order by paymentid";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        while (resultSet.next()) {
		            int paymentId = resultSet.getInt("PAYMENTID");
		            int userId = resultSet.getInt("USERID");
		            int bookingId = resultSet.getInt("BOOKINGID");
		            double amount = resultSet.getDouble("AMOUNT");
		            Timestamp paymentDate = resultSet.getTimestamp("PAYMENTDATE");
		            String paymentMethod = resultSet.getString("PAYMENTMETHOD");
		            String transactionId = resultSet.getString("TRANSACTIONID");
		
		            System.out.println("Payment ID: " + paymentId);
		            System.out.println("User ID: " + userId);
		            System.out.println("Booking ID: " + bookingId);
		            System.out.println("Amount: " + amount);
		            System.out.println("Payment Date: " + paymentDate);
		            System.out.println("Payment Method: " + paymentMethod);
		            System.out.println("Transaction ID: " + transactionId);
		            System.out.println("---------------------------------------------");
		        }
	        }
        }
	}
	public static void viewHistory(Connection connection,int userid) throws SQLException,SQLIntegrityConstraintViolationException  {
        String query = "SELECT * FROM busticketbookingsystem.booking where userId=? order by bookingid";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
	        preparedStatement.setInt(1,userid);
	        try(ResultSet resultSet = preparedStatement.executeQuery()){
		        while (resultSet.next()) {
		            int bookingId = resultSet.getInt("BOOKINGID");
		            int userId = resultSet.getInt("USERID");
		            int routeId = resultSet.getInt("ROUTEID");
		            int numSeats = resultSet.getInt("NUMSEATS");
		            double totalFare = resultSet.getDouble("TOTALFARE");
		            Timestamp bookingDate = resultSet.getTimestamp("BOOKINGDATE");
		            int activeStatus = resultSet.getInt("ACTIVESTATUS");
		
		            System.out.println("Booking ID: " + bookingId);
		            System.out.println("User ID: " + userId);
		            System.out.println("Route ID: " + routeId);
		            System.out.println("Number of Seats: " + numSeats);
		            System.out.println("Total Fare: " + totalFare);
		            System.out.println("Booking Date: " + bookingDate);
		            System.out.println("Active Status: " + activeStatus);
		            System.out.println("---------------------------------------------");
		        }
	        }
        }
	}
	public static void changePass(Connection connection,int userId,UserType usertype) throws SQLException,SQLIntegrityConstraintViolationException  {
		String userchange="update busticketbookingsystem.users set password=? from where userid=?";
		String customerchange="update busticketbookingsystem.users set password=? from where userid=?";
		String adminchange="update busticketbookingsystem.users set password=? from where userid=?";
		System.out.println("Enter the new password");
		String password=scanner.nextLine();
		int customerUpdated=0,adminUpdated=0;
		try(PreparedStatement pre=connection.prepareStatement(userchange)){
			pre.setString(1, password);
			pre.setInt(2, userId);
			int userupdated=pre.executeUpdate();
			if(usertype.equals(UserType.ADMINISTRATOR)) {
				try(PreparedStatement pred=connection.prepareStatement(adminchange)){
					pre.setString(1, password);
					pre.setInt(2, userId);
					int adminupdated=pred.executeUpdate();
				}
			}
			else if(usertype.equals(UserType.CUSTOMER)) {
				try(PreparedStatement pred=connection.prepareStatement(customerchange)){
					pre.setString(1, password);
					pre.setInt(2, userId);
					int customerupdated=pred.executeUpdate();
				}
			}
			if((userupdated>0 && customerUpdated>0) || (userupdated>0 && adminUpdated>0)){
				System.out.println("Successfully updated");
			}else {
				System.out.println("Updation failed");
			}
		}
	}
	public static void updateUserInfo(Connection connection,int userId,UserType usertype) throws SQLException, InvalidInputException ,SQLIntegrityConstraintViolationException {
		boolean update=false;
		int userid=userId;
		if(usertype.equals(UserType.ADMINISTRATOR)) {
			System.out.println("1.Update own profile\n2.Update others profile\n3.Go to previous page\n4.Exit\nEnter the choice");
			int choice=scanner.nextInt();
			scanner.nextLine();
			if(choice>=1 && choice<=4) {
				switch(choice) {
					case 1:
						update=true;
						break;
					case 2:
						System.out.println("Enter the userId to update");
						userid=scanner.nextInt();
						scanner.nextLine();
						update=true;
						break;
					case 3:
						System.out.println("Now! you are in previous page");
						return;
					case 4:
						System.out.println("Thank you!...............");
						break;
				}
			}
			else {
				System.out.println("Please! Enter the valid choice");
			}
		}
		else if(usertype.equals(UserType.CUSTOMER)) {
			update=true;
		}
		if(update) {
	        String sql = "UPDATE busticketbookingsystem.USERS SET FIRSTNAME=?, LASTNAME=?, PHONENUMBER=?, DATEOFBIRTH=?, GENDER=?, " +
	                     "ADDRESS=?, CITY=?, COUNTRY=?, POSTALCODE=?, EMAILVERIFIED=?, PHONENUMBERVERIFIED=? WHERE USERID=?";
	        String lastName="";
			String firstName="";
			String phoneNumber="";
			LocalDate date;
			Date sqlDate = null;
			String gender="";
			String address="";
			String city="";
			String country="";
			String postalCode="";
			int emailVerified=0;
			int phoneNumberVerified=0;
				while(true) {
	            System.out.println("Enter the firstName:");
	            firstName = scanner.nextLine();
		            if (!Validation.isValidName(firstName)) {
		                System.out.println("Invalid input for firstName. Please try again.");
		                continue;
		            }
		            else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the lastName:");
		            lastName = scanner.nextLine();
		            if (!Validation.isValidName(lastName)) {
		                System.out.println("Invalid input for lastName. Please try again.");
		                continue;
		            }
		            else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the phoneNumber:");
		            phoneNumber = scanner.nextLine();
		            if (!Validation.isValidPhoneNumber(phoneNumber)) {
		                System.out.println("Invalid input for phoneNumber. Please try again.");
		                continue;
		            }else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the dateOfBirth (YYYY-MM-DD):");
		            String dobInput = scanner.nextLine();
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            try {
		            	if (Validation.isValidDate(dobInput)) {
		            		date=LocalDate.parse(dobInput);
		            		sqlDate = Date.valueOf(date);
		            		break;
		                } else {
		                    System.out.println(dobInput+ " is not a valid date.");
		                }
		            } 
		            catch (Exception e) {
		                System.out.println("Invalid input format for dateOfBirth. Please enter in YYYY-MM-DD format.");
		                continue;
		            }
				}
				while(true) {
		            System.out.println("Enter the gender:");
		            gender = scanner.nextLine();
		            if (!Validation.isValidGender(gender)) {
		                System.out.println("Invalid input for gender. Please try again.");
		                continue;
		            }
		            else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the address:");
		            address = scanner.nextLine();
		            if (!Validation.isValidAddress(address)) {
		                System.out.println("Invalid address format.");
		                continue;
		            }else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the city:");
		            city = scanner.nextLine();
		            if (!Validation.isValidCity(city)) {
		                System.out.println("Invalid city format.");
		                continue;
		            }else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the country:");
		            country = scanner.nextLine();
		            if (!Validation.isValidCountry(country)) {
		                System.out.println("Invalid country format.");
		                continue;
		            }
		            else {
		            	break;
		            }
				}
				while(true) {
		            System.out.println("Enter the postalcode:");
		            String postalcode = scanner.nextLine();
		            if (!Validation.isValidPostalCode(postalcode)) {
		                System.out.println("Invalid input for postalcode. Please try again.");
		                continue;
		            }
		            else {
		            	break;
		            }
				}
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, firstName);
				statement.setString(2, lastName);
	            statement.setString(3, phoneNumber);
				statement.setDate(4, sqlDate);
	            statement.setString(5, gender);
	            statement.setString(6, address);
	            statement.setString(7, city);
	            statement.setString(8, country);
				statement.setString(9, postalCode);
				statement.setInt(10, emailVerified);
				statement.setInt(11, phoneNumberVerified);
				statement.setInt(12, userid);
	            int rowsUpdated = statement.executeUpdate();
	            int adminUpdated=0,customerUpdated=0;
	            if(usertype.equals(UserType.ADMINISTRATOR)) {
	            	String admin= "UPDATE busticketbookingsystem.admin SET FIRSTNAME=?, LASTNAME=?, PHONENUMBER=?, DATEOFBIRTH=?, GENDER=?, " +
		                     "ADDRESS=?, CITY=?, COUNTRY=?, POSTALCODE=?, EMAILVERIFIED=?, PHONENUMBERVERIFIED=? WHERE USERID=?";
	            	 try (PreparedStatement statement1 = connection.prepareStatement(admin)) {
	     				statement1.setString(1, firstName);
	     				statement1.setString(2, lastName);
	     	            statement1.setString(3, phoneNumber);
	     				statement1.setDate(4, sqlDate);
	     	            statement1.setString(5, gender);
	     	            statement1.setString(6, address);
	     	            statement1.setString(7, city);
	     	            statement1.setString(8, country);
	     				statement1.setString(9, postalCode);
	     				statement1.setInt(10, emailVerified);
	     				statement1.setInt(11, phoneNumberVerified);
	     				statement1.setInt(12, userid);
	     	            adminUpdated = statement1.executeUpdate();
	            	 }
	            }
	            else if(usertype.equals(UserType.CUSTOMER)) {
	            	String customer= "UPDATE busticketbookingsystem.customer SET FIRSTNAME=?, LASTNAME=?, PHONENUMBER=?, DATEOFBIRTH=?, GENDER=?, " +
		                     "ADDRESS=?, CITY=?, COUNTRY=?, POSTALCODE=?, EMAILVERIFIED=?, PHONENUMBERVERIFIED=? WHERE USERID=?";
	            	 try (PreparedStatement statement1 = connection.prepareStatement(customer)) {
	     				statement1.setString(1, firstName);
	     				statement1.setString(2, lastName);
	     	            statement1.setString(3, phoneNumber);
	     				statement1.setDate(4, sqlDate);
	     	            statement1.setString(5, gender);
	     	            statement1.setString(6, address);
	     	            statement1.setString(7, city);
	     	            statement1.setString(8, country);
	     				statement1.setString(9, postalCode);
	     				statement1.setInt(10, emailVerified);
	     				statement1.setInt(11, phoneNumberVerified);
	     				statement1.setInt(12, userid);
	     	            customerUpdated = statement1.executeUpdate();
	            	 }
	            }
	            if ((rowsUpdated > 0 && adminUpdated>0) || (rowsUpdated>0 && customerUpdated>0)) {
	                System.out.println("User information updated successfully.");
	            } else {
	                System.out.println("Failed to update user information.");
	            }
	        }
		}
		else {
			System.out.println("Not able to update profile");
		}
    }
	public static void viewFeedback(Connection connection) throws SQLException ,SQLIntegrityConstraintViolationException {
		String sql = "SELECT * FROM busticketbookingsystem.FEEDBACK ORDER BY RATING DESC";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
        	try( ResultSet resultSet = statement.executeQuery()){
        	// List to store feedback records
	        	List<Feedback> feedbackList = new ArrayList<>();
		        // Iterate over the result set and populate the list
		        while (resultSet.next()) {
		            int feedbackId = resultSet.getInt("FEEDBACKID");
		            int userId = resultSet.getInt("USERID");
		            String comments = resultSet.getString("COMMENTS");
		            int rating = resultSet.getInt("RATING");
		            Timestamp feedbackDate = resultSet.getTimestamp("FEEDBACKDATE");
		
		            // Create Feedback object and add it to the list
		            Feedback feedback = new Feedback(feedbackId, userId,comments, rating, feedbackDate);
		            feedbackList.add(feedback);
		        }
		
		        // Sort the feedback list based on rating (highest to lowest)
		        Collections.sort(feedbackList, Collections.reverseOrder());
		        // Display the feedback records
		        for (Feedback feedback : feedbackList) {
		            System.out.println(feedback);
		        }
	       }
        }
	}
	public static List<Feedback> getFeedbackFromDatabase(Connection connection) throws SQLException ,SQLIntegrityConstraintViolationException {
        String sql = "SELECT * FROM busticketbookingsystem.FEEDBACK ORDER BY RATING DESC";
        List<Feedback> feedbackList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
	        try(ResultSet resultSet = statement.executeQuery()){        
		        while (resultSet.next()) {
		            int feedbackId = resultSet.getInt("FEEDBACKID");
		            int userId = resultSet.getInt("USERID");
		           // long routeId = resultSet.getLong("ROUTEID");
		            String comments = resultSet.getString("COMMENTS");
		            int rating = resultSet.getInt("RATING");
		            Timestamp feedbackDate = resultSet.getTimestamp("FEEDBACKDATE");
		
		            Feedback feedback = new Feedback(feedbackId, userId,comments, rating, feedbackDate);
		            feedbackList.add(feedback);
		        }
	        }
        }
        return feedbackList;
    }
	 public static Feedback getFeedbackFromUser(Connection con,int userId) throws SQLIntegrityConstraintViolationException  {
	        System.out.println("Enter your feedback:");
	        System.out.print("Comments: ");
	        String comments = scanner.nextLine();
	        System.out.print("Rating (1-5): ");
	        int rating = scanner.nextInt();
	        return new Feedback(0, userId, comments, rating, null);
	    }
	 public static void giveFeedback(Connection connection, Feedback feedback) throws SQLException,SQLIntegrityConstraintViolationException  {
	        String sql = "INSERT INTO busticketbookingsystem.FEEDBACK (USERID, COMMENTS, RATING, FEEDBACKDATE) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
	        try(PreparedStatement statement = connection.prepareStatement(sql)){
		        statement.setLong(1, feedback.getUserId());
		        statement.setString(2, feedback.getComments());
		        statement.setInt(3, feedback.getRating());
		        statement.executeUpdate();
	        }
	    }
	 public static void addFAQ(Connection connection ,String question, String category,int userid) throws SQLException,SQLIntegrityConstraintViolationException  {
		 String query="INSERT INTO busticketbookingsystem.FAQ (QUESTION, CATEGORY,userid) VALUES (?,?,?)";
		try(PreparedStatement statement = connection.prepareStatement(query)){
		    statement.setString(1, question);
		    statement.setString(2, category);
		    statement.setInt(3, userid);
		    int rowsInserted = statement.executeUpdate();
		    if (rowsInserted > 0) {
		        System.out.println("FAQ added successfully. Waiting for admin response.");
		        String getting="Select faqid from busticketbookingsystem.faq where question=? and category=?";
		         PreparedStatement state=connection.prepareStatement(getting);
		         state.setString(1, question);
		          state.setString(2, category);
		          ResultSet set = state.executeQuery();
		          while(set.next()) {
		        	  set.getInt("FAQID");
		          }
		    }
		}
	  }
	 public static void answerFAQs(Connection connection,int id, String answer) throws SQLException,SQLIntegrityConstraintViolationException  {
		 List<FAQ> faqs = getFAQs(connection);
	        try (PreparedStatement statement = connection.prepareStatement("UPDATE busticketbookingsystem.FAQ SET ANSWER = ? WHERE FAQID = ?")) {
	            statement.setString(1, answer);
	            statement.setInt(2, id);
	            int rowsUpdated = statement.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("FAQ answered successfully.");
	            }
	        }
	    }
	 public static List<FAQ> getFAQs(Connection connection) throws SQLException {
        List<FAQ> faqs = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM busticketbookingsystem.FAQ order by faqid")){
	            while (resultSet.next()) {
	                int id = resultSet.getInt("FAQID");
	                String question = resultSet.getString("QUESTION");
	                String answer = resultSet.getString("ANSWER");
	                String category = resultSet.getString("CATEGORY");
	                faqs.add(new FAQ(id,question, answer, category));
	            }
	         }
	     }
	     return faqs;
	  }
	 public static void viewFAQs(Connection connection,UserType usertype) throws SQLException {
	        List<FAQ> faqs = getFAQs(connection);
	        if (usertype.equals(UserType.CUSTOMER)) {
	            System.out.println("Frequently Asked Questions:");
	            for (FAQ faq : faqs) {
	                System.out.println("Q: " + faq.getQuestion());
	                System.out.println("A: " + (faq.getAnswer() == null ? "Answer pending" : faq.getAnswer()));
	                System.out.println();
	            }
	        } else if (usertype.equals(UserType.ADMINISTRATOR)) {
	            System.out.println("Questions from Customers:");
	            for (FAQ faq : faqs) {
	                if (faq.getAnswer() == null) {
	                    System.out.println("Id: "+faq.getFaqId()+" Q: " + faq.getQuestion());
	                }
	            }
	        }
	    }
	 public static void ViewProfile(Connection connection,int userid) throws SQLException,SQLIntegrityConstraintViolationException  {
		 String query="select * from busticketbookingsystem.users where userid=? order by userid";
		 try(PreparedStatement statement=connection.prepareStatement(query)){
			 statement.setInt(1, userid);
			 try(ResultSet resultSet=statement.executeQuery()){
				 System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				 System.out.printf("| %-7s | %-15s | %-20s | %-30s | %-15s | %-15s | %-20s | %-15s | %-10s | %-40s | %-20s | %-20s | %-10s |%n",
				                   "USERID", "USERNAME", "PASSWORD", "EMAIL", "USERTYPE", "FIRSTNAME", "LASTNAME", "PHONENUMBER",
				                   "DATEOFBIRTH", "GENDER", "ADDRESS", "CITY", "COUNTRY", "POSTALCODE");
				 System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				 while (resultSet.next()) {
				     System.out.printf("| %-7d | %-20s | %-20s | %-30s | %-15s | %-20s | %-20s | %-15s | %-10s | %-40s | %-20s | %-20s | %-10s |%n",
				                       resultSet.getInt("USERID"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
				                       resultSet.getString("EMAIL"), resultSet.getString("USERTYPE"), resultSet.getString("FIRSTNAME"),
				                       resultSet.getString("LASTNAME"), resultSet.getString("PHONENUMBER"), resultSet.getDate("DATEOFBIRTH"),
				                       resultSet.getString("GENDER"), resultSet.getString("ADDRESS"), resultSet.getString("CITY"),
				                       resultSet.getString("COUNTRY"), resultSet.getString("POSTALCODE"));
				     System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				 }
			 }
		 }
	 }
}
















