
package de.wifhm.se1.battleship.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlID;


@Entity
/**
 * 
 * @author Marc Paaßen
 * 
 * Klasse User bildet die Datenbank-Tabelle User ab, sie besitz folgende Werte, username, password, playergamestate, agentgamestate
 * und highscore.
 *
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
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
		this.highscore = new Integer(0);
		this.playergamestate = "";
		this.agentgamestate = "";
	}

	/**
	 * @return the username
	 */
	@XmlID
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
