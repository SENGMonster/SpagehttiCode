package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;

import de.wifhm.se1.android.battleship.manager.FieldState;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.HitStates;
import de.wifhm.se1.android.battleship.manager.Schiff;
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
		
		
		 //a = new Agent(S1, coordinates);
		
	}
	
	public int getNextChoice()
	{
		int result =  a.nextTurn().getCoordinateNr();
		return result;
	}
	
	public void setShip2Destroy(int position)
	{
		if (a.getShip2Destroy()==null){
			Destroy d = new Destroy();
			//d.setShot(AgentManager.getInstance().getCoordinateForNr(position));
			a.setShip2Destroy(d);	
		}		
	}
	
	public void DestroyedShip(Schiff s)
	{
		a.setDestroyedShip(s);
	}
	
	public void setFieldState(HitStates result, int pos){
		if (result == HitStates.HIT || result == HitStates.DESTROYED){
			//AgentManager.getInstance().setFieldStateForCoordinate(pos, FieldState.HIT);
			if (a.getShip2Destroy()!=null){
				//a.getShip2Destroy().setShot(AgentManager.getInstance().getCoordinateForNr(pos));
			}
		}else{
			//AgentManager.getInstance().setFieldStateForCoordinate(pos, FieldState.WATER);
			
			if (a.getShip2Destroy()!=null){
				a.getShip2Destroy().getFalseDirections().add(a.getShip2Destroy().getLastDirection());
			}
		}
	}
}
