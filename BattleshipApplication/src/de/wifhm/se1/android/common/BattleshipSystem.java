package de.wifhm.se1.android.common;

import java.util.List;

import org.ksoap2.SoapFault;


public interface BattleshipSystem {
	/**
	 * Login, logout register functions
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 */
	public User login(String username, String password) throws SoapFault;
	/**
	 * 
	 */
	public void logout() throws SoapFault;
	/**
	 * 
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 */
	public void register(String username, String password) throws SoapFault;
	
	/**
	 * 
	 * @param gamestate
	 * @throws NotLoggedInException
	 */
	public void setGameState(String gamestate) throws SoapFault;	
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 */
	public String getGameState() throws SoapFault;
	
	/**
	 * 
	 * @param points
	 * @throws NotLoggedInException
	 */
	public void setHighscore(int points) throws SoapFault;
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 */
	public Highscore getHighscore() throws SoapFault;
	/**
	 * 
	 * @param points
	 * @throws NotLoggedInException
	 */
	public void addPoints(int points, String password) throws SoapFault;
	
	public List<Highscore> getHighscoreList() throws SoapFault;
	
}
