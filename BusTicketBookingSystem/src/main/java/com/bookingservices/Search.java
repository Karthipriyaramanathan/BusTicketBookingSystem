/**
 * 
 */
package com.bookingservices;

import java.sql.Connection;

import com.exceptionhandling.InvalidInputException;
import com.userdetails.UserType;

/**
 * @author KARTHIPRIYA R
 *
 */
public interface Search {
	boolean searchBusRoutes(Connection con);
	void viewBusSchedules(Connection con);
	void viewBusRoute(Connection con);
	void updateProfile(Connection con,int userId,UserType usertype) throws InvalidInputException;
	//void viewFeedback(Connection con);
}
