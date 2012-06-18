package de.wifhm.se1.android.battleship.manager;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import de.wifhm.se1.android.activity.R;



public class Battlefieldmanager {

	//private BattleFieldImageAdapter mImageAdapter;
	private Spielvorlage mSpielvorlage;
	
	public Battlefieldmanager(Spielvorlage spiel){
		
		mSpielvorlage= spiel;
	}
	
	public boolean hasHitAShip(int position, View v, Activity a)
	{
		
		for(Schiff s:mSpielvorlage.getSchiffsliste()){
			for (int pos:s.Positions){
				if (pos==position){
					
					ImageView imageview = (ImageView) v;
					imageview.setImageResource(R.drawable.rot);
					
					MakeToast("Treffer: " + s.getName()+ " auf " + String.valueOf(position), a);
					
					return true;
				}
			}
		}
		
		
		ImageView imageview = (ImageView) v;
		imageview.setImageResource(R.drawable.keintreffer);
		
		MakeToast("Wasser: " + String.valueOf(position), a);
		return false;
	}
	
	
	public void MakeToast(String text, Activity a)
	{
		Toast.makeText(a, text, Toast.LENGTH_SHORT).show();
	}
	
	
}
