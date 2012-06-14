package de.wifhm.se1.android.battleship.shiptypes;


import java.util.ArrayList;
import java.util.Collection;

import de.wifhm.se1.android.battleship.manager.SchiffstypAmount;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;

public class Spielvorlage1  extends Spielvorlage{

	
	public Spielvorlage1()
	{
		super();
	}
	
	@Override
	protected Collection<SchiffstypAmount> setSchiffstypenAnzahlListe() {
		
		Collection<SchiffstypAmount> c = new ArrayList<SchiffstypAmount>(5);
		c.add(new SchiffstypAmount(1, Flugzeugtraeger.class));
		c.add(new SchiffstypAmount(2, Schlachtschiff.class));
		c.add(new SchiffstypAmount(1, Tankschiff.class));
		c.add(new SchiffstypAmount(2, Minensuchboot.class));
		c.add(new SchiffstypAmount(3, Schnellboote.class));
		
		
		System.out.println("SchiffstypenAnzahlliste gesetzt");
		
		return c;
		
	}
	

}
