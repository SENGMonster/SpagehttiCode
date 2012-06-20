package de.wifhm.se1.android.battleship.manager;

public class Helper {

	public static int getGerundetFromInteger(int Zahl, int numOfRowsCols){
		  String number = String.valueOf(Zahl);
		String subStrNumber=number;
	    if (number.length()>1)
	    {
	    	 subStrNumber = number.substring(0,number.length()-1);
		}

	    int gerundet=Integer.valueOf(subStrNumber)*numOfRowsCols;
	    return gerundet;
	}
	
	
	public static boolean validateLeftToTop(int value, int gerundet){
		if(value<gerundet) {
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateRightToBottom(int value, int gerundet, int numOfRowsCols){
		if(value >= gerundet+numOfRowsCols){
			return false;	
		}
		else{
			return true;
		}
	}
	
	public static boolean validateBottom(int value, int numOfRowsCols){
		if (value>(numOfRowsCols*numOfRowsCols)-1){
			return false;
		}
		return true;	
	}
	
	public static boolean validateTop(int value){
		if (value<0) {
			return false;
		}else{
			return true;
		}
	}
}
