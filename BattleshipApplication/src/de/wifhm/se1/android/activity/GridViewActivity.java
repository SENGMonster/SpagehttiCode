package de.wifhm.se1.android.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.agent.Tester;
import de.wifhm.se1.android.battleship.manager.Battlefieldmanager;
import de.wifhm.se1.android.battleship.manager.BattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.HitStates;

import de.wifhm.se1.android.battleship.manager.Spielvorlage;


public class GridViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Battlefieldmanager mBattlefieldmanager;
	BattleFieldImageAdapter imgadp;
	Tester AgentTester;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        
    
        Spielvorlage spiel = GlobalHolder.getInstance().getUserShips();
        imgadp = new BattleFieldImageAdapter(this,spiel);
        GridView gridview = (GridView) findViewById (R.id.gridview);
        gridview.setAdapter(imgadp);
        
        
       /* TEST AGENT */
       AgentTester = new Tester();
       Button testAgent = (Button) findViewById(R.id.btnTestAgent);
       testAgent.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
				int nextTurn = AgentTester.getNextChoice();				
				HitStates result =  GlobalHolder.getInstance().getUserField().hasHitAShip(nextTurn,  imgadp, GridViewActivity.this);			
				AgentTester.setFieldState(result, nextTurn);
				
				switch(result)
				{
					case HIT:
						imgadp.setTreffer(nextTurn);
	            		AgentTester.setShip2Destroy(nextTurn);
						break;
					case DESTROYED:
						imgadp.setTreffer(nextTurn);
	            		AgentTester.DestroyedShip(GlobalHolder.getInstance().getUserField().lastDestroyedShip);
						break;
					case WATER:
						imgadp.setWasser(nextTurn);
						break;
						
					
				}      
				
			}
		});
        
        
        mBattlefieldmanager = GlobalHolder.getInstance().getUserField();
       

        gridview.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View v, int position,
  					long id) {
            	
            	HitStates result = mBattlefieldmanager.hasHitAShip(position, imgadp, GridViewActivity.this); 

				switch(result)
				{
					case HIT:
						imgadp.setTreffer(position);
						break;
					case DESTROYED:
						imgadp.setTreffer(position);
						break;
					case WATER:
						imgadp.setWasser(position);
						break;
					
				}      
  				
  			}
          	
          });
		
    }
}