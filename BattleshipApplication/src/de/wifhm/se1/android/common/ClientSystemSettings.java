package de.wifhm.se1.android.common;

import java.io.Serializable;

public class ClientSystemSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private boolean savePasswordUsername;
	private int boardlength;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
