package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.cards.Card;
import game.cards.Deck;
import game.cards.monsters.DragonCard;
import game.cards.monsters.ZombieCard;
import game.units.Unit;
import game.units.heroes.Freelancer;
import game.util.Matrix3x3f;
import game.vectors.Vector2f;

public class Game extends TileFramework {

	private Deck deck;
	private Card grabbedCard;
	private int waveNum;
	private int boneNum;
	Vector2f centeredMouseVec;

	@Override
	protected void initialize() {
		super.initialize();
		
		deck = new Deck();
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new ZombieCard(getWorld()));
		deck.getHand().add(new DragonCard(getWorld()));
		deck.getHand().add(new DragonCard(getWorld()));
		deck.getHand().add(new DragonCard(getWorld()));
		
		getWorld().policyIteration();
		
		this.waveNum = 69;
		this.boneNum = 420;
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
		
		if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
			getWorld().addUnitToTile(new TileLocation(0, 6), new Freelancer(getWorld()));
		}
	}
	
	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
		centeredMouseVec = getCenteredRelativeWorldMousePosition();

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
					unit.update(delta);
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
		
		// Deck
		for (int i = 0; i < deck.getHand().size(); i++) {
			Card card = deck.getHand().get(i);
			card.setView(view);
			card.draw(g2d);
			if (displayBounds) {
				renderBounds(card, g2d);
			}
		}
		
		if (grabbedCard != null || displayCoordinates) {
			renderGrid(g2d);
		}
		if (displayDirections) {
			renderDirections(g2d);
		}
		if (displayCoordinates) {
			renderCoordinates(g2d);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		g.drawString("WAVE", 90,640);
		g.drawString("BONES", 1287, 640);
		
		g.setFont(new Font("Titillium Web", Font.PLAIN, 25));
		g.drawString(String.format("%03d", waveNum), 99, 664);
		g.drawString(String.format("%03d", boneNum), 1299, 664);

		renderSelectedTile(g2d, getWorld().getTileLocationAtPosition(centeredMouseVec));
	}
	
	public static void main(String[] args) {
		launchApp(new Game());
	}
	
}
