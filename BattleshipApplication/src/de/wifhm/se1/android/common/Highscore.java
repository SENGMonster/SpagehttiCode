package de.wifhm.se1.android.common;

import java.io.Serializable;

public class Highscore implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int highscore;
	private String gamename;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the highscore
	 */
	public int getHighscore() {
		return highscore;
	}
	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	/**
	 * @return the gamename
	 */
	public String getGamename() {
		return gamename;
	}
	/**
	 * @param gamename the gamename to set
	 */
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
}
