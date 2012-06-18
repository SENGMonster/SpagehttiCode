/**
 * 
 */
package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
		
		final TextView failuretext = (TextView)findViewById(R.id.failuremessage);
		
		login.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				if(bsStub.getBsStub() != null){
					try {
						Log.i(TAG, "username: "+username.getText().toString());
						Log.i(TAG, "password: "+password.getText().toString());
						bsStub.getBsStub().login(username.getText().toString(), password.getText().toString());
						LoginActivity.this.setContentView(R.layout.succlogin);
					} catch (SoapFault e) {
						failuretext.setVisibility(View.VISIBLE);
						failuretext.setText(e.getMessage());
						Log.e(TAG, "Login failed");
						Log.e(TAG, e.toString());
					}
				}
				else{
					failuretext.setVisibility(View.VISIBLE);
					failuretext.setText("Service isn't online");
				}
			}
		});
		
		registerNew.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
	}

}
