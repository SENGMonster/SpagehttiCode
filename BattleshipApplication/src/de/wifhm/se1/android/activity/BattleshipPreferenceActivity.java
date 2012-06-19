package de.wifhm.se1.android.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BattleshipPreferenceActivity extends PreferenceActivity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);
	}
}
