package com.example.roger.alzheimerhelper;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;

public class MainActivity extends FragmentActivity implements android.location.LocationListener {
    //private static final String LATITUDE = "BUNDLE_LA";
    //private static final String LONGTITUDE = "BUNDLE_LONG";
    String bestProvider;
    int positionCount = 0;
    float zoom=17;
    //String Alzheimer = "ALZHEIMER";
    private LocationManager locationManager;
    private GoogleMap mMap;

    public static Intent createIntent(Context context, String Latitude, String Longtitude) {
        String axis = "( " + Latitude + " , " + Longtitude + " )";
        Intent intent = new Intent();
        intent.setAction("ALZHEIMER");
        intent.putExtra("POSITION", axis);


        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        /*zoom = 17;
        LatLng Point = new LatLng(25.033611, 121.565000);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));*/
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
        Intent run = new Intent(this, Background.class);
        startService(run);
    }

    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
        //Intent intent=createIntent(this,"0","0");
        Intent intent = new Intent(this, Background.class);
        stopService(intent);

    }

    protected void onResume() {
        super.onResume();
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(bestProvider, 5000, 1, this);
        } else {
            Toast toast = Toast.makeText(this, "GPS need to Open", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onLocationChanged(Location location) {

        LatLng Point = new LatLng(location.getLatitude(), location.getLongitude());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String date = sDateFormat.format(new java.util.Date());

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Point, zoom));
        mMap.setMyLocationEnabled(true);

        String x = Double.toString((location.getLatitude()));
        String y = Double.toString((location.getLongitude()));
        sendBroadcast(createIntent(this, x, y));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Point)
                .title(String.valueOf(positionCount))
                .snippet(date)
                .draggable(false)
                .visible(true);
        mMap.addMarker(markerOptions);

        positionCount++;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Criteria criteria = new Criteria();
        bestProvider = locationManager.getBestProvider(criteria, true);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
