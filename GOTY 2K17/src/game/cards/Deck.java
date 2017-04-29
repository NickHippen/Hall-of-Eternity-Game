package game.cards;

import java.util.ArrayList;
import java.util.List;

import game.TileWorld;
import game.cards.curses.CardBack;
import game.units.monsters.Zombie;

public class Deck {

	private List<Card> cards;
	private List<Card> hand;
	private Card cardBack;
	
	public Deck() {
		cards = new ArrayList<>();
		hand = new ArrayList<>();
		cardBack = new CardBack();
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public Card getCardBack(){
		return cardBack;
	}
	
}
