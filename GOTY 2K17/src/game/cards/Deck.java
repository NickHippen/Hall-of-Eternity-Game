package game.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.TileWorld;
import game.cards.curses.BlastCard;
import game.cards.curses.CardBack;
import game.cards.curses.FirestormCard;
import game.cards.curses.FlashFreezeCard;
import game.cards.curses.HealCard;
import game.cards.curses.TouchOfDeathCard;
import game.cards.curses.TransmuteCard;
import game.cards.monsters.ZombieCard;
import game.cards.traps.FireTrapCard;
import game.cards.traps.IceTrapCard;
import game.cards.traps.IronMaidenTrapCard;
import game.cards.traps.PitfallTrapCard;
import game.cards.traps.StunTrapCard;
import game.vectors.Vector2f;

public class Deck {

	private List<Card> cards;
	private List<Card> hand;
	private Card cardBack;
	private TileWorld world;
	private int removed;
	private boolean drawing;
	public Deck(TileWorld world) {
		cards = new ArrayList<>();
		hand = new ArrayList<>();
		this.world = world;
		
		cardBack = new CardBack(world);
		setupDeck();
		shuffle();
		this.removed = -1;
	}
	
	//Currently the only way to put cards into deck
	public void setupDeck(){
		cards.add(new ZombieCard(this.world));
		cards.add(new ZombieCard(this.world));
		cards.add(new ZombieCard(this.world));
		cards.add(new ZombieCard(this.world));
		cards.add(new ZombieCard(this.world));
		cards.add(new BlastCard(this.world));
		cards.add(new BlastCard(this.world));
		cards.add(new BlastCard(this.world));
		cards.add(new FlashFreezeCard(this.world));
		cards.add(new FlashFreezeCard(this.world));
		cards.add(new FlashFreezeCard(this.world));
		cards.add(new HealCard(this.world));
		cards.add(new HealCard(this.world));
		cards.add(new HealCard(this.world));
		cards.add(new FirestormCard(this.world));
		cards.add(new FirestormCard(this.world));
		cards.add(new FirestormCard(this.world));
		cards.add(new TouchOfDeathCard(this.world));
		cards.add(new TouchOfDeathCard(this.world));
		cards.add(new TouchOfDeathCard(this.world));
		cards.add(new TransmuteCard(this.world));
		cards.add(new TransmuteCard(this.world));
		cards.add(new TransmuteCard(this.world));
		cards.add(new IceTrapCard(this.world));
		cards.add(new IceTrapCard(this.world));
		cards.add(new IceTrapCard(this.world));
		cards.add(new StunTrapCard(this.world));
		cards.add(new StunTrapCard(this.world));
		cards.add(new StunTrapCard(this.world));
		cards.add(new PitfallTrapCard(this.world));
		cards.add(new PitfallTrapCard(this.world));
		cards.add(new PitfallTrapCard(this.world));
		cards.add(new IronMaidenTrapCard(this.world));
		cards.add(new IronMaidenTrapCard(this.world));
		cards.add(new IronMaidenTrapCard(this.world));
		cards.add(new FireTrapCard(this.world));
		cards.add(new FireTrapCard(this.world));
		cards.add(new FireTrapCard(this.world));
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
	
	public void setRemoved(int removed){
		this.removed = removed;
	}
	
	public int getCardsRemaining(){
		return cards.size();
	}
	
	public void shuffle(){
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	//Adds card to your hand, triggers draw for draw animation
	public void drawCard(){
		if(hand.size() < 5 && !drawing && cards.size() != 0){
			hand.add(cards.remove(0));
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
}
