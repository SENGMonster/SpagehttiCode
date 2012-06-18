package de.wifhm.se1.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HighscoreActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscorelist);
		
		ListView highscorelist = (ListView)findViewById(R.id.highscorelist);
		
		LayoutInflater inflater = this.getLayoutInflater();
		
		LinearLayout header = (LinearLayout)inflater.inflate(R.layout.highscorelist_header, null);
		
		highscorelist.addHeaderView(header);
		String [] test = {"Test", "Test", "Test"};
		
		highscorelist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test));
		Button playgame = (Button)findViewById(R.id.playgamebtn);
		
		playgame.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View view) {
				//TODO startActivity(new Intent());
			}
		});
	}
}
