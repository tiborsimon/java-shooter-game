package tiborsimon.javagame.core;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import tiborsimon.javagame.objects.BaseObject;
import tiborsimon.javagame.objects.Explosion;
import tiborsimon.javagame.objects.ObjectType;
import tiborsimon.javagame.objects.Spark;

/**
 * Aktív objektumok és a háttér kirajzolását elvégző osztály.
 * @author Tibor
 *
 */
public class Renderer {
	
	/**
	 * Objektumokat kirajzoló függvény.
	 * @param objectList Kirajzolandó objektumokat tartalmazó lista.
	 * @param g Grafikát kezelő objektum.
	 */
	public static void renderObjects(ArrayList<BaseObject> objectList, Graphics g) {
		for (BaseObject object : objectList) {
			Image img = object.image();
			Circle circle = object.outline();
			if (object.type() == ObjectType.Explosion) {
			    Animation animation = ((Explosion)object).animation();
                g.drawAnimation(animation, circle.getX(), circle.getY());
            } else if (object.type() == ObjectType.Spark) {
                Animation animation = ((Spark)object).animation();
                g.drawAnimation(animation, circle.getX(), circle.getY());
            } else {
                g.drawImage(img, circle.getX(), circle.getY());
            }
		}
	}

}
