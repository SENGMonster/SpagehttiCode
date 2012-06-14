package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Spielvorlage {

	
	protected abstract Collection<SchiffstypAmount> setSchiffstypenAnzahlListe();
	
	
	private Collection<SchiffstypAmount> SchiffstypenAnzahlListe;
	private ArrayList<Schiff> Schiffsliste = new ArrayList<Schiff>();
		
	public Spielvorlage()
	{
		initializeSchiffsliste();
	}
	
	
	public ArrayList<Schiff> getSchiffsliste() {
		return Schiffsliste;
	}
	
	public void initializeSchiffsliste()
	{
		
		
		//Vorlagen Schiffe holen
		SchiffstypenAnzahlListe= setSchiffstypenAnzahlListe();
		
		
		
		for(SchiffstypAmount element : SchiffstypenAnzahlListe) {
			
			for (int i=0; i<element.getAmount();i++)
			{
				    try {
				    	
				    	//Soviele Instanzen zum Spiel hinzuf�gen wie im Plan vorgesehen
				    	//String name = element.getSchiffstyp().getClass().getName();
				    	Object obj = element.getSchiffstyp().newInstance();
				    	
				    	//System.out.print(name);
				    	//mona.Schiffstypen.Flugzeugtraeger flugzeugtraeger = (mona.Schiffstypen.Flugzeugtraeger) obj;
				    	//mona.Schiffstypen.Flugzeugtraeger newflugzeugtraeger = flugzeugtraeger.getClass().newInstance();
				    	
				    	//Class clazz = obj.getClass();
				    	//Constructor c = clazz.getConstructor();
						Schiffsliste.add((Schiff) obj);
						
					//Falls der Plan kaputt war	
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
//					} catch (NoSuchMethodException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
			}
			
		}
		
	}
	
	
	
	
	
	
}
