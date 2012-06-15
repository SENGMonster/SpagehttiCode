package de.wifhm.se1.android.activity;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.wifhm.se1.android.battleship.manager.PositionShipBattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.PositionShipGalleryImageAdapter;
import de.wifhm.se1.android.battleship.manager.Schiff;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;


public class PositionShipActivity extends Activity {
	
	
	private final int numOfRowsCols =10; 
	private final int FieldCount=numOfRowsCols*numOfRowsCols;
	
	private ImageView shipimg;
	private TextView txtShipLength;
	private TextView txtShipName;
	private Spielvorlage v1; 
	private Button btnOK;
	private Button btnCancel;
	private PositionShipBattleFieldImageAdapter gvPositionView;
	
	private Schiff currShip;
	
    private int StartPosition=-1;
    private int EndPosition=-1;
    private int ClickCounter=0; 
    private int[] possibleEndPositions;
    
    public void Reset()
    {
    	if (StartPosition==-1)
    	{
    		gvPositionView.getView(StartPosition, null, null);
    	}
    	if (EndPosition==-1)
    	{
    		gvPositionView.getView(StartPosition, null, null);
    	}
    	
    	StartPosition=-1;
    	EndPosition=-1;
    	ClickCounter=0;
    	
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.positionships);

	    // ------------- SPIELVORLAGE -------
	    v1 = new Spielvorlage1();
	    v1.initializeSchiffsliste();
	    
	    
	  
	    // --------------- GET ELEMENTS FROM VIEW
	    shipimg = (ImageView) findViewById(R.id.ship_image);
	    txtShipLength = (TextView) findViewById(R.id.shiplength);
	    txtShipName = (TextView) findViewById(R.id.shipname);
	    btnOK = (Button) findViewById(R.id.btnPosShipsOK);
	    btnCancel = (Button) findViewById(R.id.btnPosShipsCancel);
	   

	    // -------- GALLERY STUFF -----------
	    DisplayMetrics metrics = new DisplayMetrics();
	    this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

	    Gallery gallery = (Gallery) findViewById(R.id.gallery);

	    MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
	    mlp.setMargins(-(metrics.widthPixels/2 + 50), 
	                   mlp.topMargin, 
	                   mlp.rightMargin, 
	                   mlp.bottomMargin
	    );

	    gallery.setAdapter(new PositionShipGalleryImageAdapter(this));

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	            Toast.makeText(PositionShipActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	            
	            v.setVisibility(0);
	            Schiff item = v1.getSchiffsliste().get(position);
	            currShip = item;
	            shipimg.setImageResource(item.getImage());
	            txtShipName.setText(item.getSchiffsname());
	            txtShipLength.setText(String.valueOf(item.getShipLength()));
	            //btnOK.setVisibility(1);
	            btnCancel.setVisibility(1);
	            
	           
	        }
	    });
	    
	    
	    
	    //--------- GRIDVIEW STUFF ------
	    
	    TypedArray a = obtainStyledAttributes(R.styleable.galleryBorder);
        int mGalleryItemBackground = a.getResourceId(R.styleable.galleryBorder_android_galleryItemBackground, 0);
        a.recycle();
	    
	    GridView gridView = (GridView) findViewById(R.id.gvPosShips);
	    gvPositionView = new PositionShipBattleFieldImageAdapter(this,mGalleryItemBackground);
        gridView.setAdapter(gvPositionView);
        
        
        gridView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View v, int position,
  					long id) {
            	
            	  ImageView imageView = (ImageView) v;
            	  
            	 if (currShip!=null)
            	 {
		            	if (ClickCounter==0)
		            	{
		            		StartPosition = position;
		            		possibleEndPositions =  new int[4];
		            		
		            	    int value=-1;
		            	    boolean correct=true;
		            	    
		            	    String number = String.valueOf(StartPosition);
		            	    String subStrNumber = number.substring(0,number.length()-1);		            	   
		            	    int gerundet=Integer.valueOf(subStrNumber)*numOfRowsCols;
		            	    
		            	    //Alle 4 Richtungen überprüfen und mögliche Felder setzen
		            	    for(int i=0; i<4; i++){
		            	    	correct=true;
		            	    	switch(i)
		            	    	{
			            	    	case 0:
			            	    		value= StartPosition + currShip.getShipLength()-1;
			            	    		//Es Darf nicht über mehrere Zeilen gehen (keine Umbrüche)
			            	    		if(value > gerundet+numOfRowsCols) correct=false;
			            	    		break;
			            	    	case 1:
			            	    		//es Darf keinen Zeilenumbruch geben
			            	    		value=StartPosition - currShip.getShipLength()-1;
			            	    		if(value<gerundet-numOfRowsCols) correct=false;
			            	    	case 2: 
			            	    		//ES DARF BEI VERTIKAL NICHT NACH OBEN HINAUS LAUFEN
			            	    		 value = StartPosition - ((currShip.getShipLength()-1)*numOfRowsCols);
			            	    		 if (value<0) correct=false;
			            	    		break;
			            	    	case 3:
			            	    		//ES DARF BEI VERTIKAL NICHT NACH UNTEN HINAUS LAUFEN
			            	    		value = StartPosition + ((currShip.getShipLength()-1)*numOfRowsCols);
			            	    		if(value>FieldCount) correct=false;
			            	    		break;
		            	    	}
		            	    	
		            	    	
		            	    	if(!correct){
	            	    			possibleEndPositions[i] = -1;
	            	    		}
	            	    		else{
	            	    			possibleEndPositions[i] = value;
	            	    		}
		            	    }
		            		
		            		gvPositionView.addNewImgToPosition(StartPosition, currShip.getImage());
		            		
		            		 for(int i=0; i<4; i++){
		            			 gvPositionView.addNewImgToPosition(possibleEndPositions[i], R.drawable.rot);
		            		 }
		            		
		            	}
		            	else{
		            		if(EndPosition!=-1)
		            		{
		            			//gvPositionView.addNewImgToPosition(EndPosition, currShip.getImage());
		            		
		            		}
		            		EndPosition = position;
		            		
		            		int differenz= EndPosition-StartPosition;
		            		int differenzbetrag= Math.abs(differenz);
		            		if (differenzbetrag>9) //vertikale Anordnung
		            		{
		            			if (differenz>0) //nach unten
		            			{
			            			for(int i=0; i<=differenzbetrag; i+=numOfRowsCols)
			            			{
			            				gvPositionView.addNewImgToPosition(StartPosition+i, currShip.getImage());
			            			}
		            			}
		            			else{ //nach oben
		            				for(int i=0; i<=differenzbetrag; i+=numOfRowsCols)
			            			{
			            				gvPositionView.addNewImgToPosition(StartPosition-i, currShip.getImage());
			            			}
		            			}
		            		}
		            		else { //horizontale Anordnung
		            			
		            			if(differenz>0)//nach rechts
		            			{
		            				for(int i=0; i<=differenzbetrag; i++)
			            			{
			            				gvPositionView.addNewImgToPosition(StartPosition+i, currShip.getImage());
			            			}
		            			}
		            			else{ //nach links
		            				for(int i=0; i<=differenzbetrag; i++)
			            			{
			            				gvPositionView.addNewImgToPosition(StartPosition-i, currShip.getImage());
			            			}
		            			}
		            			
		            			
		            		}
		            		
		            		

		            		
		            	}//end if ClickCounter==0
		            	
		           	 ClickCounter+=1;
		             gvPositionView.notifyDataSetChanged();
            	 
          	
          }//end if currShip!=null


	}//end Intern OnClickListener
	
	}); //end OnClickListener

	} //end onCreate

} //end Class