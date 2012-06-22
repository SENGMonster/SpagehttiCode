package de.wifhm.se1.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.agent.Communicator;
import de.wifhm.se1.android.battleship.manager.Battlefieldmanager;
import de.wifhm.se1.android.battleship.manager.BattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.CopyOfBattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.HitStates;
import de.wifhm.se1.android.battleship.manager.WebServiceCommunicator;

import de.wifhm.se1.android.battleship.manager.Spielvorlage;


public class GridViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	
	private Battlefieldmanager mBattlefieldmanager;
	BattleFieldImageAdapter imgadp;
	BattleFieldImageAdapter agent_imgadp;
	Communicator AgentCommunicator;
	ViewSwitcher profilSwitcher;
	Button UserOK;
	Button AgentOK;
	WebServiceCommunicator Serializer;
	
	private boolean isAllowedToSwitch=false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        
        Serializer = new WebServiceCommunicator((BattleshipApplication) getApplication());
        
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
       
       
       AgentOK = (Button) findViewById(R.id.btnAgentOK);
       UserOK = (Button) findViewById(R.id.btnUserOK);
       UserOK.setVisibility(View.INVISIBLE);
       
       AgentOK.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				 if (isAllowedToSwitch)
				 {
					 setIsAllowedToSwitch(false, false);
					 profilSwitcher.showNext();
				 }
			}
		});
       
       UserOK.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
			 if (isAllowedToSwitch)
			 {
				setIsAllowedToSwitch(false, true);
				profilSwitcher.showPrevious();
				while(!isAllowedToSwitch)
				{
					AgentTurn();
				}
			 }
			}
		});
       
       
//       Button testAgent = (Button) findViewById(R.id.btnTestAgent);
//       testAgent.setOnClickListener(new OnClickListener(){
//			public void onClick(View v) {
//				
//				
//				
//			}
//		});
       
   
        
        
        mBattlefieldmanager = GlobalHolder.getInstance().getComputerField();
       

        //WENN DER USER AUF DAS SPIELFELD DES AGENTS KLICKT!
        gridviewAgent.setOnItemClickListener(new OnItemClickListener(){ 
            public void onItemClick(AdapterView<?> arg0, View v, int position,long id) {
            	
            
            	//falls er noch nicht geschossen hat
            	if(!isAllowedToSwitch)
            	{
            	
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
							setIsAllowedToSwitch(true, true);
							break;
						case END:
							MakeEndToast(true, mBattlefieldmanager);
							break;
					} 
            	}
  				
  			}
          	
          });
		
    }
    
    private void AgentTurn(){
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
					setIsAllowedToSwitch(true, false);
					break;
				case END:
					MakeEndToast(false, GlobalHolder.getInstance().getUserField());
					break;
					
				
			}   
    }
    
    private void setIsAllowedToSwitch(boolean value, boolean User)
    {
    	isAllowedToSwitch = value;
    	if (isAllowedToSwitch)
    	{
    		if(User)
    		{
    			UserOK.setVisibility(View.VISIBLE);
    			String UserString = GlobalHolder.getInstance().getUserField().serializeInfoToString();
    			boolean hasSerialized = Serializer.sendPlayerGame(UserString);
    			
    		}else{
    			AgentOK.setVisibility(View.VISIBLE);
    			String AgentString = GlobalHolder.getInstance().getUserField().serializeInfoToString();
    			
    			System.out.println(AgentString);
    		}
    	}
    	else{
    		if(User)
    		{
    			UserOK.setVisibility(View.INVISIBLE);
    			
    		}else{
    			AgentOK.setVisibility(View.INVISIBLE);
    		}
    	}

    }
    
    private void MakeEndToast(boolean user, Battlefieldmanager bm)
    {
    	String text="";
    	if(user){
    		text+="YEAH! Sie haben gewonnen mit " + String.valueOf(bm.getHighScore()) + " Punkten und " + String.valueOf(bm.getTurnCounter()) + "Zügen.";
    	}
    	else{ 
    		text+="Schade! Sie haben verloren. \n Das Handymonster gewinnt mit " + String.valueOf(bm.getHighScore()) + " Punkten und " + String.valueOf(bm.getTurnCounter()) + "Zügen.";
    	}
    	
    	Toast.makeText(GridViewActivity.this, text, Toast.LENGTH_LONG).show();
    }
}