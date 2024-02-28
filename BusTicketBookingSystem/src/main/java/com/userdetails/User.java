/**
 * 
 */
package com.userdetails;

/**
 * The User class that implements an application that a user of a 
 * bus ticket booking application
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class User {
    private static int userIDCounter = 1;
    private int userID;
    private Account account;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private boolean emailVerified;
    private boolean phoneNumberVerified;
    /**
     * @param account
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
	 */
    /**
	 * @param account
	 */
    public User() {
    	super();
    }
	/**
	 * @param account
	 */
	public User(Account account) {
		super();
		this.account = account;
	}
	public User(String username, String password, String email, UserType userType) {
		super();
        this.account = new Account(username, password, email, userType);
	}
    public User(String username, String password, String email, UserType userType, String firstName, String lastName,
                String phoneNumber, String dateOfBirth, String gender, String address, String city, String country,
                String postalCode, boolean emailVerified, boolean phoneNumberVerified) {
        this.userID = userIDCounter++;
        this.account = new Account(username, password, email, userType);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.emailVerified = emailVerified;
        this.phoneNumberVerified = phoneNumberVerified;
    }
    /**
	 * @return the userID
	 */
    public int getUserID() {
        return userID;
    }
    /**
	 * @param userID the userID to set
	 */

    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
  	 * @return the account
  	 */
    public Account getAccount() {
        return account;
    }
    /**
	 * @param account the account to set
	 */

    public void setAccount(Account account) {
        this.account = account;
    }
    /**
	 * @return the firstName
	 */

    public String getFirstName() {
        return firstName;
    }
    /**
	 * @param firstName the firstName to set
	 */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
	 * @return the lastName
	 */

    public String getLastName() {
        return lastName;
    }
    /**
	 * @param lastName the lastName to set
	 */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
	 * @return the phoneNumber
	 */

    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
	 * @param phoneNumber the phoneNumber to set
	 */

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
	 * @return the dateOfBirth
	 */

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    /**
	 * @param dateOfBirth the dateOfBirth to set
	 */

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    /**
	 * @return the gender
	 */

    public String getGender() {
        return gender;
    }
    /**
	 * @param gender the gender to set
	 */

    public void setGender(String gender) {
        this.gender = gender;
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
	 * @return the postalCode
	 */
    public String getPostalCode() {
        return postalCode;
    }
    /**
	 * @param postalCode the postalCode to set
	 */

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
	 * @return the emailVerified
	 */

    public boolean isEmailVerified() {
        return emailVerified;
    }
    /**
	 * @param emailVerified the emailVerified to set
	 */

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    /**
	 * @return the phoneNumberVerified
	 */

    public boolean isPhoneNumberVerified() {
        return phoneNumberVerified;
    }
    /**
	 * @param phoneNumberVerified the phoneNumberVerified to set
	 */

    public void setPhoneNumberVerified(boolean phoneNumberVerified) {
        this.phoneNumberVerified = phoneNumberVerified;
    }
	@Override
	public String toString() {
		return "User \nUSERID : " + getUserID() + "\nAccount : " + getAccount() + "\nFIRSTNAME : "
				+ getFirstName() + "\nLASTNAME : " + getLastName() + "\nPHONE NUMBER : " + getPhoneNumber()
				+ "\nDATE OF BIRTH : " + getDateOfBirth() + "\nGENDER : " + getGender() + "\nADDRESS : "
				+ getAddress() + "\nCITY : " + getCity() + "\nCOUNTRY : " + getCountry() + "\nPOSTAL CODE : "
				+ getPostalCode() + "\nEMAIL VERIFIED : " + isEmailVerified() + "\nPHONE NUMBER : "
				+ isPhoneNumberVerified() + "\n--------------------------------------------------------";
	}


}
