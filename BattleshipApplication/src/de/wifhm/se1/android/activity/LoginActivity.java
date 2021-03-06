/**
 * 
 */
package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import de.wifhm.se1.android.common.BattleshipSystemStub;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity {
	
	private static final String TAG = "LoginActivity";
	private BattleshipApplication bsStub;
	private SharedPreferences prefs;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		bsStub = (BattleshipApplication)getApplication();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		final EditText username = (EditText)findViewById(R.id.loginUsername);
		final EditText password = (EditText)findViewById(R.id.loginPassword);
		
		Button login = (Button)findViewById(R.id.login);
		Button registerNew = (Button)findViewById(R.id.registerNew);
		
		final TextView failuretext = (TextView)findViewById(R.id.failuremessage);
		
		String prefusername = prefs.getString("username", null);
		String prefpassword = prefs.getString("password", null);
		if(!prefusername.matches("") || prefusername != null){
			username.setText(prefusername);
		}
		if(!prefpassword.matches("") || prefusername != null){
			password.setText(prefpassword);
		}
		
		login.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				if(bsStub.getBsStub() != null){
					try {
						Log.i(TAG, "username: "+username.getText().toString());
						Log.i(TAG, "password: "+password.getText().toString());
						bsStub.setAngemeldeterUser(bsStub.getBsStub().login(username.getText().toString(), password.getText().toString()));
						startActivity(new Intent(LoginActivity.this, HighscoreActivity.class));
					} catch (SoapFault e) {
						failuretext.setVisibility(View.VISIBLE);
						failuretext.setText(e.getMessage());
						Log.e(TAG, "Login failed");
						Log.e(TAG, e.toString());
					}
				}
				else{
					bsStub.setBsStub(new BattleshipSystemStub());
					failuretext.setVisibility(View.VISIBLE);
					failuretext.setText("Service isn't online");
				}
			}
		});
		
		registerNew.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			}
		});
	}
	/**
	 * Method stellt das Menü dar, wenn der Nutzer auf die Menü taste seines Handys drückt
	 */
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startmenu, menu);
		return true;
	}
	/**
	 * OnClickListener für die einzelnen Elemente in dem Optionsmenu
	 */
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
		return true;
	}

}
