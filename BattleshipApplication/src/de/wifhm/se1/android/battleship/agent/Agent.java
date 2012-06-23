package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;
import java.util.Random;
import de.wifhm.se1.android.battleship.manager.*;;


public class Agent {
	private ArrayList<Schiff>shiplist;
	AgentManager AgentManager;
	
	public Agent(Spielvorlage s, ArrayList<Coordinate> coordinates, AgentManager am)
	{
		shiplist = s.getSchiffsliste();
		AgentManager = am;
		AgentManager.setKoordinatenliste(coordinates);
	}

	private boolean isThereAShipToDestroy =false;
	
	private Destroy Ship2Destroy;	
	public void setShip2Destroy(Destroy ship2Destroy) {
		if(ship2Destroy==null){
			isThereAShipToDestroy = false;	
		}
		else{
			isThereAShipToDestroy = true;
		}
		Ship2Destroy = ship2Destroy;
		
	}
	public Destroy getShip2Destroy(){
		return Ship2Destroy;
	}
	
	public void setDestroyedShip(Schiff s){
		
		if(s!=null & Ship2Destroy!=null){

			setImpossibleFieldsAroundShip(s);
			
			setShip2Destroy(null);
		}
		
	}
	
	
	
	public void setFieldStatesAfterLoadingGame(Battlefieldmanager bm, Spielvorlage sv){
		
		//wasser eintragen
		for(int el:bm.getWaterHits()){
			AgentManager.setFieldStateForCoordinate(el, FieldState.WATER);
		}
		
		for(Schiff s:sv.getSchiffsliste()){
			
			//falls das Schiff schon gesunken ist alle Impossibles drumherum setzen
			if(s.getIsSunk()){
				setImpossibleFieldsAroundShip(s);
			}
			else{
				
				//wenn der Agent gerade ein Schiff am Abballern war dieses Restoren
				if(s.getHitPositions()!=null && s.getHitPositions().size()>0)
				{
					Destroy d = new Destroy();
					//alle bereits getätigten Schüsse eiinfüllen
					for(int el:s.getHitPositions())
					{
						d.setShot(AgentManager.getCoordinateForNr(el));
					}
					
					//alle getroffenen Feldern den FieldState Hit geben
					for(int el:s.getHitPositions())
					{
						AgentManager.setFieldStateForCoordinate(el, FieldState.HIT);
					}
				}
				
			}
			
		
		}
		
	}
	
	private void setImpossibleFieldsAroundShip(Schiff s){
		//ImpossibleStates setzen um jedes Schiff drumherum
		for(int i=0; i< s.getSchiffspositions().size(); i++)
		{
			ArrayList<Integer> impossibleList = Helper.setImpossibleFieldsaroundShip(s.getSchiffspositions(), Ship2Destroy.isHorizontal());
			for(int el:impossibleList){
				AgentManager.setFieldStateForCoordinate(el, FieldState.IMPOSSIBLE);
			}
		}
	}

	private int turnCounter = 0;
	private int initialTurnCounter =1;
	
	public Coordinate nextTurn(){
		
		//how often the agent has shot already

		turnCounter +=1;
		
		Coordinate bestCoordinate = null;
		
		// There is a ship hit which isnt destroyed yet
		if(isThereAShipToDestroy){
			
			
		
				  	int[] valuesForSinkShot = new int[4];
					
					//if the direction is unclear (only 1 hit)
					if (!Ship2Destroy.isHas2Hitsalready())
					{
						//calculate the value for each neighbour for each ship which is left
						for (Schiff s:shiplist)
						{
							//wenn es noch nicht versunken ist
							if (!s.getIsSunk())
							{		
								 //iterate through all neighbours
								for (int i=1; i<5; i++)
								{
									Directions d;
									try {
										d = AgentManager.getDirectionForInteger(i);
										if (!Ship2Destroy.getFalseDirections().contains(d)){
											Coordinate c = AgentManager.getNeighbourHelper(i, Ship2Destroy.getLastShot());
											if (c!=null){
												
													try {
														int DirectionValue = AgentManager.getValueForSinkShot(c,s.getShipLength(), d);
														valuesForSinkShot[i-1] = valuesForSinkShot[i-1] + DirectionValue;
													} catch (Exception e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
											
											}
										}//end if not false Directions contains d
										
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}//end is sunk
						}//end for each ship
						
						int bestValue=-99;
						int direction=0;
						for (int i = 0; i < valuesForSinkShot.length; i++) {
							if (valuesForSinkShot[i]>bestValue){
								bestValue=valuesForSinkShot[i];
								direction=i;
							}
						}
						
						Directions bestdirect;
						try {
							bestdirect = AgentManager.getDirectionForInteger(direction+1);
							Ship2Destroy.setLastDirection(bestdirect);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						bestCoordinate=AgentManager.getNeighbourHelper(direction+1, Ship2Destroy.getLastShot());
						
					}
						//else{
//						
//						//we know the direction already 
//						// if its horziontal only left and right neigbour must be scanned
//						if (Ship2Destroy.isHorizontal())
//						{
//							//links scannen
//							int leftValue= AgentManager.getValueForSinkShot(AgentManager.getLeftNeighbour(Ship2Destroy.getInitialShot()),s.getShipLength());
//							//rechts scannen
//							int rightValue= AgentManager.getValueForSinkShot(AgentManager.getRightNeighbour(Ship2Destroy.getInitialShot()),s.getShipLength());
//							if(leftValue>rightValue){
//								bestCoordinate =  AgentManager.getLeftNeighbour(Ship2Destroy.getInitialShot());
//							}else
//							{
//								bestCoordinate =  AgentManager.getRightNeighbour(Ship2Destroy.getInitialShot());
//							}
//						}
//						else{
//								//oben scannen
//								int topValue= AgentManager.getValueForSinkShot(AgentManager.getTopNeighbour(Ship2Destroy.getInitialShot()),s.getShipLength());
//								//unten scannen
//								int bottomValue= AgentManager.getValueForSinkShot(AgentManager.getBottomNeighbour(Ship2Destroy.getInitialShot()),s.getShipLength());
//								if(topValue>bottomValue){
//									bestCoordinate =  AgentManager.getTopNeighbour(Ship2Destroy.getInitialShot());
//								}else
//								{
//									bestCoordinate =  AgentManager.getBottomNeighbour(Ship2Destroy.getInitialShot());
//								}
//						}
//					}//end Ship2Destroy
//					
		
		}// THERE IS NO SHIP TO DESTROY
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
				
				bestCoordinate = AgentManager.getCoordinateForNr(field);
				return bestCoordinate;
			}
			
			ArrayList<Coordinate> UnknownList = AgentManager.getAllUnknown();
			//ArrayList<Coordinate> bestPossibilityValueList = new ArrayList<Coordinate>();
			
			int bestValue = 0;
			
			for(Coordinate c:UnknownList)
			{
				int overallPossiblityValue =0;
				for(Schiff s:shiplist)
				{					
					overallPossiblityValue+=AgentManager.getPossibilityValue(c, s.getShipLength());	
				}
				
				if (overallPossiblityValue>bestValue)
				{
					bestValue= overallPossiblityValue;
				}
				
				c.setPossiblityValue(overallPossiblityValue);
			}
			
			ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
			int tempbestValue = -99;

			//die besten aus Wahrscheinlichkeitswert filtern und für die direkt den NAchbarschaftswert berechnen
			for(Coordinate c:UnknownList)
			{
				//Alle mit dem besten Werten beachten
				if (c.getPossiblityValue() == bestValue)
				{
					// für die guten auch noch die NachbachschaftsWerte ausrechnen 
					 int currCoordinatevalue = AgentManager.getNeighbourhoodValue(c);
					 c.setNeigbourhoodValue(currCoordinatevalue);
					
					 //beim ersten mal initialisieren
					 if (tempbestValue==-99) tempbestValue=currCoordinatevalue;
					 
					 //die mit dem besten Wert sind gut und kommen weiter
					 if (tempbestValue <= currCoordinatevalue)
					 {
						 temp.add(c);
						 tempbestValue = currCoordinatevalue;
					 }
				}
			}
 			
			//die besten aus dem Nachbarschaftswert filtern
			ArrayList<Coordinate> bestOfStep2 = new ArrayList<Coordinate>();
			for (Coordinate S2Coordinate:temp){
			 	if (S2Coordinate.getNeigbourhoodValue()==tempbestValue){
			 		bestOfStep2.add(S2Coordinate);
			 	}
			} 
			
			if(bestOfStep2.size() == 1) {
				bestCoordinate = bestOfStep2.get(0); 
			}
			else{
				
				Random r = new Random();
				bestCoordinate = bestOfStep2.get(r.nextInt(bestOfStep2.size()-1));
			}

		}
		
		return bestCoordinate;
	}
	
	
	public void positionShips(){
		
		Random r = new Random();
		final int max = GlobalHolder.getInstance().getNumOfRowsCols()*GlobalHolder.getInstance().getNumOfRowsCols(); 
		ArrayList<Integer> impossibleFields = new ArrayList<Integer>();
		
		//Jedes Schiff positionieren
		for(Schiff currShip:GlobalHolder.getInstance().getComputerShips().getSchiffsliste())
		{
			boolean hasPositionedShipAlready=false;
		
			while(!hasPositionedShipAlready)
			{
				int StartPosition = r.nextInt(max);
				int value;
				
				Helper helper = new Helper(StartPosition, currShip.getShipLength()-1);	            	    
		 	     
		 	    //Alle 4 Richtungen überprüfen und mögliche Felder setzen
		 	    for(int i=0; i<4; i++){		            	    	
		 	    	if(!hasPositionedShipAlready)
		 	    	{
			 	    	switch(i)
			 	    	{
			     	    	case 0:
			     	    		//Es Darf nicht über mehrere Zeilen gehen (keine Umbrüche) nach oben -->
			     	    		value= helper.validateRightToBottom();
			     	    		
			     	    		//überprüfen ob ein Schiff im weg ist
			     	    		if (value!=-1){
			     	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 1);
			     	    			if (!Helper.hasHorizontalColidations(impossibleFields, positions))
			     	    			{
			     	    				currShip.setSchiffspositions(positions.get(0), positions.get(positions.size()-1));
			     	    				impossibleFields.addAll(currShip.getSchiffspositions());
			     	    				hasPositionedShipAlready=true;
			     	    			}
			     	    		}
			     	    		break;
			     	    	case 1:
			     	    		//es Darf keinen Zeilenumbruch nach unten hin geben <--
			     	    		value=helper.validateLeftToTop();
			     	    		
			     	    		//überprüfen ob ein Schiff im weg ist
			     	    		if (value!=-1){
			     	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 1);
			     	    			if (!Helper.hasHorizontalColidations(impossibleFields, positions))
			     	    			{
			     	    				currShip.setSchiffspositions(positions.get(0), positions.get(positions.size()-1));
			     	    				impossibleFields.addAll(currShip.getSchiffspositions());
			     	    				hasPositionedShipAlready=true;
			     	    			}
			     	    		}
			     	    		break;
			     	    	case 2: 
			     	    		//ES DARF BEI VERTIKAL NICHT NACH OBEN HINAUS LAUFEN
			     	    		 value = helper.validateTop();
			     	    		 
			     	    		//überprüfen ob ein Schiff im weg ist
			         	    		if (value!=-1){
			         	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 10);
			         	    			if (!Helper.hasVerticalColidations(impossibleFields, positions))
			         	    			{
			         	    				currShip.setSchiffspositions(positions.get(0), positions.get(positions.size()-1));
				     	    				impossibleFields.addAll(currShip.getSchiffspositions());
				     	    				hasPositionedShipAlready=true;
			         	    			}
			         	    		}
			     	    		break;
			     	    	case 3:
			     	    		//ES DARF BEI VERTIKAL NICHT NACH UNTEN HINAUS LAUFEN
			     	    		value=helper.validateBottom();
			     	    		
			     	    		//überprüfen ob ein Schiff im weg ist
			     	    		if (value!=-1){
			     	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 10);
			     	    			if (!Helper.hasVerticalColidations(impossibleFields, positions))
			     	    			{
			     	    				currShip.setSchiffspositions(positions.get(0), positions.get(positions.size()-1));
			     	    				impossibleFields.addAll(currShip.getSchiffspositions());
			     	    				hasPositionedShipAlready=true;
			     	    			}
			     	    		}
			     	    		break;
			 	    	}//end switch
		 	    	}//end if HasPositionedShipAlready
		 	    }//end for
	 	    			    		
	 	    }//end HasPositionedShipAlready
		}//end for ship
	}//end void
	


}
