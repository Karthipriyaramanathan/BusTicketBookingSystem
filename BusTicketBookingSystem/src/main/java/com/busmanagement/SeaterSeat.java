/**
 * 
 */
package com.busmanagement;

/**
 * The SeaterSeat is a class that inherits from the super class called seat
 * and it will have the seater seat informations.
 * @author KARTHIPRIYA RAMANATHAN (EXPLEO)
 * @since 27 Feb 2024
 *
 */
public class SeaterSeat extends Seat{
	private Deck deck;
	/**
	 * @param seatNumber
	 * @param isOccupied
	 * @param seatType
	 * @param seatFare
	 * @param deck
	 */
	public SeaterSeat(int seatNumber, boolean isOccupied, SeatType seatType, double seatFare, Deck deck) {
		super(seatNumber, isOccupied, seatType, seatFare);
		this.deck = deck;
	}
	/**
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}
	/**
	 * @param deck the deck to set
	 */
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	@Override
	public String toString() {
		return "SeaterSeat \nDECK : " + getDeck() + "\n---------------------------------------------------";
	}
	
}
