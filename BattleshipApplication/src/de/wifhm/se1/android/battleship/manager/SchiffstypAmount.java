package de.wifhm.se1.android.battleship.manager;

public class SchiffstypAmount {

	private int amount;
	private Class<?> Schiffstyp;
	
	
	public SchiffstypAmount()
	{
		
	}
	
	public SchiffstypAmount(int amo, Class<?> Schiffstyp)
	{
		setAmount(amo);
		setSchiffstyp(Schiffstyp);
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Class<?> getSchiffstyp() {
		return Schiffstyp;
	}
	public void setSchiffstyp(Class<?> schiffstyp) {
		Schiffstyp = schiffstyp;
	}
	
}
