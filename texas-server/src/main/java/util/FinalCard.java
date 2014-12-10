package util;

import java.util.ArrayList;
import java.util.Collections;

import server.Card;
import util.Util.FinalCardType;

/**
 * Encapsulate one certain final card. The card size should be 5.
 * @author Michael
 *
 */
public class FinalCard implements Comparable<FinalCard>{

	private ArrayList<Card> sortedCardList;
	
	private FinalCardType type;
	
	private int[] cmpPoint;
	
	public FinalCard(ArrayList<Card> sortedCardList, FinalCardType type) {
		super();
		assert(sortedCardList.size() == Util.EFFECTIVE_CARD_NUM);
		
		this.sortedCardList = sortedCardList;
		this.type = type;
		
		Collections.sort(this.sortedCardList);
		
		cmpPoint = initCmpPoint(sortedCardList, type);
	}

	public ArrayList<Card> getSortedCardList() {
		return sortedCardList;
	}

	public void setSortedCardList(ArrayList<Card> sortedCardList) {
		this.sortedCardList = sortedCardList;
	}

	public FinalCardType getType() {
		return type;
	}

	public void setType(FinalCardType type) {
		this.type = type;
	}

	public int[] getCmpPoint() {
		return cmpPoint;
	}

	public void setCmpPoint(int[] cmpPoint) {
		this.cmpPoint = cmpPoint;
	}
	
	/**
	 * 
	 * @param list SORTED CARD LIST! with size of 5.
	 * @param type
	 * @return
	 */
	private int[] initCmpPoint(ArrayList<Card> list, FinalCardType type){
		int[] point = null;
		
		switch (type) {
		case STRAIGHT_FLUSH:
		case STRAIGHT:
			point = new int[1];
			if (Util.containLowestStraight(list) >= 0) {
				point[0] = 5;
			}
			else {
				point[0] = list.get(4).getPoint();
			}
			break;
		case KINGKONG:
			point = new int[2];
			for ( int i = 1; i < 4; i++ ){
				if (list.get(i).getPoint() != list.get(i - 1).getPoint()){
					if (i == 1) {
						point[0] = list.get(4).getPoint();
						point[1] = list.get(0).getPoint();
					}
					else {
						point[0] = list.get(0).getPoint();
						point[1] = list.get(4).getPoint();
					}
				}
			}
			
			break;
		case FULLHOUSE:
			point = new int[2];
			
			if (list.get(2).getPoint() == list.get(1).getPoint()) {
				point[0] = list.get(0).getPoint();
				point[1] = list.get(4).getPoint();
			}
			else {
				point[0] = list.get(4).getPoint();
				point[1] = list.get(0).getPoint();
			}
			break;
		case FLUSH:
		case HICH_CARD:
			point = new int[5];
			
			for ( int i = 4, j = 0; i >= 0; i--, j++ ) {
				point[j] = list.get(i).getPoint();
			}
			break;
		case SET:
			point = new int[3];
			
			for ( int i = 0, j = 1; i < 4; i++ ) {
				if (list.get(i).getPoint() == list.get(i + 1).getPoint()) {
					point[0] = list.get(i).getPoint();
					i += 2;
				}
				else {
					point[j++] = list.get(i).getPoint();
				}
			}
			break;
		case TWO_PAIRS:
			point = new int[3];
			
			//Kicker is at the beginning.
			if (list.get(0).getPoint() != list.get(1).getPoint()) {
				point[0] = list.get(3).getPoint();
				point[1] = list.get(1).getPoint();
				point[2] = list.get(0).getPoint();
			}
			//Kicker is in the middle.
			else if (list.get(2).getPoint() != list.get(1).getPoint() && 
					 list.get(2).getPoint() != list.get(3).getPoint() ) {
				point[0] = list.get(3).getPoint();
				point[1] = list.get(0).getPoint();
				point[2] = list.get(2).getPoint();
			}
			else {
				//Kicker is in the end.
				point[0] = list.get(2).getPoint();
				point[1] = list.get(0).getPoint();
				point[2] = list.get(4).getPoint();
			}
			
			break;
		case ONE_PAIR:
			point = new int[4];
			
			for ( int i = 0, j = 1; i < 4; i++ ) {
				if (list.get(i).getPoint() == list.get(i + 1).getPoint()) {
					point[0] = list.get(i).getPoint();
					i++;
				}
				else {
					point[j++] = list.get(i).getPoint();
				}
			}
			break;

		default:
			System.out.println("Error when initializing compare point! Unknow type!");
			break;
		}
		
		return point;
	}

	public int compareTo(FinalCard another) {
		// TODO Auto-generated method stub
		if (type.compareTo(another.getType()) > 0) {
			return 1;
		}
		
		if (type.compareTo(another.getType()) < 0) {
			return -1;
		}
		
		return compareSameType(another);
	}

	private int compareSameType(FinalCard another) {
		// TODO Auto-generated method stub
		assert(cmpPoint != null);
		
		for ( int i = 0; i < cmpPoint.length; i++ ) {
			if (cmpPoint[i] > another.getCmpPoint()[i]) {
				return 1;
			}

			if (cmpPoint[i] < another.getCmpPoint()[i]) {
				return -1;
			}
		}
		
		return 0;
	}

	public FinalCard clone() {
		return new FinalCard(sortedCardList, type);
	}
	
	public String toString() {
		String result = "Final Card: ";
		
		for ( int i = 0; i < sortedCardList.size(); i++ ) {
			result += sortedCardList.get(i).toString() + " ";
		}
		
		result += ". Type : " + type.toString();
		
		return result;
	}
}
