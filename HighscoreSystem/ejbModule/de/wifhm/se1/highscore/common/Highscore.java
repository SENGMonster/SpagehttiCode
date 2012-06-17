package de.wifhm.se1.highscore.common;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Highscore {
	@Id
	private String username;
	
	private String password;
	
	private int score;

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
	 * @return the score
	 */
	public int getScore() {
		return score;
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
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * Add points to Users Highscore
	 * @param points
	 */
	public void addPoints(int points){
		this.score = this.score + points;
	}
}
