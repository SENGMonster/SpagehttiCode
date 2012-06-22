package de.wifhm.se1.android.battleship.shiptypes;

import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.manager.Schiff;

public class Schlachtschiff  extends Schiff{

	public Schlachtschiff()
	{
		super();
	}
	
	
	@Override
	public int getShipLength() {
		return 4;
	}

	@Override
	protected String getName() {
		return "Schlachtschiff";
	}

	@Override
	public int getImage() {
		return R.drawable.sample_4;
	}


	@Override
	public char getShipChiffre() {
		return 's';
	}

}
