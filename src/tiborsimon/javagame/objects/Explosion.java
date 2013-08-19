package tiborsimon.javagame.objects;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



/**
 * Robbanási animációt megvalósító osztály. Egy adott helyre kirajzol egy robbanási animációt.
 * @author Tibor
 *
 */
public class Explosion extends BaseObject {
    
    private Animation animation;
	
	private static final float scaleFactor = 2.0f;

	public Explosion(float x, float y) {
		super(x-scaleFactor*80.0f, y-scaleFactor*60.0f, ObjectType.Explosion);
		
		// default boundingBox méretéhez igazítása
		// boundingBox.setSize(scaleFactor*160.0f, scaleFactor*120.0f);
		
		
		// animáció inicializálása
		Image[] frames = new Image[90];
		int[] duration = new int[90];
		
		Random random = new Random();
		float rot = random.nextFloat()*360.0f;

		try {
			for (int i = 0; i < 90; i++) {
				frames[i] = new Image("res\\expl\\explosion_" + i + ".png").getScaledCopy(scaleFactor);
				frames[i].rotate(rot);
				duration[i] = 16;  
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}

		animation = new Animation(frames, duration);
		animation.setLooping(false);
	}
	
	/**
	 * Animáció visszadása.
	 * @return Robbanás animáció.
	 */
	public Animation animation() {
	    return animation;
	}
	
	/**
	 * Egyszer játszódik le az animáció, ha lejárt, az objektum visszakerül a poolba.
	 * @return Igaz, ha vége az animációnak.
	 */
	public boolean isStopped() {
		return animation.isStopped();
	}
	
	/**
	 * A poolból kivéve, meg kell mondani, hogy hol legyen a robbanás, majd újra kell engedélyezni az animációt.
	 * @param x Robbanás középpontjának az X pozíciója.
	 * @param y Robbanás középpontjának az X pozíciója.
	 */
	public void addExplosion(float x, float y) {
		outline.setX(x-scaleFactor*80.0f);
		outline.setY(y-scaleFactor*60.0f);
		animation.restart();
	}

	@Override
	public void collidedWithObject(BaseObject object) {
		// Nincs feladat, a robbanás nem tud ütközni semmivel.
	}

}
