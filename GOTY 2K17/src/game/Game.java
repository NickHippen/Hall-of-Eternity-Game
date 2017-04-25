package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import game.cards.Card;
import game.cards.Deck;
import game.cards.monsters.ZombieCard;
import game.units.Unit;
import game.util.Direction;
import game.util.Matrix3x3f;
import game.vectors.Vector2f;

public class Game extends TileFramework {

	private Deck deck;
	private Card grabbedCard;
	
	@Override
	protected void initialize() {
		super.initialize();
		deck = new Deck();
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
	}
	
	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		
		Vector2f centeredMouseVec = getCenteredRelativeWorldMousePosition();
		if (!mouse.buttonDown(MouseEvent.BUTTON1) && grabbedCard != null) {
			// Player dropped card
			if (grabbedCard.performAction(centeredMouseVec)) {
				deck.getHand().remove(grabbedCard);
			}
			grabbedCard = null;
		}
		if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
			for (Card card : deck.getHand()) {
				if (card.isPointWithin(centeredMouseVec)) {
					grabbedCard = card;
					break;
				}
			}
		}
		if (mouse.buttonDown(MouseEvent.BUTTON1)) {
			if (grabbedCard != null) {
				grabbedCard.setLocation(centeredMouseVec);
			}
		}
		
		if (keyboard.keyDownOnce(KeyEvent.VK_D)) {
			for (int i = 0; i < 100; i++) {
				getWorld().valueIteration();
			}
			displayDirections = !displayDirections;
		}
		
		if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) { // TODO Remove me!
			for (Tile[] tileRow : getWorld().getTiles()) {
				for (Tile tile : tileRow) {
					for (Unit unit : tile.getUnits()) {
						unit.setMoving(Direction.values()[new Random().nextInt(4)]);
					}
				}
			}
		}
	}
	
	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
		// Deck
		for (int i = 0; i < deck.getHand().size(); i++) {
			Card card = deck.getHand().get(i);
			if (card == grabbedCard) {
				continue;
			}
			card.setLocation(new Vector2f(-2.95f + (i * 1f), -0.87f));
		}
		
		for (Tile[] tileRow : getWorld().getTiles()) {
			for (Tile tile : tileRow) {
				for (Unit unit : tile.getUnits()) {
					if (unit.getMoving() != null) {
						unit.move(unit.getMoving(), 0.25f, delta);
					}
				}
			}
		}
	}
	
	@Override
	protected void render(Graphics g) {
		super.render(g);
		Matrix3x3f view = getViewportTransform();
		Graphics2D g2d = (Graphics2D) g;
		
		getWorld().getMap().setView(view);
		getWorld().getMap().draw(g2d);

		renderTiles(g2d);
		
		if (grabbedCard != null) {
			renderGrid(g2d);
		}
		
		// Deck
		for (int i = 0; i < deck.getHand().size(); i++) {
			Card card = deck.getHand().get(i);
			card.setView(view);
			card.draw(g2d);
			if (displayBounds) {
				renderBounds(card, g2d);
			}
		}
		
		if (displayDirections) {
			renderDirections(g2d);
		}
	}
	
	public static void main(String[] args) {
		launchApp(new Game());
	}
	
}
