package tiborsimon.javagame.scenes;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.GameManager;
import tiborsimon.javagame.ui.TextField;

/**
 * Képernyő - Új játékost létrehozó képernyő.
 * 
 * @author Tibor
 * 
 */
public class NewPlayer extends BasicGameState {
    
    private boolean newButton = false;
    private boolean loadButton = false;
    private boolean backButton = false;
    
    private TextField textField;

    public NewPlayer(int state) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        System.out.println("NewPlayer State");
        textField = new TextField(200, 100);
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        
        g.drawImage(new Image("res\\new-player.png"), 0, 0);
        
        if (newButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 287);
        }
        
        if (loadButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 382);
        }
        
        if (backButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 478);
        }
        
        textField.render(g);

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        
        Input input = gc.getInput();
        
        
        // New
        if ((xPos>323 && xPos<475) && (yPos>234 && yPos<312)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                newButton = true;
            } else {
                if (newButton) {
                    if (textField.getText() != "") {
                        DataManager.sharedDataManager().newPlayer(textField.getText());
                        GameManager.sharedGameManager().newButton();
                    }
                    System.out.println("NEW");
                }
                newButton = false;
            }
        } else {
            newButton = false;
        }
        
        // Load
        if ((xPos>323 && xPos<475) && (yPos>138 && yPos<217)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                loadButton = true;
            } else {
                if (loadButton) {
                    DataManager.sharedDataManager().load();
                    System.out.println("LOAD");
                    GameManager.sharedGameManager().loadButton();
                }
                loadButton = false;
            }
        } else {
            loadButton = false;
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
        
        boolean activate = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
        textField.update(input, activate, xPos, yPos);

    }

    public int getID() {
        return 2;
    }

}
