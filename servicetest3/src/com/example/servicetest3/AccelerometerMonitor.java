package com.example.servicetest3;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

public class AccelerometerMonitor extends Service implements SensorEventListener {
	
	public final static String ACCELEROMETER_DATA = "usli.smd.payload.ACCELEROMETER_DATA";
	
	Sensor accelerometer;
  	SensorManager sm;
  	float x, y, z;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Toast.makeText(this, "ACCL.onStart()", Toast.LENGTH_LONG).show();
		
		//set up Accelerometer service and its corresponding textViews
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		String message = intent.getStringExtra(MainActivity.ACCELEROMETER_GO); //grab intent from MainActivity
		if (message.equals("ACCELEROMETER_INTENT_GO")) {
			Intent accelerometerDataSendIntent = new Intent(getApplicationContext(), DataAggregator.class);
			accelerometerDataSendIntent.putExtra(ACCELEROMETER_DATA, " | " + x + " | " + y + " | " + z);
        	startService(accelerometerDataSendIntent);
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		x = event.values[0];
 	    y = event.values[1];
 	    z = event.values[2];
		
	}

}
