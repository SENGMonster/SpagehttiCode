package de.wifhm.se1.android.battleship.manager;

import org.ksoap2.SoapFault;

import de.wifhm.se1.android.activity.BattleshipApplication;
import de.wifhm.se1.android.common.BattleshipSystemStub;

public class WebServiceCommunicator {
	
	BattleshipApplication battleshipapp;
	BattleshipSystemStub bsstub;

	public WebServiceCommunicator(BattleshipApplication ab)
	{
		  battleshipapp = ab;
		  bsstub = battleshipapp.getBsStub();
	}
	
	
	public String getUserGame(){	
		
		try {
			String result=bsstub.getPlayerGameState();
			return result;
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// so tun als hätte der User kein Spiel?
			return "\"\"";
		}
		
	}
	
	public String getComputerGame(){
		
		try {
			String result=bsstub.getAgentGameState();
			return result;
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// so tun als hätte der Computer kein Spiel?
			return "\"\"";
		}
		
		
	}
	
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
	
	public boolean sendHighscorePoints(int points){
		try {
			
			bsstub.setHighscore(points);
			return true;	
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
}
