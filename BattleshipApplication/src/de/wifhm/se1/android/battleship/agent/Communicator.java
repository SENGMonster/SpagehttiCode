package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;

import de.wifhm.se1.android.battleship.manager.Battlefieldmanager;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.HitStates;
import de.wifhm.se1.android.battleship.manager.Schiff;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;

/**
 * 
 * Dient als Vermittler zwischen der Anzeige und den Klassen Agent und AgentManager
 * Ist Kommunikationsschicht zwischen dem Paket Agent und den Paketen Daten und Anzeige 
 * 
 * @author Jens
 *
 */
public class Communicator {

	Agent a;
	AgentManager AgentManager;
		

	public Communicator(){
		
		Spielvorlage S1 = GlobalHolder.getInstance().getUserShips();
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(); 
		
		int limit=GlobalHolder.getInstance().getNumOfRowsCols() *GlobalHolder.getInstance().getNumOfRowsCols() ; 
		
		for(int i=0; i<limit; i++)
		{
			coordinates.add(new Coordinate(FieldState.UNKNOWN, i));
		}
		
		AgentManager = new AgentManager();
		a = new Agent(S1, coordinates,AgentManager);
		 
		
	}
	
	/**
	 * liefert das Ergbnis des Agenten für den nächsten Schuss des Computers zurück
	 * @return Feld des nächsten Schusses 
	 */
	public int getNextChoice()
	{
		int result =  a.nextTurn().getCoordinateNr();
		return result;
	}
	
	/**
	 * setzt anhand der getroffenen Schiffspositionen die Werte initialen Werte für die Klasse Destroy 
	 * 
	 * @param position
	 * die getroffene Position die Hit zurückliefert
	 */
	public void setShip2Destroy(int position)
	{
		if (a.getShip2Destroy()==null){
			Destroy d = new Destroy();
			d.setShot(AgentManager.getCoordinateForNr(position));
			a.setShip2Destroy(d);	
		}		
	}
	
	/**
	 * leitet zum Auswerten eines Abschuss das Schiff weiter
	 * @param s
	 * das abgeschosssene Schiff
	 */
	public void DestroyedShip(Schiff s)
	{
		a.setDestroyedShip(s);
	}
	
	/**
	 * Übersetzt den Spielstatus anhand des HitState Result was nach dem Schuss kam für den Agent.
	 * Setzt entsprechend auch weitere Werte die der Agent brauch wie z.B. falls gerade ein Schiff in Beschuss steht die nächste Position in die Liste der "Versuchsfelder"
	 * 
	 * @param result
	 * das Ergebnis des Schusses
	 * @param pos
	 * die feldnummer
	 */
	public void setFieldState(HitStates result, int pos){
		if (result == HitStates.HIT || result == HitStates.DESTROYED){
			AgentManager.setFieldStateForCoordinate(pos, FieldState.HIT);
			if (a.getShip2Destroy()!=null){
				a.getShip2Destroy().setShot(AgentManager.getCoordinateForNr(pos));
			}
		}else{
			AgentManager.setFieldStateForCoordinate(pos, FieldState.WATER);
			
			if (a.getShip2Destroy()!=null){
				a.getShip2Destroy().getFalseDirections().add(a.getShip2Destroy().getLastDirection());
			}
		}
	}
	
	
	/**
	 * leitet die Anfrage weiter dass der Agent seine Flotte positionieren soll
	 */
	public void setComputerShips(){
		a.positionShips();
	}
	
	/**
	 * fordert den Agent auf nach Laden eines Spiels vom Server die Zustände der Koordinaten anhand des deserialisierten Strings wieder zu geben 
	 * @param bm
	 * @param sv
	 */
	public void setFieldStatesAfterReloadingGame(Battlefieldmanager bm, Spielvorlage sv)
	{
		a.setFieldStatesAfterLoadingGame(bm, sv);
	}
}
