package com.example.servicetest3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

public class DataAggregator extends Service {

	String hour, minute, second;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String time[] = getTime();
		
		//String[] timeData = intent.getStringArrayExtra(MainActivity.TIME_DATA);
		//sendDataToSD(timeData[0] + " | " + timeData[1] + " | " + timeData[2]);
		////String timeData = intent.getStringExtra(MainActivity.TIME_DATA);
		//sendDataToSD(timeData);
				
		String gpsData = intent.getStringExtra(GPSMonitor.GPS_DATA);
		//sendDataToSD(gpsData);
		
		String accelerometerData = intent.getStringExtra(AccelerometerMonitor.ACCELEROMETER_DATA);
		//sendDataToSD(accelerometerData);
		
		//String telephonyData = intent.getStringExtra(TelephonyMonitor.TELEPHONY_DATA);
		//sendDataToSD(telephonyData);
		
		sendDataToSD("<PACKET><type></type><hour>"+time[0]+"</hour><minute>"+time[1]+"</minute><second>"+time[2]+"</second><pressure_1></pressure_1>" +
    			"<temperature_1></temperature_1><humidity_1></humidity_1><solarirradiace_1></solarirradiace_1>" +
    			"<uvradiation_1></uvradiation_1><pressure_2></pressure_2><temperature_2></temperature_2>" +
    			"<humidity_2></humidity_2><solarirradiace_2></solarirradiace_2><uvradiation_2></uvradiation_2>" +
    			"<latitude>"+gpsData+"</latitude><longitude>"+gpsData+"</longitude><accelerometer_x>"+accelerometerData+"</accelerometer_x><accelerometer_y>"+accelerometerData+"</accelerometer_y>" +
    			"<accelerometer_z>"+accelerometerData+"</accelerometer_z><gyro_x></gyro_x><gyro_y></gyro_y><gyro_z></gyro_z><bearing>"+gpsData+"</bearing></PACKET>");
		
		//return START_REDELIVER_INTENT;
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void sendDataToSD(String data) {
	    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/external_sd/uoflsli/";
		String fname = "data.txt";
		String extState = Environment.getExternalStorageState();
		if (extState.equals(Environment.MEDIA_MOUNTED))
	    {
	        try {
	        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path + fname, false))); //the true enables append	        	
	        	out.println(data);
	            out.close();

	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	          }
	    }
    }
	
	public String[] getTime() {
		hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR));
    	minute = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
    	second = Integer.toString(Calendar.getInstance().get(Calendar.SECOND));
    	String time[] = { hour, minute, second };
    	return time;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
