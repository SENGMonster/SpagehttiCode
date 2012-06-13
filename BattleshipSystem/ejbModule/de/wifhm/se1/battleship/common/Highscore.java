package de.wifhm.se1.battleship.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Highscore implements HighscoreInterface {
	
	@Id @GeneratedValue int id;
	
	private int highscore;
	private String game;

	/**
	 * @return the game
	 */
	public String getGame() {
		return game;
	}


	/**
	 * @param game the game to set
	 */
	public void setGame(String game) {
		this.game = game;
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

}
