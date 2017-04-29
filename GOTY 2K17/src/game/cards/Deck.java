package game.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.TileWorld;
import game.cards.curses.CardBack;
import game.cards.curses.HealCard;
import game.cards.curses.tempCard;
import game.cards.monsters.DragonCard;
import game.cards.monsters.ZombieCard;
import game.vectors.Vector2f;

public class Deck {

	private List<Card> cards;
	private List<Card> hand;
	private Card cardBack;
	private TileWorld world;
	private boolean drawing;
	
	public Deck(TileWorld world) {
		cards = new ArrayList<>();
		hand = new ArrayList<>();
		cardBack = new CardBack();
		this.world = world;
		setupDeck();
		shuffle();
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
	
	public void shuffle(){
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	public void drawCard(){
		if(hand.size() < 5 && !drawing && cards.size() != 0){
			hand.add(cards.remove(0));
			drawing = true;
			hand.get(hand.size() - 1).setLocation((new Vector2f(-2.95f + 5f, -0.87f)));
		}
	}
	
	public void setupDeck(){
		cards.add(new ZombieCard(this.world));
		cards.add(new HealCard(this.world));
		cards.add(new tempCard(this.world));
		cards.add(new ZombieCard(this.world));
		cards.add(new DragonCard(this.world));
	}

	public void drawAnimation(float delta) {

		if (drawing) {
			Vector2f nextLoc = new Vector2f(-2.95f + (((hand.size()) - 1) * 1f), -0.87f);
			if (hand.get(hand.size() - 1).getLocation().x > nextLoc.x) {
				hand.get(hand.size() - 1).setLocation(new Vector2f(hand.get(hand.size() - 1).getLocation().x - delta * 10, -0.87f));
			}else{
				drawing = false;
			}
		}
	}
}
