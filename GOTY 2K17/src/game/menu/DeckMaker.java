package game.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import game.TileFramework;
import game.TileWorld;
import game.cards.curses.*;
import game.cards.monsters.*;
import game.cards.traps.*;
import game.cards.Card;
import game.cards.Deck;
import game.sprites.BoundingSprite;
import game.units.monsters.Zombie;
import game.util.Matrix3x3f;
import game.util.RelativeMouseInput;
import game.vectors.AxisAlignedBoundingBox;
import game.vectors.Vector2f;

public class DeckMaker extends BoundingSprite{

	private static BufferedImage spriteSheet;
	
	private Button monsterButton;
	private Button curseButton;
	private Button trapButton;
	private Button doneButton;
	
	public Deck deck;
	
	private BlastCard blastC;
	private FirestormCard firestormC;
	private FlashFreezeCard freezeC;
	private HealCard healC;
	private TouchOfDeathCard todC;
	private TransmuteCard transmuteC;
	
	private BowSkeletonCard bowskelM;
	private CentaurCard centaurM;
	private CerberusCard cerbM;
	private CyclopsCard cyclopsM;
	private DragonCard dragonM;
	private SpiderCard spiderM;
	private SwordSkeletonCard swordskelM;
	private WolfCard wolfM;
	private ZombieCard zombieM;
	
	private BlindTrapCard blindT;
	private FireTrapCard fireT;
	private IceTrapCard iceT;
	private IronMaidenTrapCard ironT;
	private PitfallTrapCard pitfallT;
	private PoisonTrapCard poisonT;
	private StunTrapCard stunT;
	private VulnerableTrapCard vulnT;
	
	private ArrayList<Card> curseCards;
	private ArrayList<Card> monsterCards;
	private ArrayList<Card> trapCards;

	//Select 0 to show monsters, 1 to show curses, 2 to show traps
	private boolean quit;
	
	private Vector2f mouseVec;
	
	ArrayList<Card> tempList = new ArrayList<Card>();

	private int selection;
	static {
		try {
			URL url = Zombie.class.getResource("/resources/menus/deckMaker.png");
			spriteSheet = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public DeckMaker(TileWorld world) {
		super(spriteSheet, 1, 1);		
		mouseVec = new Vector2f();
		
		setOuterBound(new AxisAlignedBoundingBox(
				new Vector2f(-TileFramework.TILE_SIZE_X / 2F, TileFramework.TILE_SIZE_Y / 2F),
				new Vector2f(TileFramework.TILE_SIZE_X / 2F, -TileFramework.TILE_SIZE_Y / 2F)));
		
		monsterButton = new Button(world);
		curseButton = new Button(world);
		trapButton = new Button(world);
		doneButton = new Button(world);
		
		monsterButton.selectButton(4);
		curseButton.selectButton(6);
		trapButton.selectButton(8);
		doneButton.selectButton(0);
		
		doneButton.setLocation(doneButton.getLocation().add(new Vector2f(1,-1.6f)));
		monsterButton.setLocation(monsterButton.getLocation().add(new Vector2f(-2f,1.5f)));
		curseButton.setLocation(curseButton.getLocation().add(new Vector2f(-.5f,1.5f)));
		trapButton.setLocation(trapButton.getLocation().add(new Vector2f(1f,1.5f)));
		
		
		deck = new Deck(world);
		tempList = new ArrayList<Card>();
		//Initialize all curse cards and them to a set
		blastC = new BlastCard(world);
		firestormC = new FirestormCard(world);
		freezeC = new FlashFreezeCard(world);
		healC = new HealCard(world);
		todC = new TouchOfDeathCard(world);
		transmuteC = new TransmuteCard(world);
		
		curseCards = new ArrayList<Card>();
		curseCards.add(blastC);
		curseCards.add(firestormC);
		curseCards.add(freezeC);
		curseCards.add(healC);
		curseCards.add(todC);
		curseCards.add(transmuteC);

		//Initialize all monster cards and add them to a set
		bowskelM = new BowSkeletonCard(world);
		centaurM = new CentaurCard(world);
		cerbM = new CerberusCard(world);
		cyclopsM = new CyclopsCard(world);
		dragonM = new DragonCard(world);
		spiderM = new SpiderCard(world);
		swordskelM = new SwordSkeletonCard(world);
		wolfM = new WolfCard(world);
		zombieM = new ZombieCard(world);
		
		monsterCards = new ArrayList<Card>();
		monsterCards.add(bowskelM);
		monsterCards.add(centaurM);
		monsterCards.add(cerbM);
		monsterCards.add(cyclopsM);
		monsterCards.add(dragonM);
		monsterCards.add(spiderM);
		monsterCards.add(swordskelM);
		monsterCards.add(wolfM);
		monsterCards.add(zombieM);

		//Initialize all trap cards and add them to a set
		blindT = new BlindTrapCard(world);
		fireT = new FireTrapCard(world);
		iceT = new IceTrapCard(world);
		ironT = new IronMaidenTrapCard(world);
		pitfallT = new PitfallTrapCard(world);
		poisonT = new PoisonTrapCard(world);
		stunT = new StunTrapCard(world);
		vulnT = new VulnerableTrapCard(world);
		
		trapCards = new ArrayList<Card>();
		trapCards.add(blindT);
		trapCards.add(fireT);
		trapCards.add(iceT);
		trapCards.add(ironT);
		trapCards.add(pitfallT);
		trapCards.add(poisonT);
		trapCards.add(stunT);
		trapCards.add(vulnT);

		this.selection = 0;
	}
	
	public Deck getDeck(){
		return this.deck;
	}
	
	public void processInput(Vector2f mouseVec, RelativeMouseInput mouse){
		this.mouseVec = mouseVec;
		//THERE IS A BUG SOMEWHERE THAT CAUSES ISSUES WITH THE HIT BOXES OF BUTTONS, NOBODY COULD SOLVE IT SO VALUES ARE HARD CODED
		if(mouseVec.x > -2.56 && mouseVec.x < -1.44 && mouseVec.y < 1.7 && mouseVec.y > 1.29){
			monsterButton.selectButton(5);
			if (mouse.buttonDown(MouseEvent.BUTTON1)) selection = 0;
		}
		else monsterButton.selectButton(4);
		
		if(mouseVec.x > -1.06 && mouseVec.x < .048 && mouseVec.y < 1.7 && mouseVec.y > 1.29){
			curseButton.selectButton(7);
			if (mouse.buttonDown(MouseEvent.BUTTON1)) selection = 1;
		}
		else curseButton.selectButton(6);
		
		if(mouseVec.x > .44 && mouseVec.x < 1.54 && mouseVec.y < 1.7 && mouseVec.y > 1.29){
			trapButton.selectButton(9);
			if (mouse.buttonDown(MouseEvent.BUTTON1)) selection = 2;
		}
		else trapButton.selectButton(8);
		
		if(mouseVec.x > .44 && mouseVec.x < 1.54 && mouseVec.y < -1.4 && mouseVec.y > -1.8){
			doneButton.selectButton(1);
			if (mouse.buttonDown(MouseEvent.BUTTON1)) quit = true;
		}
		else doneButton.selectButton(0);
		
		for(int i = 0; i < tempList.size(); i++){
			Card card = tempList.get(i);
			if(card.isPointWithin(mouseVec)){
				if(mouse.buttonDownOnce(MouseEvent.BUTTON1)){
					deck.addCard(card.copy(card));
				}
			}
		}
		
		
		int removedIndex = -1;
		int yPos = 60;
		for(int i = 0; i < deck.getDeck().size(); i++){
			float topY = 2.1f - (1/240f) * yPos + .02f;
			if(mouseVec.x > 2.79 && mouseVec.x < 2.87 && mouseVec.y < topY && mouseVec.y > topY - .04f){
				if(mouse.buttonDownOnce(MouseEvent.BUTTON1)){
					removedIndex = i;
					break;
				}
			}
			yPos+=45;
			if(i % 2 == 0) yPos++;
		}
		if(removedIndex != -1)  deck.removeCard(removedIndex);
		
	}
	
	public void render(Matrix3x3f view, Graphics2D g2d){
		this.setView(view);
		this.draw(g2d);
		
		doneButton.setView(view);
		doneButton.draw(g2d);
		
		monsterButton.setView(view);
		monsterButton.draw(g2d);
		
		curseButton.setView(view);
		curseButton.draw(g2d);
		
		trapButton.setView(view);
		trapButton.draw(g2d);
		
		tempList.clear();
		if(selection == 0) tempList.addAll(monsterCards);
		else if(selection == 1) tempList.addAll(curseCards);
		else if(selection == 2) tempList.addAll(trapCards);
		
		float rowSize = .7f;
		for(int i = 0; i < tempList.size(); i++){
			Card card = tempList.get(i);
			int index = tempList.indexOf(card);
			if (index >= 5)	index -= 5;

			if(tempList.indexOf(card)== 5) rowSize *= -1;
			
			card.setLocation(new Vector2f(-2.85f + index * 1f, .5f +  rowSize));
			card.setView(view);
			card.draw(g2d);
			
			if(card.isPointWithin(mouseVec)){
				card.getOuterBound().render(g2d);
			}	
		}
		
		int yPos = 60;
		for(int i = 0; i < deck.getDeck().size(); i++){
			Card card = deck.getDeck().get(i);
			//Needs to move 45.5 up each time, so 45 then 46 I guess
			if(card instanceof MonsterSpawnCard) g2d.setColor(Color.ORANGE);
			if(card instanceof CurseCard) g2d.setColor(Color.CYAN);
			if(card instanceof TrapSpawnCard) g2d.setColor(Color.RED);

			g2d.setFont(new Font("Cooper Black", Font.PLAIN, 22));
			g2d.drawString(card.getName(), 1222, yPos);
			
			Button deleteX;
			BufferedImage image = null;
			try {
				URL url = Zombie.class.getResource("/resources/menus/buttons/deleteButton.png");
				image = ImageIO.read(url);
			} catch (Exception e) {
				System.exit(1);
			}
			deleteX = new Button(image);

			deleteX.setLocation(new Vector2f(2.83f, 2.1f - (1/240f) * yPos));
			deleteX.setView(view);
			deleteX.draw(g2d);
			
			yPos+=45;
			if(deck.getDeck().indexOf(card) % 2 == 0) yPos++;
		}
		
	}
	
	public boolean getQuit(){
		return quit;
	}
	
	public void setQuit(boolean quit){
		this.quit = quit;
	}
}
