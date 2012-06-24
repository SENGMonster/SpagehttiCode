
package de.wifhm.se1.android.common;

import java.io.Serializable;

/**
 * 
 * @author Marc Paaßen
 * 
 * Klasse bildet die User-Klasse ab, die auf dem Server verwaltet wird. 
 * Diese Klasse wird benötigt damit die SoapObjects die vom Server kommen, wieder in einen User 
 * gecastet werden können.
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	
	private String playergamestate;
	private String agentgamestate;
	
	private Integer highscore;
	
			
	public String getPlayerGameState() {
		return playergamestate;
	}

	public void setPlayerGameState(String playergamestate) {
		this.playergamestate = playergamestate;
	}

	public String getAgentGameState() {
		return agentgamestate;
	}

	public void setAgentGameState(String agentgamestate) {
		this.agentgamestate = agentgamestate;
	}

	public Integer getHighscore() {
		return highscore;
	}

	public void setHighscore(Integer highscore) {
		this.highscore = highscore;
	}
	
	public void addPoints(int points){
		this.highscore = this.highscore + points;
	}

	public User(){
		super();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.playergamestate = "";
		this.agentgamestate = "";
		this.highscore = 0;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
