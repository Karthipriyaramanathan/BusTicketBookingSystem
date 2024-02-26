/**
 * 
 */
package com.busmanagement;

/**
 * @author KARTHIPRIYA R
 *
 */
public class Seat {
	private int seatNumber;
    private boolean isOccupied;
    private SeatType seatType;
    private double seatFare;
	/**
	 * @param seatNumber
	 * @param isOccupied
	 * @param seatType
	 * @param seatFare
	 */
	public Seat(int seatNumber, boolean isOccupied,SeatType seatType, double seatFare) {
		super();
		this.seatNumber = seatNumber;
		this.isOccupied = isOccupied;
		this.seatType = seatType;
		this.seatFare = seatFare;
	}
	/**
	 * @return the seatNumber
	 */
	public int getSeatNumber() {
		return seatNumber;
	}
	/**
	 * @param seatNumber the seatNumber to set
	 */
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	/**
	 * @return the isOccupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}
	/**
	 * @param isOccupied the isOccupied to set
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	/**
	 * @return the seatType
	 */
	public SeatType getSeatType() {
		return seatType;
	}
	/**
	 * @param seatType the seatType to set
	 */
	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}
	/**
	 * @return the seatFare
	 */
	public double getSeatFare() {
		return seatFare;
	}
	/**
	 * @param seatFare the seatFare to set
	 */
	public void setSeatFare(double seatFare) {
		this.seatFare = seatFare;
	}
	@Override
	public String toString() {
		return "Seat \nSEAT NUMBER : " + getSeatNumber() + "\nISOCCUPIED : " + isOccupied() + "\nSEAT TYPE : "
				+ getSeatType() + "\nSEAT FARE : " + getSeatFare() + "\n-----------------------------------------------------------";
	}
    
}
