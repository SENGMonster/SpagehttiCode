package de.wifhm.se1.android.activity;

import android.app.Application;
import de.wifhm.se1.android.common.BattleshipSystemStub;
import de.wifhm.se1.android.common.User;

public class BattleshipApplication extends Application {
	
	private User angemeldeterUser;
	private BattleshipSystemStub bsStub;
	
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
