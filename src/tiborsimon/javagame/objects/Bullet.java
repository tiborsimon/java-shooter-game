package tiborsimon.javagame.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;

/**
 * Lövedésket megvalósító osztály.
 * 
 * @author Tibor
 * 
 */
public class Bullet extends BaseObject {

	private boolean isDirectionUp;
	private float speedX,speedY;

	/**
	 * Lövedék konstruktora.
	 * 
	 * @param x
	 *            X koordináta.
	 * @param y
	 *            Y koordináta.
	 * @param deg
	 *            Lövedék forgatása.
	 */
	public Bullet(float x, float y, float deg) {
		super(x, y, ObjectType.Bullet);
		
		
		// System.out.println(deg);
		speedX = 0.6f*((float)Math.sin(((-deg)*Math.PI/180)));
		speedY = 0.6f*((float)Math.cos(((-deg)*Math.PI/180)));
		
		// System.out.println(speedX);
		// System.out.println(speedY);
		
		outline.setRadius(2.0f);

		try {
            image = new Image("res\\bullet.png");
        } catch (SlickException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		image.setCenterOfRotation(8, 4);
		image.rotate(deg);
	}

	/**
	 * Lövedék pozícióját frissítő függvény. Igaz értékkel tér vissza, ha
	 * elhagyta a pályát, ezzel lehet tudni, hogy mikor kell kiszedni a
	 * listából.
	 * 
	 * @param delta
	 *            Előző hívás óta eltelt idő.
	 * @return Igaz, ha a lövedék elhagyta a pályát.
	 */
	public boolean updateBullet(int delta) {
	    Boolean ret = false;
		if (speedX >= 0) {
		    ret |= goRightBy(delta*speedX, false);
        } else {
            ret |= goLeftBy(-delta*speedX, false);
        }
		
		if (speedY >= 0) {
		    ret |= goDownBy(delta*speedY, false);
        } else {
            ret |=  goUpBy(-delta*speedY, false);
        }
		
		return ret;
	}

	/**
	 * Visszaadja a haladási irányt.
	 * 
	 * @return Igaz, ha felfelé halad, hamis, ha lefelé halad.
	 */
	public boolean direction() {
		return isDirectionUp;
	}

	@Override
	public void collidedWithObject(BaseObject object) {
	    // System.out.println("BULLET");
	    
		// megszűnik, ha felfelé megy a lövés, és ellenfelet talált
		if (object.type() == ObjectType.Enemy) {
		    DataManager.sharedDataManager().enemyKilled(1);
		    FieldDirector.sharedFieldDirector().addSpark(outline.getCenterX(), outline.getCenterY());
			FieldDirector.sharedFieldDirector().removeBullet(this);
		}
		
	}

}
