package server;

import util.Util;

public class Card implements Comparable<Card>{
	private String suit;
	private int point;
	
	public Card(String suit, int point) {
		if (point < 2 || point > 14) {
			System.out.println("Error when creating Card. Invalid point!");
			return;
		}
		
		if (!suit.equals("S") && !suit.equals("H") && !suit.equals("C") && !suit.equals("D")) {
			System.out.println("Error when creating Card. Invalid suit!");
			return;
		}
		
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

	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		if (getPoint() == o.getPoint()){
			return Util.suitToInt(getSuit()) - Util.suitToInt(o.getSuit());
		}
		
		return getPoint() - o.getPoint();
	}	
}