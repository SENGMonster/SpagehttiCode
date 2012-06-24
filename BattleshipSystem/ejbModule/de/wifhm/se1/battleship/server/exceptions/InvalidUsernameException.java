package de.wifhm.se1.battleship.server.exceptions;
/**
 * @author Marc Paaßen
 * 
 * Exception für einen falschen bzw nicht vorhandenen eingegeben Usernamen 
 */
public class InvalidUsernameException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public InvalidUsernameException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidUsernameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidUsernameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidUsernameException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	

}
