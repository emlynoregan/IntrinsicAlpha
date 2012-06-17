package com.emlynoregan.androidapp.thehuntbuilder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TheHuntBuilderActivity extends Activity 
{
	TextView tvCoords;
	TextView tvClue;
	ImageView ivEgg;
	
	Location SaveLocation = null;
	double latitude;
	double longitude;
	
	// Acquire a reference to the system Location Manager
	LocationManager locationManager;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        // tvCoords = (TextView) findViewById(R.id.tvCoords);
        tvClue = (TextView) findViewById(R.id.tvClue);
        ivEgg = (ImageView) findViewById(R.id.ivEgg);
        
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (savedInstanceState != null)
        {
        	latitude = savedInstanceState.getDouble("latitude");
        	longitude = savedInstanceState.getDouble("longitude");
			SaveLocation = new Location(LocationManager.GPS_PROVIDER);
			SaveLocation.setLatitude(savedInstanceState.getDouble("savelatitude"));
			SaveLocation.setLongitude(savedInstanceState.getDouble("savelongitude"));
        	tvClue.setText(savedInstanceState.getString("pointlist"));
        }
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, 
        		new LocationListener() {
					
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub
						
					}
					
					public void onProviderEnabled(String provider) {
						runOnUiThread(
							new Runnable() {
								
								public void run() {
									tvCoords.setText("Provider Enabled");
								}
							}
						);
					}
					
					public void onProviderDisabled(String provider) {
						runOnUiThread(
								new Runnable() {
									
									public void run() {
										tvCoords.setText("Provider Disabled");
									}
								}
							);
						
					}
					
					public void onLocationChanged(Location location) {
						final Location finalLocation = location;
						runOnUiThread(
								new Runnable() {
									
									public void run() {
										latitude = finalLocation.getLatitude();
										longitude = finalLocation.getLongitude();
										
										if (SaveLocation == null)
										{
											SaveLocation = new Location(LocationManager.GPS_PROVIDER);
											SaveLocation.setLatitude(latitude);
											SaveLocation.setLongitude(longitude);
										}

										double ldistanceMeters = finalLocation.distanceTo(SaveLocation);
										double lfactor = 100 / (ldistanceMeters + 100);
										ivEgg.setLayoutParams(new LinearLayout.LayoutParams((int)Math.round(Math.floor(87*4*lfactor)), (int) Math.round(Math.floor(121*4*lfactor))));
//										tvCoords.setText(String.format("%f meters", ldistanceMeters));
									}
								}
							);
					}
				}
        );
        
        ivEgg.setOnClickListener(
        	new OnClickListener() 
        	{
	            public void onClick(View v) 
	            {
					SaveLocation = new Location(LocationManager.GPS_PROVIDER);
					SaveLocation.setLatitude(latitude);
					SaveLocation.setLongitude(longitude);

	            	String lsavePointString = String.format("\nlat=%f long=%f", SaveLocation.getLatitude(), SaveLocation.getLongitude());

	            	CharSequence lstuff = tvClue.getText();
	            	tvClue.setText(lstuff + lsavePointString);

	            	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                     
	                 emailIntent.setType("plain/text");
	            
	                 emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "emlynoregan@gmail.com"});
	          
	                 emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, lsavePointString);
	          
	                 emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, lsavePointString);
	  
	                 startActivity(Intent.createChooser(emailIntent, "Send mail..."));

					double lfactor = 1;
					ivEgg.setLayoutParams(new LinearLayout.LayoutParams((int)Math.round(Math.floor(87*4*lfactor)), (int) Math.round(Math.floor(121*4*lfactor))));
	            }
        	}        
        );
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) 
    {
    	savedInstanceState.putDouble("latitude", latitude);
    	savedInstanceState.putDouble("longitude", longitude);
    	savedInstanceState.putDouble("savelatitude", SaveLocation.getLatitude());
    	savedInstanceState.putDouble("savelongitude", SaveLocation.getLongitude());
    	savedInstanceState.putString("pointlist", tvClue.getText().toString());
    }
}