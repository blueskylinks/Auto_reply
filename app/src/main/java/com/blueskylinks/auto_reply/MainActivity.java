package com.blueskylinks.auto_reply;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String SMSBody1;
    boolean logged;
    String phoneNumber;
    String reply;
    SmsManager smsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS}, 200);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(sms_notify_reciver, mIntentFilter);
        registerReceiver(sms_notify_reciver,mIntentFilter);
    }

    private final BroadcastReceiver sms_notify_reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent2) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent2.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent2)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();

                    SMSBody1 = smsMessage.getMessageBody().toString();
                    Log.i("length", String.valueOf(SMSBody1.length()));
                    Log.i("Received SMS:", SMSBody1);
                    Toast.makeText(context, "Received SMS"+SMSBody1, Toast.LENGTH_SHORT).show();
                    switch(SMSBody1){
                        case "SPC,25":
                            Log.i("Received SMS:", SMSBody1);
                            reply="Motor off because of over load \n R phase 0.00 \n Y phase 0.00 \n B phase 0.10 \n Run Time:-0:1 \n Date:-07/01/2019 \n Time:-12:50:46";
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(senderNum, null, reply, null, null);
                            break;
                        case "SPC,24":
                           reply="RTC function on \n Run Time:-0:1 \n Date:-07/01/2017 \n Time:-13:11:37";
                            smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(senderNum, null, reply, null, null);
                            break;
                    }
                  /*  if(SMSBody1.contains("SPC,25")){
                        Log.i("Received SMS:", SMSBody1);
                        String reply="Motor off because of over load \n R phase 0.00 \n Y phase 0.00 \n B phase 0.10 \n Run Time:-0:1 \n Date:-07/01/2019 \n Time:-12:50:46";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(senderNum, null, reply, null, null);
                    }

                    else if(SMSBody1.contains("SPC,24")){
                        Log.i("Received SMS:", SMSBody1);
                        String reply="RTC function on \n Run Time:-0:1 \n Date:-07/01/2017 \n Time:-13:11:37";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(senderNum, null, reply, null, null);
                    }

                    else{}*/
                }
            }
        }
    };
}
