package tiborsimon.javagame.core;

import java.io.File;

import java.io.IOException;

import java.util.prefs.Preferences;

import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;

import tiborsimon.javagame.scenes.Play;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

/**
 * Jákékos adatainak a kezelésére és nyilvántartására való singleton osztály.
 * 
 * @author Tibor
 * 
 */
public class DataManager {

    // Tehermentesítés. CSak változásnál hívódik meg a pontos kiírása.
    private Play play;
    
    public PlayerData playerData;

    // singleton viselkedéshez a statikus változó
    private static DataManager dataManagerInstance = null;

    /**
     * Egyszer meghívandó konstruktor, amit a main függvényben kell mehívni.
     * Paraméterként átadandó neki a játék képernyőit kezelni képes objektum.
     */
    public DataManager() {
        System.out.println("DataManager initialized.");
        dataManagerInstance = this;
        loadData();
        if (playerData == null) {
            playerData = new PlayerData("Anonymous");
            saveData();
        }
    }

    /**
     * Singleton viselkedés megvalósításához a bárhonnan elérhető statikus
     * tagfüggvény, ami visszaadja a singleton objektumot.
     * 
     * @return GameManager objektum.
     */
    public static DataManager sharedDataManager() {
        if (dataManagerInstance != null) {
            return dataManagerInstance;
        }
        System.out
                .println("Error uninitialized DataManager! Program terminated!");
        System.exit(0);
        return null;
    }

    /**
     * Kapcsolat létrehozása DataManager és a játékot futtató osztály között.
     * 
     * @param p
     *            Játékot futtató objektum.
     */
    public void setPlayContainer(Play p) {
        play = p;
        playerData.lastScore = 0;
    }

    /**
     * Ez a függvény hívódik meg a játék indulásakor. Felhasználható kezdeti
     * beállításhoz.
     */
    public void newGame() {
        // TODO Inicializálni kell az adatszerkezetet, hogy a játék kezdetekor 0
        // pontról kezdjen a számláló.
    }

    /**
     * Új pont hozzáadása
     * @param point hozzáadandó pont.
     */
    public void enemyKilled(int point) {
        playerData.lastScore += point;
        play.updateScore(playerData.lastScore);
    }
    
    /**
     * Ellenfél megölésnek számolása.
     */
    public void enemyKilled() {
        playerData.enemyKilled++;
    }
    
    /**
     * Lövések számolása.
     */
    public void bulletFired() {
        playerData.bulletFired++;
    }
    
    /**
     * Játék vége, adatok összesítése és mentés.
     */
    public void gameEnded() {
        playerData.playCount++;
        playerData.summedScore += playerData.lastScore;
        saveData();
    }
    
    /**
     * Új játékos indítása.
     * @param name
     */
    public void newPlayer(String name) {
        playerData = null;
        playerData = new PlayerData(name);
        saveData();
    }
    
    /**
     * Adatok mentése lemezre.
     */
    private void saveData() {
        System.out.println("SaveData");
        
        String dataString = null;
        try {
            dataString = JsonWriter.objectToJson(playerData);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        if (dataString != null) {
            Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
            prefs.put("data", dataString);
        }
    }
    
    /**
     * Adatok olvasása lemezről.
     */
    private void loadData() {
        System.out.println("LoadData");
        
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        String loadedString = prefs.get("data", null);
        
        if (loadedString != null) {
            try {
                playerData = (PlayerData) JsonReader.jsonToJava(loadedString);
                System.out.println("File loaded :)");
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(-1);
            }
        } else {
            playerData = null;
        }
    }

    /**
     * Játékos adatainak exportálása.
     */
    public void export() {
        System.out.println("DataManager: export");
        String filename = showFileChooser("Export");
        
        String dataString = null;
        try {
            dataString = JsonWriter.objectToJson(playerData);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        if (dataString != null) {
            try {
                FileUtils.writeStringToFile(new File(filename), dataString);
                System.out.println("File exported :)");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
            
        /*
        // Save the object to file
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(playerData);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
    }

    /**
     * Játékos adatainak betöltése. Sikeres betöltés esetén töltődik csak be az új adat az adatszerkezetbe.
     */
    public void load() {
        System.out.println("DataManager: load");
        String filename = showFileChooser("Load");
        
        // load file
        try {
            File file = new File(filename);
            String string = FileUtils.readFileToString(file);
            
            if (string != null) {
                try {
                    playerData = (PlayerData) JsonReader.jsonToJava(string);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(-1);
                }
            } else {
                playerData = null;
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        /*
        // Read the object from file
        // Save the object to file
        FileInputStream fis = null;
        ObjectInputStream in = null;
        PlayerData p = null;
        try {
          fis = new FileInputStream(filename);
          in = new ObjectInputStream(fis);
          p = (PlayerData) in.readObject();
          in.close();
          playerData = null;
          playerData = p;
          System.out.println(p);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        */
        
    }

    /**
     * Fájlkiválasztó ablak meghívása. 
     * jvgd - java game data
     */
    private String showFileChooser(String name) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("playerData.jvgd"));
        fileChooser.showDialog(null, name);
        String retString = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println(retString);
        return retString;
    }

}
