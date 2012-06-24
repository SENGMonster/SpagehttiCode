package de.wifhm.se1.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.WebServiceCommunicator;
import de.wifhm.se1.android.util.HighscoreListAdapter;
/**
 * 
 * @author Marc Paaßen
 *
 *Klasse bildet die Seite auf der die HighscoreListe angezeigt wird ab, von dieser Seite aus kann ein Spiel gestartet werden.
 */
public class HighscoreActivity extends Activity {
	BattleshipApplication bsstub;
	private final String TAG = "HighscoreActivity";
	
	/**
	 * Initalisiert das Layout der Activity und einen OnCklicklistener für den "Play Game" Button, um ein Spiel zu starten
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscorelist);
		
		bsstub = (BattleshipApplication)getApplication();
		/*try {
			bsstub.getBsStub().addPoints(20);
		} catch (SoapFault e1) {
			// TODO Auto-generated catch block
			Log.e(TAG , e1.toString());
		}*/
		ListView highscorelist = (ListView)findViewById(R.id.highscorelist);
		
		LayoutInflater inflater = this.getLayoutInflater();
		
		LinearLayout header = (LinearLayout)inflater.inflate(R.layout.highscorelist_header, null);
		
		highscorelist.addHeaderView(header);
		//TODO HighscoreListe vom Server beziehen
		
		
		//try {
			//TODO
			//List<String>highscores = bsstub.getBsStub().getHighscoreList();
			List<String>highscores = new ArrayList<String>();
			
			highscores.add("Ramona;200");
			highscores.add("Failure;189");
			highscores.add("Damn;162");
			highscores.add("Brigde;122");
			highscores.add("Test;104");
			highscores.add("Dummy3;80");
			highscores.add("Dummy2;40");
			highscores.add("Haha;20");
			highscores.add("Brain;10");
			highscores.add("xD;-5");
			
			
			if(highscores.size() > 0 ){
				highscorelist.setAdapter(new HighscoreListAdapter(this, highscores));
			}
			else{
				String[] value = {"HighscoreList is empty"};
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, value);
				highscorelist.setAdapter(adapter);
			}
			
		/*} catch (SoapFault e) {
			String[] value = {"No connection"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, value);
			highscorelist.setAdapter(adapter);
		}*/
		
		Button playgame = (Button)findViewById(R.id.playgamebtn);
		
		playgame.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View view) {
				
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HighscoreActivity.this);
				
				Integer size = new Integer(prefs.getString("boardsize", null));
				GlobalHolder.getInstance().setNumOfRowsCols(size);
				
				//Beim Spielstart laden
	            WebServiceCommunicator communicator = new WebServiceCommunicator((BattleshipApplication) getApplication());	
	            String UserGame= communicator.getUserGame();
	            String AgentGame = communicator.getComputerGame();
	            
	            GlobalHolder.getInstance().initializeNew("", "");
	            
				startActivity(new Intent(HighscoreActivity.this, PositionShipActivity.class));
			}
		});
	}
	
	/**
	 * @see LoginActivity#onCreateOptionsMenu(Menu)
	 * Layout bildet zusätzlich in dem Menü den Logout und Prefernces Button ab
	 */
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	/**
	 * @see LoginActivity#onOptionsItemSelected(MenuItem)
	 */
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.logout:
				if(bsstub.getBsStub() != null && bsstub.getAngemeldeterUser() != null){
					try {
						bsstub.getBsStub().logout();
						startActivity(new Intent(HighscoreActivity.this, RegisterLoginActivity.class));
					} catch (SoapFault e) {	}
				}
				break;
			case R.id.preferences:
				startActivity(new Intent(HighscoreActivity.this, BattleshipPreferenceActivity.class));
				break;
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
		return true;
	}
}
