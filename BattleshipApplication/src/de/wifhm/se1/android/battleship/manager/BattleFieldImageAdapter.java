package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import de.wifhm.se1.android.activity.R;

public class BattleFieldImageAdapter extends BaseAdapter {
    
	
	private static final int count = 100;
		
	private Context mContext;
    protected Spielvorlage mSpielvorlage;
    protected Hashtable<Integer, Integer> dict;
    protected ArrayList<Integer> Waterfields;
    
    public BattleFieldImageAdapter(Context c, Spielvorlage spielv, ArrayList<Integer> tempWaterfields) {
        mContext = c;
        mSpielvorlage=spielv;
        
        setSchiffe();
        Waterfields = tempWaterfields;
      }
    
    protected void setSchiffe()
    {
    	 dict = new Hashtable<Integer, Integer>();
    	 
    	 if (Waterfields!=null)
    	{	 
	         //alle Wasser und abgeschossenen Positionen setzen
	    	 for(int el:Waterfields)
	    	 {
	    		 dict.put(el, R.drawable.keintreffer);
	    	 }
    	}
         
    	 
    	 for (Schiff s:mSpielvorlage.getSchiffsliste()) {
    	
    		 
    		 //alle Treffer setzen
    		 if(s.getHitPositions().size()>0)
    		 {
    			 for(int i=0; i<s.getHitPositions().size(); i++)
    			 {
    				 dict.put(i, R.drawable.rot);
    			 }
    		 }
    		 
    		 
         	if(s.Positions != null)
         	{
         		String temp = s.getSchiffsname() +": ";
         		for (int i = 0; i < s.Positions.size(); i++) {
         			
         			//falls an der Stelle noch kein Wasser oder Treffer ist das Bild hinzufügen
         			if(dict.get(s.Positions.get(i))==null)
         			{
         				dict.put(s.Positions.get(i), s.getImage());
         			}
         			temp+=String.valueOf(s.Positions.get(i)) + ";";
         		}
         		System.out.println(temp);
         	}
         }
         
         int counter = GlobalHolder.getInstance().getNumOfRowsCols() *GlobalHolder.getInstance().getNumOfRowsCols() ;
         for (int i = 0; i < counter; i++) {
         	if (dict.get(i)==null) dict.put(i, R.drawable.wasser);
         }         
    	
    }
    
    public void setWasser(int position){
    	
    	dict.remove(position);
    	dict.put(position, R.drawable.keintreffer);
    	notifyDataSetChanged();
    	
    }
    
    public void setTreffer(int position){
    	
    	dict.remove(position);
    	dict.put(position, R.drawable.rot);
    	notifyDataSetChanged();
    	
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
   
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(1, 1, 1, 1);
        imageView.setImageResource(dict.get(position));
   
       return imageView;
    }


    
}
