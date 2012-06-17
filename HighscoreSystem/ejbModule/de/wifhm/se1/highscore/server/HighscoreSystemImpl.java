package de.wifhm.se1.highscore.server;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.wifhm.se1.highscore.common.HighscoreSystem;

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
     * Default constructor. 
     */
   
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        
    }

}
