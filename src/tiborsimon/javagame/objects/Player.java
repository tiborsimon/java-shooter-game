package tiborsimon.javagame.objects;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;

/**
 * Játékost megvalósító osztály.
 * 
 * @author Tibor
 * 
 */
public class Player extends BaseObject {
    
	private static final float MOVING_SPEED = 0.21f;
	private static final float ROTATION_SPEED = 0.35f; 
	private int fireCoutner = 0;
	private boolean fireEnabled = true;
	
	private static final int FIRE_RATE = 6;
	
	private int immortalCounter;
	private boolean dead = false;
	
	private boolean autoAim = false;
	private float targetAngle = 0.0f;

	public Player(float x, float y) {	    
		// szülő konstruktorának hívása
		super(x, y, ObjectType.Player);
		
		autoAim = false;
	
		immortalCounter = 0;
		
		dead = false;

		// animáció inicializálása
		try {
            image = new Image("res\\player.png");
        } catch (SlickException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		image.setAlpha(1.0f);
	}

	@Override
	public void collidedWithObject(BaseObject object) {
	    // játékos életet veszít, ha ellenfélnek megy
        if (object.type() == ObjectType.Enemy) {
            System.out.println("HIT");
            if (immortalCounter == 0) {
                immortalCounter = 200;
                // logger.severe("Player HIT!");
                FieldDirector.sharedFieldDirector().playerHit();
                FieldDirector.sharedFieldDirector().addSpark(outline.getCenterX(), outline.getCenterY());
            }
            
            // FieldDirector.sharedFieldDirector().addExplosion(outline.getCenterX(), outline.getCenterY());
        }
	}
	
	/**
	 * Meghívódik, ha a játékos meghal. FEladata, hogy láthatatlanná tegye a játékost, és a helyére betegyen 
	 * egy robbanást.
	 */
	public void playerIsDead() {
	    // logger.severe("Player died");
	    dead = true;
	    FieldDirector.sharedFieldDirector().addExplosion(outline.getCenterX(), outline.getCenterY());
	}
	
	/**
	 * Automatikus célzáshoz használt segédfüggvény. Meghatározza az egységkörön két elforgatási szognek megfelelő
	 * pont közötti távolságot. Trigonomegtrikus függvények használatával kiküszöböljük a forgási szögek 
	 * periodicitásából származó esetlekezeléseket. HA nem hesználnánk ilyeneket, akkor három felé kéne bontani a
	 * az algoritmust: a szög változása nem halad át a 0/360 átmeneten, a szög változása jobbról vagy balról halad át
	 * a 0/360 fokos átmeneten. Feleslegesen elbonyolítaná az algoritmust, és a kvantáltság miatt plusz hibakezelést is
	 * kéne alkalmazni.
	 * @param base     Egyik szög.
	 * @param target   Másik szög.
	 * @return         A szögek által meghatározott két pont távolsága az egységkörön.
	 */
	private float calculateDistance(float base, float target) {
        
	    double sinB = Math.sin((base)*Math.PI/180.0);
	    double cosB = Math.cos((base)*Math.PI/180.0);
	    
	    double sinT = Math.sin((target)*Math.PI/180.0);
        double cosT = Math.cos((target)*Math.PI/180.0);
        
        double ret = Math.sqrt(Math.pow(Math.abs(sinB-sinT),2) + Math.pow(Math.abs(cosB-cosT),2));
        return (float)ret;
	}
	
	/**
	 * Alapvető időzítési funkciók ellátása.
	 * <p>
	 * Célzási algoritmus működése. 
	 * A <pre>calculateDistance(float base, float target)</pre> függvény segítségével kiszámoljuk az aktuális
	 * játékos elforduláshoz tartozó szög és a cél elfordulási szög által meghatározott két pont távolságát. Majd
	 * a kiindulási szöget (base) elforgatjuk egy fokkal jobbra, és újra számolunk egy távolságot. Amennyiben az első
	 * távolság hosszabb, mint a második, tudjuk, hogy balra kell forgatni a szöget, hogy a legrövidebb úton eljussunk
	 * a target szögög. A forgatás leáll, ha a távolság egy határ alá ér. A határtávolság úgy van meghatározva, hogy
	 * a lépésenkénti frissítés és az időkvantáltság miatt ne ugráljon a játékos.
	 * </p>
	 */
	public void playerUpdate(int delta) {
	    
	    if (autoAim) {
	        // forgatási irány meghatározása
	        float base = image.getRotation();
	        
	        float d1 = calculateDistance(base, targetAngle);
	        float d2 = calculateDistance(base-1.0f, targetAngle);
	        
	        // ha nagyobb a két pont távolsága, mint kb 3 foknak megfelelő távolság, forgatunk
	        if (d1 > 0.05f) {
                if (d1 > d2) {
                    turnLeft(delta);
                } else {
                    turnRight(delta);
                }
            }
        }
	    
	    if (dead) {
	        image.setAlpha(0.0f);
        } else {
            immortalCounter--;
            if (immortalCounter < 0) {
                immortalCounter = 0;
            }
            
            if (immortalCounter > 0) {
                image.setAlpha(0.3f);
            } else {
                image.setAlpha(1.0f);
            }
        }
	    
		
	    if (!fireEnabled) {
            if (fireCoutner++ > FIRE_RATE) {
                fireEnabled = true;
                fireCoutner = 0;
            }
        }
		
	}

	/**
	 * Játékos balra mozgatásához használható függvény.
	 * 
	 * @param delta
	 *            Balra mozgás hossza.
	 */
	public void left(int delta) {
		if (goLeftBy(delta * MOVING_SPEED, true)) {
			goRightBy(delta * MOVING_SPEED, true);
			System.out.println("Player touched left border.");
		}
	}

	/**
	 * Játékos jobbra mozgatásához használható függvény.
	 * 
	 * @param delta
	 *            Jobbra mozgás hossza.
	 */
	public void right(int delta) {
		if (goRightBy(delta * MOVING_SPEED, true)) {
			goLeftBy(delta * MOVING_SPEED, true);
			System.out.println("Player touched right border.");
		}
	}
	
	/**
     * Játékos jobbra mozgatásához használható függvény.
     * 
     * @param delta
     *            Jobbra mozgás hossza.
     */
    public void up(int delta) {
        if (goUpBy(delta * MOVING_SPEED, true)) {
            goDownBy(delta * MOVING_SPEED, true);
            System.out.println("Player touched top border.");
        }
    }
    
    /**
     * Játékos jobbra mozgatásához használható függvény.
     * 
     * @param delta
     *            Jobbra mozgás hossza.
     */
    public void down(int delta) {
        if (goDownBy(delta * MOVING_SPEED, true)) {
            goUpBy(delta * MOVING_SPEED, true);
            System.out.println("Player touched bottom border.");
        }
    }

	/**
	 * Játékos lő. extraWeapontól függően vagy egyet vagy kettőt.
	 */
	public void fire() {
	    if (fireEnabled && !dead) {
            fireEnabled = false;
            FieldDirector.sharedFieldDirector().addBullet(outline.getCenterX(),outline.getCenterY(), image.getRotation());
            DataManager.sharedDataManager().bulletFired();
            // System.out.println("Player fire!");
        }
	}
	
	
	/**
	 * Balra forgatja a játékost delta mértékében.
	 */
	public void turnLeft(int delta) {
	    rotateImageBy(-delta*ROTATION_SPEED);
	}
	
	/**
     * Jobbra forgatja a játékost delta mértékében.
     */
	public void turnRight(int delta) {
	    rotateImageBy(delta*ROTATION_SPEED);
	}
	
	/**
	 * Automatikus célzásnál beállítja a célpont koordinátáit. Ha meghívódik, beindítja az automatikus célzási rendszert, 
	 * ami a játékoshoz hasonlóan beállítja a a forgást úgy, hogy a legközelebbi ellenfélre mutasson a célkereszt mindig.
	 * @param targetCircle
	 */
	public void setTargetPosition(Circle targetCircle) {
	    autoAim = true;
        
        targetAngle = FieldDirector.calculateAngleForTwoPoints(outline, targetCircle);
	}

}
