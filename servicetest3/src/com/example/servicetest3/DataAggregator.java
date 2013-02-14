package com.example.servicetest3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class DataAggregator extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		String gpsData = intent.getStringExtra(GPSMonitor.GPS_DATA);
		sendDataToSD(gpsData);
		
		//String accelerometerData = intent.getStringExtra(AccelerometerMonitor.ACCELEROMETER_DATA);
		//sendDataToSD(accelerometerData);
		
		//String telephonyData = intent.getStringExtra(TelephonyMonitor.TELEPHONY_DATA);
		//sendDataToSD(telephonyData);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void sendDataToSD(String data) {
	    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/external_sd/uoflsli/";
		String fname = "data.txt";
		String extState = Environment.getExternalStorageState();
		if (extState.equals(Environment.MEDIA_MOUNTED))
	    {
	        try {
	        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + fname, true))); //the true enables append	        	
	        	out.println(data);
	            out.close();

	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	          }
	    }
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
