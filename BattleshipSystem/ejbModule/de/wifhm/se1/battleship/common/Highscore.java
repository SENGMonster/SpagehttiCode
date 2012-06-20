package de.wifhm.se1.battleship.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
public class Highscore implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULTVALUE = 0;
	@Id @GeneratedValue int id;
	
	private int highscore;
	
	@OneToOne(optional = false)
	private User owner;
	
	public Highscore(){
		super();
	}
	
	public Highscore(User user){
		this.owner = user;
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
	@XmlIDREF
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
