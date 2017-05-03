package game.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.TileWorld;
import game.cards.curses.CardBack;

import game.vectors.Vector2f;

public class Deck {

	private List<Card> deck;
	private List<Card> tempDeck;
	private List<Card> hand;

	private Card cardBack;
	private int removed;
	private boolean drawing;
	
	public Deck(TileWorld world) {
		deck = new ArrayList<>();
		tempDeck = new ArrayList<>();
		hand = new ArrayList<>();
		
		cardBack = new CardBack(world);
		shuffle();
		this.removed = -1;
	}
	
	//Currently the only way to put tempDeck into deck
	
	public List<Card> getTempDeck() {
		return tempDeck;
	}
	
	public List<Card> getHand() {
		return hand;
	}
	
	public List<Card> getDeck(){
		return deck;
	}
	
	public Card getCardBack(){
		return cardBack;
	}
	
	public void setRemoved(int removed){
		this.removed = removed;
	}
	
	public int getCardsRemaining(){
		return tempDeck.size();
	}
	
	public void shuffle(){
		long seed = System.nanoTime();
		Collections.shuffle(tempDeck, new Random(seed));
	}
	
	//Adds card to your hand, triggers draw for draw animation
	public void drawCard(){
		if(hand.size() < 5 && !drawing && tempDeck.size() != 0){
			hand.add(tempDeck.remove(0));
			drawing = true;
			hand.get(hand.size() - 1).setLocation((new Vector2f(-2.95f + 5f, -0.87f)));
		}
	}

	//Performs animation for drawing a new card and shifting hand when a card is played
	public void drawAnimation(float delta) {
		//Animation for drawing a card
		if (drawing) {
			Vector2f nextLoc = new Vector2f(-2.95f + (((hand.size()) - 1) * 1f), -0.87f);
			if (hand.get(hand.size() - 1).getLocation().x > nextLoc.x) {
				hand.get(hand.size() - 1).setLocation(new Vector2f(hand.get(hand.size() - 1).getLocation().x - delta * 15, -0.87f));
			}else{
				drawing = false;
			}
		}
		
		//Animation for shifting hand. Perform until cards aren't moving over anymore
		int done=0;
		if(this.removed != -1){
			for(int i = removed; i < hand.size(); i++){
				Vector2f nextLoc = new Vector2f(-2.95f + (i * 1f), -0.87f);
				if (hand.get(i).getLocation().x > nextLoc.x) {
					done++;
					hand.get(i).setLocation(new Vector2f(hand.get(i).getLocation().x - delta * 15, -0.87f));
				}else{
					drawing = false;
				}
			}
		}
		if(done == 0) this.removed = -1;
	}
	
	public void addCard(Card card){
		deck.add(card);
	}
	
	public void removeCard(Card card){
		deck.remove(card);
	}
	
	public void resetDeck(){
		tempDeck.clear();
		tempDeck.addAll(deck);
		System.out.println(deck.size());
		this.shuffle();
	}
}
