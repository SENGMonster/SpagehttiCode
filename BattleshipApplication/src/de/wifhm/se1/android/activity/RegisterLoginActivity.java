package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.wifhm.se1.android.common.BattleshipSystemStub;

public class RegisterLoginActivity extends Activity {
	
	private static final String TAG = "LoginRegisterActivity";
	
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
        	
        	final Editor editor = prefs.edit();
        	
        	final EditText usernametext = (EditText) findViewById(R.id.registerUsername);
        	final EditText passwordtext = (EditText) findViewById(R.id.registerPassword);
        	final EditText repasswordtext = (EditText) findViewById(R.id.registerPasswordRetype);
        	
        	Button send = (Button)findViewById(R.id.send);
        	Button login = (Button)findViewById(R.id.registerlogin);
        	
        	final TextView failure = (TextView)findViewById(R.id.failuremessage); 
        	
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
							        editor.commit();
							        
							        startActivity(new Intent(RegisterLoginActivity.this, HighscoreActivity.class));
								} catch (SoapFault e) {
									failure.setText("Service isn't reachable");
								}
							}
							else{
								failure.setText("Password fields aren't equal");
							}
						}
						else{
							failure.setText("Password fields couldn't be empty");
						}
					}
					else{
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
        	
        	usernametext.setText(username);
        	passwordtext.setText(password);
        	
        	Button send = (Button)findViewById(R.id.login);
        	Button register = (Button)findViewById(R.id.registerNew);
        	
        	final TextView failure = (TextView)findViewById(R.id.failuremessage);
        	
        	send.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					if(!usernametext.getText().toString().matches("")){
						if(!passwordtext.getText().toString().matches("")){
							try {
								systemStub.getBsStub().login(usernametext.getText().toString(), passwordtext.getText().toString());
								startActivity(new Intent(RegisterLoginActivity.this, HighscoreActivity.class));
							} catch (SoapFault e) {
								failure.setText("Service isn't reachable");
							}
						}
						else{
							failure.setText("Password couldn't be emty");
						}
					}
					else{
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
}
