/**
 * 
 */
package com.busmanagement;

import java.sql.ResultSet;

/**
 * The BusOperator class is the class that have its states and behaviours 
 * in it and the busoperator is like an organization which can provide a 
 * information about the bus routes and buses.
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class BusOperator {
	private int operatorID;
    private String operatorName;
    private String contactNumber;
    private String email;
    private String address;
    private String city;
    private String country;
    private int numberOfBuses;
    private boolean isActive;
	/**
	 * @param operatorID
	 * @param operatorName
	 * @param contactNumber
	 * @param email
	 * @param address
	 * @param city
	 * @param country
	 * @param numberOfBuses
	 * @param isActive
	 */
	public BusOperator(int operatorID, String operatorName, String contactNumber, String email, String address,
			String city, String country, int numberOfBuses, boolean isActive) {
		super();
		this.operatorID = operatorID;
		this.operatorName = operatorName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.address = address;
		this.city = city;
		this.country = country;
		this.numberOfBuses = numberOfBuses;
		this.isActive = isActive;
	}
	public BusOperator(ResultSet resultSet){
		try {
        this.operatorID = resultSet.getInt("OPERATORID");
        this.operatorName = resultSet.getString("OPERATORNAME");
        this.contactNumber = resultSet.getString("CONTACTNUMBER");
        this.email = resultSet.getString("EMAIL");
        this.address = resultSet.getString("ADDRESS");
        this.city = resultSet.getString("CITY");
        this.country = resultSet.getString("COUNTRY");
        this.numberOfBuses = resultSet.getInt("NUMBEROFBUSES");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * @return the operatorID
	 */
	public int getOperatorID() {
		return operatorID;
	}
	/**
	 * @param operatorID the operatorID to set
	 */
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the numberOfBuses
	 */
	public int getNumberOfBuses() {
		return numberOfBuses;
	}
	/**
	 * @param numberOfBuses the numberOfBuses to set
	 */
	public void setNumberOfBuses(int numberOfBuses) {
		this.numberOfBuses = numberOfBuses;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "BusOperator \nOPERATOR ID : " + getOperatorID() + "\nOPERATOR NAME : " + getOperatorName()
				+ "\nCONTACT NUMBER : " + getContactNumber() + "\nEMAIL : " + getEmail() + "\nADDRESS : "
				+ getAddress() + "\nCITY : " + getCity() + "\nCOUNTRY : " + getCountry() + "\nNUMBER OF BUSES : "
				+ getNumberOfBuses() + "\nACTIVE STATUS" + isActive() + "\n----------------------------------------------------";
	}
    
}
