package de.wifhm.se1.android.activity;

import java.util.List;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import de.wifhm.se1.android.common.User;
import de.wifhm.se1.android.util.HighscoreListAdapter;

public class HighscoreActivity extends Activity {
	BattleshipApplication bsstub;
	private final String TAG = "HighscoreActivity";
	
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
		
		
		try {
			//TODO
			List<String>highscores = bsstub.getBsStub().getHighscoreList();
			Log.d("Highscore", highscores.toString());
			
			for(String s : highscores){
				Log.d(TAG, s);
			}
			
			if(highscores.size() > 0 ){
				highscorelist.setAdapter(new HighscoreListAdapter(this, highscores));
			}
			else{
				String[] value = {"HighscoreList is empty"};
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, value);
				highscorelist.setAdapter(adapter);
			}
			
		} catch (SoapFault e) {
			String[] value = {"No connection"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, value);
			highscorelist.setAdapter(adapter);
		}
		
		Button playgame = (Button)findViewById(R.id.playgamebtn);
		
		playgame.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View view) {
				
				
				//Beim Spielstart laden
	            WebServiceCommunicator communicator = new WebServiceCommunicator((BattleshipApplication) getApplication());	
	            String UserGame= communicator.getUserGame();
	            String AgentGame = communicator.getComputerGame();
	            GlobalHolder.getInstance().initializeNew(UserGame, AgentGame);
				
				startActivity(new Intent(HighscoreActivity.this, PositionShipActivity.class));
			}
		});
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
