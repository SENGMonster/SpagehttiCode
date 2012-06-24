package de.wifhm.se1.android.battleship.manager;

import java.util.Hashtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import de.wifhm.se1.android.activity.R;

/**
 * 
 * Beliefert das GridView zur Anzeige des Spielfeldes zur Positionierung der Schiffe mit Bildern
 * Anfänglich wird das GridView mit neutralen Bildern besetzt.
 * Kann anhand der Interaktionen des Benutzers für Positionen die Bilder aktualisieren und durch die Bilder des zu positionierenden Schiffes bzw. zur Anzeige der Positionierungsmöglichekiten ersetzen. 
 * @author Kai
 *
 */
public class PositionShipBattleFieldImageAdapter extends BaseAdapter {
    
	Hashtable<Integer, Integer> numbers = new Hashtable<Integer, Integer>();
  
	private static final int count = 100;
	
	private Context mContext;        
	
    public PositionShipBattleFieldImageAdapter(Context c) { 
        mContext = c;
       
        
        for(int i=0; i<count; i++)
        {
        	numbers.put(i, R.drawable.wasser);
        }
    }

    
    /**
     * setzt das mitgegebene Image an die Position in der GridView
     * @param position
     * die Position an der das Bild angezeigt werden soll
     * @param img
     * das anzuzeigende Bild 
     */
   public void addNewImgToPosition(int position, int img)
   {
	   numbers.remove(position);
	   numbers.put(position, img);
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
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
            
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(numbers.get(position));
    	
        return imageView;
    }

}
