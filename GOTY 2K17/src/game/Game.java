package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.cards.Card;
import game.cards.Deck;

import game.cards.monsters.*;

import game.cards.curses.*;

import game.units.Unit;
import game.units.heroes.Freelancer;
import game.util.Matrix3x3f;
import game.vectors.Vector2f;

public class Game extends TileFramework {

	private Deck deck;
	private Card grabbedCard;
	private Card activatedCard;
	
	private int waveNum;
	private int boneNum;
	Vector2f centeredMouseVec;
	
	boolean selectingTarget;
	
	ArrayList<Unit> selectedUnits;
	//Used for card movement
	Vector2f initialLoc = null;
	Vector2f initialLocCard = null;
	
	private String message;

	@Override
	protected void initialize() {
		super.initialize();
		
		deck = new Deck(getWorld());
		
		getWorld().policyIteration(tile -> tile.getStandardPathfinding());
		
		this.waveNum = 0;
		this.boneNum = 123;
		this.message = "";
		
		selectedUnits = new ArrayList<Unit>();
	}
	
	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		
		centeredMouseVec = getCenteredRelativeWorldMousePosition();
		
		// Player drops card
		if (!mouse.buttonDown(MouseEvent.BUTTON1) && grabbedCard != null) {
			if(onBoard(centeredMouseVec)){
				if(grabbedCard instanceof MonsterSpawnCard) selectingTarget = true;
				deck.getHand().remove(grabbedCard);
				activatedCard = grabbedCard;
			}
			grabbedCard = null;
		}

		//Player grabs card
		if(!selectingTarget && !onBoard(centeredMouseVec)){
			if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
				for (Card card : deck.getHand()) {
					if (card.isPointWithin(centeredMouseVec)) {
						grabbedCard = card;
						initialLocCard = grabbedCard.getLocation();
						initialLoc = centeredMouseVec;
						break;
					}
				}
			}
		}
		
		//Initial code for unit selection
		if(onBoard(centeredMouseVec)){
			if(mouse.buttonDownOnce(MouseEvent.BUTTON1)){
				selectedUnits.clear();
				int xPos = getWorld().getTileLocationAtPosition(centeredMouseVec).getX();
				int yPos = getWorld().getTileLocationAtPosition(centeredMouseVec).getY();
				selectedUnits.addAll((getWorld().getTiles()[xPos][yPos].getUnits()));
				for(int i = 0; i < selectedUnits.size(); i++){
					System.out.println(selectedUnits.get(i).getName());
				}
			}
		}
		
		//Moves card that player has grabbed
		if (mouse.buttonDown(MouseEvent.BUTTON1)) {
			if (grabbedCard != null) {
				grabbedCard.setLocation(initialLocCard.sub(initialLoc.sub(centeredMouseVec)));
			}
		}
		
		if(deck.getCardBack().isPointWithin(centeredMouseVec)){
			if (mouse.buttonDownOnce(MouseEvent.BUTTON1)){
				deck.drawCard();
			}
		}
	
		
		//Player selects target
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1) && selectingTarget){
			if (activatedCard.performAction(centeredMouseVec)) {
				selectingTarget = false;
				grabbedCard = null;
			}
		}
		
		if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
			getWorld().addUnitToTile(new TileLocation(0, 6), new Freelancer(getWorld()));
		}
	}
	
	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
		
		deck.getCardBack().setLocation(new Vector2f(-2.95f + 5f, -0.87f));
		deck.drawAnimation(delta);
		
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
		
		if (selectingTarget || displayCoordinates) {
			renderGrid(g2d);
		}
		if (displayDirections) {
			renderDirections(g2d);
		}
		if (displayCoordinates) {
			renderCoordinates(g2d);
		}
		
		//Renders Wave and Bones text
		g.setColor(Color.WHITE);
		g.setFont(new Font("Cooper Black", Font.PLAIN, 18));
		g.drawString("WAVE", 90,640);
		g.drawString("BONES", 1287, 640);
		
		if(selectingTarget){
			g.setFont(new Font("Cooper Black", Font.PLAIN, 20));
			message = "";
			if(activatedCard instanceof MonsterSpawnCard){
				message = "Activated";
				g.drawString(message, 581, 645);
				g.setColor(Color.ORANGE);
				message = activatedCard.getName();
				g.drawString(message, 688, 645);
				g.setColor(Color.WHITE);
				message = "select summon location";
				g.drawString(message, 581, 666);
			}
			message = "";
		}
		
		g.setFont(new Font("Titillium Web", Font.PLAIN, 25));
		g.drawString(String.format("%03d", waveNum), 99, 664);
		g.drawString(String.format("%03d", boneNum), 1299, 664);
	
		
		//Renders map tile selection
		if ((selectingTarget || grabbedCard == null) && onBoard(centeredMouseVec)) renderSelectedTile(g2d, getWorld().getTileLocationAtPosition(centeredMouseVec));
		
		// Renders cards
		for (int i = 0; i < deck.getHand().size(); i++) {
			Card card = deck.getHand().get(i);
			card.setView(view);
			card.draw(g2d);
			//Shows line around selected cards
			g.setColor(Color.green);
			if (card.isPointWithin(centeredMouseVec) && !selectingTarget && grabbedCard == null) card.getOuterBound().render(g);
			if(grabbedCard != null) grabbedCard.getOuterBound().render(g);
		}
		
		deck.getCardBack().setView(view);
		deck.getCardBack().draw(g2d);
	}
	
	public static void main(String[] args) {
		launchApp(new Game());
	}
	
}
