package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.Toast;
import de.wifhm.se1.android.common.BattleshipSystemStub;

public class RegisterActivity extends Activity {
	
	private static final String TAG = "RegisterActivity";
	
	private BattleshipApplication systemStub;
	private SharedPreferences prefs;
	
	/** 
	 * 
	 * Called when the activity is first created.
	 * Stellt das Reigster-Layout dar und implementiert die Register-Funktionen
	 * Greift auf die Klasse BattleshipApplication zu, und darin auf die Instanz des BattleshipSystemSubs und deren Methoden */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        systemStub = (BattleshipApplication) this.getApplication();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Editor editor = prefs.edit();
        
        if(systemStub == null){
        	systemStub.setBsStub(new BattleshipSystemStub());
        }
        
        final EditText username = (EditText)findViewById(R.id.registerUsername);
        final EditText password = (EditText)findViewById(R.id.registerPassword);
        final EditText repassword = (EditText)findViewById(R.id.registerPasswordRetype);
        
        final TextView failure = (TextView)findViewById(R.id.failuremessage2);
        
        Button register = (Button)findViewById(R.id.send);
        Button login = (Button)findViewById(R.id.registerlogin);
        
        register.setOnClickListener(new OnClickListener(){

			public void onClick(View view) {
				if(password.getText().toString().equals(repassword.getText().toString())){
					try {
						systemStub.getBsStub().register(username.getText().toString(), password.getText().toString());
						//Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
						//startActivity(i);

				        editor.putString("username", username.getText().toString());
				        editor.putString("password", password.getText().toString());
				        editor.putString("boardsize", "10");
				        editor.putBoolean("savedata", true);
				        editor.commit();
				        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
					} catch (SoapFault e) {
						failure.setVisibility(View.VISIBLE);
						failure.setText(e.getMessage());
						Toast.makeText(RegisterActivity.this, "Registration failed, username already exsits", Toast.LENGTH_LONG);
						Log.e(TAG, "Registration failed: " +e.getMessage());
						
					}
				}
				else{
					Toast.makeText(RegisterActivity.this, "Retyped password isn't equal to password", Toast.LENGTH_LONG);
					Log.e(TAG, "Passwords aren't equal");
					failure.setVisibility(View.VISIBLE);
					failure.setText("Passwords aren't equal");
				}
			}
        	
        });
        
        login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
				
			}
        	
        });
    }
	/**
	 * Options-Menü siehe 
	 * @see RegisterLoginActivity#onCreateOptionsMenu(Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startmenu, menu);
		return true;
	}
	/**
	 * Clicklistener siehe 
	 * @see RegisterLoginActivity#onOptionsItemSelected(MenuItem)
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
