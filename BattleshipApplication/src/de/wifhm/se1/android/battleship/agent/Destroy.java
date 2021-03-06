package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;



/**
 * Speichert die Werte die bei dem Beschuss eines Schiffes anfallen.
 * Die Positionen die bereits beschossen wurden, die Richtungen die auszuschließen sind etc.
 * @author Ramona, Conny, Jens
 *
 */
public class Destroy {

	//the first Hit of the ship
	private Coordinate initialShot;
	
	//contains if the direction of the ship is already known (if there are 2 hits the direction comes clear)
	private boolean has2Hitsalready=false;
	//contains the direction (horizontal, vertical) of the ship which comes clear after 2 hits 
	private boolean isHorizontal =false; 
	private Directions lastDirection;	
	private ArrayList<Coordinate> shots = new ArrayList<Coordinate>();
	private ArrayList<Directions> falseDirections = new ArrayList<Directions>();

	
	public ArrayList<Directions> getFalseDirections() {
		return falseDirections;
	}

	
	public boolean isHas2Hitsalready() {
		return has2Hitsalready;
	}
	public void setHas2Hitsalready(boolean has2Hitsalready) {
		this.has2Hitsalready = has2Hitsalready;
	}
	
	public boolean isHorizontal() {
		return isHorizontal;
	}
	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}
	public Coordinate getInitialShot() {
		return initialShot;
	}
	
	/**
	 * 
	 * @return gibt die Position zurück für die die Richtung errechnet werden soll an der weitergeschossen wird
	 */
	public Coordinate getLastShot(){
		if (shots.size()!=0){
			
			//Wenn schon drei Seiten ausgeschlossen sind, die 4. Seite zurückgeben
			if(falseDirections.size()==3){
				if(!falseDirections.contains(Directions.LEFT) ||  !falseDirections.contains(Directions.BOTTOM))
				{
					//den größten Wert zurückgeben
					Coordinate highestValueCoordinate=null;
					for(Coordinate c:shots){
						if (highestValueCoordinate ==null) highestValueCoordinate=c;
						if (c.getCoordinateNr() > highestValueCoordinate.getCoordinateNr()){
							highestValueCoordinate = c;
						}
					}
					
					return highestValueCoordinate;
				}
				else{					
					//den kleinsten Wert zurückgeben
					Coordinate lowestValueCoordinate=null;
					for(Coordinate c:shots){
						if (lowestValueCoordinate ==null) lowestValueCoordinate=c;
						if (c.getCoordinateNr() < lowestValueCoordinate.getCoordinateNr()){
							lowestValueCoordinate = c;
						}
					}
					
					return lowestValueCoordinate;
				}
			}
			else{
				return shots.get(shots.size()-1);	
			}
				
		}
		else{
			return null;
		}
	}
	
	/**
	 * die Richtung die als letztes beschossen wurde
	 * @return
	 */
	public Directions getLastDirection() {
		return lastDirection;
	}
	/**
	 * setzt die Richtung die als letztes beschossen wurde
	 * @param lastDirection
	 */
	public void setLastDirection(Directions lastDirection) {
		this.lastDirection = lastDirection;
	}
	
	/**
	 * setzt einen Versuchsschuss, wenn es der erste ist wird dieser zusätzlich in in InitialShot gespeichert
	 * Wenn bereits 2 Hit Schüsse getätigt wurden, wird ermittelt ob das Schiff vertikal oder horizontal liegt
	 * Anhand der Richtung der Positionierung des Schiffes werden Richtungen ausgeschlossen auf die geschossen wird
	 * @param Shot
	 */
	public void setShot(Coordinate Shot) {
		if (this.initialShot ==null)
		{ 
			this.initialShot = Shot;
		}
		
		shots.add(Shot);
		
		if(shots.size()==2){
			Coordinate a = shots.get(0);
			Coordinate b = shots.get(1);
			
			int differenz= a.getCoordinateNr()- b.getCoordinateNr();
			int differenzbetrag= Math.abs(differenz);
			if (differenzbetrag>9) //vertikale Anordnung
			{
				isHorizontal=false;
				if(!falseDirections.contains(Directions.LEFT)) falseDirections.add(Directions.LEFT);
				if(!falseDirections.contains(Directions.RIGHT)) falseDirections.add(Directions.RIGHT);
			}
			else { //horizontale Anordnung
				
				isHorizontal=true;
				if(!falseDirections.contains(Directions.BOTTOM)) falseDirections.add(Directions.BOTTOM);
				if(!falseDirections.contains(Directions.TOP)) falseDirections.add(Directions.TOP);
			}
		}
		
	}
	

	
	
	
	

	
	
}
