/**
 * 
 */
package com.userdetails;

/**
 * @author KARTHIPRIYA R
 *
 */
public class Account {
    private String username;
    private String password;
    private String email;
    private UserType userType;
	/**
	 * @param username
	 * @param password
	 * @param email
	 * @param userType
	 */
    public Account(String username, String password, String email, UserType userType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }
    /**
	 * @return the username
	 */
    public String getUsername() {
        return username;
    }
    /**
	 * @param username the username to set
	 */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * @return the password
	 */
    public String getPassword() {
        return password;
    }
    /**
	 * @param password the password to set
	 */

    public void setPassword(String password) {
        this.password = password;
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
	 * @return the userType
	 */
    public UserType getUserType() {
        return userType;
    }
    /**
	 * @param userType the userType to set
	 */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Account \n USERNAME : " + username + "\n EMAIL :" + email
                + "\n USERTYPE : " + userType+"\n---------------------------------------------------";
    }
}
