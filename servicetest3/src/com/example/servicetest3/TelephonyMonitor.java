package com.example.servicetest3;

//import com.example.servicetest3.MainActivity.MyPhoneStateListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class TelephonyMonitor extends Service {

	public final static String TELEPHONY_DATA = "usli.smd.payload.TELEPHONY_DATA";
	
	//telephony vars
  	TextView SignalStrengthandBitRateErr;
  	TelephonyManager Tel;
  	MyPhoneStateListener MyListener;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//set up Telephony service and its corresponding textViews to get strength and any errors
		MyListener = new MyPhoneStateListener();
		Tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		
		String message = intent.getStringExtra(MainActivity.TELEPHONY_GO); //grab intent from MainActivity
		if (message.equals("TELEPHONY_INTENT_GO")) {
			Intent telephonyDataSendIntent = new Intent(getApplicationContext(), DataAggregator.class);
			telephonyDataSendIntent.putExtra(TELEPHONY_DATA, "T");
        	startService(telephonyDataSendIntent);
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	/* Start the class for signal strength and bit err */
	class MyPhoneStateListener extends PhoneStateListener {
	      /* Get the Signal strength from the provider, each time there is an update */
	      @Override
	      public void onSignalStrengthsChanged(SignalStrength signalStrength)
	      {
	         super.onSignalStrengthsChanged(signalStrength);
	         //copy to SD (uncomment to copy straight to sd)
	         //sendDataToSD("<SStren>" + Integer.toString(signalStrength.getGsmSignalStrength()) + "</SStren><BErr>" + Integer.toString(signalStrength.getGsmBitErrorRate()) + "</BErr>");
	         //SignalStrengthandBitRateErr.setText(Integer.toString(signalStrength.getGsmSignalStrength()) + " | " + Integer.toString(signalStrength.getGsmBitErrorRate()));
	      }

	 };
	 /* end the class for signal strength and bit err */

}
