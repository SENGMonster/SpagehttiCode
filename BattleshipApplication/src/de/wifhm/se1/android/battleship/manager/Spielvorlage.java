package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstrakte Klasse
 * Stellt eine Flotte dar.
 * Erstellt anhand einer Liste von @see SchiffstypAmount eine Liste von Instanzen die von Schiff abgeleitet sind.
 * 
 * @author joe
 *
 */
public abstract class Spielvorlage {

	
	protected abstract Collection<SchiffstypAmount> setSchiffstypenAnzahlListe();
	
	
	private Collection<SchiffstypAmount> SchiffstypenAnzahlListe;
	private ArrayList<Schiff> Schiffsliste = new ArrayList<Schiff>();
	public int alreadySunkCounter =0;
		
	public Spielvorlage()
	{
		initializeSchiffsliste();
	}
	
	
	public ArrayList<Schiff> getSchiffsliste() {
		return Schiffsliste;
	}
	
	/**
	 * instanziert für die Zusammenstellung von Schiffstypen und ihrer Anzahl mit Hilfe von Reflection die angegebene Anzahl von Instanzen des Schifftyps
	 * und speichert diese in einer Liste von Schiffen  
	 */
	public void initializeSchiffsliste()
	{
		
		SchiffstypenAnzahlListe=null;
		Schiffsliste.clear();
		
		//Vorlagen Schiffe holen
		SchiffstypenAnzahlListe= setSchiffstypenAnzahlListe();
		
		
		
		for(SchiffstypAmount element : SchiffstypenAnzahlListe)
		{
			
			for (int i=0; i<element.getAmount();i++)
			{
				    try {
				    	
				    	//Soviele Instanzen zum Spiel hinzuf�gen wie im Plan vorgesehen
				    	Object obj = element.getSchiffstyp().newInstance();
						Schiffsliste.add((Schiff) obj);
						
					//Falls der Plan kaputt war	
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			}		
			
		}
		
	}
	
	
	
	
	
	
	}
