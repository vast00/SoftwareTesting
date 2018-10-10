package com.android.ecnu.hospital;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction()!=null){
            if (intent.getAction().equals("com.android.ecnu.hospital.MY_BROADCAST")){
                String type = intent.getStringExtra("Type");
                Intent startIntent = new Intent(context, MainActivity.class);
                startIntent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startIntent);
                Intent webIntent = new Intent("com.android.ecnu.hospital.WEBVIEW");
                webIntent.putExtra("Type", type);
                context.sendBroadcast(webIntent);
            }
        }

    }
}
