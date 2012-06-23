package de.wifhm.se1.android.battleship.manager;



import java.util.ArrayList;
import java.util.Hashtable;

import de.wifhm.se1.android.activity.R;
import android.content.Context;


public class CopyOfBattleFieldImageAdapter extends BattleFieldImageAdapter {
	
	public CopyOfBattleFieldImageAdapter(Context c, Spielvorlage spielv, ArrayList<Integer> Water) {
		super(c, spielv, Water);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setSchiffe(){
		dict = new Hashtable<Integer, Integer>();

		 if (Waterfields!=null)
	    {	 
		  //alle Wasser und abgeschossenen Positionen setzen
		   	 for(int el:Waterfields)
		   	 {
		   		 dict.put(el, R.drawable.keintreffer);
		   	 }
	    }
	        
	   	 
	   	 for (Schiff s:mSpielvorlage.getSchiffsliste()) {
	   	
	   		 //alle Treffer setzen
	   		 if(s.getHitPositions().size()>0)
	   		 {
	   			 for(int i=0; i<s.getHitPositions().size(); i++)
	   			 {
	   				 dict.put(i, R.drawable.rot);
	   			 }
	   		 }
	   	}
         
         int counter = GlobalHolder.getInstance().getNumOfRowsCols() *GlobalHolder.getInstance().getNumOfRowsCols() ;
         for (int i = 0; i < counter; i++) {
         	if (dict.get(i)==null) dict.put(i, R.drawable.wasser);
 		}
	}
    
    
}
