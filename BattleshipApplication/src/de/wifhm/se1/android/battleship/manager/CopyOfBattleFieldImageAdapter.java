package de.wifhm.se1.android.battleship.manager;



import java.util.Hashtable;

import de.wifhm.se1.android.activity.R;
import android.content.Context;


public class CopyOfBattleFieldImageAdapter extends BattleFieldImageAdapter {

	public CopyOfBattleFieldImageAdapter(Context c, Spielvorlage spielv) {
		super(c, spielv);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setSchiffe(){
		dict = new Hashtable<Integer, Integer>();
         
//         for (Schiff s:mSpielvorlage.getSchiffsliste()) {
//         	if(s.Positions != null)
//         	{
//         		String temp = s.getSchiffsname() +": ";
//         		for (int i = 0; i < s.Positions.size(); i++) {
//         			dict.put(s.Positions.get(i), s.getImage());	
//         			temp+=String.valueOf(s.Positions.get(i)) + ";";
//         		}
//         		System.out.println(temp);
//         	}
//         }
         
         int counter = GlobalHolder.getInstance().getNumOfRowsCols() *GlobalHolder.getInstance().getNumOfRowsCols() ;
         for (int i = 0; i < counter; i++) {
         	if (dict.get(i)==null) dict.put(i, R.drawable.wasser);
 		}
	}
    
    
}
