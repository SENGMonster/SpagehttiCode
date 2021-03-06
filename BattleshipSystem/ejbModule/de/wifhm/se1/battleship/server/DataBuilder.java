
package de.wifhm.se1.battleship.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.wifhm.se1.battleship.common.User;

@Startup
@Singleton
public class DataBuilder {
	private Logger logger;
	
	@PersistenceContext
	EntityManager entitymanager;
	
	@Resource private String name1, password1;
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void init(){
		logger = Logger.getLogger(DataBuilder.class.getPackage().getName());
		
		User user1 = entitymanager.find(User.class, name1);
		if(user1 != null){
			user1 = new User(name1, password1);
			entitymanager.persist(user1);
			logger.log(Level.INFO, name1);
		}
	}
}
