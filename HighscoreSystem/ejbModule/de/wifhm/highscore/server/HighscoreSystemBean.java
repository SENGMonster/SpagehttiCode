package de.wifhm.highscore.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;

import de.wifhm.se1.battleship.common.User;




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
	
	@Resource EntityManager entitymanager;
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        try {
        	
        	TextMessage textmessage = (TextMessage) message;
        	String[] args = textmessage.getText().split(";");
        	User user = entitymanager.find(User.class, args[0]);
        	if(user.getPassword().equals(args[1])){
        		user.getHighscore().addPoints(new Integer(args[2]));
        		entitymanager.persist(user);
        	}
        	logger.log(Level.INFO, "Added" +args[2] + "points to User: "+args[0]);
        }
        catch(JMSException e){
        	logger.log(Level.INFO, "Exception occured: " + e.toString());
        }
        
    }

}