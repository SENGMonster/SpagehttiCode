package de.wifhm.highscore.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;



/**
 * Message-Driven Bean implementation class for: HighscoreSystemBean
 *
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/HighscoreQueue")
		})
public class HighscoreSystemBean implements MessageListener {
	private Logger logger = Logger.getLogger(HighscoreSystemBean.class.getPackage().getName());
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        try {
        	TextMessage textmessage = (TextMessage) message;
        	logger.log(Level.INFO, textmessage.getText());
        }
        catch(JMSException e){
        	logger.log(Level.INFO, "Exception occured: " + e.toString());
        }
        
    }

}
