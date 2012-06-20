package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;

import de.wifhm.se1.android.battleship.manager.FieldState;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;

public class AgentManager {
	
	private static AgentManager myInstance;
	private int maxlength=5;
	private int numOfRowsCols =10;
	private int numOfRowsColsT2 =100;
	
	private AgentManager(){
	}	
	
	public static AgentManager getInstance()
	{
		if (myInstance==null)
		{
			myInstance = new AgentManager();		}

		return myInstance; 
	}
	

	private ArrayList<Coordinate> Koordinatenliste;
	
	
	public ArrayList<Coordinate> getKoordinatenliste() {
		return Koordinatenliste;
	}

	public void setKoordinatenliste(ArrayList<Coordinate> koordinatenliste) {
		Koordinatenliste = koordinatenliste;
	}
	
	public void setFieldStateForCoordinate(int nr, FieldState fs)
	{
		Koordinatenliste.get(nr).setStateField(fs);
	}
	
	//returns the Coordinate at the given Index
	public Coordinate getCoordinateForNr(int nr)
	{
		if(nr >0 && nr < (numOfRowsColsT2-1))
		{
			return Koordinatenliste.get(nr);
		}
		return null;
	}

	//return the Coordinate which is the right neighbour
	public Coordinate getRightNeighbour(Coordinate c)
	{
		if (c.getCoordinateNr()>= numOfRowsColsT2-1)
		{
			return null;
		}
		
		return Koordinatenliste.get(c.getCoordinateNr() +1);
	}
	
	public Coordinate getLeftNeighbour(Coordinate c)
	{
		
		if (c.getCoordinateNr() <= 0)
		{
			return null;
		}
		
		return Koordinatenliste.get(c.getCoordinateNr() -1);
	}
	
	public Coordinate getTopNeighbour(Coordinate c)
	{
		if(c.getCoordinateNr() < numOfRowsCols){
			return null;
		}
		return Koordinatenliste.get(c.getCoordinateNr()-numOfRowsCols);
	}
	
	public Coordinate getBottomNeighbour(Coordinate c)
	{
		if (c.getCoordinateNr() >= (numOfRowsCols* (numOfRowsCols -1)))
		{
			return null;
		}
		
		return Koordinatenliste.get(c.getCoordinateNr()+numOfRowsCols);
	}
	
	
	public ArrayList<Coordinate> getAllUnknown()
	{
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		
		for(Coordinate c:Koordinatenliste)
		{
			if (c.getStateField() == FieldState.UNKNOWN)
			{
				list.add(c);
			}
		}
		
		return list;
	}
	
	
	
	//STEP 1 OF FINDING THE BEST NEW SHOT
	public int getPossibilityValue(Coordinate ic, int shiplength)
	{
	
		Coordinate initialC = ic;
		Coordinate tempC = initialC;
		
		int[] counters = new int[4];
		
		//Counter for possible fields in all directions
		int rightcounter = 0;
		int leftcounter =0;
		int topcounter =0;
		int bottomcounter =0;

		
		//Calculate possible fields in all directions
		while(getRightNeighbour(tempC)!=null && getRightNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
			//nicht mehr Felder in Betracht zeiehen als das Größte Schiff lang sein kann + Abstandshalter
			if (rightcounter>maxlength+2)
			{
				break;
			}
			rightcounter += 1;
			tempC = getRightNeighbour(tempC);
		}
		
		tempC = initialC;
		while(getLeftNeighbour(tempC) != null && getLeftNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
			//nicht mehr Felder in Betracht zeiehen als das Größte Schiff lang sein kann + Abstandshalter
			if (leftcounter>maxlength+2)
			{
				break;
			}
			leftcounter += 1;
			tempC = getLeftNeighbour(tempC);
		}
		
		tempC = initialC;
		while(getBottomNeighbour(tempC) != null && getBottomNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
			//nicht mehr Felder in Betracht zeiehen als das Größte Schiff lang sein kann + Abstandshalter
			if (bottomcounter>maxlength+2)
			{
				break;
			}
			bottomcounter += 1;	
			tempC = getBottomNeighbour(tempC);
		}
		
		tempC = initialC;
		while(getTopNeighbour(tempC) !=null && getTopNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
			//nicht mehr Felder in Betracht zeiehen als das Größte Schiff lang sein kann + Abstandshalter
			if (topcounter>maxlength+2)
			{
				break;
			}
			topcounter += 1;
			tempC = getTopNeighbour(tempC);
		}
		
		
		//for avoidance of 4 times if then else
		counters[0] = rightcounter;
		counters[1] = leftcounter;
		counters[2] = topcounter;
		counters[3] = bottomcounter;

		//Possibility Value for this coordinate and the ship length
		int value = 0;

		for(int i = 0 ; i < counters.length; i++)
		{
			if (i>=shiplength)
			{
				value+=1;
			}
		}
		
		
		
		//Looking for possibilities for placing the ship with the coordinate being a point "in the middle"
		int rightleftsum = rightcounter + leftcounter;
		int topbottomsum = topcounter + bottomcounter;
				
		
		if(rightleftsum >=shiplength)
		{
			int overflow = rightleftsum - shiplength;
			if (overflow > 0)
			{
				value += overflow;
			}
			else{
				value +=1;
			}
		}
		
		if(topbottomsum >= shiplength)
		{
			int overflow = topbottomsum -shiplength;
			if (overflow -shiplength>0)
			{
				value += overflow;
				
			}
			else{
				value +=1;
			}
		}

		
		//return the Possibility Value of the Coordinate with the ship length
		return value;
	}
	
	
	
	//STEP 2 OF FINDING THE BEST NEW SHOT
	public int getNeighbourhoodValue(Coordinate c)
	{
		
		int overallvalue =0;
		
		//scan all 4 Neighbours
		for(int i= 1; i < 5; i++)
		{
			Coordinate directNeighbour = getNeighbourHelper(i, c);
			
			int directNeighbourValue=0;
			
			if (directNeighbour!=null)
			{				
				//scan all Neighbours of each neighbour
				for(int y=1; y < 5; y++)
				{
					//neighbour cell of a direct neighbour
					Coordinate NeighboursNeighbour = getNeighbourHelper(y, directNeighbour);
					
					if(NeighboursNeighbour!=null)
					{
						if(NeighboursNeighbour.getStateField()==FieldState.UNKNOWN)
						{
							directNeighbourValue +=1;
						}
	
					}
				}
				
				//subtract 4 points because the initially scanned Coordinate behaves 4 times as a neighbour
				overallvalue += directNeighbourValue -4;
			}
		}
		
		return overallvalue;
	}
	
	
	public int getValueForSinkShot(Coordinate c, int shiplength, Directions d)
	{
				
		// 25% Chance for each of the 4 sides
		if (shiplength==2)
		{
			return 1;
		}
		else
		{
			int value=0;
			Coordinate initialC = c;
			Coordinate tempC = initialC;

			
			if (d==Directions.LEFT || d == Directions.RIGHT)
			{			
				int rightcounter = 0;
				int leftcounter =0;
				
				
				// Schauen ob das Schiff Rechts lang liegen kann
				while(getRightNeighbour(tempC)!=null && getRightNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
					rightcounter += 1;
					tempC = getRightNeighbour(tempC);
					
					//falls soviele Plätze frei sind wie das Schiff maximal brauchen würde abbrechen
					if (rightcounter>=shiplength-1)
					{
						//wenn wir gerade rechts scannen den Wert erhöhen
						if(d == Directions.RIGHT){
							
							//Wenns der Rand ist okay
							if(getRightNeighbour(tempC) ==null){
								value+=1;
							}
							else{
								//der nächste Nachbar darf kein Schiff sein!
								if(getRightNeighbour(tempC).getStateField()!=FieldState.HIT)
								{
									value +=1;	
								}	
							}
							
						}
						//ansonsten abbrechen
						break;
					}
					
				}
				
				
				tempC = initialC;
				while(getLeftNeighbour(tempC) != null && getLeftNeighbour(tempC).getStateField() == FieldState.UNKNOWN){

					leftcounter += 1;
					tempC = getLeftNeighbour(tempC);
					
					//falls soviele Plätze frei sind wie das Schiff maximal brauchen würde abbrechen
					if (leftcounter>=shiplength-1)
					{
						if(d == Directions.LEFT){
							
							if(getLeftNeighbour(tempC)==null){
							 value+=1;	
							}
							else{
								//der nächste Nachbar darf kein Schiff sein!
								if(getLeftNeighbour(tempC).getStateField()!=FieldState.HIT)
								{
									value +=1;	
								}
							}
							
						}
						//ansonsten abbrechen
						break;
					}

				}
				
			
				
				//Looking for possibilities for placing the ship with the coordinate being a point "in the middle"
				int rightleftsum = rightcounter + leftcounter;
				
				if(rightleftsum >=shiplength)
				{
					int overflow = rightleftsum - shiplength;
					if (overflow > 0)
					{
						value += overflow;
					}
					else{
						value +=1;
					}
				}
			}
			else{
				
				int topcounter =0;
				int bottomcounter =0;
				
				tempC = initialC;
				while(getBottomNeighbour(tempC) != null && getBottomNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
					
					bottomcounter += 1;	
					tempC = getBottomNeighbour(tempC);
					
					//nicht mehr Felder in Betracht zeiehen als das Größte Schiff lang sein kann + Abstandshalter
					if (bottomcounter>=shiplength-1)
					{
						if(d == Directions.BOTTOM){
							//der nächste Nachbar darf kein Schiff sein, wenn es der Rand ist dann okay.
							if(getBottomNeighbour(tempC)==null)
							{
								value +=1;	
							}
							else{
								if(getBottomNeighbour(tempC).getStateField()!=FieldState.HIT) value+=1;
							}
						}
						//ansonsten abbrechen
						break;
					}
		
				}
				
				tempC = initialC;
				while(getTopNeighbour(tempC) !=null && getTopNeighbour(tempC).getStateField() == FieldState.UNKNOWN){
					
					topcounter += 1;
					tempC = getTopNeighbour(tempC);
					
					//nicht mehr Felder in Betracht zeiehen als das Größte Schiff lang sein kann + Abstandshalter
					if (topcounter>=shiplength+1)
					{
						if(d == Directions.TOP){
							//der nächste Nachbar darf kein Schiff sein!
							if(getTopNeighbour(tempC)==null)
							{
								value +=1;	
							}else{
								if(getTopNeighbour(tempC).getStateField()!=FieldState.HIT){
									value+=1;
								}
							}
						}
						//ansonsten abbrechen
						break;
					}
			
				}
				
				
				int topbottomsum = topcounter + bottomcounter;
				if(topbottomsum >= shiplength)
				{
					int overflow = topbottomsum -shiplength;
					if (overflow -shiplength>0)
					{
						value += overflow;
						
					}
					else{
						value +=1;
					}
				}
				
			}
			
			return value;
			
		}//end if shiplength=1;
	
		
	}
	
	public Coordinate getNeighbourHelper(int i, Coordinate c)
	{
		Directions d;
		try {
			d = getDirectionForInteger(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		switch (d)
		{
			case RIGHT:
				return getRightNeighbour(c);
			case BOTTOM: 
				return getBottomNeighbour(c);
			case LEFT:
				return getLeftNeighbour(c);
			case TOP:
				return getTopNeighbour(c);
			default:
				return null;
		}
	}
	
	public Directions getDirectionForInteger(int i) throws Exception
	{
		if (i>4 || i<0)
		{
			throw new Exception("Get Direction can only accept integer between (inclusevly) 1 and 4");			
		}
		
		switch (i)
		{
			case 1:
				return Directions.RIGHT;
			case 2:
				return Directions.BOTTOM;
			case 3:
				return Directions.LEFT;
			case 4:
				return Directions.TOP;
		}
		
		return null;
	}
	
}
