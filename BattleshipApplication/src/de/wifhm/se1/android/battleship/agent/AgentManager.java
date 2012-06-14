package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;

import de.wifhm.se1.android.battleship.manager.FieldState;

public class AgentManager {
	
	private static AgentManager myInstance;
	
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
	
	
	//returns the Coordinate at the given Index
	public Coordinate getCoordinateForNr(int nr)
	{
		return Koordinatenliste.get(nr);
	}

	//return the Coordinate which is the right neighbour
	public Coordinate getRightNeighbour(Coordinate c)
	{
		if (c.getCoordinateNr()== 100)
		{
			return null;
		}
		
		return Koordinatenliste.get(c.getCoordinateNr() +1);
	}
	
	public Coordinate getLeftNeighbour(Coordinate c)
	{
		
		if (c.getCoordinateNr() == 1)
		{
			return null;
		}
		
		return Koordinatenliste.get(c.getCoordinateNr() -1);
	}
	
	public Coordinate getTopNeighbour(Coordinate c)
	{
		if(c.getCoordinateNr() < 10){
			return null;
		}
		return Koordinatenliste.get(c.getCoordinateNr()-10);
	}
	
	public Coordinate getBottomNeighbour(Coordinate c)
	{
		if (c.getCoordinateNr() > 90)
		{
			return null;
		}
		
		return Koordinatenliste.get(c.getCoordinateNr()+10);
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
	public int getPossibilityValue(Coordinate c, int shiplength)
	{
	
		int[] counters = new int[4];
		
		//Counter for possible fields in all directions
		int rightcounter = 0;
		int leftcounter =0;
		int topcounter =0;
		int bottomcounter =0;

		
		//Calculate possible fields in all directions
		while(getRightNeighbour(c)!=null && getRightNeighbour(c).getStateField() == FieldState.UNKNOWN){
			rightcounter += 1;			
		}
		
		while(getLeftNeighbour(c) != null && getLeftNeighbour(c).getStateField() == FieldState.UNKNOWN){
			leftcounter += 1;			
		}
		
		while(getBottomNeighbour(c) != null && getBottomNeighbour(c).getStateField() == FieldState.UNKNOWN){
			bottomcounter += 1;			
		}
		
		while(getTopNeighbour(c) !=null && getTopNeighbour(c).getStateField() == FieldState.UNKNOWN){
			topcounter += 1;			
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
	
	
	public int getValueForSinkShot(Coordinate c, int shiplength, Directions dir)
	{
				
		// 25% Chance for each of the 4 sides
		if (shiplength==2)
		{
			return 1;
		}
		else
		{
			switch (dir)
			{
				case LEFT:
				case RIGHT:
				case TOP:
				case BOTTOM:
				
			}
			
		}
		
		//should never come here...
		return 0;
	
		
	
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
