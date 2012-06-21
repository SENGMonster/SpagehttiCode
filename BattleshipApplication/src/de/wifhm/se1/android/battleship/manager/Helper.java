package de.wifhm.se1.android.battleship.manager;

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
}
