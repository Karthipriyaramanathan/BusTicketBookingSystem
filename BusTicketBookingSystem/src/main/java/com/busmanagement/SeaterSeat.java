/**
 * 
 */
package com.busmanagement;

/**
 * @author KARTHIPRIYA R
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
