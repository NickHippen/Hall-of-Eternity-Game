package edu.unomaha.nknc.game.cards;

import java.util.ArrayList;
import java.util.List;

import edu.unomaha.nknc.game.cards.monsters.ZombieCard;

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
