package de.wifhm.se1.android.battleship.shiptypes;

import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.manager.Schiff;

public class Flugzeugtraeger extends Schiff {

	public Flugzeugtraeger()
	{
		super();
	}
	

	@Override
	public int getShipLength() {
		return 5;
	}

	@Override
	protected String getName() {
		return "Flugzeugtr�ger";
	}


	@Override
	public int getImage() {
		return R.drawable.sample_1;
	}

}
