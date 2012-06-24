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
/**
 * 
 * @author Kai
 * 
 * Zeigt dem Benutzer das Spielergebnis an. 
 * Der Benutzer kann durch bestätigen des Button Fertig zurück auf die Highscore Liste, von wo aus er ein neues Spiel starten kann. 
 * Der Benutzer kann das Menü aufrufen und sich dort ausloggen, seine Einstellungen ändern oder beenden.
 * 
 */
public class FinalActivity extends Activity
{
	
    private static final String TAG = "BattleshipStartActivity";
    
    BattleshipApplication bsstub;
    
    /**
     * Beim initialisieren des Layouts wird das Spielergebnis aus GlobalHolder geholt und zur Anzeige in die TextView geschrieben.
     * Beim Drücken auf den Button Fertig wird der letzte Spielstand gelöscht.
     */
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
			itemtext += "GEWONNEN!\n\n";
		}
		else{
			itemtext += "LEIDER VERLOREN!\n\n";
			UserScore=-UserScore;
		}
		
		itemtext+="Sie holten " +String.valueOf(UserScore)+" Punkte in "+ String.valueOf(UserSteps) + " Schritten.";
		
		tvEnde.setText(itemtext);
		
		
		btnEnde.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
				
				//Gamestates leeren
				WebServiceCommunicator wc = new WebServiceCommunicator(bsstub);
				wc.sendAgentGame("");
				wc.sendPlayerGame("");
				
				//Punktestand senden
				wc.sendHighscorePoints(GlobalHolder.getInstance().getUserField().getHighScore());
				
				//neues Spiel initalisieren
				GlobalHolder.getInstance().initializeNew("", "");
				
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
						startActivity(new Intent(FinalActivity.this, RegisterLoginActivity.class));
					} catch (SoapFault e) {	}
				}
				break;
			case R.id.preferences:
				startActivity(new Intent(FinalActivity.this, BattleshipPreferenceActivity.class));
				break;
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
		return true;
	}
}