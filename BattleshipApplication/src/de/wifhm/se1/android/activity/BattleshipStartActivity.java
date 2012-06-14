package de.wifhm.se1.android.activity;


import de.wifhm.se1.android.common.BattleshipSystemStub;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BattleshipStartActivity extends Activity {
	
    private static final String TAG = "BattleshipStartActivity";
    
    BattleshipApplication bsstub;
        
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		bsstub = (BattleshipApplication)getApplication();
		bsstub.setBsStub(new BattleshipSystemStub());
		
		Button login = (Button)findViewById(R.id.log);
		Button register = (Button)findViewById(R.id.reg);
		
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
	}
}