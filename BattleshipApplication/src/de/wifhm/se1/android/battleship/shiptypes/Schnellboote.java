package de.wifhm.se1.android.battleship.shiptypes;

import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.manager.Schiff;

public class Schnellboote extends Schiff{
	
	public Schnellboote()
	{
		super();
	}

	@Override
	public int getShipLength(){
		return 1;
	}

	@Override
	protected String getName() {
		return "Schnellboot";
	}

	@Override
	public int getImage() {
		return R.drawable.sample4;
	}

	@Override
	public char getShipChiffre() {
		return 'b';
	}
	

}
