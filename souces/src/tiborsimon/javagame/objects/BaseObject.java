package tiborsimon.javagame.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;


/**
 * Képernyőn megjelenő objektumok őse. Minden szükséges tulajonságot tartalmaz, amit az
 *  ütközésvizsgálathoz el kell végezni.
 * @author Tibor
 *
 */
public abstract class BaseObject implements Collidable {

	protected ObjectType type;
	protected Circle outline;
	protected Image image;
	
	private static final float DEFAULT_RADIUS = 32.0f;
	
	/**
	 * Inicializáló konstruktor. Fontos, hogy nem minden tagváltozó kap használható értéket a hívásakor. Az animation
	 * objektumot később kell inicializálni.
	 * @param x Objektum x koordinátája.
	 * @param y Objektum y koordinátája.
	 * @param t Objektum típusa.
	 */
	public BaseObject(float x, float y, ObjectType t) {
		type = t;
		
		outline = new Circle(0, 0, DEFAULT_RADIUS);
		outline.setX(x);
		outline.setY(y);
		
		// az aniation alapértelmezetten null!
		image = null;
	}
	
	/**
	 * Objektum típusát elkérő függvény.
	 * @return ObjectType érték.
	 */
	public ObjectType type() {
		return type;
	}
	
	/**
	 * Collidable interfész megvalósítása.
	 */
	public Circle outline() {
		return outline;
	}
	
	/**
	 * Collidable interfész megvalósítása.
	 */
	public abstract void collidedWithObject(BaseObject object);
	
	
	/**
	 * Az objektum kirajzolásához ezen a függvényen keresztül lehet elkérni az inimációját.
	 * @return Kirajzolandó animáció.
	 */
	public Image image() {
		return image;
	}
	
	/**
	 * Balra mozgatja az objektumot, miközben vizsgálja, hogy a pálya széléhez ért-e,
	 * vagy elhagyta-e a pályát. Az értesítés típúsa egy paraméterrel állítható.
	 * @param pixels Mennyit mozogjon balra.
	 * @param notifyByTouchingTheEdgeNOTLeavingTheField Mikor adjon értesítést? true: pálya 
	 * szélének érintésekor. false: pálya elhagyásakor (kimegy a képernyőről)
	 * @return Igaz értéket ad, ha a beállított feltétel teljesül.
	 */
	protected boolean goLeftBy(float pixels, boolean notifyByTouchingTheEdgeNOTLeavingTheField) {
		outline.setX(outline.getX()-pixels);
		if (notifyByTouchingTheEdgeNOTLeavingTheField) {
			return outline.getX() <= 0;
		} else {
			return outline.getX()+outline.getWidth() < 0;
		}
	}
	
	/**
	 * Jobbra mozgatja az objektumot, miközben vizsgálja, hogy a pálya széléhez ért-e,
	 * vagy elhagyta-e a pályát. Az értesítés típúsa egy paraméterrel állítható.
	 * @param pixels Mennyit mozogjon balra.
	 * @param notifyByTouchingTheEdgeNOTLeavingTheField Mikor adjon értesítést? true: pálya 
	 * szélének érintésekor. false: pálya elhagyásakor (kimegy a képernyőről)
	 * @return Igaz értéket ad, ha a beállított feltétel teljesül.
	 */
	protected boolean goRightBy(float pixels, boolean notifyByTouchingTheEdgeNOTLeavingTheField) {
		outline.setX(outline.getX()+pixels);
		if (notifyByTouchingTheEdgeNOTLeavingTheField) {
			return outline.getX()+outline.getWidth() >= 800.0f;
		} else {
			return outline.getX() > 800.0f;
		}
	}
	
	/**
	 * Felfelé mozgatja az objektumot, miközben vizsgálja, hogy a pálya széléhez ért-e,
	 * vagy elhagyta-e a pályát. Az értesítés típúsa egy paraméterrel állítható.
	 * @param pixels Mennyit mozogjon balra.
	 * @param notifyByTouchingTheEdgeNOTLeavingTheField Mikor adjon értesítést? true: pálya 
	 * szélének érintésekor. false: pálya elhagyásakor (kimegy a képernyőről)
	 * @return Igaz értéket ad, ha a beállított feltétel teljesül.
	 */
	protected boolean goUpBy(float pixels, boolean notifyByTouchingTheEdgeNOTLeavingTheField) {
		outline.setY(outline.getY()-pixels);
		if (notifyByTouchingTheEdgeNOTLeavingTheField) {
			return outline.getY() <= 0;
		} else {
			return outline.getY()+outline.getHeight() < 0;
		}
	}
	
	/**
	 * Lefelé mozgatja az objektumot, miközben vizsgálja, hogy a pálya széléhez ért-e,
	 * vagy elhagyta-e a pályát. Az értesítés típúsa egy paraméterrel állítható.
	 * @param pixels Mennyit mozogjon balra.
	 * @param notifyByTouchingTheEdgeNOTLeavingTheField Mikor adjon értesítést? true: pálya 
	 * szélének érintésekor. false: pálya elhagyásakor (kimegy a képernyőről)
	 * @return Igaz értéket ad, ha a beállított feltétel teljesül.
	 */
	protected boolean goDownBy(float pixels, boolean notifyByTouchingTheEdgeNOTLeavingTheField) {
		outline.setY(outline.getY()+pixels);
		if (notifyByTouchingTheEdgeNOTLeavingTheField) {
			return outline.getY()+outline.getHeight() >= 600.0f;
		} else {
			return outline.getY() > 600.0f;
		}
	}
	
	/**
	 * Az objektum elforgatásáért felelős metódus. Az aktuális forgatás mértékét nylvántartja további felhasználás céljából.
	 * @param degree Elforgatás mértéke fokban kifejezve
	 */
	protected void rotateImageBy(float degree) {
	    image.rotate(degree);
	}
	
	/**
	 * Objektum élforgatásának a mértékét visszaadtó függvény.
	 * @return Objektum képének szöge, fokban.
	 */
	public float getRotation() {
	    return image.getRotation();
	}
	
	
}
