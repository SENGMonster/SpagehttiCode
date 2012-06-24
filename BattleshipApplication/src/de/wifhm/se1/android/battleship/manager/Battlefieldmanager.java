package de.wifhm.se1.android.battleship.manager;


import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import de.wifhm.se1.android.activity.R;


/** 
 * Stellt die Informationen für ein Spielfeld zusammen.
 * Enthält die Informationen für die Wasserschüsse und den Algorithmus zum Errechnen des Ergebnis eines Angriffs.
 * Kann den den Punktestand errchnen
 * Kann das Spielfeld serialisieren bzw. deserialisieren
 * Dieses geschieht nach einer selbst entwickelten Codierung anhand eines ; -separierten Strings. Jedes Element was durch ';' getrennt wird, stellt ein Feld dar. 
 * @author Ramona
 *
 */
public class Battlefieldmanager {


	private Spielvorlage mSpielvorlage;
	private ArrayList<Integer> WaterHits = new ArrayList<Integer>();
	private int turnCounter;
	
	public Schiff lastDestroyedShip;
	
	public Battlefieldmanager(Spielvorlage spiel){
		
		mSpielvorlage= spiel;
	}
	
	public ArrayList<Integer> getWaterHits(){
		return WaterHits;
	}
	
	/**
	 * Ermittelt für einen Angriff das Ergebnis 
	 * @param position
	 * die Angriffsposition
	 * @param mImageAdapter
	 * der ImageAdapter der in seiner ImageListe das Ergebnis aufnehmen soll damit es angezeigt wird
	 * @param a
	 * Das Fenster auf dem der Toast mit dem Ergebnis angezeigt werden soll
	 * @return das Ergebnis eines Angriffs
	 */
	public HitStates hasHitAShip(int position, BattleFieldImageAdapter mImageAdapter,  Activity a)
	{
		
		for(Schiff s:mSpielvorlage.getSchiffsliste()){
			
			//mSpielvorlage.alreadySunkCounter=8;
			
			//überprüfen ob alle Tod sind			
			if (!s.getIsSunk())
			{
				for (int pos:s.Positions){
					if (pos==position){
						
						//einen Spielzug hochzählen
						turnCounter+=1;
						
						//anzeigen
						mImageAdapter.setTreffer(position);
						
						//überprüfen ob das Ding versenkt ist
						boolean versenkt =  s.setHitPosition(position);
						if (versenkt){
							MakeToast("Treffer versenkt: " + s.getName()+ " auf " + String.valueOf(position), a);
							lastDestroyedShip = s;
							
							mSpielvorlage.alreadySunkCounter+=1;
							System.out.println("Versenkt--> sunkCounter :" + String.valueOf(mSpielvorlage.alreadySunkCounter)+ " |Anzahl: " + String.valueOf(mSpielvorlage.getSchiffsliste().size()));
							if (mSpielvorlage.alreadySunkCounter==mSpielvorlage.getSchiffsliste().size())
							{
								
								//falls alle weg sind ENDE
								return HitStates.END;
							
							}
							
							//Destroyed zurückgeben
							return HitStates.DESTROYED;
							
						}
						else{
							MakeToast("Treffer auf " + String.valueOf(position), a);
							return HitStates.HIT;
						}
						
						
					}
				}
			}
		}
		
		//einen Spielzug hochzählen
		turnCounter+=1;
		mImageAdapter.setWasser(position);
		MakeToast("Wasser auf " + String.valueOf(position), a);
		WaterHits.add(position);
		return HitStates.WATER;
	}
	
	
	/**
	 * Blendet eine Anzeige für den Text ein
	 * @param text
	 * der anzuzeigende Text
	 * @param a
	 * das Fenster in das der Text eingeblendet werden soll
	 */
	public void MakeToast(String text, Activity a)
	{
		Toast.makeText(a, text, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * errechnet den Highscorewert anhand der getätigten Angriffe
	 * @return Spielergebnis für den Highscore
	 */
	public int getHighScore(){
		return (GlobalHolder.getInstance().getNumOfRowsCols() * GlobalHolder.getInstance().getNumOfRowsCols()) - turnCounter;
	}
	
	/**
	 * liefert die Anzahl der gemachten Angriffe zurück
	 * @return
	 */
	public int getTurnCounter(){
		return turnCounter;
	}
	
	
	/**
	 * serialisiert das Spielfeld anhand des Codes im Stil einer Komma separierten Datei.
	 * uxx;uxx;uxx;
	 * die erste Stelle steht für den Zustand des Felsd [Water (w), Getroffen (h), Unbeschossen (u)]
	 * die zweite Stelle steht für den Schiffsbuchstaben, falls dort kein Schiff steht wird x genommen. Jedes Schiff bestimmt seinen Buchstaben durch das es kodiert ist.
	 * die dritte Stelle steht für die Ausrichtung des Schiffes, [Horizontal (t), Vertikal (f), kein Schiff (x)
	 * @return das als String kodierte Spielfeld 
	 */
	public String serializeInfoToString()
	{
		ArrayList<StringBuilder> items = new ArrayList<StringBuilder>();
		
		for(int i=0; i<(GlobalHolder.getInstance().getNumOfRowsCols()*GlobalHolder.getInstance().getNumOfRowsCols()); i++)
		{
			items.add(new StringBuilder("uxx;"));
		}
	
		for(int i=0; i<WaterHits.size(); i++)
		{
			items.get(i).setCharAt(0, 'w');
		}
		
		for(Schiff s:mSpielvorlage.getSchiffsliste()){
			
			for(int pos:s.getSchiffspositions())
			{
				items.get(pos).setCharAt(1, s.getShipChiffre());
				if(s.isHorizontal())
				{
					items.get(pos).setCharAt(2, 't');	
				}else{
					items.get(pos).setCharAt(2, 'f');
				}
				
			} 
			
			if(s.getHitPositions()!=null)
			{
				for(int pos:s.getHitPositions()){
					items.get(pos).setCharAt(0, 'h');
				}
			}
			
		}
		
		String returnString ="";
		for(StringBuilder stringBuilder:items){
			returnString+=stringBuilder.toString();
		}
		
		return returnString;

	}
	
	
	/**
	 * deserialisiert ein Spielfeld anhand eines kodierten String. Codieurung siehe @see {@link Battlefieldmanager#serializeInfoToString()}
	 * @param BattlefieldString
	 * der String der deserialisiert werden soll
	 */
	public void deserializeMeFromString(String BattlefieldString){
		
		if (BattlefieldString!=null && !BattlefieldString.contentEquals("") && !BattlefieldString.contentEquals("anyType{}"))
		{
		
			Hashtable<String, Schiff> ht = new Hashtable<String, Schiff>();
			for(Schiff s:mSpielvorlage.getSchiffsliste())
			{
				ht.put(String.valueOf(s.getShipChiffre()), s);
			}
			
			
			String[] Felder = BattlefieldString.split(";");
			for(int i=0;i<Felder.length; i++){
				
				String gesamt= Felder[i];
				
				//Spielfeld Status : wasser (w), getroffen (h), noch nicht beschossen (u)
				char spielstatus = gesamt.charAt(0);
				//Schiff (falls eins da steht) sonst x
				char schiffChiffre = gesamt.charAt(1);
				//Ausrichtung des Schiffes (horizontal (t), vertikal (f), falls kein Schiff(x)
				char isHorizontal = gesamt.charAt(2);
				
				//alle Wasser felder ins Array aufnehmen.
				if(spielstatus=='w'){
					WaterHits.add(i);
				}
				
				if(schiffChiffre!='x'){
					Schiff ship = ht.get(schiffChiffre);
					if (ship!=null)
					{
						
							int counter = 1;
							
							//alle Positionen auffüllen
							//horizontales Schiff:
							if(isHorizontal=='t'){
								counter =1;
							}
							//vertikales Schiff:
							if(isHorizontal=='f')
							{
								counter =GlobalHolder.getInstance().getNumOfRowsCols();
							}
						
						
							for(int y=0; y<ship.getShipLength(); y+=counter){
								
								int pos = i + y;
								
								ship.Positions.add(pos);
								
								//alle abgeschossenen Felder für dieses Schiff auffüllen
								if(Felder[pos].charAt(0)=='h'){
									ship.setHitPosition(pos);
								}
								
							}//end for
					}//end if ship!=null
						
						
				}
			}
		}
			
	}
	
}
		
	

	

