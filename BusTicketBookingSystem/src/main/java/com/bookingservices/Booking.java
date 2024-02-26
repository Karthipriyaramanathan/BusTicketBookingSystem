/**
 * 
 */
package com.bookingservices;

/**
 * @author KARTHIPRIYA R
 *
 */
public class Booking {
	 private int bookingId;
	    private int userId;
	    private double totalFare;
		/**
		 * @param bookingId
		 * @param userId
		 * @param totalFare
		 */
		public Booking(int bookingId, int userId, double totalFare) {
			super();
			this.bookingId = bookingId;
			this.userId = userId;
			this.totalFare = totalFare;
		}
		/**
		 * @return the bookingId
		 */
		public int getBookingId() {
			return bookingId;
		}
		/**
		 * @param bookingId the bookingId to set
		 */
		public void setBookingId(int bookingId) {
			this.bookingId = bookingId;
		}
		/**
		 * @return the userId
		 */
		public int getUserId() {
			return userId;
		}
		/**
		 * @param userId the userId to set
		 */
		public void setUserId(int userId) {
			this.userId = userId;
		}
		/**
		 * @return the totalFare
		 */
		public double getTotalFare() {
			return totalFare;
		}
		/**
		 * @param totalFare the totalFare to set
		 */
		public void setTotalFare(double totalFare) {
			this.totalFare = totalFare;
		}
		@Override
		public String toString() {
			return "Booking \nBOOKING ID : " + getBookingId() + "\nUSER ID : " + getUserId() + "\nTOTAL FARE : "
					+ getTotalFare() + "\n---------------------------------------------------";
		}
	    
}
