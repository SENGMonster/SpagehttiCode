package de.wifhm.se1.android.battleship.manager;

import org.ksoap2.SoapFault;

import de.wifhm.se1.android.activity.BattleshipApplication;
import de.wifhm.se1.android.common.BattleshipSystemStub;

/**
 * übernimmt für die Clientaufrufe der Darstellung und des Spiels die Kommunikation mit dem Webservice
 * @author Ramona, Jens
 *
 */
public class WebServiceCommunicator {
	
	BattleshipApplication battleshipapp;
	BattleshipSystemStub bsstub;

	public WebServiceCommunicator(BattleshipApplication ab)
	{
		  battleshipapp = ab;
		  bsstub = battleshipapp.getBsStub();
	}
	
	
	/**
	 * holt das zuletzt gespeicherte Spielfeld des Benutzers ab
	 * @return Erfolg der Kommunikation
	 */
	public String getUserGame(){	
		
		try {
			String result=bsstub.getPlayerGameState();
			return result;
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// so tun als hätte der User kein Spiel?
			return "";
		}
		
	}
	
	/**
	 * holt das zuletzt gespeicherte Spielfeld des Computers ab
	 * @return Erfolg der Kommunikation
	 */
	public String getComputerGame(){
		
		try {
			String result=bsstub.getAgentGameState();
			return result;
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// so tun als hätte der Computer kein Spiel?
			return "";
		}
		
		
	}
	
	/**
	 * sendet das kodierte Spielfeld des Benutzers
	 * Bei Misserfolg soll dem Benutzer Verboten werden weiterzuspielen 
	 * @param UserString
	 * @return Erfolg der Übermittlung
	 */
	public boolean sendPlayerGame(String UserString){
		
			try {
				bsstub.setPlayerGameState(UserString);
			
			} catch (SoapFault e) {
				
				e.printStackTrace();
				return false;
			}
			System.out.println(UserString);
			return true;
	}
	
	/**
	 * sendet das kodierte Spielfeld des Computers
	 * Bei Misserfolg soll dem Benutzer Verboten werden weiterzuspielen 
	 * @param AgentString
	 * @return Erfolg der Übermittlung
	 */
	public boolean sendAgentGame(String AgentString){
		
		try {
			bsstub.setPlayerGameState(AgentString);
		
		} catch (SoapFault e) {
			
			e.printStackTrace();
			return false;
		}
		System.out.println(AgentString);
		return true;
}
	
	/**
	 * sendet das Spielergebnis in Punkten an den Webservice
	 * @param points
	 * das Spielergebis
	 * @return Erfolg der Übermittlung
	 */
	public boolean sendHighscorePoints(int points){
		try {
			
			bsstub.addPoints(points);
			return true;	
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
}
