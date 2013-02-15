package com.example.servicetest3;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity { /***implements SensorEventListener {***/

	/**private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds**/
    
	public final static String TIME_DATA = "usli.smd.payload.TIME_DATA";
    public final static String GPS_GO = "usli.smd.payload.GPS_GO";
    public final static String ACCELEROMETER_GO = "usli.smd.payload.ACCELEROMETER_GO";
    public final static String TELEPHONY_GO = "usli.smd.payload.TELEPHONY_GO";
    
    
    /**protected LocationManager locationManager;**/
  	String Long, Latt, Bear;
  	
    protected Button retrieveLocationButton;
    protected TextView displayView;
    
  //accelerometer vars
  	/***Sensor accelerometer;
  	SensorManager sm;
  	TextView acceleration;***/
  	TextView time;
  	/***float x, y, z;***/
    
  //telephony vars
  	/****TextView SignalStrengthandBitRateErr;
  	TelephonyManager Tel;
  	MyPhoneStateListener MyListener;****/
  	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveLocationButton = (Button) findViewById(R.id.retrieve_location_button);
        displayView = (TextView) findViewById(R.id.displayView);
        
        /**locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );**/
        
        //time
        time = (TextView) findViewById(R.id.time);
        		
		//set up Accelerometer service and its corresponding textViews
		/***acceleration = (TextView) findViewById(R.id.acceleration);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);***/
        
		//set up Telephony service and its corresponding textViews to get strength and any errors
		/****SignalStrengthandBitRateErr = (TextView) findViewById(R.id.signal);
		MyListener = new MyPhoneStateListener();
		Tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);****/
		
    retrieveLocationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {            	
            	
                //showCurrentLocation();
            	String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR));
            	String minute = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
            	String second = Integer.toString(Calendar.getInstance().get(Calendar.SECOND));
            	time.setText(hour + " | " + minute + " | " + second);
            	/*Intent timeIntent = new Intent(getApplicationContext(), DataAggregator.class);
            	timeIntent.putExtra(TIME_DATA, hour + " | " + minute + " | " + second);
            	startService(timeIntent);*/

				Intent gpsIntent = new Intent(getApplicationContext(), GPSMonitor.class);
				gpsIntent.putExtra(GPS_GO, "GPS_INTENT_GO");
				startService(gpsIntent);

				
				Intent accelerometerIntent = new Intent(getApplicationContext(), AccelerometerMonitor.class);
				accelerometerIntent.putExtra(ACCELEROMETER_GO, "ACCELEROMETER_INTENT_GO");
				startService(accelerometerIntent);

            	
            	
            	/*Intent TelephonyIntent = new Intent(getApplicationContext(), TelephonyMonitor.class);
            	TelephonyIntent.putExtra(ACCELEROMETER_GO, "TELEPHONY_INTENT_GO");
            	startService(TelephonyIntent);*/
            	
            	
            	/*Intent i2 = getIntent();
            	String data_message = i2.getStringExtra(MainActivity.TIME_EXTRA_MSG);
            	time.setText(data_message);
            	stopService(timeIntent);*/
            	
            	
            	/***acceleration.setText("X: " + x + "\nY: " + y + "\nZ: " + z);***/
            	//sendDataToSD("<PACKET><type></type><hour>"+hour+"</hour><minute>"+minute+"</minute><second>"+second+"</second><pressure_1></pressure_1>" +
            	//		"<temperature_1></temperature_1><humidity_1></humidity_1><solarirradiace_1></solarirradiace_1>" +
            	//		"<uvradiation_1></uvradiation_1><pressure_2></pressure_2><temperature_2></temperature_2>" +
            	//		"<humidity_2></humidity_2><solarirradiace_2></solarirradiace_2><uvradiation_2></uvradiation_2>" +
            	//		"<latitude>"+Latt+"</latitude><longitude>"+Long+"</longitude><accelerometer_x>"+x+"</accelerometer_x><accelerometer_y>"+y+"</accelerometer_y>" +
            	//		"<accelerometer_z>"+z+"</accelerometer_z><gyro_x></gyro_x><gyro_y></gyro_y><gyro_z></gyro_z><bearing>"+Bear+"</bearing></PACKET>");
            }
    }); //end onClick() listener 
        
    } //end onCreate()
    
    /**public void setLong(String l) { Long = l; }
    public void setLatt(String l) { Latt = l; }
    public void setBear(String b) { Bear = b; }
    
    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {

            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s \n Bearing: %3$s",
                    location.getLongitude(), location.getLatitude(), location.getBearing()
            );
            Long = Double.toString(location.getLongitude());
            setLong(Long);
            Latt = Double.toString(location.getLatitude());
            setLatt(Latt);
            Bear = Float.toString(location.getBearing());
            setBear(Bear);
            displayView.setText(message);
            
            //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
            		"New Location \n Longitude: %1$s \n Latitude: %2$s \n Bearing: %3$s",
                    location.getLongitude(), location.getLatitude(), location.getBearing()
            );
            //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MainActivity.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }**/
    
    //accelerometer
    /***public void onSensorChanged(SensorEvent event) {
 	   //acceleration.setText("X: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
 	   x = event.values[0];
 	   y = event.values[1];
 	   z = event.values[2];
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}***/
	
	
	/* Start the class for signal strength and bit err */
	/****class MyPhoneStateListener extends PhoneStateListener {****/
	      /* Get the Signal strength from the provider, each time there is an update */
	      /****@Override
	      public void onSignalStrengthsChanged(SignalStrength signalStrength)
	      {
	         super.onSignalStrengthsChanged(signalStrength);
	         //copy to SD (uncomment to copy straight to sd)
	         //sendDataToSD("<SStren>" + Integer.toString(signalStrength.getGsmSignalStrength()) + "</SStren><BErr>" + Integer.toString(signalStrength.getGsmBitErrorRate()) + "</BErr>");
	         //SignalStrengthandBitRateErr.setText(Integer.toString(signalStrength.getGsmSignalStrength()) + " | " + Integer.toString(signalStrength.getGsmBitErrorRate()));
	      }

	 };****/
	 /* end the class for signal strength and bit err */
	
	/*public void sendDataToSD(String data) {
	    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/external_sd/uoflsli/";
		String fname = "data2.txt";
		String extState = Environment.getExternalStorageState();
		if (extState.equals(Environment.MEDIA_MOUNTED))
	    {
	        try {
	        	System.out.println('1');
	        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + fname, true))); //the true enables append	        	
	        	out.println(data);
	        	System.out.println('2');
	            out.close();

	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	          }
	    }
    }*/

    
}
