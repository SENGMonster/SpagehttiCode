package de.wifhm.se1.android.common;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	private ClientSystemSettings settings;
	private Highscore highscore;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the settings
	 */
	public ClientSystemSettings getSettings() {
		return settings;
	}
	/**
	 * @return the highscore
	 */
	public Highscore getHighscore() {
		return highscore;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(ClientSystemSettings settings) {
		this.settings = settings;
	}
	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(Highscore highscore) {
		this.highscore = highscore;
	}

}
