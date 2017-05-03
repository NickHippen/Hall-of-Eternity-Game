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
import game.cards.traps.TrapSpawnCard;
import game.maps.DungeonMap;
import game.maps.SnowMap;
import game.maps.TownMap;
import game.menu.Button;
import game.menu.DeckMaker;
import game.menu.LevelSelect;
import game.menu.TitleScreen;
import game.sound.PlayerControl;
import game.units.GameOverSprite;
import game.units.Unit;
import game.units.heroes.Hero;
import game.units.heroes.HeroClassType;
import game.units.heroes.HeroFactory;
import game.units.monsters.Boss;
import game.util.Matrix3x3f;
import game.vectors.Vector2f;

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
	static  PlayerControl bg;
	private GameOverSprite gameOverSprite;
	private final HeroFactory heroFactory = new HeroFactory();
	

	private boolean titleScreen = true;
	private boolean levelSelection;
	private boolean deckCreation;
	private boolean gameplay;
	private boolean pause;
	
	private float spawnTimer;
	private float waveTimer;
	private boolean waveStarted = false;

	private TitleScreen title;
	private LevelSelect level;
	private DeckMaker deckEditor;
	Button quitButton;
	Button doneButton;

	@Override
	protected void initialize() {
		super.initialize();

		this.gameOverSprite = new GameOverSprite(getWorld());
		this.getWorld().setWaveNum(1);
		this.getWorld().setBoneNum(75);
		this.message = "";

		selectedUnits = new ArrayList<Unit>();

		title = new TitleScreen(getWorld());
		level = new LevelSelect(getWorld());
		
		doneButton = new Button(getWorld());
		doneButton.setLocation(doneButton.getLocation().add(new Vector2f (0, .3f)));
		quitButton = new Button(getWorld());
		quitButton.setLocation(quitButton.getLocation().add(new Vector2f (0, -.3f)));
		bg = new PlayerControl();
		bg.playBG();
		
		deckEditor = new DeckMaker(getWorld());
		deck = new Deck(getWorld()); 

		deck = deckEditor.getDeck();
		deck.resetDeck();
		deck.getHand().clear();
	}
	
	protected void restart(){
		selectingTarget = false;
		selectingArea = false;
		waveStarted = false;
		waveTimer = 0f;
		spawnTimer = 0f;
		grabbedCard = null;
		activatedCard = null;
		deck = deckEditor.getDeck();
		deck.resetDeck();
		deck.getHand().clear();
		this.getWorld().setWaveNum(1);
		this.getWorld().setBoneNum(75);
		this.getWorld().getUnits().clear();
		getWorld().setGameover(false);
		getWorld().getMap().addBoss(getWorld(), new Boss(getWorld()));
	}

	@Override
	protected void processInput(float delta) {
		super.processInput(delta);
		mouseVec = getCenteredRelativeWorldMousePosition();
		//THERE IS A BUG SOMEWHERE THAT CAUSES ISSUES WITH THE HIT BOXES OF BUTTONS, NOBODY COULD SOLVE IT SO VALUES ARE HARD CODED
		if (titleScreen) {
			if (mouseVec.x < .55 && mouseVec.x > -.55 && mouseVec.y < -1.2 && mouseVec.y > -1.57) {
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					titleScreen = false;
					levelSelection = true;
					return;
				}
				title.selectButton();
			} else
				title.unselectButton();
		}

		if (levelSelection) {
			// Player hovering over CARDS button
			if (mouseVec.x < 2.8 && mouseVec.x > 2.29 && mouseVec.y < 1.15 && mouseVec.y > -.08) {
				level.selectButton(4);
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					deckCreation = true;
					levelSelection = false;
					return;
				}
			}
			// Player hovering over RUINS button
			else if (mouseVec.x < .4 && mouseVec.x > -.4 && mouseVec.y < 1.26 && mouseVec.y > .97) {
				level.selectButton(1);
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					levelSelection = false;
					gameplay = true;
					getWorld().setMap(new DungeonMap());
					getWorld().getMap().getSoung(bg);
					restart();
					return;
				}
			}
			// Player hovering over SNOW button
			else if (mouseVec.x < .4 && mouseVec.x > -.4 && mouseVec.y < .014 && mouseVec.y > -.3) {
				level.selectButton(2);
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					levelSelection = false;
					gameplay = true;
					getWorld().setMap(new SnowMap());
					getWorld().getMap().getSoung(bg);
					restart();
					return;
				}
			}
			// Player hovering over TOWN button
			else if (mouseVec.x < .4 && mouseVec.x > -.4 && mouseVec.y < -1.22 && mouseVec.y > -1.54) {
				level.selectButton(3);
				if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
					levelSelection = false;
					gameplay = true;
					getWorld().setMap(new TownMap());
					getWorld().getMap().getSoung(bg);
					restart();
					return;
				}
			} else
				level.selectButton(0);
		}
		
		if(deckCreation){
			deck = deckEditor.getDeck();
			deckEditor.processInput(mouseVec, mouse);
			if(deckEditor.getQuit()){
				deck = deckEditor.getDeck();
				deckEditor.setQuit(false);
				levelSelection = true;
				deckCreation = false;
				return;
			}
		}

		if (gameplay) {
			if (!pause) {
				// Player drops card
				if (!mouse.buttonDown(MouseEvent.BUTTON1) && grabbedCard != null) {
					if (onBoard(mouseVec)) {
						if (grabbedCard instanceof MonsterSpawnCard || grabbedCard instanceof UnitSelectCard
								|| grabbedCard instanceof TrapSpawnCard) {
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
						deck.getHand().get(deck.getHand().indexOf(grabbedCard)).setLocation(
								new Vector2f(-2.95f + ((deck.getHand().indexOf(grabbedCard)) * 1f), -0.87f));
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
					waveStarted = true;
//					getWorld().addUnitToTile(new TileLocation(0, RANDOM.nextInt(4) + 5), new Freelancer(getWorld()));
//					getWorld().addUnitToTile(new TileLocation(0, 5), new Bard(getWorld()));
//					getWorld().addUnitToTile(new TileLocation(0, 5), new Freelancer(getWorld()));
//					getWorld().addUnitToTile(new TileLocation(0, 8), new Freelancer(getWorld()));
				}
			}
			if (keyboard.keyDownOnce(KeyEvent.VK_ESCAPE)) {
				pause = !pause;
			}
		}
		
		if(pause){
			doneButton.selectButton(0);
			if (mouseVec.x < .55 && mouseVec.x > -.55 && mouseVec.y < .515 && mouseVec.y > .073) {
				doneButton.selectButton(1);
				if (mouse.buttonDown(MouseEvent.BUTTON1)) {
					pause = false;
					return;
				}
			}

			quitButton.selectButton(2);
			if (mouseVec.x < .55 && mouseVec.x > -.55 && mouseVec.y < -.085 && mouseVec.y > -.52) {
				quitButton.selectButton(3);
				if (mouse.buttonDown(MouseEvent.BUTTON1)) {
					levelSelection = true;
					gameplay = false;
					pause = false;
					this.restart();
					bg.shutDownClips();
					return;
				}
			}
		}
	}

	@Override
	protected void updateObjects(float delta) {
		super.updateObjects(delta);
		if (waveStarted) {
			updateWave(delta);
		}
		if (gameplay && !pause) {
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

		if (titleScreen) {
			title.setView(view);
			title.draw(g2d);
		}

		if (levelSelection) {
			level.setView(view);
			level.draw(g2d);
		}
		
		if (deckCreation){
			deckEditor.render(view, g2d);	
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
			g.drawString(String.format("%04d", getWorld().getBoneNum()), 1292, 664);

			// Renders map tile selection or area selection
			if ((selectingArea && grabbedCard == null && !pause) && onBoard(mouseVec))
				renderSelectedArea(g2d, getWorld().getTileLocationAtPosition(mouseVec));
			else if ((selectingTarget || grabbedCard == null && !pause) && onBoard(mouseVec))
				renderSelectedTile(g2d, getWorld().getTileLocationAtPosition(mouseVec));

			// Renders cards
			for (Card card : deck.getHand()) {
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
		
		if (getWorld().isGameover()) {
			gameOverSprite.setView(view);
			gameOverSprite.draw(g2d);
		}
		
		if(pause){
			doneButton.setView(view);
			doneButton.draw(g2d);
			quitButton.setView(view);
			quitButton.draw(g2d);
		}
	}
	
	public void updateWave(float delta) {
		spawnTimer += delta;
		waveTimer += delta;
		float timeBetweenSpawns = 1f / getWorld().getWaveNum();
		if (waveTimer > 20f) { // Time for new wave
			tryNextWave();
		} else if (spawnTimer > timeBetweenSpawns) { // Time to spawn new hero
			Hero hero = heroFactory.getHero(HeroClassType.values()[RANDOM.nextInt(HeroClassType.values().length)], getWorld());
			getWorld().spawnHero(hero);
			spawnTimer = 0f;
		}
	}
	
	public void tryNextWave() {
		if (getWorld().isGameover()) { // Already lost, no continuing
			return;
		}
		for (Unit unit : getWorld().getUnits()) {
			if (unit instanceof Hero) { // Heroes still remain
				return;
			}
		}
		waveTimer = 0f;
		spawnTimer = 0f;
		waveStarted = false;
		getWorld().setWaveNum(getWorld().getWaveNum() + 1);
	}

	public static void main(String[] args) {
		launchApp(new Game());
		
	}

}
