package de.wifhm.se1.android.common;

import java.util.List;

import org.ksoap2.SoapFault;

/**
 * 
 * @author Cornelia Hensen
 * 
 * Interface stellt die Methoden für die Interaktion zwischen Client und Server bereit
 *
 */
public interface BattleshipSystem {
	/**
	 * Login, logout register functions
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * 
	 * Funktion für das Einloggen eines Benutzers, username und password identifizieren den User
	 */
	public User login(String username, String password) throws SoapFault;
	/**
	 * Logout Methode
	 */
	public void logout() throws SoapFault;
	/**
	 * 
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 * 
	 * Registrieren eines neuen Benutzers mit den Parametern username und password
	 */
	public void register(String username, String password) throws SoapFault;
	
	/**
	 * 
	 * @param points
	 * @throws NotLoggedInException
	 * 
	 * Funktion addiert Punkte auf den Highscore-Punkte-Stand des jeweiligen Users
	 */
	public void addPoints(int points) throws SoapFault;

	/**
	 * 
	 * @param playergamestate
	 * @throws SoapFault
	 * Methode setzt den GameState des Users des aktuellen Spiel und leitet ihn weiter an den Server damit dieser ihn peristieren kann
	 */
	
	public void setPlayerGameState(String playergamestate) throws SoapFault;	
	
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 * Methode liefert den gespeicherten Wert für den GameState des Players zurück
	 */
	public String getPlayerGameState() throws SoapFault;
	
	/**
	 * 
	 * @param agentgamestate
	 * @throws SoapFault
	 * 
	 * Methode setzt den GameState des Agent des aktuellen Spiels und leitet ihn weiter an den Server damit dieser ihn peristieren kann
	 */
	public void setAgentGameState(String agentgamestate) throws SoapFault;
	
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 * Methode liefert den gespeicherten Wert für den GameState des Agents zurück
	 */
	
	public String getAgentGameState() throws SoapFault;
	
	/**
	 * 
	 * @return
	 * @throws SoapFault
	 * Methode liefert eine absteigend sortierte Liste, nach dem Highscore Wert, der registrietrten User vom Server zurück
	 */
	public List<String> getHighscoreList() throws SoapFault;
	
}
