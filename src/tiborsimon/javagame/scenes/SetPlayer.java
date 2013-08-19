package tiborsimon.javagame.scenes;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.GameManager;

/**
 * Képernyő - Játékost kiválasztó képernyő.
 * 
 * @author Tibor
 * 
 */
public class SetPlayer extends BasicGameState {
    
    private boolean exportButton = false;
    private boolean newButton = false;
    private boolean backButton = false;
    
    private String name;
    private String playCount;
    private String summedScore;
    private String lastScore;
    private String enemyKilled;
    private String bulletFired;

    public SetPlayer(int state) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        System.out.println("SetPlayer State");
        
    }
    
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        System.out.println("Set Player");
        
        name = DataManager.sharedDataManager().playerData.name;
        playCount = "Game played: " + DataManager.sharedDataManager().playerData.playCount;
        lastScore = "Current score: " + DataManager.sharedDataManager().playerData.lastScore;
        summedScore = "Summed score: " + DataManager.sharedDataManager().playerData.summedScore;
        enemyKilled = "Enemy killed: " + DataManager.sharedDataManager().playerData.enemyKilled;
        bulletFired = "Bullet fired: " + DataManager.sharedDataManager().playerData.bulletFired;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        
        g.drawImage(new Image("res\\set-player.png"), 0, 0);
        
        if (exportButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 287);
        }
        
        if (newButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 382);
        }
        
        if (backButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 478);
        }
        
        int xx = 326;
        int yy = 120;
        g.drawString(name, xx, yy);
        g.drawString(playCount, xx, yy+20);
        g.drawString(lastScore, xx, yy+40);
        g.drawString(summedScore, xx, yy+60);
        g.drawString(enemyKilled, xx, yy+80);
        g.drawString(bulletFired, xx, yy+100);

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        
        Input input = gc.getInput();
        
        
        // Export
        if ((xPos>323 && xPos<475) && (yPos>234 && yPos<312)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                exportButton = true;
            } else {
                if (exportButton) {
                    DataManager.sharedDataManager().export();
                    System.out.println("EXPORT");
                }
                exportButton = false;
            }
        } else {
            exportButton = false;
        }
        
        // New
        if ((xPos>323 && xPos<475) && (yPos>138 && yPos<217)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                newButton = true;
            } else {
                if (newButton) {
                    GameManager.sharedGameManager().newButton();
                    System.out.println("NEW");
                }
                newButton = false;
            }
        } else {
            newButton = false;
        }
        
        // Back
        if ((xPos>323 && xPos<475) && (yPos>42 && yPos<121)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                backButton = true;
            } else {
                if (backButton) {
                    GameManager.sharedGameManager().backButton();
                    System.out.println("BACK");
                }
                backButton = false;
            }
        } else {
            backButton = false;
        }
        

    }

    public int getID() {
        return 1;
    }

}