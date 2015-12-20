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
    /*private static final String LATITUDE = "BUNDLE_LA";
    private static final String LONGTITUDE = "BUNDLE_LONG";*/
    String Alzheimer = "ALZHEIMER";
    String x, y;
    MyReceiver myReceiver;

    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        myReceiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
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

    public class  MyReceiver extends BroadcastReceiver{
            // intent.getStringExtra("POSITION")
            public void onReceive(Context context, Intent intent) {
                Bundle bundle=intent.getExtras();
                String a=bundle.getString("POSITION");
                Toast.makeText(Background.this,a,Toast.LENGTH_LONG).show();//intent.getStringExtra("POSITION") , Toast.LENGTH_LONG).show();
                System.out.println(a);
            }
    }
    /*@Override
    public void onLocationChanged(Location location) {
        String x = Double.toString((location.getLatitude()));
        String y = Double.toString((location.getLongitude()));
        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(this, "(" + x + " , " + y + ")", Toast.LENGTH_LONG).show();
        //Intent intent = new Intent();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/
}


