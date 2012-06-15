package de.wifhm.se1.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.manager.Battlefieldmanager;
import de.wifhm.se1.android.battleship.manager.BattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.Schiff;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;

public class GridViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Battlefieldmanager mBattlefieldmanager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        
        //fuer Random Layout bei Neustart zuruecksetzen
        Schiff.counter=0;
     
        Spielvorlage spiel = new Spielvorlage1();
        BattleFieldImageAdapter imgadp = new BattleFieldImageAdapter(this,spiel);
        GridView gridview = (GridView) findViewById (R.id.gridview);
        gridview.setAdapter(imgadp);
        
        
        mBattlefieldmanager = new Battlefieldmanager(spiel, GridViewActivity.this);
       

        gridview.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View v, int position,
  					long id) {
            	
            	mBattlefieldmanager.hasHitAShip(position,v); 			
  				
  			}
          	
          });
		
    }
}