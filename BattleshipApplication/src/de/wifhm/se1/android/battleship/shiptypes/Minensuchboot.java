package de.wifhm.se1.android.battleship.shiptypes;

import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.manager.Schiff;

public class Minensuchboot extends Schiff{

	
	public Minensuchboot()
	{
		super();
	}
	
	@Override
	public int getShipLength() { 
		return 2;
	}

	@Override
	protected String getName() {
		return "Minensuchboot";
	}

	@Override
	public int getImage() { 
		return R.drawable.sample4;
	}

	@Override
	public char getShipChiffre() {
		return 'm';
	}

}
