package game;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import game.cards.Card;
import game.cards.Deck;
import game.cards.monsters.ZombieCard;
import game.maps.GameMap;
import game.maps.Map2;
import game.util.Matrix3x3f;
import game.vectors.Vector2f;

public class Game extends TileFramework {

	private Deck deck;
	private Card grabbedCard;
	
	private GameMap map;
	
	@Override
	protected void initialize() {
		super.initialize();
		deck = new Deck();
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		
		map = new Map2();
	}
	
	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		
		Vector2f centeredMouseVec = getCenteredRelativeWorldMousePosition();
		if (!mouse.buttonDown(MouseEvent.BUTTON1) && grabbedCard != null) {
			// Player dropped card
			grabbedCard.performAction(centeredMouseVec);
			System.out.println(deck.getHand().remove(grabbedCard));
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
	}
	
	@Override
	protected void render(Graphics g) {
		super.render(g);
		Matrix3x3f view = getViewportTransform();
		Graphics2D g2d = (Graphics2D) g;
		
		map.setView(view);
		map.draw(g2d);

		renderTiles(g2d);
		
		// Deck
		for (int i = 0; i < deck.getHand().size(); i++) {
			Card card = deck.getHand().get(i);
			card.setView(view);
			card.draw(g2d);
			if (displayBounds) {
				renderBounds(card, g2d);
			}
		}
		
		if (grabbedCard != null) {
			renderGrid(g2d);
		}
	}
	
	@Override
	public void renderGrid(Graphics2D g) {
		super.renderGrid(g);
		g.setStroke(new BasicStroke(2));
		for (TileLocation loc : map.getInvalidTileLocations()) {
			Point p = convertTileLocationToPoint(loc);
			g.drawLine(p.x, p.y, p.x + 48, p.y + 48);
			g.drawLine(p.x, p.y + 48, p.x + 48, p.y);
		}
	}
	
	public static void main(String[] args) {
		launchApp(new Game());
	}
	
}
