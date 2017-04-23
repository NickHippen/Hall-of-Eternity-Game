package game.cards;

import java.util.ArrayList;
import java.util.List;

public class Deck {

	private List<Card> cards;
	private List<Card> hand;
	
	public Deck() {
		cards = new ArrayList<>();
		hand = new ArrayList<>();
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
}
