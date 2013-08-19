package tiborsimon.javagame.objects;

import java.util.Random;
// import java.util.logging.logger;

import org.newdawn.slick.geom.Circle;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;

/**
 * Ellenfeleket megvalósító ősosztály, amiből le fognak származni az egyes
 * konkrét ellenféltípusok. Az egyszerű használat érdekében itt történik meg a
 * véletlenszerű mozgásgenerálás, a lövések indítása és a ütközésvizsgálat
 * lekezelése. Az osztály absztekt, nem példányosítható közvetlenül, mert a
 * működéshez szükséges paraméterek alapértelmezésben vannak.
 * 
 * @author Tibor
 * 
 */
public abstract class Enemy extends BaseObject {
    
    // private final static // logger // logger = // logger.get// logger(// logger.GLOBAL_// logger_NAME);

	protected float speedX; // beállítható
	protected float speedY; // beállítható
	protected int toughness; // beállítható
	
	protected float rotation; // véletlenzerű érték -0.4 és 0.4 között

	private final static float DEFAULT_SPEED_X = 1.0f;
	private final static float DEFAULT_SPEED_Y = 1.0f;
	private final static int DEFAULT_TOUGHNESS = 5;

	private Random randomGeneratoRandom;

	public Enemy(float x, float y) {
		// szülő konstruktorának hívása
		super(x, y, ObjectType.Enemy);

		// változók alapértelmezett értékekkel való feltöltése
		speedX = DEFAULT_SPEED_X;
		speedY = DEFAULT_SPEED_Y;
		toughness = DEFAULT_TOUGHNESS;
		
		randomGeneratoRandom = new Random();
		
		rotation = randomGeneratoRandom.nextFloat()*0.8f - 0.4f;
		if (Math.abs(rotation) < 0.15f) {
		    if (rotation>0) {
                rotation += 0.15f;
            } else {
                rotation -= 0.15f;
            }
		}
		

	}
	

	/**
	 * Ellenfelek frissítő metódusa. Feladata a játékos pozíciója alapján meghatározni a mozgás irányát.
	 * 
	 * @param delta
	 *            Legutóbbi frissítés óta eltelt idő.
	 */
	public void updateEnemy(int delta) {
	    Circle pC = FieldDirector.sharedFieldDirector().playerCircle();
	    float angle = FieldDirector.calculateAngleForTwoPoints(outline, pC);
	    
	    
	    float sX = speedX*((float)Math.sin(((-angle)*Math.PI/180)));
        float sY = speedY*((float)Math.cos(((-angle)*Math.PI/180)));
        
        if (sX >= 0) {
            goRightBy(delta*sX, false);
        } else {
            goLeftBy(-delta*sX, false);
        }
        
        if (sY >= 0) {
            goDownBy(delta*sY, false);
        } else {
            goUpBy(-delta*sY, false);
        }
        
        // kép forgatása
        image.rotate(delta*rotation);
	    
	}

	@Override
	public void collidedWithObject(BaseObject object) {
		// ellenfél meghal, ha felfelé haladó lövedék találja el
		if (object.type() == ObjectType.Bullet) {
		    System.out.println("Enemy hit");
		    
		    // logger.severe("Enemy hit, but still alive");
			if (--toughness == 0) {
			    // logger.severe("Enemy died");
			    DataManager.sharedDataManager().enemyKilled();
				FieldDirector.sharedFieldDirector().removeEnemy(this);
				enemyDiedAddScore();
				FieldDirector.sharedFieldDirector().addExplosion(outline.getCenterX(), outline.getCenterY());
				
			}
		} else if (object.type() == ObjectType.Player) {
            System.out.println("Enemy hit");
            
            DataManager.sharedDataManager().enemyKilled();
            FieldDirector.sharedFieldDirector().removeEnemy(this);
            // enemyDiedAddScore();
            FieldDirector.sharedFieldDirector().addExplosion(outline.getCenterX(), outline.getCenterY());
        }
	}
	
	protected abstract void enemyDiedAddScore();
}
