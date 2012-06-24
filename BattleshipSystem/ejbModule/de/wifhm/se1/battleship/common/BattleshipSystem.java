package de.wifhm.se1.battleship.common;

import java.util.List;

import javax.ejb.Remote;

import de.wifhm.se1.battleship.server.exceptions.InvalidPasswordException;
import de.wifhm.se1.battleship.server.exceptions.InvalidUsernameException;
import de.wifhm.se1.battleship.server.exceptions.NotLoggedInException;

@Remote
public interface BattleshipSystem {
	/**
	 * 
	 * @param username
	 * @param password
	 * @return User
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * 
	 * Methode die den Login eines Nutzers durchführt und den User zurück gibt
	 */
	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException;
	/**
	 * Methode für den Logout
	 */
	public void logout();
	/**
	 * 
	 * @param username
	 * @param password
	 * @throws InvalidUsernameException
	 * 
	 * Registriert einen neuen Nutzer und persitiert ihn auf der Datenbank
	 */
	public void register(String username, String password) throws InvalidUsernameException;
	
	/**
	 * 
	 * @param gamestate
	 * @throws NotLoggedInException
	 * 
	 * Methode um den Spielstand des CLients zu speichern(persitierern)
	 */
	public void setPlayerGameState(String playergamestate) throws NotLoggedInException;	
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 * 
	 * Methode die den letzten vom Client gespeicherten Spielstand zurück gibt
	 */
	public String getPlayerGameState() throws NotLoggedInException;
	
	/**
	 * 
	 * @param agentgamestate
	 * @throws NotLoggedInException
	 * 
	 * Methode um den Spielstand des Agent zu speichern(persitierern)
	 */
	public void setAgentGameState(String agentgamestate) throws NotLoggedInException;
	
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 * 
	 * Methode die den letzten vom Agent gespeicherten Spielstand zurück gibt
	 */
	public String getAgentGameState() throws NotLoggedInException;
	
	/**
	 * 
	 * @param points
	 * @throws NotLoggedInException
	 * 
	 * Methode addiert auf den Highscorestand des angemeldeten Users die Punkte
	 */
	public void addPoints(int points) throws NotLoggedInException;
	
	/**
	 * 
	 * @return
	 * @throws NotLoggedInException
	 * 
	 * Gibt eine Liste aller registrierten Nutzer zurück
	 */
	public List<String> getHighscoreList() throws NotLoggedInException;
	
}
