package de.wifhm.se1.android.battleship.manager;

import org.ksoap2.SoapFault;

import de.wifhm.se1.android.activity.BattleshipApplication;

public class WebServiceCommunicator {
	
	BattleshipApplication bsstub;

	public WebServiceCommunicator(BattleshipApplication ab)
	{
		  bsstub = ab;
	}
	
	public boolean sendPlayerGame(String UserString){
		
			try {
				bsstub.getBsStub().setPlayerGameState(UserString);
			
			} catch (SoapFault e) {
				
				e.printStackTrace();
				return false;
			}
			System.out.println(UserString);
			return true;
	}
	
	public boolean sendAgentGame(String AgentString){
		
		try {
			bsstub.getBsStub().setPlayerGameState(AgentString);
		
		} catch (SoapFault e) {
			
			e.printStackTrace();
			return false;
		}
		System.out.println(AgentString);
		return true;
}
}
