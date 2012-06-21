package de.wifhm.se1.battleship.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
public class GameState implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	int id;
	
	@OneToOne(optional = false)
	private User owner;
	
	private String state;

	public String getState() {
		return state;
	}

	public void setState(User user) {
		this.state = null;
		this.owner = user;
		this.owner.setGamestate(this);
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

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
