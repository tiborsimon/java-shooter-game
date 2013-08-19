package tiborsimon.javagame.objects;

import org.newdawn.slick.geom.Circle;


/**
 * Interfész az egységesen kezelhető ütközésvizsgálathoz. Minden objektumnek, ami szerepelni szeretne az 
 * ütközésvizsgálatban, meg kell valósítani ezt az interfészt.
 * @author Tibor
 *
 */
public interface Collidable {
	
	/**
	 * Ütközésvizsgálatnál a függvény által visszaadott befoglaló téglalap alapján fut az ütközésvizsgáló algoritmus.
	 */
	public Circle outline();
	
	/**
	 * Ha egy objektum ütközik egy másikkal, akkor mindkét objektumban meghívódik z a függvény, paraméterben átadva
	 * a másik objektumot, ami alapján eldönthető a szükséges további viselkedés.
	 * @param object
	 */
	public void collidedWithObject(BaseObject object);
}
