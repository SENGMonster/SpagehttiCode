package de.wifhm.se1.android.battleship.manager;

import java.util.ArrayList;
import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;


public class PositionShipImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;
    
    private Spielvorlage mGameLayout;
    private ArrayList<Schiff> Schiffliste;
    
    private Integer[] mImageIds = {
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7
    };

    public PositionShipImageAdapter(Context c) {
        mContext = c;
        
        mGameLayout = new Spielvorlage1();
        mGameLayout.initializeSchiffsliste();
        Schiffliste = mGameLayout.getSchiffsliste();
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        imageView.setImageResource(Schiffliste.get(position).getImage());
        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);

        
        return imageView;
    }
}