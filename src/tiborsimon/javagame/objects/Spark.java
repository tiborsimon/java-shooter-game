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
public class Spark extends BaseObject {
    
    private Animation animation;
    
    private static final float scaleFactor = 2.0f;

    public Spark(float x, float y) {
        super(x-50*scaleFactor, y-50*scaleFactor, ObjectType.Spark);
        
        // default boundingBox méretéhez igazítása
        // boundingBox.setSize(scaleFactor*160.0f, scaleFactor*120.0f);
        
        
        // animáció inicializálása
        Image[] frames = new Image[4];
        int[] duration = new int[4];
        
        Random random = new Random();
        float rot = random.nextFloat()*360.0f;

        try {
            for (int i = 0; i < 4; i++) {
                frames[i] = new Image("res\\spark\\spark-" + i + ".png").getScaledCopy(scaleFactor);
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
    public void addSpark(float x, float y) {
        outline.setX(x-50*scaleFactor);
        outline.setY(y-50*scaleFactor);
        animation.restart();
    }

    @Override
    public void collidedWithObject(BaseObject object) {
        // Nincs feladat, a robbanás nem tud ütközni semmivel.
    }

}
