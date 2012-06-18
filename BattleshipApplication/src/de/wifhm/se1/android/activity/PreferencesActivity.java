package de.wifhm.se1.android.activity;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class PreferencesActivity extends Activity {
	
	protected static int sizeint;
	BattleshipApplication bsstub;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		
		bsstub = (BattleshipApplication) getApplication();
		
		final CheckBox saveup = (CheckBox)findViewById(R.id.saveup);
		final Spinner size = (Spinner)findViewById(R.id.spinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.boardsizes, android.R.layout.simple_spinner_item);
		final int[] number = getResources().getIntArray(R.array.sizeint);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    size.setAdapter(adapter);
	    
	    Button save = (Button)findViewById(R.id.savesettings);
	    
	    save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(bsstub.getBsStub() != null && bsstub.getAngemeldeterUser() != null && bsstub.isOnline()){
					try {
						bsstub.getBsStub().setClientSystemSettings(saveup.isChecked(), sizeint);
					} catch (SoapFault e) {
					}
				}
				
			}
	    	
	    });
	    size.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				PreferencesActivity.sizeint = number[position];
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    
	}
}
