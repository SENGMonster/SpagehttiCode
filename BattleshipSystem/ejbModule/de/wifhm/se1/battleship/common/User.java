
package de.wifhm.se1.battleship.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlID;


@Entity
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	
	private String password;
	
	
	private String gamestate;
	
			
	public User(){
		super();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.gamestate = "";
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

	/**
	 * @return the highscore
	 */
	

	/**
	 * @param highscore the highscore to set
	 */
	

	public void setGameState(String s){
		this.gamestate = s;
	}
	
	public String getGameState(){
		return this.gamestate;
	}
}
