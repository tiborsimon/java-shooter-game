package tiborsimon.javagame.core;

import java.io.Serializable;

/**
 * Játékos adatainak a tárolására hivatott osztály.
 * @author Tibor
 *
 */
public class PlayerData implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public String name;
    public int playCount;
    public long summedScore;
    public int lastScore;
    public int enemyKilled;
    public long bulletFired;

    public PlayerData(String name) {
        this.name = name;
        playCount = 0;
        summedScore = 0;
        lastScore = 0;
        enemyKilled = 0;
        bulletFired = 0;
    }

    @Override
    public String toString() {
        return "PlayerData [name=" + name + ", playCount=" + playCount
                + ", summedScore=" + summedScore + ", lastScore=" + lastScore
                + ", enemyKilled=" + enemyKilled + ", bulletFired=" + bulletFired + "]";
    }
    

}
