package de.wifhm.se1.android.battleship.agent;


public class Destroy {

	//the first Hit of the ship
	private Coordinate initialShot;
	
	//contains if the direction of the ship is already known (if there are 2 hits the direction comes clear)
	private boolean has2Hitsalready=false;
	//contains the direction (horizontal, vertical) of the ship which comes clear after 2 hits 
	private boolean isHorizontal =false;
	
	
	
	public boolean isHas2Hitsalready() {
		return has2Hitsalready;
	}
	public void setHas2Hitsalready(boolean has2Hitsalready) {
		this.has2Hitsalready = has2Hitsalready;
	}
	
	public boolean isHorizontal() {
		return isHorizontal;
	}
	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}
	public Coordinate getInitialShot() {
		return initialShot;
	}
	public void setInitialShot(Coordinate initialShot) {
		this.initialShot = initialShot;
	}

	

	
	
}
