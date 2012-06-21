package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;
import java.util.Hashtable;

import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;


public class PositionShipGalleryImageAdapter extends BaseAdapter {
    
    private Context mContext;
    private Spielvorlage mGameLayout;
      
    Hashtable<Integer, Integer> Schiffliste = new Hashtable<Integer, Integer>();
    
    public PositionShipGalleryImageAdapter(Context c) {
        mContext = c;
        
        mGameLayout = new Spielvorlage1();
        mGameLayout.initializeSchiffsliste();
        ArrayList<Schiff> Ships =mGameLayout.getSchiffsliste();
        
        for(int i=0; i< Ships.size(); i++)
        {
        	Schiff item = Ships.get(i);
        	Schiffliste.put(i, item.getImage());
        }
    }
    
    public void RemoveItem(int position)
    {
    	Schiffliste.remove(position);
    	Schiffliste.put(position, R.drawable.keintreffer);
    	notifyDataSetChanged();
    }

    public int getCount() {
        return Schiffliste.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(Schiffliste.get(position));
        imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(GlobalHolder.getInstance().getGridViewBackground());
        imageView.setPadding(10, 5, 0, 5);
        
        return imageView;
    }
}