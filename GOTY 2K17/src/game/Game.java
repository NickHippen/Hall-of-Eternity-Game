package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.cards.Card;
import game.cards.Deck;
import game.cards.curses.ActionCard;
import game.cards.curses.AreaCard;
import game.cards.curses.CurseCard;
import game.cards.curses.UnitSelectCard;
import game.cards.monsters.MonsterSpawnCard;
import game.units.Unit;
import game.units.heroes.Freelancer;
import game.units.monsters.Boss;
import game.util.Matrix3x3f;
import game.vectors.Vector2f;
import menu.TitleScreen;

public class Game extends TileFramework {

	private static final Random RANDOM = new Random();

	private Deck deck;
	private Card grabbedCard;
	private Card activatedCard;

	Vector2f mouseVec;

	boolean selectingTarget;
	boolean selectingArea;

	ArrayList<Unit> selectedUnits;
	// Used for card movement
	Vector2f initialLoc = null;
	Vector2f initialLocCard = null;

	private String message;

	private boolean titleScreen;
	private boolean levelSelection;
	private boolean deckCreation;
	private boolean gameplay;

	private TitleScreen title;
	
	@Override
	protected void initialize() {
		super.initialize();

		deck = new Deck(getWorld());

		getWorld().policyIteration(Tile::getStandardPathfinding);
		getWorld().policyIteration(Tile::getAggroPathfinding);

		this.getWorld().setWaveNum(0);
		this.getWorld().setBoneNum(123);
		this.message = "";

		selectedUnits = new ArrayList<Unit>();

		this.titleScreen = true;
		
		title = new TitleScreen(getWorld());
	}

	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		mouseVec = getCenteredRelativeWorldMousePosition();

		if (titleScreen) {
			if (mouseVec.x < .55 && mouseVec.x > -.55 && mouseVec.y < -1.2 && mouseVec.x > -1.57){
				if(mouse.buttonDownOnce(MouseEvent.BUTTON1)){
					titleScreen = false;
					gameplay = true;
				}
				title.selectButton();
			}else title.unselectButton();
		}

		if (gameplay) {
			// Player drops card
			
			// Player drops card
			if (!mouse.buttonDown(MouseEvent.BUTTON1) && grabbedCard != null) {
				if(onBoard(mouseVec)){
					if (grabbedCard instanceof MonsterSpawnCard || grabbedCard instanceof UnitSelectCard) {
						selectingTarget = true;
					} else if (grabbedCard instanceof AreaCard) {
						selectingArea = true;
					} else if (grabbedCard instanceof ActionCard) {
						grabbedCard.performAction(mouseVec);
					}
					
					deck.setRemoved(deck.getHand().indexOf(grabbedCard));
					deck.getHand().remove(grabbedCard);
					activatedCard = grabbedCard;
					for (Unit unit : getWorld().getUnits()) {
						if (unit instanceof Boss) {
							((Boss) unit).setAttacking(true);
							break;
						}
					}
				} else {
					// Puts card back in its original spot
					deck.getHand().get(deck.getHand().indexOf(grabbedCard))
							.setLocation(new Vector2f(-2.95f + ((deck.getHand().indexOf(grabbedCard)) * 1f), -0.87f));
				}
				grabbedCard = null;
			}

			// Player grabs card
			if (!selectingTarget && !onBoard(mouseVec)) {
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					for (Card card : deck.getHand()) {
						if (card.isPointWithin(mouseVec)) {
							grabbedCard = card;
							initialLocCard = grabbedCard.getLocation();
							initialLoc = mouseVec;
							break;
						}
					}
				}
			}

			// Initial code for unit selection
			if (onBoard(mouseVec)) {
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					selectedUnits.clear();
					int xPos = getWorld().getTileLocationAtPosition(mouseVec).getX();
					int yPos = getWorld().getTileLocationAtPosition(mouseVec).getY();
					selectedUnits.addAll((getWorld().getTiles()[xPos][yPos].getUnits()));
					for (int i = 0; i < selectedUnits.size(); i++) {
						// Do something here on unit select
					}
				}
			}

			// Moves card that player has grabbed
			if (mouse.buttonDown(MouseEvent.BUTTON1)) {
				if (grabbedCard != null) {
					grabbedCard.setLocation(initialLocCard.sub(initialLoc.sub(mouseVec)));
				}
			}

			// Draw card by clicking on deck
			if (deck.getCardBack().isPointWithin(mouseVec)) {
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					deck.drawCard();
				}
			}

			// Player selects single tile
			if (mouse.buttonDownOnce(MouseEvent.BUTTON1) && selectingTarget) {
				if (activatedCard.performAction(mouseVec)) {
					selectingTarget = false;
					grabbedCard = null;
				}
			}

			// Player selects 3x3 area tile
			if (mouse.buttonDownOnce(MouseEvent.BUTTON1) && selectingArea) {
				if (activatedCard.performAction(mouseVec)) {
					selectingArea = false;
					grabbedCard = null;
				}
			}

			if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
				getWorld().addUnitToTile(new TileLocation(0, RANDOM.nextInt(4) + 5), new Freelancer(getWorld()));
			}
		}
	}

	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);

		if (gameplay) {
			deck.getCardBack().setLocation(new Vector2f(-2.95f + 5f, -0.87f));

			deck.drawAnimation(delta);

			List<Unit> units = getWorld().getUnits();
			for (int i = units.size() - 1; i >= 0; i--) {
				Unit unit = units.get(i);
				unit.update(delta);
			}
		}
	}

	@Override
	protected void render(Graphics g) {
		super.render(g);

		Matrix3x3f view = getViewportTransform();
		Graphics2D g2d = (Graphics2D) g;

		if(titleScreen){
			title.setView(view);
			title.draw(g2d);
		}
		
		if (gameplay) {
			getWorld().getMap().setView(view);
			getWorld().getMap().draw(g2d);

			renderTiles(g2d);

			if (selectingTarget || selectingArea || displayCoordinates) {
				renderGrid(g2d);
			}
			if (displayDirections != 0) {
				renderDirections(g2d);
			}
			if (displayCoordinates) {
				renderCoordinates(g2d);
			}

			// Renders Wave and Bones text
			g.setColor(Color.WHITE);
			g.setFont(new Font("Cooper Black", Font.PLAIN, 18));
			g.drawString("WAVE", 90, 640);
			g.drawString("BONES", 1287, 640);

			if (selectingTarget || selectingArea) {
				g.setFont(new Font("Cooper Black", Font.PLAIN, 20));
				message = "";
				message = "Activated";
				g.drawString(message, 581, 645);
				g.setColor(Color.ORANGE);
				message = activatedCard.getName();
				g.drawString(message, 688, 645);
				g.setColor(Color.WHITE);
				if (activatedCard instanceof CurseCard)
					message = "select target location";
				if (activatedCard instanceof MonsterSpawnCard)
					message = "select summon location";
				g.drawString(message, 581, 666);
				message = "";
			}

			g.setFont(new Font("Titillium Web", Font.PLAIN, 25));
			g.drawString(String.format("%03d", getWorld().getWaveNum()), 99, 664);
			g.drawString(String.format("%03d", getWorld().getBoneNum()), 1299, 664);

			// Renders map tile selection or area selection
			if ((selectingArea && grabbedCard == null) && onBoard(mouseVec))
				renderSelectedArea(g2d, getWorld().getTileLocationAtPosition(mouseVec));
			else if ((selectingTarget || grabbedCard == null) && onBoard(mouseVec))
				renderSelectedTile(g2d, getWorld().getTileLocationAtPosition(mouseVec));

			// Renders cards
			for (int i = 0; i < deck.getHand().size(); i++) {
				Card card = deck.getHand().get(i);
				card.setView(view);
				card.draw(g2d);
				// Shows line around selected cards
				g.setColor(Color.green);
				if (card.isPointWithin(mouseVec) && !selectingTarget && grabbedCard == null)
					card.getOuterBound().render(g);
			}

			// Renders the card back
			deck.getCardBack().setView(view);
			if (deck.getCardsRemaining() > 0)
				deck.getCardBack().draw(g2d);

			// Render grabbed card last so it's on top of everything
			if (grabbedCard != null)
				grabbedCard.draw(g2d);
			if (grabbedCard != null)
				grabbedCard.getOuterBound().render(g);
		}
	}

	public static void main(String[] args) {
		launchApp(new Game());
	}

}
