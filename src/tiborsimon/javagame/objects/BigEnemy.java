package tiborsimon.javagame.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;
import tiborsimon.javagame.core.GameLevel;

/**
 * Nagy ellenfelet megvalósító osztály.
 * Az Enemy osztálytól örökölve csupán néhány paramétert állít be. Ha meghal, betesz a helyére három kicsi ellenfelet.
 * @author Tibor
 *
 */
public class BigEnemy extends Enemy {
    
    public BigEnemy(float x, float y) {
        super(x, y);
        
        // ütközési kör méretének beállítása
        outline.setRadius(48.0f);
        
        
        if (FieldDirector.sharedFieldDirector().getGameLevel() == GameLevel.Normal) {
            // sebességek beállítása normál módban
         // sebességek beállítása
            speedX = 0.03f;
            speedY = 0.03f;
        } else {
            // sebességek beállítása easy és extreme
            speedX = 0.015f;
            speedY = 0.015f;
        }
        
        
        toughness = 20;
        
        try {
            image = new Image("res\\big-enemy.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void enemyDiedAddScore() {
        // TODO Auto-generated method stub
        float x = outline.getX()+48;
        float y = outline.getY()+48;
        float offset = 35.0f;
        
        FieldDirector.sharedFieldDirector().addEnemy(x+offset, y, EnemyType.Small);
        FieldDirector.sharedFieldDirector().addEnemy(x-offset, y, EnemyType.Small);
        FieldDirector.sharedFieldDirector().addEnemy(x, y+offset, EnemyType.Small);
        FieldDirector.sharedFieldDirector().addEnemy(x, y-offset, EnemyType.Small);
        
        DataManager.sharedDataManager().enemyKilled(500);
    }

}
