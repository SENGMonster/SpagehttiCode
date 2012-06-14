/**
 * 
 */
package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
	
	private static final String TAG = "LoginActivity";
	private BattleshipApplication bsStub;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		bsStub = (BattleshipApplication)getApplication();
		
		final EditText username = (EditText)findViewById(R.id.loginUsername);
		final EditText password = (EditText)findViewById(R.id.loginPassword);
		
		Button login = (Button)findViewById(R.id.login);
		Button registerNew = (Button)findViewById(R.id.registerNew);
		
		login.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
					try {
						Log.i(TAG, "username: "+username.getText().toString());
						Log.i(TAG, "password: "+password.getText().toString());
						bsStub.getBsStub().login(username.getText().toString(), password.getText().toString());
						LoginActivity.this.setContentView(R.layout.succregister);
					} catch (SoapFault e) {
						Toast.makeText(LoginActivity.this, "Login failed, username already exsits", Toast.LENGTH_LONG);
						Log.e(TAG, "Login failed");
					}
				}
		});
		
		registerNew.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
	}

}
