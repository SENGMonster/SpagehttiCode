
package de.wifhm.se1.battleship.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;

@Entity
@Table(name = "USER")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	
	private String password;
	
	@OneToOne(mappedBy="owner", fetch=FetchType.EAGER, optional=false)
	private GameState gamestate;
	
	@OneToOne(mappedBy="owner", fetch=FetchType.EAGER, optional=false)
	private Highscore highscore;
			
	public User(){
		super();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
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
	public Highscore getHighscore() {
		return highscore;
	}

	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(Highscore highscore) {
		this.highscore = highscore;
	}

	public GameState getGamestate() {
		return gamestate;
	}

	public void setGamestate(GameState gamestate) {
		this.gamestate = gamestate;
	}
}
