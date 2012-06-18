package de.wifhm.se1.android.activity;


import org.ksoap2.SoapFault;

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

public class BattleshipStartActivity extends Activity
{
	
    private static final String TAG = "BattleshipStartActivity";
    
    BattleshipApplication bsstub;
        
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bsstub = (BattleshipApplication)getApplication();
		bsstub.setBsStub(new BattleshipSystemStub());
		
		Button login = (Button)findViewById(R.id.log);
		Button register = (Button)findViewById(R.id.reg);
		Button spielfeld = (Button)findViewById(R.id.btnspielfeld);
		Button schiffePo = (Button) findViewById(R.id.btnpositionship);
		Button high = (Button)findViewById(R.id.gotothighscore);
		
		login.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				startActivity(new Intent(BattleshipStartActivity.this, LoginActivity.class));
				Log.i(TAG, "login-click");
			}
		});
		
		register.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				startActivity(new Intent(BattleshipStartActivity.this, RegisterActivity.class));
				Log.i(TAG, "register-click");
			}
		});
		
		
		spielfeld.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				startActivity(new Intent(BattleshipStartActivity.this, GridViewActivity.class));
				Log.i(TAG, "spielfeld-click");
			}
		});
		
		schiffePo.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				startActivity(new Intent(BattleshipStartActivity.this, PositionShipActivity.class));
				Log.i(TAG, "schiffepoonsiti-click");
			}
		});
		
		high.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(BattleshipStartActivity.this, HighscoreActivity.class));
				
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
						startActivity(new Intent(BattleshipStartActivity.this, BattleshipStartActivity.class));
					} catch (SoapFault e) {	}
				}
				break;
			case R.id.preferences:
				startActivity(new Intent(BattleshipStartActivity.this, PreferencesActivity.class));
				break;
			case R.id.exit:
				break;
		}
		return true;
	}
}