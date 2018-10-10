package com.android.ecnu.hospital;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyService extends Service {

    private String TAG = "hospital";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(new WebViewCookieHandler())
                            .build();
                    Request request = new Request.Builder()
                            .url("http://jzdc.ecnucpp.com:6100/api/messages?version="+MainActivity.version)
                            .build();
                    Response response = client.newCall(request).execute();
                    if(response.code()==200){
                        String responseData = response.body().string();
                        Log.d(TAG, responseData);
                        if(responseData.equals("[]")){
                            Log.d(TAG, "get null");
                        }else {
                            Gson gson = new Gson();
                            List<MyMessage> messages = gson.fromJson(responseData, new TypeToken<List<MyMessage>>(){}.getType());
                            for (MyMessage message : messages){
                                String type = message.getMessageType();
                                String title = message.getMessageTitle();
                                String content = message.getMessageContent();
                                MyNotification.notification(MyService.this, title, content, type);
                            }
                        }
                    }else {
                        Log.d(TAG, String.valueOf(response.code()));
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 2*1000;
        Intent i = new Intent(this, MyService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
