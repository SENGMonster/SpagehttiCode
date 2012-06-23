package de.wifhm.se1.android.battleship.shiptypes;

import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.manager.Schiff;

public class Tankschiff  extends Schiff{
	
	public Tankschiff()
	{
	}
	
	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return "Tankschiff";
	}

	@Override
	public int getImage() {
		return R.drawable.sample1;
	}

	@Override
	public int getShipLength() {
		return 3;
	}

	@Override
	public char getShipChiffre() {
		// TODO Auto-generated method stub
		return 't';
	}

}
