package de.wifhm.se1.android.common;

import java.io.Serializable;



public class Highscore implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int id;
	
	private int highscore;
	
	private User owner;
	
	
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
	
	public void addPoints(int points){
		this.highscore = this.highscore + points;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

}
