package de.wifhm.se1.android.activity;


import org.ksoap2.SoapFault;

import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.WebServiceCommunicator;
import de.wifhm.se1.android.common.BattleshipSystemStub;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends Activity
{
	
    private static final String TAG = "BattleshipStartActivity";
    
    BattleshipApplication bsstub;
        
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finale);
		
		bsstub = (BattleshipApplication)getApplication();
		bsstub.setBsStub(new BattleshipSystemStub());
		
		Button btnEnde = (Button)findViewById(R.id.btnEnde);
		TextView tvEnde = (TextView) findViewById(R.id.tvWinText);
		
		int UserScore = GlobalHolder.getInstance().getUserField().getHighScore();
		int AgentScore = GlobalHolder.getInstance().getComputerField().getHighScore();
		
		int UserSteps = GlobalHolder.getInstance().getUserField().getTurnCounter();
		int AgentSteps = GlobalHolder.getInstance().getComputerField().getTurnCounter();
		
		String itemtext ="";
		
		if (UserScore>AgentScore){
			itemtext += "GEWONNEN!\n";
		}
		else{
			itemtext += "LEIDER VERLOREN!\n";
		}
		
		itemtext+="Ihr Punktestand von diesem Spiel:" +String.valueOf(UserScore)+"in "+ String.valueOf(UserSteps) + " Schritten.";
		
		tvEnde.setText(itemtext);
		
		
		btnEnde.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
				
				//Gamestates leeren
				WebServiceCommunicator wc = new WebServiceCommunicator(bsstub);
				wc.sendAgentGame("\"\"");
				wc.sendPlayerGame("\"\"");
				
				//Punktestand senden
				wc.sendHighscorePoints(GlobalHolder.getInstance().getUserField().getHighScore());
				
				//neues Spiel initalisieren
				GlobalHolder.getInstance().initializeNew("\"\"", "\"\"");
				
				startActivity(new Intent(FinalActivity.this, HighscoreActivity.class));
				Log.i(TAG, "Ende-click");
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
						startActivity(new Intent(FinalActivity.this, FinalActivity.class));
					} catch (SoapFault e) {	}
				}
				break;
			case R.id.preferences:
				startActivity(new Intent(FinalActivity.this, BattleshipPreferenceActivity.class));
				break;
			case R.id.exit:
				break;
		}
		return true;
	}
}