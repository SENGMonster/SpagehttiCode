package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;
import java.util.Random;

import de.wifhm.se1.android.battleship.manager.*;;


public class Agent {
	private ArrayList<Schiff>shiplist;
	
	public Agent(Spielvorlage s, ArrayList<Coordinate> coordinates)
	{
		shiplist = s.getSchiffsliste();
		AgentManager.getInstance().setKoordinatenliste(coordinates);
	}

	private boolean isThereAShipToDestroy =false;
	
	private Destroy Ship2Destroy;	
	public void setShip2Destroy(Destroy ship2Destroy) {
		isThereAShipToDestroy = true;
		Ship2Destroy = ship2Destroy;
	}

	private int turnCounter = 0;
	private int initialTurnCounter =1;
	
	public Coordinate nextTurn(){
		
		//how often the agent has shot already
		turnCounter +=1;
		
		Coordinate bestCoordinate = null;
		
		// There is a ship hit which isnt destroyed yet
		if(isThereAShipToDestroy){
			
			
			//calculate the value for each neighbour for each ship which is left
			for (Schiff s:shiplist)
			{
				
				//if the direction is unclear (only 1 hit)
				if (!Ship2Destroy.isHas2Hitsalready())
				{
				 //iterate through all neighbours
					for (int i=1; i<5; i++)
					{
						Coordinate c = AgentManager.getInstance().getNeighbourHelper(i, Ship2Destroy.getInitialShot());
						try {
							AgentManager.getInstance().getValueForSinkShot(c,s.getShipLength(), AgentManager.getInstance().getDirectionForInteger(i));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
					}
				}else{
					
					//we know the direction already 
					// if its horziontal only left and right neigbour must be scanned
					if (Ship2Destroy.isHorizontal())
					{
						
					}
				}
			}
			
		}
		else{
			
			// at the beginning of the ship shot at a given pattern to have a starting base of shots
			if(initialTurnCounter<11)
			{
				int field =0;
				switch(initialTurnCounter)
				{
					case 1:
						field = 22;
						break;
					case 2:
						field=  37;
						break;
					case 3:
						field = 53;
						break;
					case 4:
						field = 66;
						break;
					case 5:
						field = 81;
						break;
					case 6:
						field = 98;
						break;
					case 7:
						field = 41;
						break;
					case 8:
						field = 94;
						break;
					case 9:
						field = 59;
						break;
					case 10:
						field = 30;
						break;
				}
				
				initialTurnCounter +=1;
				
				bestCoordinate = AgentManager.getInstance().getCoordinateForNr(field);
				return bestCoordinate;
			}
			
			ArrayList<Coordinate> UnknownList = AgentManager.getInstance().getAllUnknown();
			//ArrayList<Coordinate> bestPossibilityValueList = new ArrayList<Coordinate>();
			
			int bestValue = 0;
			
			for(Coordinate c:UnknownList)
			{
				int overallPossiblityValue =0;
				for(Schiff s:shiplist)
				{					
					overallPossiblityValue+=AgentManager.getInstance().getPossibilityValue(c, s.getShipLength());	
				}
				
				if (overallPossiblityValue>bestValue)
				{
					bestValue= overallPossiblityValue;
				}
				
				c.setPossiblityValue(overallPossiblityValue);
			}
			
			ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
			int tempValue  =0;
			
			for(Coordinate c:UnknownList)
			{
				if (c.getPossiblityValue() == bestValue)
				{
					 int value = AgentManager.getInstance().getNeighbourhoodValue(c);
					 c.setNeigbourhoodValue(value);
					 
					 if (tempValue <= value)
					 {
						 temp.add(c);
						 tempValue = value;
					 }
				}
			}
			if(temp.size() == 1) {
				bestCoordinate = temp.get(1); 
			}
			else{
				
				Random r = new Random();
				bestCoordinate = temp.get(r.nextInt(temp.size()-1));
			}

		}
		
		return bestCoordinate;
	}
	
}
