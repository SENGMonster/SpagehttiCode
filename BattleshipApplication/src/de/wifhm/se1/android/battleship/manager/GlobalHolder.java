package de.wifhm.se1.android.battleship.manager;

import android.preference.PreferenceManager;
import de.wifhm.se1.android.battleship.agent.Communicator;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;

/**
 * SINGLETON
 * Kapselt ein gesamtes Spiel:
 * - Spielfeld und Schiffliste für den Benutzer
 * - Spielfeld und Schiffliste für den Computer
 * - den Communicator für die künstliche Spiellogik, der jeweils Agent und AgentManager instanziert hält
 * - die Spielfeldgröße
 * 
 * Die Spielfeldgröße wird bei Starten eines neuen Spiels in die Instanz geladen 
 * @author Ramona
 *
 */
public class GlobalHolder { 

	
	private static GlobalHolder instance = null;
	 
  
    private GlobalHolder() {}
 
    public static GlobalHolder getInstance() {
        if (instance == null) {
            instance = new GlobalHolder();
        }
        
        
        
        
        return instance;
    }

    public void initializeNew(String oldUserGame, String oldComputerGame){
    	
   
          //Spielvorlage holen und die Schiffe initialisieren
          instance.UserShips = new Spielvorlage1();
          instance.UserShips.initializeSchiffsliste();
          
          instance.ComputerShips = new Spielvorlage1();
          instance.ComputerShips.initializeSchiffsliste();
          
          
          //Manager für das Userspielfeld 
          instance.UserField = new Battlefieldmanager(instance.UserShips );
          
          //Manager für das Computerspielfeld
          instance.ComputerField = new Battlefieldmanager(instance.ComputerShips);
          
          if(oldUserGame!=null && !oldUserGame.contentEquals("")){
        	  instance.UserField.deserializeMeFromString(oldUserGame);
          }
          
          if(oldComputerGame!=null && !oldComputerGame.contentEquals("")){
        	  instance.ComputerField.deserializeMeFromString(oldComputerGame);
          }
          
          instance.AgentCommunicator = new Communicator();
          
        //hier noch den Agent alle KoordinatenDaten FieldStates setzen lassen damit der Algorithmus ordentlich rechnen kann
          if(oldComputerGame!=null && !oldComputerGame.contentEquals("")){
        	  instance.AgentCommunicator.setFieldStatesAfterReloadingGame(UserField, UserShips);
          }
         
          
    }

	
	private Battlefieldmanager UserField;
	private Battlefieldmanager ComputerField;
	private Spielvorlage UserShips;
	private Spielvorlage ComputerShips;
	private Communicator AgentCommunicator;
	private int NumOfRowsCols =10;

	private int GridViewBackground;
	
	public int getGridViewBackground() {
		return GridViewBackground;
	}

	public void setGridViewBackground(int gridViewBackground) {
		GridViewBackground = gridViewBackground;
	}

	public int getNumOfRowsCols() {
		return NumOfRowsCols;
	}

	public void setNumOfRowsCols(int numOfRowsCols) {
		NumOfRowsCols = numOfRowsCols;
	}

	public Battlefieldmanager getUserField() {
		return UserField;
	}

	public Battlefieldmanager getComputerField() {
		return ComputerField;
	}

	public Spielvorlage getUserShips() {
		return UserShips;
	}

	public Spielvorlage getComputerShips() {
		return ComputerShips;
	}
	
	
	
	
}
