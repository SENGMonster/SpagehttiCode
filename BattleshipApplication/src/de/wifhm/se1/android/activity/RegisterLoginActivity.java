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
import de.wifhm.se1.android.common.BattleshipSystemStub;

/**
 * 
 * @author Holger Tenbeitel
 * 
 * Klasse bildet die StartActivity der Applikation dar, auf dieser Seite der App kann der Nutzer sich entweder einloggen oder registrieren
 * jenachdem ob der Nutzer sich im Vorfeld schon einmal registriert hat
 *
 */
public class RegisterLoginActivity extends Activity {
	
	//private static final String TAG = "LoginRegisterActivity";
	
	private BattleshipApplication systemStub;
	private SharedPreferences prefs;
	
	/**
	 * Überschreibt die onCreate()-Methode von Android. In dieser Methode werden die Layouts und Grafischen Felder der Applikation initialisiert
	 * Zusätzlich werden den Buttons OnClickListener zugeordent, damit der User mit der OBerfläche interagieren kann.
	 * In dieser Methode werden zudem die in Android hinterlegten Einstelleung abruft.
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		systemStub = (BattleshipApplication) this.getApplication();
        systemStub.setBsStub(new BattleshipSystemStub());
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password", "");
        
        if(username.matches("") && password.matches("")){
        	setContentView(R.layout.register);
        	
        	final Editor editor = prefs.edit();
        	
        	final EditText usernametext = (EditText) findViewById(R.id.registerUsername);
        	final EditText passwordtext = (EditText) findViewById(R.id.registerPassword);
        	final EditText repasswordtext = (EditText) findViewById(R.id.registerPasswordRetype);
        	
        	Button send = (Button)findViewById(R.id.send);
        	Button login = (Button)findViewById(R.id.registerlogin);
        	
        	final TextView failure = (TextView)findViewById(R.id.failuremessage2); 
        	
        	send.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					if(!usernametext.getText().toString().matches("")){
						if(!(passwordtext.getText().toString().matches("") || repasswordtext.getText().toString().matches(""))){
							if(passwordtext.getText().toString().equals(repasswordtext.getText().toString())){
								try {
									systemStub.getBsStub().register(usernametext.getText().toString(), passwordtext.getText().toString());
									editor.putString("username", usernametext.getText().toString());
							        editor.putString("password", passwordtext.getText().toString());
							        editor.putString("boardsize", "10");
							        editor.putBoolean("savedata", true);
							        editor.commit();
							        
							        startActivity(new Intent(RegisterLoginActivity.this, LoginActivity.class));
								} catch (SoapFault e) {
									failure.setVisibility(View.VISIBLE);
									failure.setText(e.getMessage());
								}
							}
							else{
								failure.setVisibility(View.VISIBLE);
								failure.setText("Password fields aren't equal");
							}
						}
						else{
							failure.setVisibility(View.VISIBLE);
							failure.setText("Password fields couldn't be empty");
						}
					}
					else{
						failure.setVisibility(View.VISIBLE);
						failure.setText("Please enter a username");
					}
					
				}
        		
        	});
        	
        	login.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(RegisterLoginActivity.this, LoginActivity.class));
					
				}
        		
        	});
        }
        else{
        	setContentView(R.layout.login);
        	
        	final EditText usernametext = (EditText) findViewById(R.id.loginUsername);
        	final EditText passwordtext = (EditText) findViewById(R.id.loginPassword);
        	if(prefs.getBoolean("savedata", false)){
        		usernametext.setText(username);
            	passwordtext.setText(password);
        	}
        	
        	
        	Button send = (Button)findViewById(R.id.login);
        	Button register = (Button)findViewById(R.id.registerNew);
        	
        	final TextView failure = (TextView)findViewById(R.id.failuremessage);
        	
        	send.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					if(!usernametext.getText().toString().matches("")){
						if(!passwordtext.getText().toString().matches("")){
							try {
								systemStub.setAngemeldeterUser(systemStub.getBsStub().login(usernametext.getText().toString(), passwordtext.getText().toString()));
								startActivity(new Intent(RegisterLoginActivity.this, HighscoreActivity.class));
							} catch (SoapFault e) {
								failure.setVisibility(View.VISIBLE);
								Log.e("RegisterLogin", e.getMessage());
								failure.setText(e.getMessage());
							}
						}
						else{
							failure.setVisibility(View.VISIBLE);
							failure.setText("Password couldn't be empty");
						}
					}
					else{
						failure.setVisibility(View.VISIBLE);
						failure.setText("Username couldn't be empty");
					}
				}
        		
        	});
        	
        	register.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(RegisterLoginActivity.this, RegisterActivity.class));
					
				}
        		
        	});
        	
        	
        }
        
	}
	
	
	/**
	 * Options-Menü siehe 
	 * @see LoginActivity#onCreateOptionsMenu(Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startmenu, menu);
		return true;
	}
	
	/**
	 * Clicklistener siehe 
	 * @see LoginActivity#onOptionsItemSelected(MenuItem)
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
