package tiborsimon.javagame.scenes;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.GameManager;

/**
 * Képernyő - Játék végét megjelenítő képernyő.
 * 
 * @author Tibor
 * 
 */
public class GameEnded extends BasicGameState {

    private boolean okButton = false;
    
    private String name;
    private String playCount;
    private String summedScore;
    private String lastScore;
    private String enemyKilled;
    private String bulletFired;
    
    public GameEnded(int state) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        System.out.println("GameEnded Screen");
        
    }
    
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        System.out.println("Game Ended");
        container.setMouseGrabbed(false);
        
        name = DataManager.sharedDataManager().playerData.name;
        playCount = "Game played: " + DataManager.sharedDataManager().playerData.playCount;
        lastScore = "Current score: " + DataManager.sharedDataManager().playerData.lastScore;
        summedScore = "Summed score: " + DataManager.sharedDataManager().playerData.summedScore;
        enemyKilled = "Enemy killed: " + DataManager.sharedDataManager().playerData.enemyKilled;
        bulletFired = "Bullet fired: " + DataManager.sharedDataManager().playerData.bulletFired;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        g.drawImage(new Image("res\\game-ended.png"), 0, 0);
        
        if (okButton) {
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
        
        // Ok
        if ((xPos>323 && xPos<475) && (yPos>42 && yPos<121)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                okButton = true;
            } else {
                if (okButton) {
                    GameManager.sharedGameManager().okButton();
                    System.out.println("OK");
                }
                okButton = false;
            }
        } else {
            okButton = false;
        }

    }

    public int getID() {
        return 5;
    }

}
