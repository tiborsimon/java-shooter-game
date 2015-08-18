package tiborsimon.javagame.core;

import org.newdawn.slick.state.*;

/**
 * Játék állapotgépét kezelő singleton osztály. A játékból bárhonnan elérhető.
 * Egy példányban példányosodik a játék felkapcsolásakor a main függvényben,
 * ahol paraméterként megkapja a játék API-ját kezelő fő objektumot.
 * 
 * @author Tibor
 * 
 */
public class GameManager {

	// singleton viselkedéshez a statikus változó
	private static GameManager gameManagerInstance = null;
	// játék API fő objektuma
	private StateBasedGame stateBasedGame;
	// játék állapota
	private StateMachineState gameState;
	// FPS visszaírás
	private boolean showFPS;
	private int explosionPoolLimit;
	private int sparkPoolLimit;


    // játék API képernyői (állapotai)
	private static final int MAIN_SCREEN = 0;
	private static final int SET_PLAYER = 1;
	private static final int NEW_PLAYER = 2;
	private static final int LEVEL_SELECT = 3;
	private static final int PLAY = 4;
	private static final int GAME_ENDED = 5;
	// private static final int PAUSE = 6;

	/**
	 * Ez a konstruktor nem hívható! Felborítaná a singleton működést.
	 */
	protected GameManager() {
		System.out.println("Invalid constructor call for GameManager!");
		System.exit(0);
		
	}

	/**
	 * Egyszer meghívandó konstruktor, amit a main függvényben kell mehívni.
	 * Paraméterként átadandó neki a játék képernyőit kezelni képes objektum.
	 * 
	 * @param sbg
	 *            Játék-kezelő API fő objektuma. Szükséges az
	 *            képernyőváltásokhoz.
	 */
	public GameManager(StateBasedGame sbg, boolean showfps, int explosionlimit, int sparklimit) {
	    
		System.out.println("GameManager initialized.");
		stateBasedGame = sbg;
		showFPS = showfps;
		explosionPoolLimit = explosionlimit;
		sparkPoolLimit = sparklimit;
		gameManagerInstance = this;
		gameState = StateMachineState.MainScreen;
	}

	/**
	 * Singleton viselkedés megvalósításához a bárhonnan elérhető statikus
	 * tagfüggvény, ami visszaadja a singleton objektumot.
	 * 
	 * @return GameManager objektum.
	 */
	public static GameManager sharedGameManager() {
		if (gameManagerInstance != null) {
			return gameManagerInstance;
		}
		System.out
				.println("Error uninitialized GameManager! Program terminated!");
		System.exit(0);
		return null;
	}
	
	/**
	 * Játék állapotának a lekérdezése.
	 * @return Játék aktuális állapota.
	 */
	public StateMachineState getState() {
		return gameState;
	}


	/**
	 * Play gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
	 */
	public void playButton() {
		String transition = "play";
		System.out.println("GameManager " + transition + " transition called");

		switch (gameState) {
		case MainScreen:
			gameState = StateMachineState.LevelSelect;
			stateBasedGame.enterState(LEVEL_SELECT);
			break;
		case SetPlayer:
			errorInStateTransition(transition);
			break;
		case NewPlayer:
			errorInStateTransition(transition);
			break;
		case LevelSelect:
		    gameState = StateMachineState.Play;
            stateBasedGame.enterState(PLAY);
			break;
		case Play:
			errorInStateTransition(transition);
			break;
		case GameEnded:
			errorInStateTransition(transition);
			break;
		default:
			break;
		}
	}
	
	/**
     * Player gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void playerButton() {
        String transition = "player";
        System.out.println("GameManager " + transition + " transition called");

        switch (gameState) {
        case MainScreen:
            gameState = StateMachineState.SetPlayer;
            stateBasedGame.enterState(SET_PLAYER);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            errorInStateTransition(transition);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * Exit gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void exitButton() {
        String transition = "exit";
        System.out.println("GameManager " + transition + " transition called");

        switch (gameState) {
        case MainScreen:
            System.out.println("Exiting..");
            System.exit(0);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            errorInStateTransition(transition);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    /**
     * Back gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void backButton() {
        String transition = "back";
        System.out.println("GameManager " + transition + " transition called");

        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            gameState = StateMachineState.MainScreen;
            stateBasedGame.enterState(MAIN_SCREEN);
            break;
        case NewPlayer:
            gameState = StateMachineState.SetPlayer;
            stateBasedGame.enterState(SET_PLAYER);
            break;
        case LevelSelect:
            gameState = StateMachineState.MainScreen;
            stateBasedGame.enterState(MAIN_SCREEN);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * Export gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void exportButton() {
        String transition = "export";
        System.out.println("GameManager " + transition + " transition called");

        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            errorInStateTransition(transition);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * New gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void newButton() {
        String transition = "new";
        System.out.println("GameManager " + transition + " transition called");

        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            gameState = StateMachineState.NewPlayer;
            stateBasedGame.enterState(NEW_PLAYER);
            break;
        case NewPlayer:
            gameState = StateMachineState.SetPlayer;
            stateBasedGame.enterState(SET_PLAYER);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * Load gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void loadButton() {
        String transition = "load";
        System.out.println("GameManager " + transition + " transition called");

        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            gameState = StateMachineState.SetPlayer;
            stateBasedGame.enterState(SET_PLAYER);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * End gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void endButton() {
        String transition = "end";
        System.out.println("GameManager " + transition + " transition called");
        
        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            errorInStateTransition(transition);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            gameState = StateMachineState.GameEnded;
            stateBasedGame.enterState(GAME_ENDED);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * Ok gombnyomás. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void okButton() {
        String transition = "ok";
        System.out.println("GameManager " + transition + " transition called");
        
        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            errorInStateTransition(transition);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            errorInStateTransition(transition);
            break;
        case GameEnded:
            gameState = StateMachineState.MainScreen;
            stateBasedGame.enterState(MAIN_SCREEN);
            break;
        default:
            break;
        }
    }
    
    
    /**
     * Játék vége gomb. Az állapotgép ennek megfelelően lép tovább, vagy hbával kilép.
     */
    public void gameOver() {
        String transition = "gameOver";
        System.out.println("GameManager " + transition + " transition called");
        
        switch (gameState) {
        case MainScreen:
            errorInStateTransition(transition);
            break;
        case SetPlayer:
            errorInStateTransition(transition);
            break;
        case NewPlayer:
            errorInStateTransition(transition);
            break;
        case LevelSelect:
            errorInStateTransition(transition);
            break;
        case Play:
            gameState = StateMachineState.GameEnded;
            stateBasedGame.enterState(GAME_ENDED);
            break;
        case GameEnded:
            errorInStateTransition(transition);
            break;
        default:
            break;
        }
    }

	

	/**
	 * Állapotátmeneti hibák jelzésére szolgáló, debuggolást segítő függvény.
	 * Akkor hívható meg, ha egy adott állapotban olyan állapotátmeneti kérés
	 * érkezik, ami érvénytelen. Outputon jelzi az adott állapotot, és az
	 * átmeneti kérést.
	 * 
	 * @param transition
	 *            Állapotátmeneti kérés. Helyes megadása fontos a debuggolás
	 *            szempontjából.
	 */
	private void errorInStateTransition(String transition) {
		System.out.println("Invalid State Transition! State: " + gameState
				+ " Transition: " + transition);
		System.exit(-1);
	}

	
	/**
	 * Lekérdező függvény, ami megmondja, hogy van-e folyamatban játék. 
	 * @return Igaz logikai értékkel tér vissza, ha van folyamatban játék == nem a menüben van a felhasználó.
	 */
	public Boolean isPlayInProgress() {
		return gameState == StateMachineState.Play || gameState == StateMachineState.Pause;
	}
	
	public boolean isShowFPS() {
        return showFPS;
    }

    public int getExplosionPoolLimit() {
        return explosionPoolLimit;
    }

    public int getSparkPoolLimit() {
        return sparkPoolLimit;
    }
	

}
