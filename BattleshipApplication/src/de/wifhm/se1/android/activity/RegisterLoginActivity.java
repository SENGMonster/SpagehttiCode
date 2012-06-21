package de.wifhm.se1.android.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import de.wifhm.se1.android.common.BattleshipSystemStub;

public class RegisterLoginActivity extends Activity {
	
	private static final String TAG = "RegisterActivity";
	
	private BattleshipApplication systemStub;
	private SharedPreferences prefs;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		systemStub = (BattleshipApplication) this.getApplication();
        systemStub.setBsStub(new BattleshipSystemStub());
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username", null);
        String password = prefs.getString("username", null);
        if(username != null && password != null){
        	setContentView(R.layout.register);
        	
        	final EditText usernametext = (EditText) findViewById(R.id.registerUsername);
        	final EditText passwordtext = (EditText) findViewById(R.id.registerPassword);
        	final EditText repasswordtext = (EditText) findViewById(R.id.registerPasswordRetype);
        	
        	Button send = (Button)findViewById(R.id.send);
        }
        else{
        	setContentView(R.layout.login);
        }
	}
}
