package de.wifhm.se1.battleship.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import de.wifhm.se1.battleship.common.BattleshipSystem;
import de.wifhm.se1.battleship.common.User;
import de.wifhm.se1.battleship.server.exceptions.InvalidPasswordException;
import de.wifhm.se1.battleship.server.exceptions.InvalidUsernameException;
import de.wifhm.se1.battleship.server.exceptions.NotLoggedInException;

/**
 * Session Bean implementation class BattleshipSystemWebservice
 */
@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@EJB(name="ejb/Battleship/local", beanInterface=BattleshipSystemLocal.class, beanName="BattleshipSystemImpl")

public class BattleshipSystemWebservice implements BattleshipSystem {

	private Logger logger = Logger.getLogger(BattleshipSystemWebservice.class.getPackage().getName());
	private static final String KEY_SESSION = "BattleshipSystemReference";
	
	@Resource private WebServiceContext wsContext;
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void create(){
		logger.log(Level.INFO, this + " was created");
	}
	
	@SuppressWarnings("unused")
	@PreDestroy
	private void destroy(){
		logger.log(Level.INFO, this + "will be destroyed");
	}
	private BattleshipSystem getSession() {
		BattleshipSystem result = null;
		
		// Finde die HttpSession aus dem WebServiceContext
	    MessageContext mc = wsContext.getMessageContext();
	    HttpSession session = ((javax.servlet.http.HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();
	    if (session == null) {
	       throw new WebServiceException("No HTTP Session found");
	    }
	    result = (BattleshipSystem) session.getAttribute(KEY_SESSION);
	    if (result == null) {
	    	//Neue Referenz auf Stateful Session Bean per Lookup besorgen und in der Session speichern:
	    	try {
		    	result = (BattleshipSystem) new InitialContext().lookup("BattleshipSystemEAR/BattleshipSystemImpl/local");
	    	}
	    	catch (NamingException e) {
	    		throw new WebServiceException("Anlegen einer neuen Session gescheitert." + e.getMessage());
	    	}
	    	session.setAttribute(KEY_SESSION, result);
	    }
	    return result;
	}
	
	private void invalidateSession() {
		// Finde die HttpSession aus dem WebServiceContext
	    MessageContext mc = wsContext.getMessageContext();
	    HttpSession session = ((javax.servlet.http.HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();
	    if (session == null) {
	       throw new WebServiceException("No HTTP Session found");
	    }
	    session.removeAttribute(KEY_SESSION);
	}
	
	/**
     * @throws InvalidPasswordException 
	 * @throws InvalidUsernameException 
	 * @param username
	 * @param password
	 * @see BattleshipSystem#login(String, String)
     */
	@Override
	@WebMethod
	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
		return this.getSession().login(username, password);
    }
	/**
	 * @see BattleshipSystem#logout()
	 */
	 
	@Override
	@WebMethod
    public void logout() {
		this.getSession().logout();
		this.invalidateSession();
    }

	/**
     * @throws InvalidUsernameException 
     * @param username
     * @param password
	 * @see BattleshipSystem#register(String, String)
     */
	@Override
	@WebMethod
    public void register(String username, String password) throws InvalidUsernameException {
		this.getSession().register(username, password);
    }
	/**
	 * @throws NotLoggedInException
	 * @param points
	 */
	

	@Override
	@WebMethod
	public void setPlayerGameState(String playergamestate)
			throws NotLoggedInException {
		this.getSession().setPlayerGameState(playergamestate);
		
	}

	@Override
	@WebMethod
	public String getPlayerGameState() throws NotLoggedInException {
		// TODO Auto-generated method stub
		return this.getSession().getPlayerGameState();
	}

	@Override
	@WebMethod
	public void setAgentGameState(String agentgamestate)
			throws NotLoggedInException {
		this.getSession().setAgentGameState(agentgamestate);
	}

	@Override
	@WebMethod
	public String getAgentGameState() throws NotLoggedInException {
		// TODO Auto-generated method stub
		return this.getSession().getAgentGameState();
	}

	@Override
	@WebMethod
	public void addPoints(int points) throws NotLoggedInException {
		// TODO Auto-generated method stub
		this.getSession().addPoints(points);
	}

	@Override
	@WebMethod
	public List<User> getHighscoreList() throws NotLoggedInException{
		// TODO Auto-generated method stub
		return this.getSession().getHighscoreList();
	}

}
