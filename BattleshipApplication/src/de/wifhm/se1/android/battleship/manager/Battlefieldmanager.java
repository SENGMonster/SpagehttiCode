package de.wifhm.se1.android.battleship.manager;


import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import de.wifhm.se1.android.activity.R;



public class Battlefieldmanager {


	private Spielvorlage mSpielvorlage;
	private ArrayList<Integer> WaterHits = new ArrayList<Integer>();
	
	public Battlefieldmanager(Spielvorlage spiel){
		
		mSpielvorlage= spiel;
	}
	
	public Schiff lastDestroyedShip;
	private int turnCounter;
	
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
	
	
}
