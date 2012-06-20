package de.wifhm.se1.android.battleship.manager;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import de.wifhm.se1.android.activity.R;



public class Battlefieldmanager {


	private Spielvorlage mSpielvorlage;
	
	public Battlefieldmanager(Spielvorlage spiel){
		
		mSpielvorlage= spiel;
	}
	
	public Schiff lastDestroyedShip;
	
	public HitStates hasHitAShip(int position, BattleFieldImageAdapter mImageAdapter,  Activity a)
	{
		
		for(Schiff s:mSpielvorlage.getSchiffsliste()){
			for (int pos:s.Positions){
				if (pos==position){
					
					
					mImageAdapter.setTreffer(position);
					boolean versenkt =  s.setHitPosition(position);
					if (versenkt){
						MakeToast("Treffer versenkt: " + s.getName()+ " auf " + String.valueOf(position), a);
						lastDestroyedShip = s;
						return HitStates.DESTROYED;
					}
					else{
						MakeToast("Treffer: " + s.getName()+ " auf " + String.valueOf(position), a);
						return HitStates.HIT;
					}
					
					
				}
			}
		}
		
		
		
		mImageAdapter.setWasser(position);
		MakeToast("Wasser: " + String.valueOf(position), a);
		return HitStates.WATER;
	}
	
	
	public void MakeToast(String text, Activity a)
	{
		Toast.makeText(a, text, Toast.LENGTH_SHORT).show();
	}
	
	
}
