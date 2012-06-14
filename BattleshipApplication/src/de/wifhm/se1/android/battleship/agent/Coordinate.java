package de.wifhm.se1.android.battleship.agent;

import de.wifhm.se1.android.battleship.manager.FieldState;

public class Coordinate {

	private int CoordinateNr;
	public int getCoordinateNr() {
		return CoordinateNr;
	}
	public void setCoordinateNr(int coordinateNr) {
		CoordinateNr = coordinateNr;
	}
	
	private FieldState StateField;
	public FieldState getStateField() {
		return StateField;
	}
	public void setStateField(FieldState stateField) {
		StateField = stateField;
	}
	
	private int PossiblityValue;
	
	public int getPossiblityValue() {
		return PossiblityValue;
	}
	public void setPossiblityValue(int possiblityValue) {
		PossiblityValue = possiblityValue;
	}
	
	private int NeigbourhoodValue;
	public int getNeigbourhoodValue() {
		return NeigbourhoodValue;
	}
	public void setNeigbourhoodValue(int neigbourhoodValue) {
		NeigbourhoodValue = neigbourhoodValue;
	}
	
}
