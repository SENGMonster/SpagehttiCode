package de.wifh.se1.highscore.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlID;

@Entity
public class Highscore implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULTVALUE = 0;
	@Id String username;
	
	String password;
	
	private int highscore;
	
	
	public Highscore(){
		super();
	}
	
	public Highscore(String username, String password){
		this.username = username;
		this.password = password;
		this.highscore = DEFAULTVALUE;
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
	
	/*
	 * Add points to highscore
	 */
	public void addPoints(int points){
		this.highscore = this.highscore + points;
	}
	@XmlID
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
