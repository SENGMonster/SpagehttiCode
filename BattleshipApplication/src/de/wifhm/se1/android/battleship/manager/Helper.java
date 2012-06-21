package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;

import android.app.backup.RestoreObserver;

import de.wifhm.se1.android.battleship.agent.AgentManager;
import de.wifhm.se1.android.battleship.agent.Coordinate;

public class Helper {
	
	private int Ausgangspunkt;
	private int gerundet;
	private int AnzahlSchritte;
	private int numOfRowsCols;
	
	
	public Helper(int Auspunkt, int AnzSchritte){
		 Ausgangspunkt = Auspunkt;
		 AnzahlSchritte = AnzSchritte;
		 
		 numOfRowsCols =  GlobalHolder.getInstance().getNumOfRowsCols();
		 gerundet = getGerundetFromInteger(Ausgangspunkt,numOfRowsCols);
		
	}

	public int getGerundetFromInteger(int Zahl, int numOfRowsCols){
		  String number = String.valueOf(Zahl);
		String subStrNumber=number;
	    if (number.length()>1)
	    {
	    	 subStrNumber = number.substring(0,number.length()-1);
		}

	    int gerundet=Integer.valueOf(subStrNumber)*numOfRowsCols;
	    return gerundet;
	}
	
	//000000xx
	//<--x0000 VERBOTEN
	public int validateLeftToTop(){
		int value=Ausgangspunkt - AnzahlSchritte;
		if(value<gerundet) {
			return -1;
		}else{
			return value;
		}
	}
	
	//00000xx -->
	//x0000
	public int validateRightToBottom(){
		int value =Ausgangspunkt + AnzahlSchritte;
		//Es Darf nicht über mehrere Zeilen gehen (keine Umbrüche) nach oben -->
		if(value >= gerundet+numOfRowsCols){
			return -1;	
		}
		else{
			return value;
		}
	}
	
	public int validateBottom(){
		int value = Ausgangspunkt + (AnzahlSchritte*numOfRowsCols);
		if (value>(numOfRowsCols*numOfRowsCols)-1){
			return  -1;
		}
		return value;	
	}
	
	public int validateTop(){
		int value = Ausgangspunkt - (AnzahlSchritte*numOfRowsCols);
		if (value<0) {
			return -1;
		}else{
			return value;
		}
	}
	
	
	public static ArrayList<Integer> setImpossibleFieldsaroundShip(Schiff s, boolean isHorizontal){
		
		
		ArrayList<Integer> resultList= new ArrayList<Integer>();
		
		for(int i=0; i< s.getSchiffspositions().size(); i++)
		{
			Coordinate currentCoordinate = AgentManager.getInstance().getCoordinateForNr(s.getSchiffspositions().get(i));
			if(currentCoordinate!=null)
			{
				Helper valHelper = new Helper(currentCoordinate.getCoordinateNr(), 1);
				if (isHorizontal){
					
					int unten = valHelper.validateBottom();
					int oben = valHelper.validateTop();
					
					if (unten!=-1) resultList.add(unten);
					if(oben!=-1) resultList.add(oben);
					
					//Beim ersten und letzten auch noch links und rechts setzen
					if(i==0){
						int links = valHelper.validateLeftToTop();
						if (links!=-1){
							resultList.add(links);
							
							//und auch noch oben und unten von links (kein Übereck)
							Helper locH = new Helper(links, 1);
							int linksunten = locH.validateBottom();
							int linksoben = locH.validateTop();
							
							if (linksunten!=-1) resultList.add(linksunten);
							if (linksoben!=-1) resultList.add(linksoben);
						}
						
					}
					if(i==s.getSchiffspositions().size()-1)
					{
						int rechts = valHelper.validateRightToBottom();
						if(rechts!=-1){
							resultList.add(rechts);
							
							Helper locH = new Helper(rechts, 1);
							int rechtsunten = locH.validateBottom();
							int rechtsoben = locH.validateTop();
							
							if (rechtsunten!=-1) resultList.add(rechtsunten);
							if (rechtsoben!=-1) resultList.add(rechtsoben);
						}
					}
				}
				else{
					
					int links = valHelper.validateLeftToTop();
					int rechts = valHelper.validateRightToBottom();
					
					if(links!=-1) resultList.add(links);
					if(rechts!=-1) resultList.add(rechts);
					
					if(i==0){
						int oben = valHelper.validateTop();
						if(oben!=-1){
							resultList.add(oben);
							
							Helper locH = new Helper(oben, 1);
							int obenlinks = locH.validateLeftToTop();
							int obenrechts = locH.validateRightToBottom();
							
							if (obenlinks!=-1) resultList.add(obenlinks);
							if (obenrechts!=-1) resultList.add(obenrechts);
						}
					}
					if(i==s.getSchiffspositions().size()-1)
					{
						int unten = valHelper.validateBottom();
						if(unten!=-1){
							resultList.add(unten);
							
							Helper locH = new Helper(unten, 1);
							int untenlinks = locH.validateLeftToTop();
							int untenrechts = locH.validateRightToBottom();
							
							if (untenlinks!=-1) resultList.add(untenlinks);
							if (untenrechts!=-1) resultList.add(untenrechts);
						}
					}
					
				}
			}
		}
		
		
		return resultList;
	}
}
