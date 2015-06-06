package tiborsimon.javagame.core;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;

import tiborsimon.javagame.objects.BaseObject;

/**
 * Ütközésvizsgálat hatékony lefuttatásáért felelős osztály.
 * @author Tibor
 *
 */
public class CollisionDetector {
	/**
	 * Statikus tagfüggvény az ütközésvizsgálat lebonyolításához.
	 */
	static void runCollisionDetection(ArrayList<BaseObject> objectList) {
		// TODO János
		int length = objectList.size();
		for (int i = 0; i < length; i++) {
			for (int j = i+1; j < length; j++) {
				BaseObject object1 = objectList.get(i);
				BaseObject object2 = objectList.get(j);
				
				Circle rect1 = object1.outline();
				Circle rect2 = object2.outline();
				
				/*System.out.println("rect1: " + rect1.getX() + " " + rect1.getY() + " " +  rect1.getWidth() + " " +  rect1.getHeight());
				System.out.println("rect2: " + rect2.getX() + " " + rect2.getY() + " " +  rect2.getWidth() + " " +  rect2.getHeight());
				System.out.println();*/
				
				if (rect1.intersects(rect2)) {
					// System.out.println("!!!!!!!");
					object1.collidedWithObject(object2);
					object2.collidedWithObject(object1);
				}
				
			}
		}
	}
}
