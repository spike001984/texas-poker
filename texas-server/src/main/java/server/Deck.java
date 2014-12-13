package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	public static String[] SUITS = {"S", "H", "C", "D", "J"};
	
	private List<Card> cards;
	
	private Deck() {
		cards = new ArrayList<Card>(52);
	}
	
	public static Deck getDeckInstance() {
		Deck deck = new Deck();
		deck.init();
		deck.shuffle();
		return deck;
	}
	
	private void init() {
		for(int suit = 0; suit < 4; suit++){
			for(int point = 0; point < 13; point++){
				cards.add(new Card(Deck.SUITS[suit], point+2));
			}
		}		
	}
	
	private void shuffle() {
		Random random = new Random();
		for (int i = 0, randIndex = random.nextInt(51); i < 52; i++, randIndex = random.nextInt(51)) {
			Card temp = cards.get(randIndex);
			cards.set(randIndex, cards.get(i));
			cards.set(i, temp);
		}
	}
	
	public void printDeck() {
		for(Card card:cards) {
			System.out.print(card);
		}
	}
	
	public Card deal() {
		return cards.remove(cards.size()-1);
	}	
	
	public static void main(String[] args) {
		Deck deck = Deck.getDeckInstance();
		for(int i = 0; i < 10; i++){
			deck.printDeck();
			System.out.println("-->deal:" + deck.deal());
			deck.printDeck();
			System.out.println("");
		}
	}
}
