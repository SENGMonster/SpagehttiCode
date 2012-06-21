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
import android.widget.ViewSwitcher;
import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.agent.Communicator;
import de.wifhm.se1.android.battleship.manager.Battlefieldmanager;
import de.wifhm.se1.android.battleship.manager.BattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.CopyOfBattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.HitStates;

import de.wifhm.se1.android.battleship.manager.Spielvorlage;


public class GridViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Battlefieldmanager mBattlefieldmanager;
	BattleFieldImageAdapter imgadp;
	BattleFieldImageAdapter agent_imgadp;
	Communicator AgentCommunicator;
	ViewSwitcher profilSwitcher;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        
        profilSwitcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);        
    
        Spielvorlage spiel = GlobalHolder.getInstance().getUserShips();
        imgadp = new BattleFieldImageAdapter(this,spiel);
        GridView gridview = (GridView) findViewById (R.id.gridview);
        gridview.setAdapter(imgadp);
            
       /* AGENT COMMUNICATOR */
       AgentCommunicator = new Communicator();
       AgentCommunicator.setComputerShips();
       
       Spielvorlage AgentSpiel = GlobalHolder.getInstance().getComputerShips();
       agent_imgadp = new CopyOfBattleFieldImageAdapter(this, AgentSpiel);
       GridView gridviewAgent = (GridView) findViewById(R.id.gridviewAgent);
       gridviewAgent.setAdapter(agent_imgadp);
       
       
       Button AgentOK = (Button) findViewById(R.id.btnAgentOK);
       Button UserOK = (Button) findViewById(R.id.btnUserOK);
       
       AgentOK.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				profilSwitcher.showNext();
			}
		});
       
       UserOK.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				profilSwitcher.showPrevious();
			}
		});
       
       
       Button testAgent = (Button) findViewById(R.id.btnTestAgent);
       testAgent.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
				int nextTurn = AgentCommunicator.getNextChoice();				
				HitStates result =  GlobalHolder.getInstance().getUserField().hasHitAShip(nextTurn,  imgadp, GridViewActivity.this);			
				AgentCommunicator.setFieldState(result, nextTurn);
				
				switch(result)
				{
					case HIT:
						imgadp.setTreffer(nextTurn);
	            		AgentCommunicator.setShip2Destroy(nextTurn);
						break;
					case DESTROYED:
						imgadp.setTreffer(nextTurn);
	            		AgentCommunicator.DestroyedShip(GlobalHolder.getInstance().getUserField().lastDestroyedShip);
						break;
					case WATER:
						imgadp.setWasser(nextTurn);
						break;
						
					
				}   
				
			}
		});
        
        
        mBattlefieldmanager = GlobalHolder.getInstance().getComputerField();
       

        //WENN DER USER AUF DAS SPIELFELD DES AGENTS KLICKT!
        gridviewAgent.setOnItemClickListener(new OnItemClickListener(){ 
            public void onItemClick(AdapterView<?> arg0, View v, int position,long id) {
            	
            	HitStates result = mBattlefieldmanager.hasHitAShip(position, agent_imgadp, GridViewActivity.this); 

				switch(result)
				{
					case HIT:
						agent_imgadp.setTreffer(position);
						break;
					case DESTROYED:
						agent_imgadp.setTreffer(position);
						break;
					case WATER:
						agent_imgadp.setWasser(position);
						break;
				}      
  				
  			}
          	
          });
		
    }
}