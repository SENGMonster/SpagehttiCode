package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;
import java.util.Random;
/**
 * Abstrakte Klasse
 * 
 * kapselt die Informationen über ein Schiff.
 * - Enthält die Positionen des Schiffes, die abgeschossenen Werte
 * - Speichert die Ausrichtung des Schiffes
 * - Speichert ob das Schiff gesunken ist
 * 
 * Zwingt durch Definition von abstrakten Methoden die erbenden Klasse für das Schiff eine Länge, einen Namen, eine Ziffer zur Codierung und ein Bild zu definieren.
 * 
 * @author Ramona
 *
 */
public abstract class Schiff {

	private static int numOfRowsCols = GlobalHolder.getInstance().getNumOfRowsCols();
	protected ArrayList<Integer> Positions;
	private boolean isHorizontal;
	
	public Schiff(){
		initialize();
	}
	
	public void initialize()
	{
		Image = getImage();
			
	}
	
	public boolean isHorizontal(){
		return isHorizontal;
	}
	
	
	/**
	 * die Felder die das Schiff besetzt
	 * @param StartPosition
	 * @param EndPosition
	 * @return
	 */
	public ArrayList<Integer> setSchiffspositions(int StartPosition, int EndPosition)
	{
		setEndposition(EndPosition);
		setStartposition(StartPosition);
		ArrayList<Integer> array = new ArrayList<Integer>();		
			
			int differenz= EndPosition-StartPosition;
			int differenzbetrag= Math.abs(differenz);
			if (differenzbetrag>9) //vertikale Anordnung
			{
				if (differenz>0) //nach unten
				{
	    			for(int i=0; i<=differenzbetrag; i+=numOfRowsCols)
	    			{
	    				array.add(StartPosition+i);
	    			}
				}
				else{ //nach oben
					for(int i=0; i<=differenzbetrag; i+=numOfRowsCols)
	    			{
						array.add(StartPosition-i);
	    			}
				}

				 //die drumherumReihen setzen
				 ImpossibleRowsAround = Helper.setImpossibleFieldsaroundShip(array, false);
			}
			else { //horizontale Anordnung
				
				isHorizontal=true;
				
				if(differenz>0)//nach rechts
				{
					for(int i=0; i<=differenzbetrag; i++)
	    			{
						array.add(StartPosition+i);
	    			}
				}
				else{ //nach links
					for(int i=0; i<=differenzbetrag; i++)
	    			{
						array.add(StartPosition-i);
	    			}
				}
				
				 //die drumherumReihen setzen
				 //ImpossibleRowsAround = Helper.setImpossibleFieldsaroundShip(array, true);
			}

		 Positions = array;

		 return array;
		 
	}
	
	private ArrayList<Integer> ImpossibleRowsAround;
	public ArrayList<Integer> getImpossibleRowsAround(){
		return ImpossibleRowsAround;
	}
	
	public ArrayList<Integer> getSchiffspositions()
	{
		return Positions;
	}
	
	public String getSchiffsname()
	{
		return getName();
	}
	
	
	private ArrayList<Integer> HitPositions = new ArrayList<Integer>();

	public boolean setHitPosition(int pos){	
		
		if(!HitPositions.contains(pos))
		{
			HitPositions.add(pos);
		}
		
		if (HitPositions.size() == getShipLength()){
			isSunk=true;
			return true;
		}
		else{
			return false;	
		}
	}
	
	public ArrayList<Integer> getHitPositions(){
		return HitPositions;
	}
	
	private boolean isSunk;
	public boolean getIsSunk()
	{
		return isSunk;
	}
	
	//von den Schiffen zu setzen 
	public abstract int getShipLength();
	public abstract char getShipChiffre();
	protected abstract String getName();
	
	protected int Image;
	public abstract int getImage();
	
	private int Startposition;
	private int Endposition;

	public int getStartposition() {
		return Startposition;
	}

	public void setStartposition(int startposition) {
		Startposition = startposition;
	}

	public int getEndposition() {
		return Endposition;
	}

	public void setEndposition(int endposition) {
		Endposition = endposition;
	}
	
	
	
	
}
