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
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        systemStub = (BattleshipApplication) this.getApplication();
        systemStub.setBsStub(new BattleshipSystemStub());
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
				        editor.commit();
				        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
					} catch (SoapFault e) {
						failure.setVisibility(View.VISIBLE);
						failure.setText(e.getMessage());
						Toast.makeText(RegisterActivity.this, "Registration failed, username already exsits", Toast.LENGTH_LONG);
						Log.e(TAG, "Login failed");
						
					}
				}
				else{
					Toast.makeText(RegisterActivity.this, "Retyped password isn't equal to password", Toast.LENGTH_LONG);
					Log.e(TAG, "Registration failed");
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
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.startmenu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
		return true;
	}
}
