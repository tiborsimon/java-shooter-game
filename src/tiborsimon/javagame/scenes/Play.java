package tiborsimon.javagame.scenes;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;
import tiborsimon.javagame.core.GameManager;

/**
 * Képernyő - játékot megjelenítő osztály. Ebben fut mindegyik játékmód, csak a
 * vezérlő objektumok viselkednek máshogy.
 * 
 * @author Tibor
 * 
 */
public class Play extends BasicGameState {

    private boolean pause;

    private String scoreString = "0000000";

    private String livesString = "• • •";

    private boolean endButton = false;

    private boolean backButton = false;
    
    private int scoreY = 10;

    public Play(int state) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {
        System.out.println("Play State");
        pause = false;

    }

    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        System.out.println("Game Began");
        FieldDirector.sharedFieldDirector().newGame(this);
        DataManager.sharedDataManager().setPlayContainer(this);
        scoreY = GameManager.sharedGameManager().isShowFPS()?26:10;
        pause = false;
        updateScore(0);
    }

    public void updateScore(int score) {
        scoreString = String.format("%013d", score);
    }

    public void updateLives(int lives) {
        switch (lives) {
        case 0:
            livesString = "";
            break;
        case 1:
            livesString = "    o";
            break;
        case 2:
            livesString = "  o o";
            break;
        case 3:
            livesString = "o o o";
            break;

        default:
            break;
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        g.drawImage(new Image("res\\bg.png"), 0, 0);

        /*
         * Image bullet = new Image("res\\bullet.png"); // g.drawImage(bullet,
         * 400, 300);
         * 
         * float deg = FieldDirector.sharedFieldDirector().playerRotation();
         * Circle circle = FieldDirector.sharedFieldDirector().playerCircle();
         * 
         * float r = 29.0f; x =
         * circle.getCenterX()+r*((float)Math.cos(((deg+90)*Math.PI/180))); y =
         * circle.getCenterY()+r*((float)Math.sin(((deg+90)*Math.PI/180)));
         * 
         * bullet.setCenterOfRotation(8, 4); bullet.rotate(deg);
         * g.drawImage(bullet, x, y+r-25);
         */

        FieldDirector.sharedFieldDirector().render(gc, sbg, g);

        g.drawString(scoreString, 10, scoreY);
        g.drawString(livesString, 740, 10);

        if (pause) {
            g.drawImage(new Image("res\\pause.png"), 0, 0);

            if (backButton) {
                g.drawImage(new Image("res\\button-fade.png"), 323, 287);
            }

            if (endButton) {
                g.drawImage(new Image("res\\button-fade.png"), 323, 382);
            }
        }

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {

        FieldDirector.sharedFieldDirector().update(gc, sbg, delta);

        int xPos = Mouse.getX();
        int yPos = Mouse.getY();

        gc.setMouseGrabbed(!pause);

        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            System.out.println("ESC");
            pause = !pause;
            if (pause) {
                FieldDirector.sharedFieldDirector().pause();
            } else {
                FieldDirector.sharedFieldDirector().resume();
            }
        }

        if (pause) {
            // Back
            if ((xPos > 323 && xPos < 475) && (yPos > 234 && yPos < 312)) {
                if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                    backButton = true;
                } else {
                    if (backButton) {
                        pause = false;
                        FieldDirector.sharedFieldDirector().resume();
                        System.out.println("BACK");
                    }
                    backButton = false;
                }
            } else {
                backButton = false;
            }

            // End
            if ((xPos > 323 && xPos < 475) && (yPos > 138 && yPos < 217)) {
                if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                    endButton = true;
                } else {
                    if (endButton) {
                        DataManager.sharedDataManager().gameEnded();
                        GameManager.sharedGameManager().endButton();
                        System.out.println("END");
                    }
                    endButton = false;
                }
            } else {
                endButton = false;
            }
        }

    }

    public int getID() {
        return 4;
    }

}
