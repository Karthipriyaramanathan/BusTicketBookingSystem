/**
 * 
 */
package com.busmanagement;

import java.util.List;

/**
 * The Bus class is the class the real world entity class that have the states 
 * and behaviours of the bus objects.
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class Bus {
	private int busID;
    private String busNumber;
    private int operatorID;
    private String model;
    private String manufacturer;
    private int year;
    private int capacity;
    private List<Seat> seats;
    private BusSeatType busSeatType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private boolean airConditioning;
    private boolean wiFi;
    private boolean gPS;
    private boolean entertainmentSystem;
    private boolean restroom;
    private boolean wheelchairAccessible;
	/**
	 * @param busID
	 * @param busNumber
	 * @param operatorID
	 * @param model
	 * @param manufacturer
	 * @param year
	 * @param capacity
	 * @param busSeatType
	 * @param fuelType
	 * @param transmissionType
	 * @param airConditioning
	 * @param wiFi
	 * @param gPS
	 * @param entertainmentSystem
	 * @param restroom
	 * @param wheelchairAccessible
	 */
	public Bus(int busID, String busNumber, int operatorID, String model, String manufacturer, int year, int capacity,
			BusSeatType busSeatType, FuelType fuelType, TransmissionType transmissionType, boolean airConditioning,
			boolean wiFi, boolean gPS, boolean entertainmentSystem, boolean restroom, boolean wheelchairAccessible) {
		super();
		this.busID = busID;
		this.busNumber = busNumber;
		this.operatorID = operatorID;
		this.model = model;
		this.manufacturer = manufacturer;
		this.year = year;
		this.capacity = capacity;
		this.busSeatType = busSeatType;
		this.fuelType = fuelType;
		this.transmissionType = transmissionType;
		this.airConditioning = airConditioning;
		this.wiFi = wiFi;
		this.gPS = gPS;
		this.entertainmentSystem = entertainmentSystem;
		this.restroom = restroom;
		this.wheelchairAccessible = wheelchairAccessible;
	}
	/**
	 * @return the busID
	 */
	public int getBusID() {
		return busID;
	}
	/**
	 * @param busID the busID to set
	 */
	public void setBusID(int busID) {
		this.busID = busID;
	}
	/**
	 * @return the busNumber
	 */
	public String getBusNumber() {
		return busNumber;
	}
	/**
	 * @param busNumber the busNumber to set
	 */
	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
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
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the busSeatType
	 */
	public BusSeatType getBusSeatType() {
		return busSeatType;
	}
	/**
	 * @param busSeatType the busSeatType to set
	 */
	public void setBusSeatType(BusSeatType busSeatType) {
		this.busSeatType = busSeatType;
	}
	/**
	 * @return the fuelType
	 */
	public FuelType getFuelType() {
		return fuelType;
	}
	/**
	 * @param fuelType the fuelType to set
	 */
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
	/**
	 * @return the transmissionType
	 */
	public TransmissionType getTransmissionType() {
		return transmissionType;
	}
	/**
	 * @param transmissionType the transmissionType to set
	 */
	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}
	/**
	 * @return the airConditioning
	 */
	public boolean isAirConditioning() {
		return airConditioning;
	}
	/**
	 * @param airConditioning the airConditioning to set
	 */
	public void setAirConditioning(boolean airConditioning) {
		this.airConditioning = airConditioning;
	}
	/**
	 * @return the wiFi
	 */
	public boolean isWiFi() {
		return wiFi;
	}
	/**
	 * @param wiFi the wiFi to set
	 */
	public void setWiFi(boolean wiFi) {
		this.wiFi = wiFi;
	}
	/**
	 * @return the gPS
	 */
	public boolean isgPS() {
		return gPS;
	}
	/**
	 * @param gPS the gPS to set
	 */
	public void setgPS(boolean gPS) {
		this.gPS = gPS;
	}
	/**
	 * @return the entertainmentSystem
	 */
	public boolean isEntertainmentSystem() {
		return entertainmentSystem;
	}
	/**
	 * @param entertainmentSystem the entertainmentSystem to set
	 */
	public void setEntertainmentSystem(boolean entertainmentSystem) {
		this.entertainmentSystem = entertainmentSystem;
	}
	/**
	 * @return the restroom
	 */
	public boolean isRestroom() {
		return restroom;
	}
	/**
	 * @param restroom the restroom to set
	 */
	public void setRestroom(boolean restroom) {
		this.restroom = restroom;
	}
	/**
	 * @return the wheelchairAccessible
	 */
	public boolean isWheelchairAccessible() {
		return wheelchairAccessible;
	}
	/**
	 * @param wheelchairAccessible the wheelchairAccessible to set
	 */
	public void setWheelchairAccessible(boolean wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}
	@Override
	public String toString() {
		return "Bus \nBUSID : " + getBusID() + "\nBUS NUMBER : " + getBusNumber() + "\nOPERATOR ID : "
				+ getOperatorID() + "\nMODEL : " + getModel() + "\nMANUFACTURER : " + getManufacturer()
				+ "\nYEAR : " + getYear() + "\nCAPACITY : " + getCapacity() + "\nBUS SEAT TYPE : "
				+ getBusSeatType() + "\nFUEL TYPE : " + getFuelType() + "\nTRANSMISSION TYPE : "
				+ getTransmissionType() + "\nAIR CONDITIONING : " + isAirConditioning() + "\nWIFI : " + isWiFi()
				+ "\nGPS : " + isgPS() + "\nENTERTAINMENT SYSTEM : " + isEntertainmentSystem() + "\nREST ROOM : "
				+ isRestroom() + "\nWHEELCHAIR ACCESSIBLE : " + isWheelchairAccessible() + "\n---------------------------------------------------------------";
	}
    
}
