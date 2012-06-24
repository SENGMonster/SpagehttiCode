package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


/**
 * 
 * @author Ramona
 *
 * Stellt das eigentliche Spielverhalten dar.
 * Enthält eine GridView zur Anzeige des Spielfelds des Users, und eine für die Anzeige des Computerspielfeldes.
 * Bei einem Klick des Benutzers auf das GridView Computerspielfeld wird die Anfrage für das Feld an die Funktion @see {@link Battlefieldmanager#hasHitAShip(int, BattleFieldImageAdapter, Activity)} weitergeleitet.
 * Solange das Ergebnis der Routine nicht Wasser ist, wird das Klicken des Benutzers auf das Spielfeld ausgewertet. 
 * Andernfalls wird die Anzeige mit Hilfe des ViewSwitchers auf das Spielfeld des Benutzers geändert und der Agent wird aufgefordert so lange zu schießen bist auch er Wasser erhält.
 * Der Benutzer bestätigt mit einem OK Button jeweils dass er das Ergebnis des Schusses gesehen hat und die Ansicht geändert werden kann.
 * Sollte der Benutzer die Ansicht nict wechseln dürfen, wird der OK Button ausgeblendet.
 * Nach Beendigung des Spiels wird der Benutzer auf die Final Activity umgeleitet.
 */
public class GridViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	private BattleshipApplication bsstub;
	
	private Battlefieldmanager mBattlefieldmanager;
	BattleFieldImageAdapter imgadp;
	BattleFieldImageAdapter agent_imgadp;
	Communicator AgentCommunicator;
	ViewSwitcher profilSwitcher;
	Button UserOK;
	Button AgentOK;
	WebServiceCommunicator Serializer;
	
	private boolean isAllowedToSwitch=false;
	
	/**
	 * Lädt die Steuerelemente in die Variablen,
	 * initialisiert die Ansichten und deckt die in @see GridViewActivity beschriebene Funktionaltät ab.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        
        this.bsstub = (BattleshipApplication)getApplication();
        
        Serializer = new WebServiceCommunicator((BattleshipApplication) getApplication());
        
        profilSwitcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);        
            
        imgadp = new BattleFieldImageAdapter(this,GlobalHolder.getInstance().getUserShips(), GlobalHolder.getInstance().getUserField().getWaterHits());
        GridView gridview = (GridView) findViewById (R.id.gridview);
        gridview.setAdapter(imgadp);
            
       /* AGENT COMMUNICATOR */
       AgentCommunicator = new Communicator();
       AgentCommunicator.setComputerShips();
              
       agent_imgadp = new CopyOfBattleFieldImageAdapter(this, GlobalHolder.getInstance().getComputerShips(), GlobalHolder.getInstance().getComputerField().getWaterHits());
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
							MakeEndToast();
							break;
					} 
            	}
  				
  			}
          	
          });
		
    }
    
     
    /**
     * Handelt die Anfrage an die künstliche Intellligenz aka Agent für das nächste gewählte Spielfeld ab und leitet das Ergebnis an den Battlefieldmananger
     * des Benutzers weiter, welcher den Schuss auswertet. Die Auswertung des Schusses welche in Form eines Wertes von @see HitStates zurückgeliefert wird,
     * wird gegebenenfalls an den Agent zurückgeliefert.
     * Die Kommunikation mit dem Agent erfolgt über die Klasse @see Communicator
     * 
     */
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
					MakeEndToast();
					break;
					
				
			}   
    }
    
    /**
     * 
     * @param value 
     * der Wert auf den die Boolean Variable gesetzt werden soll:
     * bei True darf der Benutzer die Ansicht wechseln und seine Schüsse werden ignoriert
     * bei False wird er durch Ausblenden des Buttons am Wechseln der Ansicht gehindert und er muss Schießen.
     * @param User
     * gibt an ob der Benutzer sich gerade auf der Agent oder der User Ansicht befindet.
     * true: Useransicht
     * false: Agentansicht
     * 
     */    
    private void setIsAllowedToSwitch(boolean value, boolean User)
    {
    	isAllowedToSwitch = value;
    	if (isAllowedToSwitch)
    	{
    		if(User)
    		{
    			UserOK.setVisibility(View.VISIBLE);
    			String UserString = GlobalHolder.getInstance().getUserField().serializeInfoToString();
    			//boolean hasSerialized = Serializer.sendPlayerGame(UserString);
    			
    		}else{
    			AgentOK.setVisibility(View.VISIBLE);
    			String AgentString = GlobalHolder.getInstance().getUserField().serializeInfoToString();
    			//boolean hasSerialized = Serializer.sendPlayerGame(AgentString);
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
    
    public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.logout:
				if(bsstub.getBsStub() != null && bsstub.getAngemeldeterUser() != null){
					try {
						bsstub.getBsStub().logout();
						startActivity(new Intent(GridViewActivity.this, RegisterLoginActivity.class));
					} catch (SoapFault e) {	}
				}
				break;
			case R.id.preferences:
				startActivity(new Intent(GridViewActivity.this, BattleshipPreferenceActivity.class));
				break;
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
		return true;
	}
    
	/**
	 * Hilfsfunktion zum Einblenden des Ergebnis des Schusses (Wasser, Treffer)
	 */
    private void MakeEndToast()
    {
    	startActivity(new Intent(GridViewActivity.this, FinalActivity.class));		
    }
}