package tiborsimon.javagame.scenes;

// import javax.swing.JFrame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.GameManager;

/**
 * Képernyő - Főképernyőt megjelenítő osztály. Ezzel indul a játék.
 * 
 * @author Tibor
 * 
 */
public class MainScreen extends BasicGameState {
    
    private boolean playButton = false;
    private boolean playerButton = false;
    private boolean scoreButton = false;
    private boolean exitButton = false;
    
    private boolean scoreRequest = false;

    public MainScreen(int state) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        System.out.println("MainScreen State");
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        g.drawImage(new Image("res\\main-screen.png"), 0, 0);
        
        if (playButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 192);
        }
        
        if (playerButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 287);
        }
        
        if (scoreButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 382);
        }
        
        if (exitButton) {
            g.drawImage(new Image("res\\button-fade.png"), 323, 478);
        }
        
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        
        Input input = gc.getInput();
        
        if (scoreRequest) {
            scoreRequest = false;
            // showScores();
        }
        
        // Play
        if ((xPos>323 && xPos<475) && (yPos>327 && yPos<407)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                playButton = true;
            } else {
                if (playButton) {
                    GameManager.sharedGameManager().playButton();
                    System.out.println("PLAY");
                }
                playButton = false;
            }
        } else {
            playButton = false;
        }
        
        // Player
        if ((xPos>323 && xPos<475) && (yPos>234 && yPos<312)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                playerButton = true;
            } else {
                if (playerButton) {
                    GameManager.sharedGameManager().playerButton();
                    System.out.println("PLAYER");
                }
                playerButton = false;
            }
        } else {
            playerButton = false;
        }
        
        // Scores
        if ((xPos>323 && xPos<475) && (yPos>138 && yPos<217)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                scoreButton = true;
            } else {
                if (scoreButton) {
                    scoreRequest = true;
                    System.out.println("SCORES");
                }
                scoreButton = false;
            }
        } else {
            scoreButton = false;
        }
        
        // Exit
        if ((xPos>323 && xPos<475) && (yPos>42 && yPos<121)){
            if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                exitButton = true;
            } else {
                if (exitButton) {
                    System.out.println("EXIT");
                    System.exit(0);
                }
                exitButton = false;
            }
        } else {
            exitButton = false;
        }
        

    }

    /*
    private void showScores() {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame("Scores");
        frame.setVisible(true);
        frame.setSize(300, 600);
        frame.setLocation(200, 100);
        frame.setResizable(false);
    }
    */

    public int getID() {
        return 0;
    }

}
