/**
 * 
 */
package com.bookingservices;

import java.sql.Connection;

import com.exceptionhandling.InvalidInputException;
import com.userdetails.UserType;

/**
 * The Search is the interface which can provide the search operations methods.
 * The administrator and customer class implements the search interface. 
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public interface Search {
	boolean searchBusRoutes(Connection con);
	void viewBusSchedules(Connection con);
	void viewBusRoute(Connection con);
	void updateProfile(Connection con,int userId,UserType usertype) throws InvalidInputException;
	//void viewFeedback(Connection con);
}
