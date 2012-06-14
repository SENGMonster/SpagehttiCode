package de.wifhm.se1.android.battleship.agent;

import java.util.ArrayList;

import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;

public class Tester {

		
	public static void main(String[] args){
		
		Spielvorlage S1 = new Spielvorlage1();
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(); 
		
		for(int i=1; i<=100; i++)
		{
			
		}
		
		
		Agent a = new Agent(S1, coordinates);
		
	}
	
	
}
