package de.wifhm.se1.highscore.common;

import de.wifhm.se1.highscore.server.exceptions.InvalidPasswordException;
import de.wifhm.se1.highscore.server.exceptions.InvalidUsernameException;

public interface HighscoreSystem {
	
	public void setHighscore(String username, String password, int points) throws InvalidPasswordException, InvalidUsernameException;
	
	public void getHighscore(String username) throws InvalidUsernameException;
	
	
		
}
