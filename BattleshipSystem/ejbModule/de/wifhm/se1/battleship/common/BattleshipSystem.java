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
	 * @param gamestate
	 * @throws NotLoggedInException
	 */
	public void setPlayerGameState(String playergamestate) throws NotLoggedInException;	
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 */
	public String getPlayerGameState() throws NotLoggedInException;
	
	/**
	 * 
	 * @param agentgamestate
	 * @throws NotLoggedInException
	 */
	public void setAgentGameState(String agentgamestate) throws NotLoggedInException;
	
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 */
	public String getAgentGameState() throws NotLoggedInException;
	
	public void addPoints(int points) throws NotLoggedInException;
	
	public List<User> getHighscoreList();
	
}
