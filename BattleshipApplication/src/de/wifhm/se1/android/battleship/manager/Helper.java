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
	
	
	public static ArrayList<Integer> setImpossibleFieldsaroundShip(ArrayList<Integer> Pos, boolean isHorizontal){
		
		
		ArrayList<Integer> resultList= new ArrayList<Integer>();
		
		for(int i=0; i< Pos.size(); i++)
		{
			Integer currentCoordinate = Pos.get(i);
			if(currentCoordinate!=null)
			{
				Helper valHelper = new Helper(currentCoordinate, 1);
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
					if(i==Pos.size()-1)
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
					if(i==Pos.size()-1)
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
	
	
	public static ArrayList<Integer> buildPositionArray(int Start, int Ende, int incrementor){
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (Start > Ende) {
			for (int s=Ende; s<=Start; s+=incrementor ){
				list.add(s);
			}	
		}
		else{
			for (int s =Start; s<=Ende; s+=incrementor ){
				list.add(s);
			}
		}

		return list;
	}
	
	public static boolean hasHorizontalColidations(ArrayList<Integer>besetzteFelder, ArrayList<Integer> Positions){
		boolean result=false;
	
		if (besetzteFelder!=null && Positions!=null){
			for(int i=0; i<Positions.size(); i++)
			{
				int pos = Positions.get(i);
				Helper h = new Helper(pos,1);
				int unten = h.validateBottom();
				int oben = h.validateTop();
				
				if (unten!=-1){
					if (besetzteFelder.contains(unten)) return true;
				}
				if (oben!=-1){
					if(besetzteFelder.contains(oben)) return true;
				}
			
				if (besetzteFelder.contains(pos)) return true;
				
				
				//RECHTES ENDE abprüfen + die schrägen Ecken
				if(i==0){
					int linkesEnde = h.validateLeftToTop();
					if (linkesEnde!=-1){
						if (besetzteFelder.contains(linkesEnde)){
							return true;
						}
						Helper hRE = new Helper(linkesEnde, 1);
						int hREunten= hRE.validateBottom();
						int hREoben = hRE.validateTop();
						
						if (hREunten!=-1){
							if(besetzteFelder.contains(hREunten)) return true;
						}
						if (hREoben!=-1){
							if(besetzteFelder.contains(hREoben)) return true;
						}
					}
				}
				
				//LINKES ENDE abprüfen + die schrägen Ecken
				if(i==Positions.size()-1){
					
					int rechtesEnde = h.validateRightToBottom();
					if (rechtesEnde!=-1){
						if (besetzteFelder.contains(rechtesEnde)){
							return true;
						}
						Helper hRE = new Helper(rechtesEnde, 1);
						int hREunten= hRE.validateBottom();
						int hREoben = hRE.validateTop();
						
						if (hREunten!=-1){
							if(besetzteFelder.contains(hREunten)) return true;
						}
						if (hREoben!=-1){
							if(besetzteFelder.contains(hREoben)) return true;
						}
					}
					
				}
			}
		
		}
		return result;
	}
	
	public static boolean hasVerticalColidations(ArrayList<Integer> besetzteFelder, ArrayList<Integer> Positions){
		boolean result=false;
		
		if (besetzteFelder !=null && !besetzteFelder.isEmpty() && Positions!=null){
			for(int i =0; i<Positions.size();i++){
				
				int pos = Positions.get(i);
				Helper h = new Helper(pos,1);
				int links = h.validateLeftToTop();
				int rechts = h.validateRightToBottom();
				
				if (links!=-1){
					if (besetzteFelder.contains(links)) return true;
				}
				if (rechts!=-1){
					if(besetzteFelder.contains(rechts)) return true;
				}
			
				if (besetzteFelder.contains(pos)) return true;
				
				//die obere Ecke abprüfen + die schrägen Ecken
				if(i==0){
					int oberesEnde = h.validateTop();
					if (oberesEnde!=-1){
						if (besetzteFelder.contains(oberesEnde)){
							return true;
						}
						Helper hRE = new Helper(oberesEnde, 1);
						int hOEleft= hRE.validateLeftToTop();
						int hREright = hRE.validateRightToBottom();
						
						if (hOEleft!=-1){
							if(besetzteFelder.contains(hOEleft)) return true;
						}
						if (hREright!=-1){
							if(besetzteFelder.contains(hREright)) return true;
						}
					}
				}
				
				//die untere Ecke abprüfen + die schrägen Ecken
				if(i==Positions.size()-1){
					int unteresEnde = h.validateBottom();
					if (unteresEnde!=-1){
						if (besetzteFelder.contains(unteresEnde)){
							return true;
						}
						Helper hRE = new Helper(unteresEnde, 1);
						int hOEleft= hRE.validateLeftToTop();
						int hREright = hRE.validateRightToBottom();
						
						if (hOEleft!=-1){
							if(besetzteFelder.contains(hOEleft)) return true;
						}
						if (hREright!=-1){
							if(besetzteFelder.contains(hREright)) return true;
						}
					}
				}
				
			}
		}	
		return result;
	}
}
