package tiborsimon.javagame.scenes;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.FieldDirector;
import tiborsimon.javagame.core.GameLevel;
import tiborsimon.javagame.core.GameManager;

/**
 * Képernyő - Nehézségi szinet kiválasztó képernyő.
 * 
 * @author Tibor
 * 
 */
public class LevelSelect extends BasicGameState {
    
    private boolean easyButton = false;
    private boolean normalButton = false;
    private boolean expertButton = false;
    private boolean backButton = false;
    
    public LevelSelect(int state) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        System.out.println("MainScreen State");
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        g.drawImage(new Image("res\\level-select.png"), 0, 0);
        
        if (easyButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 192);
        }
        
        if (normalButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 287);
        }
        
        if (expertButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 382);
        }
        
        if (backButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 478);
        }
        
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        
        Input input = gc.getInput();
        
        // Easy
        if ((xPos>323 && xPos<475) && (yPos>327 && yPos<407)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                easyButton = true;
            } else {
                if (easyButton) {
                    FieldDirector.sharedFieldDirector().setGameLevel(GameLevel.Easy);
                    GameManager.sharedGameManager().playButton();
                    System.out.println("EASY");
                }
                easyButton = false;
            }
        } else {
            easyButton = false;
        }
        
        // Normal
        if ((xPos>323 && xPos<475) && (yPos>234 && yPos<312)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                normalButton = true;
            } else {
                if (normalButton) {
                    FieldDirector.sharedFieldDirector().setGameLevel(GameLevel.Normal);
                    GameManager.sharedGameManager().playButton();
                    System.out.println("NORMAL");
                }
                normalButton = false;
            }
        } else {
            normalButton = false;
        }
        
        // Expert
        if ((xPos>323 && xPos<475) && (yPos>138 && yPos<217)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                expertButton = true;
            } else {
                if (expertButton) {
                    FieldDirector.sharedFieldDirector().setGameLevel(GameLevel.Extrem);
                    GameManager.sharedGameManager().playButton();
                    System.out.println("EXPERT");
                }
                expertButton = false;
            }
        } else {
            expertButton = false;
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
        return 3;
    }

}
