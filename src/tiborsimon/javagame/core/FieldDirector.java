package tiborsimon.javagame.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;

import tiborsimon.javagame.objects.BaseObject;
import tiborsimon.javagame.objects.BigEnemy;
import tiborsimon.javagame.objects.Bullet;
import tiborsimon.javagame.objects.Enemy;
import tiborsimon.javagame.objects.EnemyType;
import tiborsimon.javagame.objects.Explosion;
import tiborsimon.javagame.objects.Player;
import tiborsimon.javagame.objects.SmallEnemy;
import tiborsimon.javagame.objects.Spark;
import tiborsimon.javagame.scenes.Play;

/**
 * A játék adatszerkezetét megvalósító singleton osztály. Ő kezeli a képernyőn
 * megjelenő objektumokat, végzi az ütközésviszgálatot, ütemezi a kirajzolást.
 * 
 * @author Tibor
 * 
 */
public class FieldDirector {
    
    // singleton viselkedéshez a statikus változó
    private static FieldDirector fieldDirectorInstance = null;

    private boolean paused;

    private int lives;

    private Player player;

    private ArrayList<BaseObject> objectList;

    private ArrayList<Bullet> bulletList;
    private ArrayList<Bullet> removeableBulletList;

    private ArrayList<Enemy> enemyList;
    private ArrayList<Enemy> removeableEnemyList;

    private Queue<Explosion> explosionPool;
    private ArrayList<Explosion> explosionList;
    private ArrayList<Explosion> removeableExplosionList;
    
    private Queue<Spark> sparkPool;
    private ArrayList<Spark> sparkList;
    private ArrayList<Spark> removeableSparkList;

    private Random randomGenerator;

    private int enemySummonCounter;

    private Play playIsnstance;

    private GameLevel gameLevel;

    private boolean autoAim = true;

    private Enemy targetEnemy = null;
    
    private int deadCounter = 0;

    /**
     * Egyszer meghívandó konstruktor, amit a main függvényben kell mehívni.
     * Paraméterként átadandó neki a játék képernyőit kezelni képes objektum.
     */
    public FieldDirector() {
        System.out.println("FieldDirector initialized.");
        fieldDirectorInstance = this;

        paused = false;

        randomGenerator = new Random();

        enemySummonCounter = randomGenerator.nextInt(200);

        objectList = new ArrayList<BaseObject>();

        bulletList = new ArrayList<Bullet>();
        removeableBulletList = new ArrayList<Bullet>();

        enemyList = new ArrayList<Enemy>();
        removeableEnemyList = new ArrayList<Enemy>();

        explosionPool = new LinkedList<Explosion>();
        explosionList = new ArrayList<Explosion>();
        removeableExplosionList = new ArrayList<Explosion>();
        
        sparkPool = new LinkedList<Spark>();
        sparkList = new ArrayList<Spark>();
        removeableSparkList = new ArrayList<Spark>();
    }

    /**
     * Singleton viselkedés megvalósításához a bárhonnan elérhető statikus
     * tagfüggvény, ami visszaadja a singleton objektumot.
     * 
     * @return GameManager objektum.
     */
    public static FieldDirector sharedFieldDirector() {
        if (fieldDirectorInstance != null /*
                                           * && GameManager.sharedGameManager().
                                           * isPlayInProgress()
                                           */) {
            return fieldDirectorInstance;
        }
        System.out
                .println("Error uninitialized FieldDirector! Program terminated!");
        System.exit(0);
        return null;
    }

    /**
     * A játék beindítása előtt meghívandó függvény, ami beállítja az indítandó
     * játék nehézségét.
     * 
     * @param gameLevel
     *            Játék nehézsége. (Easy, Normal vagy Expert).
     */
    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }
    
    /**
     * A játék nehézségét adja vissza. A játék indítása után szabadon hívható.
     * @return Játék nehézsége.
     */
    public GameLevel getGameLevel() {
        return gameLevel;
    }

    /**
     * Új játék indításakor elvégzi a szükséges inicializálásokat.
     */
    public void newGame(Play pI) {
        paused = false;

        // játékot tartalmazó objektum referenciája. Élet és pont kiírásához
        // lesz felhasználva.
        playIsnstance = pI;

        // DataManager inicializálása
        DataManager.sharedDataManager().newGame();

        // életek inicializálása
        lives = 3;
        updateLives();

        // clean object lists
        objectList.clear();

        bulletList.clear();
        removeableBulletList.clear();

        enemyList.clear();
        removeableEnemyList.clear();

        explosionList.clear();
        removeableExplosionList.clear();
        explosionPool.clear();
        int explosionPoolLimit = GameManager.sharedGameManager().getExplosionPoolLimit();
        for (int i = 0; i < explosionPoolLimit; i++) {
            explosionPool.add(new Explosion(0, 0));
        }
        
        sparkList.clear();
        removeableSparkList.clear();
        sparkPool.clear();
        int sparkPoolLimit = GameManager.sharedGameManager().getSparkPoolLimit();
        for (int i = 0; i < sparkPoolLimit; i++) {
            sparkPool.add(new Spark(0, 0));
        }

        // add player
        player = new Player(368, 268);
        objectList.add(player);

        // System.out.println("objectList: " + objectList);
        if (gameLevel == GameLevel.Extrem) {
            autoAim = false;
        } else {
            autoAim = true;
        }
    }

    /**
     * Játék megállítása.
     */
    public void pause() {
        paused = true;
    }

    /**
     * játék újraindítása.
     */
    public void resume() {
        paused = false;
    }

    /**
     * Objektumok kirajzolásának indítása.
     * 
     * @param gc
     *            Játékot tartalmazó GameContainer.
     * @param sbg
     *            Játék fő objektuma.
     * @param g
     *            Grafikát kezelő objektum.
     * @throws SlickException
     */
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        Renderer.renderObjects(objectList, g);
    }

    /**
     * Objektumok frissítését szolgáló függvény.
     * 
     * @param gc
     *            Játékot tartalmazó GameContainer.
     * @param sbg
     *            Játék fő objektuma.
     * @param delta
     *            Legutóbbi frissítés óta eltelt idő.
     * @throws SlickException
     */
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {
        
        if (paused) {
            return;
        }
        
        if (deadCounter > 0) {
            if (deadCounter++ == 100) {
                System.out.println("GAME OVER");
                GameManager.sharedGameManager().gameOver();
                DataManager.sharedDataManager().gameEnded();
            }
        }

        // check input for player
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            player.left(delta);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.right(delta);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            player.up(delta);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            player.down(delta);
        }
        if (input.isKeyDown(Input.KEY_SPACE)) {
            player.fire();
        }

        // Ha automatikus célzás van, akkor nem lehet kézzel forgatni a
        // játékost!
        if (autoAim) {
            findClosestEnemy();

            if (targetEnemy != null) {
                player.setTargetPosition(targetCircle());
            }
        } else {
            if (input.isKeyDown(Input.KEY_T)) {
                player.turnLeft(delta);
            }
            if (input.isKeyDown(Input.KEY_H)) {
                player.turnRight(delta);
            }
        }

        // enemy berakás
        // TODO szofisztikáltabb idézési algoritmus
        if (--enemySummonCounter == 0) {
            enemySummonCounter = randomGenerator.nextInt(100) + 110;
            if (randomGenerator.nextBoolean()) {
                summonEnemy(EnemyType.Big);
            } else {
                summonEnemy(EnemyType.Small);
            }

        }

        // player update
        if (player != null) {
            player.playerUpdate(delta);
        }

        // bullet update
        if (bulletList.size() > 0) {
            bulletUpdate(delta);
        }

        // enemy update
        if (enemyList.size() > 0) {
            enemyUpdate(delta);
        }

        // explosion update
        if (explosionList.size() > 0) {
            explosionUpdate(delta);
        }
        
        // spark update
        if (sparkList.size() > 0) {
            sparkUpdate(delta);
        }

        // ütközésvizsgálat
        CollisionDetector.runCollisionDetection(objectList);

    }

    /**
     * Lövedékeket frissítő függvény. Meghívódik, ha van lövedék a pályán. Átfut
     * a lövedékek listáján, eltárolja azokat a lövedékeket, amik kifutottak a
     * pályáról, majd a végén kiszedi őket a listából.
     * 
     * @param delta
     *            Legutóbbi frissítés óta eltelt idő.
     */
    private void bulletUpdate(int delta) {
        for (Bullet bullet : bulletList) {
            if (bullet.updateBullet(delta)) {
                removeableBulletList.add(bullet);
            }
        }
        if (removeableBulletList.size() > 0) {
            bulletList.removeAll(removeableBulletList);
            objectList.removeAll(removeableBulletList);
            removeableBulletList.clear();
        }
    }

    /**
     * Lövedék hozzáadása a rendszerhez. Meghatározott koordinátára és irányba
     * teszi be a lövedéket, amit a játékos helyzetéből és forgási szögéből
     * számol. A számolás után bekerül a alövedék a rendszerbe, amjd elindul az
     * általa kiszámított irányba.
     * 
     * @param x
     *            Játékos körét befoglaló nyényzet sarkának x koordinátája.
     * @param y
     *            Játékos körét befoglaló nyényzet sarkának y koordinátája.
     * @param deg
     *            Játékos forgási szöge.
     */
    public void addBullet(float x, float y, float deg) {
        float r = 29.0f;
        float xI = x + r * ((float) Math.cos(((deg + 90) * Math.PI / 180)));
        float yI = y + r * ((float) Math.sin(((deg + 90) * Math.PI / 180))) + r
                - 25;

        Bullet bullet = new Bullet(xI, yI, deg);
        bulletList.add(bullet);
        objectList.add(bullet);
        // System.out.println("bulletList: " + bulletList);
    }

    /**
     * Kiveszi az adott lövedéket a rendszerből.
     * 
     * @param bullet
     *            Megszüntetni kívánt lövedék.
     */
    public void removeBullet(Bullet bullet) {
        removeableBulletList.add(bullet);
    }

    /**
     * Ellenfeleket frissítő függvény. MEghívódik, ha van ellenfél a
     * rendszerben. Sorban meghívja a listában lévő ellenfelek frissítő
     * metódusait, majd ha van megszüntetendő ellenfél, megszünteti azokat.
     * 
     * @param delta
     *            Legutóbbi frissítés óta eltelt idő.
     */
    private void enemyUpdate(int delta) {
        for (Enemy enemy : enemyList) {
            enemy.updateEnemy(delta);
        }
        if (removeableEnemyList.size() > 0) {
            enemyList.removeAll(removeableEnemyList);
            objectList.removeAll(removeableEnemyList);
            removeableEnemyList.clear();
        }
    }

    /**
     * Ellenfél hozzáadása a rendszerhez.
     * 
     * @param x
     *            X koordináta.
     * @param enemyType
     *            Ellenfél típusát meghatározó paraméter.
     */
    public void addEnemy(float x, float y, EnemyType enemyType) {
        Enemy enemy = null;

        switch (enemyType) {
        case Small:
            enemy = new SmallEnemy(x, y);
            break;
        case Big:
            enemy = new BigEnemy(x, y);
            break;

        default:
            break;
        }

        enemyList.add(enemy);
        objectList.add(enemy);
        System.out.println("Enemy added");
    }

    /**
     * Kiveszi az adott ellenfelet a rendszerből.
     * 
     * @param enemy
     *            Megszüntetni kívánt ellenfelet.
     */
    public void removeEnemy(Enemy enemy) {
        removeableEnemyList.add(enemy);
    }

    /**
     * Adott típusú ellenfél idézése véletlen helyre.
     * 
     * @param type
     *            Ellenfél tíőusa.
     */
    private void summonEnemy(EnemyType type) {
        // dötsük el, hogy felül, alul, jobbra vagy balra tegyük az ellenfelet.
        int side = randomGenerator.nextInt(4);

        float X = 0.0f;
        float Y = 0.0f;
        
        float baseLeft = 0.0f;
        float baseTop = 0.0f;
        float baseRight = 0.0f;
        float baseBottom = 0.0f;
        
        if (type == EnemyType.Big) {
            baseLeft = -120.0f;
            baseTop = -120.0f;
            baseRight = 800.0f;
            baseBottom = 600.0f;
        } else {
            baseLeft = -50.0f;
            baseTop = -50.0f;
            baseRight = 800.0f;
            baseBottom = 600.0f;
        }

        // felül
        if (side == 0) {
            Y = baseTop;
            X = 800.0f * randomGenerator.nextFloat();
        } else
        // jobb
        if (side == 1) {
            X = baseRight;
            Y = 600.0f * randomGenerator.nextFloat();
        } else
        // alul
        if (side == 2) {
            Y = baseBottom;
            X = 800.0f * randomGenerator.nextFloat();
        } else
        // bal
        if (side == 3) {
            X = baseLeft;
            Y = 600.0f * randomGenerator.nextFloat();
        }

        addEnemy(X, Y, type);
    }

   

    /**
     * Robbanást frissítő függvény. Meghívódik, ha van robbanás a pályán. Átfut
     * a lövedékek listáján, eltárolja azokat a lövedékeket, amik kifutottak a
     * pályáról, majd a végén kiszedi őket a listából.
     * 
     * @param delta
     *            Legutóbbi frissítés óta eltelt idő.
     */
    private void explosionUpdate(int delta) {
        for (Explosion explosion : explosionList) {
            if (explosion.isStopped()) {
                removeableExplosionList.add(explosion);
            }
        }
        if (removeableExplosionList.size() > 0) {
            explosionList.removeAll(removeableExplosionList);
            objectList.removeAll(removeableExplosionList);
            explosionPool.addAll(removeableExplosionList);
            removeableExplosionList.clear();
        }

    }

    /**
     * Robbanás hozzáadása a rendszerhez. MEghatározott koordinátára és irányba
     * teszi be a lövedéket, ami magától elindul.
     * 
     * @param x
     *            X koordináta.
     * @param y
     *            Y koordináta.
     */
    public void addExplosion(float x, float y) {
        
        if (explosionPool.size() > 0) {
            Explosion explosion = explosionPool.poll();
            explosion.addExplosion(x, y);
            explosionList.add(explosion);
            objectList.add(explosion);
        } else {
            System.out
                    .println("ERROR! ExplosionPool is empty! Program terminated!");
            System.exit(0);
        }

    }

    /**
     * Kiveszi az adott szikra a rendszerből.
     * 
     * @param spark
     *            Megszüntetni kívánt szikra animáció.
     */
    public void removeSpark(Spark spark) {
        removeableSparkList.add(spark);
    }
    
    
    
    /**
     * Szikra frissítő függvény. Meghívódik, ha van robbanás a pályán. Átfut
     * a lövedékek listáján, eltárolja azokat a lövedékeket, amik kifutottak a
     * pályáról, majd a végén kiszedi őket a listából.
     * 
     * @param delta
     *            Legutóbbi frissítés óta eltelt idő.
     */
    private void sparkUpdate(int delta) {
        for (Spark spark : sparkList) {
            if (spark.isStopped()) {
                removeableSparkList.add(spark);
            }
        }
        if (removeableSparkList.size() > 0) {
            sparkList.removeAll(removeableSparkList);
            objectList.removeAll(removeableSparkList);
            sparkPool.addAll(removeableSparkList);
            removeableSparkList.clear();
        }

    }

    /**
     * Szikra hozzáadása a rendszerhez.
     * 
     * @param x
     *            X koordináta.
     * @param y
     *            Y koordináta.
     */
    public void addSpark(float x, float y) {
        
        if (sparkPool.size() > 0) {
            Spark spark = sparkPool.poll();
            spark.addSpark(x, y);
            sparkList.add(spark);
            objectList.add(spark);
        } else {
            System.out
                    .println("ERROR! SparkPool is empty! Program terminated!");
            System.exit(0);
        }

    }

    /**
     * Kiveszi az adott robbanást a rendszerből.
     * 
     * @param explosion
     *            Megszüntetni kívánt robbanási animáció.
     */
    public void removeExplosion(Explosion explosion) {
        removeableExplosionList.add(explosion);
    }

    /**
     * DataManager hívja ezt a függgvényt, mikor változás történt a pontokban.
     * 
     * @param newScore
     *            Új pont kijelzésre.
     */
    public void updateScore(int newScore) {
        playIsnstance.updateScore(newScore);
    }

    /**
     * Akkor hívható ez a függvény, ha változás történt a kijelzendő életekben.
     * A FieldDirector privát változóját használja.
     */
    public void updateLives() {
        playIsnstance.updateLives(lives);
    }

    public void addLife() {
        lives++;
        updateLives();
    }

    public void playerHit() {
        if (--lives == 0) {
            deadCounter = 1;
            updateLives();
            player.playerIsDead();
        } else {
            updateLives();
        }
    }

    /**
     * Játékos szögét visszaadó függvény.
     * 
     * @return Forgás szöge fokban.
     */
    public float playerRotation() {
        return player.getRotation();
    }

    /**
     * Játékos ütközési körét visszadó függvény.
     * 
     * @return Játékos ütközési köre.
     */
    public Circle playerCircle() {
        return player.outline();
    }

    /**
     * Kiválasztott ellenfél körét visszaadó függvény.
     * 
     * @return Kiválasztott ellenfél ütközési köre.
     */
    public Circle targetCircle() {
        return targetEnemy.outline();
    }

    /**
     * A pályán lévő ellenségek közül kiválasztja a legközelebbi ellenfelet. Ez
     * lesz az autómatikus célzórendszer célpontja.
     */
    private boolean findClosestEnemy() {
        if (enemyList.isEmpty()) {
            targetEnemy = null;
            System.out.println("Target enemy NOT found!");
            return false;
        }
        if (enemyList.size() == 1) {
            targetEnemy = enemyList.get(0);
            // System.out.println("Target enemy found!");
            return true;
        }

        int targetIndex = 0;
        float min = 800.0f;
        Circle pC = playerCircle();
        float px = pC.getCenterX();
        float py = pC.getCenterY();

        for (int i = 0; i < enemyList.size(); i++) {
            Enemy enemy = enemyList.get(i);
            Circle eC = enemy.outline();
            float ex = eC.getCenterX();
            float ey = eC.getCenterY();
            float tempMin = (float) Math.sqrt(Math.pow(Math.abs(ex - px), 2)
                    + Math.pow(Math.abs(ey - py), 2));
            if (tempMin < min) {
                min = tempMin;
                targetIndex = i;
            }
        }

        targetEnemy = enemyList.get(targetIndex);
        // System.out.println("Target enemy found!");
        return true;
    }

    /**
     * Két pont közötti elfordulási szöget kiszámoló függvény.
     * 
     * @param base
     *            Bázispont.
     * @param target
     *            Célpont.
     * @return Bázispont és célpont közötti elfordulási szög fokban.
     */
    public static float calculateAngleForTwoPoints(Circle base, Circle target) {

        float px = base.getCenterX();
        float py = base.getCenterY();

        float ex = target.getCenterX();
        float ey = target.getCenterY();

        float angle = 0;

        if (px < ex && py < ey) {
            angle = -(float) (Math.atan(Math.abs(px - ex) / Math.abs(py - ey)) * 180 / Math.PI);
        } else if (px < ex && py > ey) {
            angle = (float) (Math.atan(Math.abs(px - ex) / Math.abs(py - ey)) * 180 / Math.PI) - 180.0f;
        } else if (px > ex && py > ey) {
            angle = -(float) (Math.atan(Math.abs(px - ex) / Math.abs(py - ey)) * 180 / Math.PI) - 180.0f;
        } else if (px > ex && py < ey) {
            angle = (float) (Math.atan(Math.abs(px - ex) / Math.abs(py - ey)) * 180 / Math.PI) - 360.0f;
        }

        return angle;

    }

}
