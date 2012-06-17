package de.wifhm.se1.highscore.server;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.wifhm.se1.highscore.common.HighscoreSystem;
import de.wifhm.se1.highscore.server.exceptions.InvalidPasswordException;
import de.wifhm.se1.highscore.server.exceptions.InvalidUsernameException;

/**
 * Message-Driven Bean implementation class for: HighscoreSystemImpl
 *
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"
		) })
public class HighscoreSystemImpl implements HighscoreSystem, MessageListener {
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        
    }

	@Override
	public void setHighscore(String username, String password, int points)
			throws InvalidPasswordException, InvalidUsernameException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getHighscore(String username) throws InvalidUsernameException {
		// TODO Auto-generated method stub
		
	}

}
