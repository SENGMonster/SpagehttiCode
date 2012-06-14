package de.wifhm.se1.android.activity;

import de.wifhm.se1.android.battleship.manager.PositionShipImageAdapter;
import de.wifhm.se1.android.battleship.manager.Schiff;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PositionShipActivity extends Activity {
	
	private ImageView shipimg;
	private TextView txtShipLength;
	private TextView txtShipName;
	private Spielvorlage v1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.positionships);

	    v1 = new Spielvorlage1();
	    v1.initializeSchiffsliste();
	    
	    Gallery gallery = (Gallery) findViewById(R.id.gallery);
	    shipimg = (ImageView) findViewById(R.id.ship_image);
	    txtShipLength = (TextView) findViewById(R.id.shiplength);
	    txtShipName = (TextView) findViewById(R.id.shipname);
	    
	    gallery.setAdapter(new PositionShipImageAdapter(this));

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	            Toast.makeText(PositionShipActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	            
	            v.setVisibility(0);
	            Schiff item = v1.getSchiffsliste().get(position);
	            shipimg.setImageResource(item.getImage());
	            txtShipName.setText(item.getSchiffsname());
	            txtShipLength.setText(String.valueOf(item.getShipLength()));
	            
	           
	        }
	    });

	}
	
}
