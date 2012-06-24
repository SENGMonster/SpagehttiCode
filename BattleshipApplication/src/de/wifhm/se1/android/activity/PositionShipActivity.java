package de.wifhm.se1.android.activity;

import java.util.ArrayList;

import org.ksoap2.SoapFault;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.wifhm.se1.android.battleship.manager.GlobalHolder;
import de.wifhm.se1.android.battleship.manager.Helper;
import de.wifhm.se1.android.battleship.manager.PositionShipBattleFieldImageAdapter;
import de.wifhm.se1.android.battleship.manager.PositionShipGalleryImageAdapter;
import de.wifhm.se1.android.battleship.manager.Schiff;
import de.wifhm.se1.android.battleship.manager.Spielvorlage;
import de.wifhm.se1.android.battleship.shiptypes.Spielvorlage1;


public class PositionShipActivity extends Activity {
	
	private BattleshipApplication bsstub;
	
	private final int numOfRowsCols =10; 
	private final int FieldCount=numOfRowsCols*numOfRowsCols;
	
	private ImageView shipimg;
	private TextView txtShipLength;
	private TextView txtShipName;
	private Button btnOK;
	private Button btnCancel;
	private PositionShipBattleFieldImageAdapter gvPositionView;
	private PositionShipGalleryImageAdapter galleryimgadp;
	
	
	private Schiff currShip;
	private ArrayList<Integer> alreadyPositionedShips = new ArrayList<Integer>();
	private int currShipIndex;	
	private int ShipPositionedCounter=0;
	
    private int StartPosition=-1;
    private int EndPosition=-1;
    private int ClickCounter=0; 
    private int[] possibleEndPositions;
    private ArrayList<Integer> impossibleFields = new ArrayList<Integer>();
    
    public void Reset()
    {
    	StartPosition=-1;
    	EndPosition=-1;
    	ClickCounter=0;
    	possibleEndPositions=null;
    }
    
    public void RemoveAllGarbageItems()
    {
    	if(StartPosition!=-1)
    	{
    		gvPositionView.addNewImgToPosition(StartPosition, R.drawable.wasser);
    		
    		RemovePossibleEndPositions(true);
    	}
    	if(EndPosition!=-1)
    	{    		
    		FulfillShip(R.drawable.wasser);
    		
    	}
    	
    }
    
    private void RemovePossibleEndPositions(boolean includingEndPo)
    {
    	if(possibleEndPositions!=null)
		{
			for(int i=0; i<4; i++){
				if (!includingEndPo)
				{
					if(possibleEndPositions[i]!=EndPosition) 
					{
						gvPositionView.addNewImgToPosition(possibleEndPositions[i], R.drawable.wasser);
					}
				}
				else{
					gvPositionView.addNewImgToPosition(possibleEndPositions[i], R.drawable.wasser);		
				}
   		 	}
		}
    }
	
    
    private void FulfillShip(int img){
    	int differenz= EndPosition-StartPosition;
		int differenzbetrag= Math.abs(differenz);
		if (differenzbetrag>9) //vertikale Anordnung
		{
			if (differenz>0) //nach unten
			{
    			for(int i=0; i<=differenzbetrag; i+=numOfRowsCols)
    			{
    				gvPositionView.addNewImgToPosition(StartPosition+i, img);
    			}
			}
			else{ //nach oben
				for(int i=0; i<=differenzbetrag; i+=numOfRowsCols)
    			{
    				gvPositionView.addNewImgToPosition(StartPosition-i, img);
    			}
			}
		}
		else { //horizontale Anordnung
			
			if(differenz>0)//nach rechts
			{
				for(int i=0; i<=differenzbetrag; i++)
    			{
    				gvPositionView.addNewImgToPosition(StartPosition+i, img);
    			}
			}
			else{ //nach links
				for(int i=0; i<=differenzbetrag; i++)
    			{
    				gvPositionView.addNewImgToPosition(StartPosition-i, img);
    			}
			}
			
			
		}
		
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.positionships);
	    
	    this.bsstub = (BattleshipApplication)getApplication();
	    // ------------- SPIELVORLAGE -------
	    
	  
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

	    galleryimgadp=new PositionShipGalleryImageAdapter(this);
	    gallery.setAdapter(galleryimgadp);

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	           // Toast.makeText(PositionShipActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        	
	         if (!alreadyPositionedShips.contains(position))
	         {
	            
	            Schiff item = GlobalHolder.getInstance().getUserShips().getSchiffsliste().get(position);
	            currShip = item;
	            currShipIndex = position;
	            shipimg.setImageResource(item.getImage());
	            shipimg.setVisibility(View.VISIBLE);
	            txtShipName.setText(item.getSchiffsname());
	            txtShipLength.setText(String.valueOf(item.getShipLength()));
	            
	         } 
	        }
	    });
	    
	    
	    
	    //--------- GRIDVIEW STUFF ------
	    
	    TypedArray a = obtainStyledAttributes(R.styleable.galleryBorder);
        int mGalleryItemBackground = a.getResourceId(R.styleable.galleryBorder_android_galleryItemBackground, 0);
        a.recycle();
        GlobalHolder.getInstance().setGridViewBackground(mGalleryItemBackground);
	    
	    GridView gridView = (GridView) findViewById(R.id.gvPosShips);
	    gvPositionView = new PositionShipBattleFieldImageAdapter(this);
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
		            		gvPositionView.addNewImgToPosition(StartPosition, currShip.getImage());
		            		
		            		if(currShip.getShipLength()==1){
		            			EndPosition=StartPosition;
		            			btnOK.setVisibility(View.VISIBLE);
		            			btnCancel.setVisibility(View.VISIBLE);
		            			 gvPositionView.notifyDataSetChanged();
		            			 return;
		            		}
		            		
		            		possibleEndPositions =  new int[4];
		            		
		            	    int value=-1;		            	    
		            	  
		            	    Helper helper = new Helper(StartPosition, currShip.getShipLength()-1);	            	    
		            	    
		            	    //Alle 4 Richtungen überprüfen und mögliche Felder setzen
		            	    for(int i=0; i<4; i++){		            	    	
		            	    	switch(i)
		            	    	{
			            	    	case 0:
			            	    		//Es Darf nicht über mehrere Zeilen gehen (keine Umbrüche) nach oben -->
			            	    		value= helper.validateRightToBottom();
			            	    		
			            	    		//überprüfen ob ein Schiff im weg ist
			            	    		if (value!=-1){
			            	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 1);
			            	    			if (Helper.hasHorizontalColidations(impossibleFields, positions))
			            	    			{
			            	    				value = -1;
			            	    			}
			            	    		}
			            	    		break;
			            	    	case 1:
			            	    		//es Darf keinen Zeilenumbruch nach unten hin geben <--
			            	    		value=helper.validateLeftToTop();
			            	    		
			            	    		//überprüfen ob ein Schiff im weg ist
			            	    		if (value!=-1){
			            	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 1);
			            	    			if (Helper.hasHorizontalColidations(impossibleFields, positions))
			            	    			{
			            	    				value = -1;
			            	    			}
			            	    		}
			            	    		break;
			            	    	case 2: 
			            	    		//ES DARF BEI VERTIKAL NICHT NACH OBEN HINAUS LAUFEN
			            	    		 value = helper.validateTop();
			            	    		 
			            	    		//überprüfen ob ein Schiff im weg ist
				            	    		if (value!=-1){
				            	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 10);
				            	    			if (Helper.hasVerticalColidations(impossibleFields, positions))
				            	    			{
				            	    				value = -1;
				            	    			}
				            	    		}
			            	    		break;
			            	    	case 3:
			            	    		//ES DARF BEI VERTIKAL NICHT NACH UNTEN HINAUS LAUFEN
			            	    		value=helper.validateBottom();
			            	    		
			            	    		//überprüfen ob ein Schiff im weg ist
			            	    		if (value!=-1){
			            	    			ArrayList<Integer> positions = Helper.buildPositionArray(StartPosition, value, 10);
			            	    			if (Helper.hasVerticalColidations(impossibleFields, positions))
			            	    			{
			            	    				value = -1;
			            	    			}
			            	    		}
			            	    		break;
		            	    	}
		            	    	
	            	    			possibleEndPositions[i] = value;
	            	    		
		            	    }
		            		
		            		
		            	    //TODO: Wenn alles -1 ist ne Anzeige fahren
		            		 for(int i=0; i<4; i++){
		            			 gvPositionView.addNewImgToPosition(possibleEndPositions[i], R.drawable.rot);
		            		 }
		            		
		            		 btnCancel.setVisibility(View.VISIBLE);
		            	}
		            	else{
		            		if(EndPosition!=-1)
		            		{
		            			//gvPositionView.addNewImgToPosition(EndPosition, currShip.getImage());
		            		
		            		}
		            		if(position!=StartPosition)
		            		{
		            			//überprüfen ob die geklickte Stelle richtig war
		            			boolean correctPosition=false;
		            			for(int i=0; i<possibleEndPositions.length; i++)
		            			{
		            				if(possibleEndPositions[i]==position)
		            				{
		            					correctPosition=true;
		            					break;
		            				}
		            			}
		            			if(correctPosition)
		            			{
				            		EndPosition = position;
				            		FulfillShip(currShip.getImage());
				            		btnOK.setVisibility(View.VISIBLE);
		            			}
		            		}
		            		
		            	}//end if ClickCounter==0
		            	
		           	 ClickCounter+=1;
		             gvPositionView.notifyDataSetChanged();
            	 
          	
          }//end if currShip!=null


	}//end Intern OnClickListener
	
	}); //end OnClickListener
        
        
        
       // ----------- BUTTON STUFF 
        
       //---- CANCEL
        btnCancel.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				RemoveAllGarbageItems();
				Reset();
				gvPositionView.notifyDataSetChanged();
				HideAllButtonStuff();
			}
		});
        
        //---- OK
        btnOK.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				RemovePossibleEndPositions(false);
				gvPositionView.notifyDataSetChanged();
				
				currShip.setSchiffspositions(StartPosition, EndPosition);
				for(Integer i:currShip.getSchiffspositions())
				{
					impossibleFields.add(i);	
				}
		
				//Das Item darf nicht mehr benutzt werden;
				alreadyPositionedShips.add(currShipIndex);
				
				Reset();
			    HideAllButtonStuff();
				HideAllShipStuff();
				
				ShipPositionedCounter+=1;
				Toast.makeText(PositionShipActivity.this, "Schiffe positioniert: "+String.valueOf(ShipPositionedCounter) + " von: " + String.valueOf(GlobalHolder.getInstance().getUserShips().getSchiffsliste().size()), Toast.LENGTH_LONG).show();
				
				if (ShipPositionedCounter==GlobalHolder.getInstance().getUserShips().getSchiffsliste().size())
				{
					 Toast.makeText(PositionShipActivity.this, "Spielfeld fertigestellt", Toast.LENGTH_LONG).show();
					 startActivity(new Intent(PositionShipActivity.this, GridViewActivity.class));
				}
				
			}
		});
        
        

	} //end onCreate
	
	private void HideAllButtonStuff(){
		btnOK.setVisibility(View.INVISIBLE);
		btnCancel.setVisibility(View.INVISIBLE);
	}
	
	private void HideAllShipStuff(){
		galleryimgadp.RemoveItem(currShipIndex);
		shipimg.setVisibility(View.INVISIBLE);
        txtShipName.setText("");
        txtShipLength.setText("");
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.logout:
				if(bsstub.getBsStub() != null && bsstub.getAngemeldeterUser() != null){
					try {
						bsstub.getBsStub().logout();
						startActivity(new Intent(PositionShipActivity.this, RegisterLoginActivity.class));
					} catch (SoapFault e) {	}
				}
				break;
			case R.id.preferences:
				startActivity(new Intent(PositionShipActivity.this, BattleshipPreferenceActivity.class));
				break;
			case R.id.exit:
				moveTaskToBack(true);
				break;
		}
		return true;
	}

} //end Class