package tiborsimon.javagame;

import java.io.File;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.FileUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import tiborsimon.javagame.core.DataManager;
import tiborsimon.javagame.core.FieldDirector;
import tiborsimon.javagame.core.GameManager;
import tiborsimon.javagame.scenes.GameEnded;
import tiborsimon.javagame.scenes.LevelSelect;
import tiborsimon.javagame.scenes.MainScreen;
import tiborsimon.javagame.scenes.NewPlayer;
import tiborsimon.javagame.scenes.Play;
import tiborsimon.javagame.scenes.SetPlayer;

/**
 * A játék belépő osztálya, valamint a játék API kövponti osztálya. Beindítja a
 * játék API-t, példányosítja a singleton osztályokat, majd beindítja a játékot.
 * 
 * @author Tibor
 * 
 */
public class Game extends StateBasedGame {

    /**
     * Program belépési pont.
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        /*
        if (args.length == 0) {
            // Nothing to do..
        } else {
            int matchedOverclockArgumentIndex = 0;
            int index = 0;
            for (String s : args) {
                System.out.println(s);
                if (s.equalsIgnoreCase("overclock")) {
                    matchedOverclockArgumentIndex = index;
                }
                if (matchedOverclockArgumentIndex + 1 == index) {
                    try {
                        overclock = Integer.parseInt(s);
                    } catch (Exception e) {
                        System.out.println("Invalid overclock value!");
                        overclock = 60;
                    }
                }
                index++;
            }
        }
        */
        
        // Játékot modosító paraméterek
        // Overclocking
        int overclock = 60;
        boolean showFPS = false;
        int explosionPoolLimit = 20;
        int sparkPoolLimit = 20;
        boolean fullScrean = false;
        String string = "";
        try {
            File file = new File("mods_for_the_game.txt");
            string = FileUtils.readFileToString(file);
            try {
                String[] splitArray = string.split("\\s+");
                int matchedOverclockArgumentIndex = -2;
                int matchedShowFPSArgumentIndex = -2;
                int matchedExplosionPoolIndex = -2;
                int matchedSparkPoolIndex = -2;
                int matchedFullScreen = -2;
                int index = 0;
                for (String s : splitArray) {
                    // System.out.println(s);
                    if (s.equalsIgnoreCase("overclock")) {
                        matchedOverclockArgumentIndex = index;
                    }
                    if (s.equalsIgnoreCase("showFPS")) {
                        matchedShowFPSArgumentIndex = index;
                    }
                    if (s.equalsIgnoreCase("explosionPoolLimit")) {
                        matchedExplosionPoolIndex = index;
                    }
                    if (s.equalsIgnoreCase("sparkPoolLimit")) {
                        matchedSparkPoolIndex = index;
                    }
                    if (s.equalsIgnoreCase("fullScreen")) {
                        matchedFullScreen = index;
                    }
                    
                    
                    if (matchedOverclockArgumentIndex + 1 == index) {
                        try {
                            overclock = Integer.parseInt(s);
                        } catch (Exception e) {
                            System.out.println("Invalid overclock value!");
                            overclock = 60;
                        }
                    }
                    if (matchedShowFPSArgumentIndex + 1 == index) {
                        try {
                            showFPS = Boolean.parseBoolean(s);
                        } catch (Exception e) {
                            System.out.println("Invalid showFPS value!");
                            showFPS = false;
                        }
                    }
                    if (matchedExplosionPoolIndex + 1 == index) {
                        try {
                            explosionPoolLimit = Integer.parseInt(s);
                        } catch (Exception e) {
                            System.out.println("Invalid explosionPoolLimit value!");
                            explosionPoolLimit = 20;
                        }
                    }
                    if (matchedSparkPoolIndex + 1 == index) {
                        try {
                            sparkPoolLimit = Integer.parseInt(s);
                        } catch (Exception e) {
                            System.out.println("Invalid sparkPoolLimit value!");
                            sparkPoolLimit = 20;
                        }
                    }
                    
                    if (matchedFullScreen + 1 == index) {
                        try {
                            fullScrean = Boolean.parseBoolean(s);
                        } catch (Exception e) {
                            System.out.println("Invalid fullscreen value!");
                            fullScrean = false;
                        }
                    }
                    
                    index++;
                }
            } catch (PatternSyntaxException ex) {
                ex.printStackTrace();
                System.out.println("Invalid mod file format..");
                overclock = 20;
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("No mod file was found..");
            overclock = 60;
        }
        
        
        System.out.println("=======================================");
        System.out.println("G A M E   H A C K I N G");
        System.out.println("=======================================");
        
        if (fullScrean) {
            System.out.println("Full Screen Mode");
        } else {
            System.out.println("Window Mode");
        }
        
        System.out.print("Framerate: " + overclock + " fps");
        
        if (overclock > 60) {
            System.out.println(" [Overclocking]");
        } else if (overclock < 60) {
            System.out.println(" [Underclocking]");
        } else {
            System.out.println("");
        }
        
        if (showFPS) {  
            System.out.println("FPS meter [on]");
        } else {
            System.out.println("FPS meter [off]");
        }
        
        System.out.println("Explosion Pool Limit:" + explosionPoolLimit);
        System.out.println("Spark Pool Limit:" + sparkPoolLimit);
        System.out.println("=======================================");
        System.out.println("");
        
        
        
        // Játék inicializálása
        AppGameContainer appgc;
        try {
            Game game = new Game(gameName);
            appgc = new AppGameContainer(game);
            appgc.setDisplayMode(800, 600, fullScrean);
            appgc.setShowFPS(showFPS);
            appgc.setTargetFrameRate(overclock);

            // init GameManager singleton instance
            new GameManager(game,showFPS,explosionPoolLimit,sparkPoolLimit);
            new DataManager();
            new FieldDirector();

            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static final String gameName = "Java Shooter Game - Tibor Simon v1.2";

    private static final int MAIN_SCREEN = 0;

    private static final int SET_PLAYER = 1;

    private static final int NEW_PLAYER = 2;

    private static final int LEVEL_SELECT = 3;

    private static final int PLAY = 4;

    private static final int GAME_ENDED = 5;

    // private static final int PAUSE = 6;

    public Game(String gameName) {
        super(gameName);
        this.addState(new Play(PLAY));
        this.addState(new MainScreen(MAIN_SCREEN));
        this.addState(new NewPlayer(NEW_PLAYER));
        this.addState(new LevelSelect(LEVEL_SELECT));
        this.addState(new GameEnded(GAME_ENDED));
        this.addState(new SetPlayer(SET_PLAYER));

    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.enterState(MAIN_SCREEN);
    }

}
