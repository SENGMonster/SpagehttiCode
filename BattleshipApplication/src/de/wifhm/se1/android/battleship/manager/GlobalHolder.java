package de.wifhm.se1.android.battleship.manager;

import de.wifhm.se1.android.activity.GridViewActivity;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;

public class GlobalHolder { 

	
	private static GlobalHolder instance = null;
	 
  
    private GlobalHolder() {}
 
    public static GlobalHolder getInstance() {
        if (instance == null) {
            instance = new GlobalHolder();
            
            //Spielvorlage holen und die Schiffe initialisieren
            instance.UserShips = new Spielvorlage1();
            instance.UserShips.initializeSchiffsliste();
            
            instance.ComputerShips = new Spielvorlage1();
            instance.ComputerShips.initializeSchiffsliste();
           
            
            //Manager für das Userspielfeld 
            instance.UserField = new Battlefieldmanager(instance.UserShips, new GridViewActivity() );
            
            //Manager für das Computerspielfeld
            instance.ComputerField = new Battlefieldmanager(instance.ComputerShips, new GridViewActivity());
            
        }
        return instance;
    }


	
	private Battlefieldmanager UserField;
	private Battlefieldmanager ComputerField;
	private Spielvorlage UserShips;
	private Spielvorlage ComputerShips;
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
