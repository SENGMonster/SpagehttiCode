package de.wifhm.se1.android.common;

import org.ksoap2.SoapFault;

public interface BattleshipSystem {
	/**
	 * Login, logout register functions
	 * @param username
	 * @param password
	 * @throws SoapFault
	 */
	public User login(String username, String password) throws SoapFault;
	/**
	 * 
	 * @throws SoapFault
	 */
	public void logout() throws SoapFault;
	/**
	 * 
	 * @param username
	 * @param password
	 * @throws SoapFault
	 */
	public void register(String username, String password) throws SoapFault;
	
	/**
	 * 
	 * @param settings
	 * @throws SoapFault
	 */
	public void setClientSystemSetting(ClientSystemSettings settings) throws SoapFault;
	/**
	 * 
	 * @param savePasswordUsername
	 * @param boardlength
	 * @throws SoapFault
	 */
	public void setClientSystemSettings(boolean savePasswordUsername, int boardlength) throws SoapFault;
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 */
	public ClientSystemSettings getClientSystemSettings() throws SoapFault;
	/**
	 * 
	 * @param points
	 * @throws SoapFault
	 */
	public void setHighscore(int points) throws SoapFault;
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 */
	public Highscore getHighscore() throws SoapFault;
}
