/**
 * 
 */
package com.busmanagement;

/**
 * @author KARTHIPRIYA R
 *
 */
public class BusSchedule {
    private int scheduleId;
    private String departureTime;
    private String arrivalTime;
	/**
	 * @param scheduleId
	 * @param departureTime
	 * @param arrivalTime
	 */
	public BusSchedule(int scheduleId, String departureTime, String arrivalTime) {
		super();
		this.scheduleId = scheduleId;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	/**
	 * @return the scheduleId
	 */
	public int getScheduleId() {
		return scheduleId;
	}
	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	/**
	 * @return the departureTime
	 */
	public String getDepartureTime() {
		return departureTime;
	}
	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@Override
	public String toString() {
		return "BusSchedule \nSCHEDULE ID : " + getScheduleId() + "\nDEPARTURE TIME : " + getDepartureTime()
				+ "\nARRIVAL TIME : " + getArrivalTime() + "\n--------------------------------------------------------------------";
	}
	
}
