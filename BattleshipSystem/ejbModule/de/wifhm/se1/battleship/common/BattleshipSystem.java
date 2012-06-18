package de.wifhm.se1.battleship.common;

import java.util.List;

import javax.ejb.Remote;

import de.wifhm.se1.battleship.server.exceptions.InvalidPasswordException;
import de.wifhm.se1.battleship.server.exceptions.InvalidUsernameException;
import de.wifhm.se1.battleship.server.exceptions.NotLoggedInException;

@Remote
public interface BattleshipSystem {
	/**
	 * Login, logout register functions
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 */
	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException;
	/**
	 * 
	 */
	public void logout();
	/**
	 * 
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 */
	public void register(String username, String password) throws InvalidUsernameException;
	
	/**
	 * 
	 * @param settings
	 * @throws NotLoggedInException
	 */
	public void setClientSystemSetting(ClientSystemSettings settings) throws NotLoggedInException;
	/**
	 * 
	 * @param savePasswordUsername
	 * @param boardlength
	 * @throws NotLoggedInException
	 */
	public void setClientSystemSettings(boolean savePasswordUsername, int boardlength) throws NotLoggedInException;
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 */
	public ClientSystemSettings getClientSystemSettings() throws NotLoggedInException;
	/**
	 * 
	 * @param points
	 * @throws NotLoggedInException
	 */
	public void setHighscore(int points) throws NotLoggedInException;
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 */
	public Highscore getHighscore() throws NotLoggedInException;
	
	public List<Highscore> getHighscoreList();
	
}
