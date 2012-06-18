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
import de.wifhm.se1.battleship.common.ClientSystemSettings;
import de.wifhm.se1.battleship.common.Highscore;
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
	private void create(){
		logger.log(Level.INFO, this + ": is created!");
	}
	
	
	@PreDestroy
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
    	   logger.log(Level.INFO, "Login failed, User doesn't exsist");
    	   throw new InvalidUsernameException("Login failed, User doesn't exsist");
       }
       return user;
    }
    
	/**
     * @see BattleshipSystem#logout()
     */
	@Override
	@Remove
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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

	

	/**
     * @throws NotLoggedInException 
	 * @see BattleshipSystem#getClientSystemSettings()
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ClientSystemSettings getClientSystemSettings() throws NotLoggedInException {
    	if(this.loggedUser != null ){
    		User user = entitymanager.find(User.class, this.loggedUser);
    		return user.getSettings();
    	}
    	else{
    		throw new NotLoggedInException("Not logged in");
    	}
        
    }

	/**
     * @throws NotLoggedInException 
     * @param savePasswordUsername
     * @param boardlength
	 * @see BattleshipSystem#setClientSystemSettings(boolean, int)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void setClientSystemSettings(boolean savePasswordUsername, int boardlength) throws NotLoggedInException {
    	if(loggedUser != null){
    		User user = entitymanager.find(User.class, loggedUser);
        	user.setSettings(new ClientSystemSettings(savePasswordUsername, boardlength));
        	entitymanager.persist(user);
        	logger.log(Level.INFO, "ClientSystemSettings have been changed savemode is "+ savePasswordUsername + " and boardlength "+boardlength);	
    	}
    	else{
    		throw new NotLoggedInException("Not logged in");
    	}
    }

	/**
     * @throws NotLoggedInException 
     * @param settings
	 * @see BattleshipSystem#setClientSystemSetting(ClientSystemSettings)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void setClientSystemSetting(ClientSystemSettings settings) throws NotLoggedInException {
    	if(loggedUser != null){
        	User user = entitymanager.find(User.class, loggedUser);
        	user.setSettings(settings);
        	entitymanager.persist(user);
        	logger.log(Level.INFO, "ClientSystemSettings have been changed savemode is "+ settings.isSavePasswordUsername() + " and boardlength "+settings.getBoardlength());
    	}
    	else{
    		throw new NotLoggedInException("Not logged in");
    	}
    }


	@Override
	public void setHighscore(int points) throws NotLoggedInException {
		if(loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			Highscore highscore = user.getHighscore();
			highscore.setHighscore(points);
			user.setHighscore(highscore);
			entitymanager.persist(user);
			logger.log(Level.INFO, "Highscore have been increaqsed by "+points+" ");
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
		
	}


	@Override
	public Highscore getHighscore() throws NotLoggedInException {
		if(this.loggedUser != null){
			User user = entitymanager.find(User.class, loggedUser);
			return user.getHighscore();
		}
		else{
			throw new NotLoggedInException("Not logged in");
		}
	}


	@Override
	public List<Highscore> getHighscoreList() {
		//TODO
		return null;
	}


	

	

}
