package com.example.roger.alzheimerhelper;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class Background extends Service {
    String Alzheimer = "ALZHEIMER";
    String x, y;
    MyReceiver myReceiver;

    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Alzheimer);
        this.registerReceiver(myReceiver, filter);

        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    public class MyReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String a = bundle.getString("POSITION");
            Toast.makeText(Background.this, a, Toast.LENGTH_LONG).show();//intent.getStringExtra("POSITION") , Toast.LENGTH_LONG).show();
            System.out.println(a);
        }
    }
}


