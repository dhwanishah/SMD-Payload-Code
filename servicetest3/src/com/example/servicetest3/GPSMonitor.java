package com.example.servicetest3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

public class GPSMonitor extends Service {
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	public final static String GPS_DATA = "usli.smd.payload.GPS_DATA";
	
	protected LocationManager locationManager;  
  	String Long, Latt, Bear;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Toast.makeText(this, "GPS.onStart()", Toast.LENGTH_LONG).show();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);        
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener());
		showCurrentLocation();
		
		String message = intent.getStringExtra(MainActivity.GPS_GO); //grab intent from MainActivity
		if (message.equals("GPS_INTENT_GO")) {
			Intent gpsDataSendIntent = new Intent(getApplicationContext(), DataAggregator.class);
			gpsDataSendIntent.putExtra(GPS_DATA, Long + " | " + Latt + " | " + Bear);
        	startService(gpsDataSendIntent);
		}
    	//stopSelf();

		//return super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}
	
	public void setLong(String l) { Long = l; }
    public void setLatt(String l) { Latt = l; }
    public void setBear(String b) { Bear = b; }
    
    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {

            /*String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s \n Bearing: %3$s",
                    location.getLongitude(), location.getLatitude(), location.getBearing());*/
            Long = Double.toString(location.getLongitude());
            setLong(Long);
            Latt = Double.toString(location.getLatitude());
            setLatt(Latt);
            Bear = Float.toString(location.getBearing());
            setBear(Bear);
            

        }

    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            /*String message = String.format(
            		"New Location \n Longitude: %1$s \n Latitude: %2$s \n Bearing: %3$s",
                    location.getLongitude(), location.getLatitude(), location.getBearing());*/

        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(GPSMonitor.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(GPSMonitor.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(GPSMonitor.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }
	
	public void sendDataToSD(String data) {
	    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/external_sd/uoflsli/";
		String fname = "data.txt";
		String extState = Environment.getExternalStorageState();
		if (extState.equals(Environment.MEDIA_MOUNTED))
	    {
	        try {
	        	//System.out.println('1');
	        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + fname, true))); //the true enables append	        	
	        	out.println(data);
	        	//System.out.println('2');
	            out.close();

	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	          }
	    }
    }
}
