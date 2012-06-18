package de.wifhm.se1.android.activity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import de.wifhm.se1.android.common.BattleshipSystemStub;
import de.wifhm.se1.android.common.User;

public class BattleshipApplication extends Application {
	
	private User angemeldeterUser;
	private BattleshipSystemStub bsStub;
	
	
	
	public boolean isOnline(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		if(networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * @return the angemeldeterUser
	 */
	public User getAngemeldeterUser() {
		return angemeldeterUser;
	}
	
	/**
	 * @return the bsStub
	 */
	public BattleshipSystemStub getBsStub() {
		return bsStub;
	}
	
	/**
	 * @param angemeldeterUser the angemeldeterUser to set
	 */
	public void setAngemeldeterUser(User angemeldeterUser) {
		this.angemeldeterUser = angemeldeterUser;
	}
	
	/**
	 * @param bsStub the bsStub to set
	 */
	public void setBsStub(BattleshipSystemStub bsStub) {
		this.bsStub = bsStub;
	}
	
	
	
}
