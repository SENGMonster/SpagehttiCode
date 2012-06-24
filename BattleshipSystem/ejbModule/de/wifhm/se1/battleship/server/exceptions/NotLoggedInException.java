package de.wifhm.se1.battleship.server.exceptions;

/**
 * 
 * @author Marc Paaßen
 * Exception die geschmissen wird falls Funktionen aufgerufen werden die nur von einem eingeloggten User aufgerufen werden können
 */
public class NotLoggedInException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotLoggedInException() {
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
