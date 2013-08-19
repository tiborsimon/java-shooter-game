package tiborsimon.javagame.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;
import tiborsimon.javagame.core.GameLevel;

/**
 * Kis ellenfelet megvalósító osztály. 
 * Az Enemy osztálytól örökölve csupán néhány paramétert állít be.
 * @author Tibor
 *
 */
public class SmallEnemy extends Enemy {
    
    public SmallEnemy(float x, float y) {
        super(x, y);
        
        // ütközési kör méretének beállítása
        outline.setRadius(16.0f);
        
        
        if (FieldDirector.sharedFieldDirector().getGameLevel() == GameLevel.Normal) {
            // sebességek beállítása normál módban
            speedX = 0.08f;
            speedY = 0.08f;
        } else {
            // sebességek beállítása easy és extreme
            speedX = 0.06f;
            speedY = 0.06f;
        }
        
        toughness = 3;
        
        try {
            image = new Image("res\\small-enemy.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void enemyDiedAddScore() {
        DataManager.sharedDataManager().enemyKilled(100);
    }

}
