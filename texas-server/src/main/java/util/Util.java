package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import server.Card;

public class Util {

	public static final int POCKET_CARD_NUM = 2;

	public static final int COMMUNITY_CARD_NUM = 5;
	
	public static final int AVAILABLE_CARD_NUM = 7;
	
	public static final int EFFECTIVE_CARD_NUM = 5;

	private static final ArrayList<Card> LOWEST_EFF_CARD = new ArrayList<Card>();
	
	public enum FinalCardType {
		HICH_CARD,
		ONE_PAIR,
		TWO_PAIRS,
		SET,
		STRAIGHT,
		FLUSH,
		FULLHOUSE,
		KINGKONG,
		STRAIGHT_FLUSH
	}
	
	public static final HashMap<String, Integer> suitIndex = new HashMap<String, Integer>();
	
	static {
		suitIndex.put("J", 4);
		suitIndex.put("S", 3);
		suitIndex.put("H", 2);
		suitIndex.put("C", 1);
		suitIndex.put("D", 0);
		
		LOWEST_EFF_CARD.add(new Card("S", 2));
		LOWEST_EFF_CARD.add(new Card("S", 3));
		LOWEST_EFF_CARD.add(new Card("C", 4));
		LOWEST_EFF_CARD.add(new Card("S", 5));
		LOWEST_EFF_CARD.add(new Card("H", 7));
	}
	
	public static int suitToInt(String suit){
		return suitIndex.get(suit);
	}

	/**
	 * Determine final card from available 7 cards.
	 * @param list available card with size = 7.
	 * @return Final card.
	 */
	public static FinalCard deterEffectCard(ArrayList<Card> avaList) {
		assert(avaList.size() == AVAILABLE_CARD_NUM);
		
		int[] arr = {0, 1, 2, 3, 4, 5, 6};
		int[] r = new int[5];
		
		FinalCard fc = combination(arr, 5, 0, 0, r, avaList);
		
		return fc;
	}
	
	private static FinalCard combination(int[] arr, int n, int start, int selected, 
			int[] result, ArrayList<Card> avaList) {
		if (n == 0) {
			ArrayList<Card> selectedCard = new ArrayList<Card>();
			
			for ( int i = 0; i < 5; i++ ) {
				selectedCard.add( avaList.get(result[i]) );
			}
			
			Collections.sort(selectedCard);
			
			return checkEffCardType(selectedCard);
		}
		
		FinalCard biggest = new FinalCard(LOWEST_EFF_CARD, FinalCardType.HICH_CARD);
		FinalCard tmp = null;
		
		for ( int i = start; i <= arr.length - n; i++ ) {
			result[selected] = arr[i];
			tmp = combination(arr, n - 1, i + 1, selected + 1, result, avaList);
			if (tmp.compareTo(biggest) > 0) {
				biggest = tmp;
			}
		}

		return biggest;
	}
	
	/**
	 * Check certain effective card type.
	 * @param list sorted or unsorted card in arraylist with size = 5.
	 * @return effect card
	 */
	public static FinalCard checkEffCardType(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		Collections.sort(list);
		
		if (isStraightFlush(list) >= 0) {
			return new FinalCard(list, FinalCardType.STRAIGHT_FLUSH);
		}
		
		if (isKingKong(list) >= 0) {
			return new FinalCard(list, FinalCardType.KINGKONG);
		}
		
		if (isFullHouse(list) >= 0) {
			return new FinalCard(list, FinalCardType.FULLHOUSE);
		}
		
		if (isFlush(list) >= 0) {
			return new FinalCard(list, FinalCardType.FLUSH);
		}
		
		if (isStraight(list) >= 0) {
			return new FinalCard(list, FinalCardType.STRAIGHT);
		}
		
		if (isSet(list) >= 0) {
			return new FinalCard(list, FinalCardType.SET);
		}
		
		if (isTwoPairs(list) >= 0) {
			return new FinalCard(list, FinalCardType.TWO_PAIRS);
		}
		
		if (isOnePair(list) >= 0) {
			return new FinalCard(list, FinalCardType.ONE_PAIR);
		}
		
		if (isHighCard(list) >= 0) {
			return new FinalCard(list, FinalCardType.HICH_CARD);
		}
		
		System.out.println("Error when check effetive card type! Unknown type!");
		
		return null;
	}
	
	private static int containPair(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		for ( int i = 1; i < list.size(); i++ ){
			if (list.get(i).getPoint() == list.get(i - 1).getPoint()){
				return i - 1;
			}
		}
		
		return -1;
	}
	
	private static int containSet(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		for ( int i = 0; i < list.size() - 2; i++ ) {
			if (list.get(i).getPoint() == list.get(i + 1).getPoint() &&
					list.get(i + 1).getPoint() == list.get(i + 2).getPoint()) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static int containLowestStraight (ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (list.get(4).getPoint() != 14){
			return -1;
		}
		
		for ( int i = 3; i >= 0; i-- ) {
			if (list.get(i).getPoint() != i + 2){
				return -1;
			}
		}
		
		return 5;
	}
	
	private static int containStraight (ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		int result = containLowestStraight(list);
		
		if (result >= 0){
			return result;
		}
		
		for ( int i = 4; i > 0; i-- ) {
			if (list.get(i).getPoint() != list.get(i - 1).getPoint() + 1){
				return -1;
			}
		}
		
		return list.get(4).getPoint();
	}
	
	private static int containFlush(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		for ( int i = 0; i < 4; i++ ) {
			if (! list.get(i).getSuit().equals( list.get(i + 1).getSuit() ) ){
				return -1;
			}
		}
		
		return list.get(4).getPoint();
	}
	
	private static int isStraightFlush(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (containStraight(list) >= 0 && containFlush(list) >= 0){
			return list.get(4).getPoint();
		}
		
		return -1;
	}
	
	private static int isKingKong(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		int change = 0;
		int changePos = 0;
		
		for ( int i = 1; i < 5; i++ ){
			if (list.get(i).getPoint() != list.get(i - 1).getPoint()){
				change++;
				changePos = i;
				if ( change > 1 || (changePos != 1 && changePos != 4) ){
					return -1;
				}
			}
		}
		
		return list.get(changePos - 1).getPoint();
	}
	
	private static int isFullHouse(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		int setPos = containSet(list);
		
		if (setPos < 0 || setPos == 1 ){
			return -1;
		}
		
		if (setPos == 0 && list.get(3).getPoint() == list.get(4).getPoint()){
			return list.get(0).getPoint();
		}
		
		if (setPos == 2 && list.get(0).getPoint() == list.get(1).getPoint()){
			return list.get(2).getPoint();
		}
		
		return -1;
	}
	
	private static int isFlush(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (containFlush(list) >= 0 && containStraight(list) < 0) {
			return list.get(4).getPoint();
		}
		
		return -1;
		
	}
	
	private static int isStraight(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (containFlush(list) < 0 && containStraight(list) >= 0) {
			return list.get(4).getPoint();
		}
		
		return -1;
	}
	
	private static int isSet(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		int pos = containSet(list);
		
		if (pos >= 0 && isFullHouse(list) < 0) {
			return list.get(pos).getPoint();
		}
		
		return -1;
	}
	
	private static int isTwoPairs(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (containSet(list) >= 0 || isKingKong(list) >= 0){
			return -1;
		}
		
		if (list.get(0).getPoint() == list.get(1).getPoint() && 
				list.get(2).getPoint() == list.get(3).getPoint()) {
			return list.get(2).getPoint();
		}
		
		if (list.get(0).getPoint() == list.get(1).getPoint() && 
				list.get(3).getPoint() == list.get(4).getPoint()) {
			return list.get(3).getPoint();
		}
		
		if (list.get(1).getPoint() == list.get(2).getPoint() && 
				list.get(3).getPoint() == list.get(4).getPoint()) {
			return list.get(3).getPoint();
		}
		
		return -1;
	}
	
	private static int isOnePair(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (isTwoPairs(list) >= 0 || containSet(list) >= 0 || isKingKong(list) >= 0) {
			return -1;
		}
		
		return containPair(list);
	}
	
	private static int isHighCard(ArrayList<Card> list) {
		assert(list.size() == EFFECTIVE_CARD_NUM);
		
		if (containPair(list) >= 0 || containFlush(list) >= 0 || containStraight(list) >= 0) {
			return -1;
		}
		
		return list.get(4).getPoint();
		
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Card> list = new ArrayList<Card>();
		list.add(new Card("S", 2));
		list.add(new Card("S", 11));
		list.add(new Card("C", 3));
		list.add(new Card("S", 4));
		list.add(new Card("D", 5));
		list.add(new Card("S", 9));
		list.add(new Card("C", 13));
		
		FinalCard fc = deterEffectCard(list);
		
		System.out.println(fc);
	}*/

}
