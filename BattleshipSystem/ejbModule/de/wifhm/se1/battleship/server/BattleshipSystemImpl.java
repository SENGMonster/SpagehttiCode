package de.wifhm.se1.battleship.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.ConnectionFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.wifhm.se1.battleship.common.BattleshipSystem;
import de.wifhm.se1.battleship.common.User;
import de.wifhm.se1.battleship.server.exceptions.InvalidPasswordException;
import de.wifhm.se1.battleship.server.exceptions.InvalidUsernameException;
import de.wifhm.se1.battleship.server.exceptions.NotLoggedInException;


/**
 * Session Bean implementation class BattleshipSystemImpl
 */
@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BattleshipSystemImpl implements BattleshipSystem, BattleshipSystemLocal{
	
	@Resource(mappedName = "java:JmsXA")
	private ConnectionFactory jmsFactory;
	
	
	@PersistenceContext
	EntityManager entitymanager;
	
	private Logger logger = Logger.getLogger(BattleshipSystemImpl.class.getPackage().getName());
	
	private String loggedUser;
	
	@SuppressWarnings("unused")
	@PostConstruct
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	/**
	 * Methode wird beim erstellen eines neuen SessionBeans ausgeführt
	 */
	private void create(){
		logger.log(Level.INFO, this + ": is created!");
	}
	
	
	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	/**
	 * Methode wird beim zerstören einer SessionBean gestartet
	 */
	public void destroyed(){
		logger.log(Level.INFO, this + ": will be destroyed!");
	}
	/**
     * @throws InvalidUsernameException 
	 * @throws InvalidPasswordException 
	 * @param username
	 * @param password
	 * @see BattleshipSystem#login(String, String)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
       User user = entitymanager.find(User.class, username);
       if(user != null){
    	   if(user.getPassword().equals(password)){
    		   this.loggedUser = username;
    		   logger.log(Level.INFO, user + ": Logged in succesfull, EJB:" + this);
    	   }
    	   else{
    		   logger.log(Level.INFO, "Login failed due invalid password");
    		   throw new InvalidPasswordException("Login failed due invalid password");
    	   }
       }
       else{
    	   logger.log(Level.INFO, "Login failed, User doesn't exsist"+ username + " " + password );
    	   throw new InvalidUsernameException("Login failed, User doesn't exsist"+ username + " " + password );
       }
       return user;
    }
    
	/**
     * @see BattleshipSystem#logout()
     */
	@Override
	@Remove
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void logout() {
		logger.log(Level.INFO, "Succesful logged out");
    }

	/**
     * @throws InvalidUsernameException 
     * @param username
     * @param password
	 * @see BattleshipSystem#register(String, String)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void register(String username, String password) throws InvalidUsernameException {
    	if(this.loggedUser == null){
    		if(entitymanager.find(User.class, username) == null){
    			User user = new User(username, password);
    			entitymanager.persist(user);
    			logger.log(Level.INFO, "New User created");
    		}
    		else{
    			throw new InvalidUsernameException("Username already exsists");
    		}
    	}
    }

	


	




	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setPlayerGameState(String playergamestate)
			throws NotLoggedInException {
		// TODO Auto-generated method stub
		if(loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			user.setPlayerGameState(playergamestate);
			entitymanager.persist(user);
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
	}


	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getPlayerGameState() throws NotLoggedInException {
		// TODO Auto-generated method stub
		if(loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			return user.getPlayerGameState();
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setAgentGameState(String agentgamestate)
			throws NotLoggedInException {
		// TODO Auto-generated method stub
		if(loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			user.setAgentGameState(agentgamestate);
			entitymanager.persist(user);
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
		
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getAgentGameState() throws NotLoggedInException {
		// TODO Auto-generated method stub
		if(loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			return user.getAgentGameState();
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addPoints(int points) throws NotLoggedInException {
		if(loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			user.addPoints(points);
			entitymanager.persist(user);
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
		
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<User> getHighscoreList() throws NotLoggedInException {
		if(loggedUser != null){
			String query = "SElECT e FROM User e ORDER BY highscore DESC ";
			return entitymanager.createQuery(query, User.class).getResultList();	
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
		
	}


	


	


	

	

}
