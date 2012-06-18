package de.wifhm.se1.android.battleship.manager;

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
    private Spielvorlage mSpielvorlage;
    
    public BattleFieldImageAdapter(Context c, Spielvorlage spielv) {
        mContext = c;
        mSpielvorlage=spielv;
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
            //imageView.setBackgroundResource(GlobalHolder.getInstance().getGridViewBackground());  
        } else {
            imageView = (ImageView) convertView;
        }

        
        boolean imageSourceSet =false;
        
        //Falls das Ding eine Position von einem Schiff ist das Schiffsbild setzen
               
    	for(Schiff element : mSpielvorlage.getSchiffsliste()) {
    		for(int i: element.getSchiffspositions())
    		{
    			if (i==position)
    			{
    				imageView.setImageResource(element.getImage());
    				imageSourceSet =true;
    			}
    		}
    	}
    	
    	if (!imageSourceSet) imageView.setImageResource(R.drawable.wasser);
    	
       
        return imageView;
    }


    
}
