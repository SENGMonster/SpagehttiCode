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
	 * @param points
	 * @throws NotLoggedInException
	 */
	public void addPoints(int points) throws SoapFault;

	/**
	 * 
	 * @param playergamestate
	 * @throws SoapFault
	 */
	public void setPlayerGameState(String playergamestate) throws SoapFault;	
	
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 */
	public String getPlayerGameState() throws SoapFault;
	
	/**
	 * 
	 * @param agentgamestate
	 * @throws SoapFault
	 */
	public void setAgentGameState(String agentgamestate) throws SoapFault;
	
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 */
	public String getAgentGameState() throws SoapFault;
	
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 */
	public List<User> getHighscoreList() throws SoapFault;
	
}
