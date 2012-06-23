package de.wifhm.se1.android.battleship.manager;


import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import de.wifhm.se1.android.activity.R;



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
	
	public HitStates hasHitAShip(int position, BattleFieldImageAdapter mImageAdapter,  Activity a)
	{
		int sunkCounter=0;
		
		for(Schiff s:mSpielvorlage.getSchiffsliste()){
			
			//überprüfen ob alle Tod sind			
			if (s.getIsSunk()) sunkCounter +=1;
			
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
						
						sunkCounter+=1;
						if (sunkCounter==mSpielvorlage.getSchiffsliste().size())
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
		
		//einen Spielzug hochzählen
		turnCounter+=1;
		mImageAdapter.setWasser(position);
		MakeToast("Wasser auf " + String.valueOf(position), a);
		WaterHits.add(position);
		return HitStates.WATER;
	}
	
	
	public void MakeToast(String text, Activity a)
	{
		Toast.makeText(a, text, Toast.LENGTH_SHORT).show();
	}
	
	public int getHighScore(){
		return (GlobalHolder.getInstance().getNumOfRowsCols() * GlobalHolder.getInstance().getNumOfRowsCols()) - turnCounter;
	}
	public int getTurnCounter(){
		return turnCounter;
	}
	
	
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
	
	
	public void deserializeMeFromString(String BattlefieldString){
		
		if (BattlefieldString!=null)
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
		
	

	

