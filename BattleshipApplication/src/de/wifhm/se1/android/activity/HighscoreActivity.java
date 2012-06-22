package de.wifhm.se1.android.activity;

import java.util.List;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.wifhm.se1.android.common.User;
import de.wifhm.se1.android.util.HighscoreListAdapter;

public class HighscoreActivity extends Activity {
	BattleshipApplication bsstub;
	private final String TAG = "BLA";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscorelist);
		
		bsstub = (BattleshipApplication)getApplication();
		
		ListView highscorelist = (ListView)findViewById(R.id.highscorelist);
		
		LayoutInflater inflater = this.getLayoutInflater();
		
		LinearLayout header = (LinearLayout)inflater.inflate(R.layout.highscorelist_header, null);
		
		highscorelist.addHeaderView(header);
		//TODO HighscoreListe vom Server beziehen
		
		
		try {
			//TODO
			List<User>highscores = bsstub.getBsStub().getHighscoreList();
			Log.d("Highscore", highscores.toString());
			
			
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
				//TODO startActivity(new Intent());
			}
		});
	}
}
