package de.wifhm.se1.android.battleship.manager;

import java.util.Random;

public abstract class Schiff {

	private static Random r = new Random();
	public static int counter =0;
	
	protected int[] Positions;
	
	public Schiff(){
		initialize();
	}
	
	public void initialize()
	{
		Image = getImage();
		int[] temp = {1,2,3,4};
		setSchiffspositions(temp);
	}
	
	public void setSchiffspositions(int[] pos)
	{
		
		
//		if (pos.length != getNumberOfPositions())
//		{
//			//TODO: Exception schmei�en
//		} 
		
		//Zum Debugging das Schiff Random setzen
		
			int[] array = new int[getShipLength()];
			int start = counter * 10 + r.nextInt(5);
			
			
			for(int i=0; i<getShipLength(); i++)
			{
				array[i]=start +i;
			}
			
			counter += 1;

		 Positions = array;
		 
		 
	}
	
	public int[] getSchiffspositions()
	{
		
		return Positions;
		
	}
	
	public String getSchiffsname()
	{
		return getName();
	}
	
	

	
	public abstract int getShipLength();
	protected abstract String getName();
	
	
	protected int Image;
	public abstract int getImage();
	
	
}
