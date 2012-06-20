package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;

import de.wifhm.se1.android.battleship.manager.FieldState;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;

public class Tester {

	Agent a;
		
	public Tester(){
		
		Spielvorlage S1 = GlobalHolder.getInstance().getUserShips();
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(); 
		
		int limit=GlobalHolder.getInstance().getNumOfRowsCols() *GlobalHolder.getInstance().getNumOfRowsCols() ; 
		
		for(int i=0; i<limit; i++)
		{
			coordinates.add(new Coordinate(FieldState.UNKNOWN, i));
		}
		
		
		 a = new Agent(S1, coordinates);
		
	}
	
	public int getNextChoice()
	{
		int result =  a.nextTurn().getCoordinateNr();
		return result;
	}
	
	public void setFieldState(boolean result, int pos){
		if (result){
			AgentManager.getInstance().setFieldStateForCoordinate(pos, FieldState.HIT);
		}else{
			AgentManager.getInstance().setFieldStateForCoordinate(pos, FieldState.WATER);
		}
	}
}
