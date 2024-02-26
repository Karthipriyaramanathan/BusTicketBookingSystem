/**
 * 
 */
package com.busmanagement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author KARTHIPRIYA R
 *
 */
public class BusRoute {
	private int routeId;
    private String originCity;
    private String destinationCity;
    private String departureTime;
    private String arrivalTime;
    private int distance;
    private LocalTime duration;
    private Bus bus;
    private List<Seat> seats;
    
	/**
	 * @param routeId
	 * @param originCity
	 * @param destinationCity
	 * @param departureTimestamp
	 * @param arrivalTimestamp
	 * @param distance
	 * @param duration
	 */
	public BusRoute(int routeId, String originCity, String destinationCity, String departureTimestamp, String arrivalTimestamp,
			int distance, LocalTime duration) {
		super();
		this.routeId = routeId;
		this.originCity = originCity;
		this.destinationCity = destinationCity;
		this.departureTime = departureTimestamp;
		this.arrivalTime = arrivalTimestamp;
		this.distance = distance;
		this.duration = duration;
	}
	/**
	 * @param routeId
	 * @param originCity
	 * @param destinationCity
	 * @param departureTime
	 * @param arrivalTime
	 * @param bus
	 * @param seats
	 */
	public BusRoute(int routeId, String originCity, String destinationCity, String departureTime, String arrivalTime,int distance,LocalTime duration,
			Bus bus, List<Seat> seats) {
		super();
		this.routeId = routeId;
		this.originCity = originCity;
		this.destinationCity = destinationCity;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.distance=distance;
		this.duration=duration;
		this.bus = bus;
		this.seats = seats;
	}
	public BusRoute(int routeId, String originCity, String destinationCity) {
		super();
		this.routeId = routeId;
		this.originCity = originCity;
		this.destinationCity = destinationCity;
	}
	public BusRoute(int routeId2, String routeOrigin, String routeDestination, String departuretime2,
			String arrivaltime2) {
		this.routeId = routeId2;
		this.originCity = routeOrigin;
		this.destinationCity = routeDestination;
		this.departureTime = departuretime2;
		this.arrivalTime = arrivaltime2;
	}
	/**
	 * @return the routeId
	 */
	public int getRouteId() {
		return routeId;
	}
	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	/**
	 * @return the originCity
	 */
	public String getOriginCity() {
		return originCity;
	}
	/**
	 * @param originCity the originCity to set
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	/**
	 * @return the destinationCity
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * @param destinationCity the destinationCity to set
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
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
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * @return the duration
	 */
	public LocalTime getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}
	/**
	 * @return the bus
	 */
	public Bus getBus() {
		return bus;
	}
	/**
	 * @param bus the bus to set
	 */
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	/**
	 * @return the seats
	 */
	public List<Seat> getSeats() {
		return seats;
	}
	/**
	 * @param seats the seats to set
	 */
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}	
	@Override
	public String toString() {
	    return "----------------------------------------------------------------------------------------------------\n" +
	           String.format("| %-7s | %-12s | %-15s | %-21s | %-21s |\n", "ROUTEID", "ORIGINCITY", "DESTINATIONCITY", "DEPARTURETIME", "ARRIVALTIME") +
	           "----------------------------------------------------------------------------------------------------\n" +
	           String.format("| %-7d | %-12s | %-15s | %-21s | %-21s |\n", getRouteId(), getOriginCity(), getDestinationCity(), getDepartureTime(), getArrivalTime()) +
	           "----------------------------------------------------------------------------------------------------";
	}

}
