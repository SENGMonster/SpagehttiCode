package de.wifhm.se1.android.activity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import de.wifhm.se1.android.common.BattleshipSystemStub;
import de.wifhm.se1.android.common.User;
/**
 * 
 * @author Jens Moelders
 * Klasse repräsentiert den gemeinsamen Zustand der Gesamten BattleshipApllication
 *
 */
public class BattleshipApplication extends Application {
	
	private User angemeldeterUser;
	private BattleshipSystemStub bsStub;
	
	
	/**
	 * Methode prüft ob das Endgerät in ein Netzwerk(Mobiles-Internet bzw. Wlan) eingewählt ist.
	 * @return
	 */
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
	 * Methode gibt den angemeldenUser zurück
	 */
	public User getAngemeldeterUser() {
		return angemeldeterUser;
	}
	
	/**
	 * @return the bsStub
	 * Methode gibt die Instanz das BattleshipSystemStubs zurück
	 */
	public BattleshipSystemStub getBsStub() {
		return bsStub;
	}
	
	/**
	 * @param angemeldeterUser the angemeldeterUser to set
	 * Methode um den zustand des Angemeldeten Users zu ändern
	 */
	public void setAngemeldeterUser(User angemeldeterUser) {
		this.angemeldeterUser = angemeldeterUser;
	}
	
	/**
	 * @param bsStub the bsStub to set
	 * Methode um die Instanz des BattleshipSystemStubs zu ändern
	 */
	public void setBsStub(BattleshipSystemStub bsStub) {
		this.bsStub = bsStub;
	}
	
	
	
}
