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



/**
 * 
 * @author Ramona
 *
 * Handelt die Positionierung der Schiffe ab.
 * Der Benutzer kann aus einer Gallery oben die Schiffe die noch zu positionieren sind auswählen.
 * Die Informaitonen über das Schiff welches ausgewählt ist werden eine Zeile darunter angezeigt.
 * Wenn der Benutzer ein Schiff aus der Gallery gewählt hat, kann er auf das GridView welches die Spielfläche darstellt klicken.
 * Ist es der erste Klick, wird der Anfang des Schiffes gesetzt. Dabei werden die möglichen Endpositionen errechnet.
 * Es wird darauf geachtet dass die Endpositionen keinem anderen Schiff in die Quere kommen und nicht über den Rand laufen.
 * Ist es der zweite Klick wird die Positionierung auf dem GridView angezeigt.
 * Der Benutzer kann nach dem zweiten Klick auf den dann eingeblendeten Button OK drücken, wodurch er die Positionierung in die Klasse überträgt und das Schiff als positioniert markiert.
 * Er kann nach dem ersten Klick den eingeblendeten Button Cancel drücken, die Positionierung wird aufgehoben.
 * Nach Beendigung der Positionierung wird er auf GridViewActivity weitergeleitet.
 */
public class PositionShipActivity extends Activity {
	
	private BattleshipApplication bsstub;
	
	private int numOfRowsCols =GlobalHolder.getInstance().getNumOfRowsCols(); 
	private int FieldCount=numOfRowsCols*numOfRowsCols;
	
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
    
    /**
     * Setzt die temporären Variablen auf die Ausgangszustände zurück
     * Beinhaltet das Zurücksetzen der Variablen für die gewählten Positionen,den Zähler für die Klickanzahl des Benutzers und die möglichen Endpositionen für ein Schiff..
     */
    public void Reset()
    {
    	StartPosition=-1;
    	EndPosition=-1;
    	ClickCounter=0;
    	possibleEndPositions=null;
    }
    
    /**
     * Entfernt alle Elemente des Positionierungsvorgangs wie das Entfernen der Anzeige der möglichen Endpunkte und nach Auswählen eines Endpunktes die Anzeige der Schiffsbilder auf den Positionen zwischen Start und Ende.
     */
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
    
    /**
     * Entfernt die Anzeige aller möglichen Endelemente auf der Oberfläche.
     * @param includingEndPo
     * beschreibt ob die erste Position auch mit von der Oberfläche genommen werden soll.
     * 
    */
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
	
    
    /**
     * Platziert von Startposition bis Endposition das Bild des Schiffes auf dem GridView.
     * @param img
     * das zu setzende Schiffsbild
     * 
     */
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
	    
	    
	    
	    numOfRowsCols=GlobalHolder.getInstance().getNumOfRowsCols();
	    
	    
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
	    gridView.setNumColumns(GlobalHolder.getInstance().getNumOfRowsCols());
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
		            	else if(ClickCounter==1){
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
	
	
	/**
	 * Versteckt die Buttons OK und Cancel
	 */
	private void HideAllButtonStuff(){
		btnOK.setVisibility(View.INVISIBLE);
		btnCancel.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Versteckt die Informationsanzeigen für das aus der Gallery ausgewählte Schiff was platziert werden soll
	 */
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