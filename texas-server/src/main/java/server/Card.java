package server;

public class Card {
	private String suit;
	private int point;
	
	public Card(String suit, int point) {
		this.suit = new String(suit);
		this.point = point;
	}
	
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.point + this.suit;
	}	
}
