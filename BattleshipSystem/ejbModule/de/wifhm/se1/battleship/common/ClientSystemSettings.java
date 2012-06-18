package de.wifhm.se1.battleship.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ClientSystemSettings implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue int id;
	
	private boolean savePasswordUsername;
	private int boardlength;
	
	public ClientSystemSettings(){
		super();
	}
	public ClientSystemSettings(boolean savePasswordUsername, int boardlength){
		this.savePasswordUsername = savePasswordUsername;
		this.boardlength = boardlength;
	}
	/**
	 * @return the savePasswordUsername
	 */
	public boolean isSavePasswordUsername() {
		return savePasswordUsername;
	}
	/**
	 * @param savePasswordUsername the savePasswordUsername to set
	 */
	public void setSavePasswordUsername(boolean savePasswordUsername) {
		this.savePasswordUsername = savePasswordUsername;
	}
	/**
	 * @return the boardlength
	 */
	public int getBoardlength() {
		return boardlength;
	}
	/**
	 * @param boardlength the boardlength to set
	 */
	public void setBoardlength(int boardlength) {
		this.boardlength = boardlength;
	}
}
