package com.marketingknob.mercury.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.marketingknob.mercury.R;
import com.marketingknob.mercury.util.GPSTracker;

import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshya on 9/10/2018.
 */

public class ClubLocationActivity extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    Double currentLatitude = 0.0, currentLongitude = 0.0;
    private static final String TAG = "ClubLocationActivity";
    GPSTracker gpsTracker;
    ArrayList<HashMap<String, String>> location = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    LatLngBounds.Builder builder;
    CameraUpdate cu;
    private static int MAP_TIME_OUT = 4000;

    @BindView(R.id.myLocationBtn) AppCompatButton LocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_location);

        ButterKnife.bind(this);
        gpsTracker  = new GPSTracker(this);
        currentLatitude = gpsTracker.getLatitude();
        currentLongitude = gpsTracker.getLongitude();
        LocationButton.setOnClickListener(this);

        // Location 1
        map = new HashMap<String, String>();
        map.put("LocationID", "1");
        map.put("Latitude", "28.613939");
        map.put("Longitude", "77.209023");
        map.put("LocationName", "Delhi");
        location.add(map);
        // Location 2
        map = new HashMap<String, String>();
        map.put("LocationID", "2");
        map.put("Latitude", "12.971599");
        map.put("Longitude", "77.594566");
        map.put("LocationName", "Banglore");
        location.add(map);

        // Location 3
        map = new HashMap<String, String>();
        map.put("LocationID", "3");
        map.put("Latitude", "23.735840");
        map.put("Longitude", "91.744530");
        map.put("LocationName", "Tripura");
        location.add(map);


        Log.d(TAG, "onCreate: "+gpsTracker.getLatitude()+","+gpsTracker.getLongitude());

        askPermission();
    }

    //Ask For Runtime Permission by user
    void askPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.home_map);
                mapFragment.getMapAsync(ClubLocationActivity.this);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                finish();
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
//                .setRationaleTitle("Runtime permission")
//                .setRationaleMessage("If u use the services of app you need to Confirm Runtime Permission")
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                        "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Go to setting")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    @Override
    public void onClick(final View view) {

        if (view == LocationButton) {
            getMyLocation();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        builder = new LatLngBounds.Builder();

        for (int i = 0; i < location.size(); i++) {
            Latitude                = Double.parseDouble(location.get(i).get("Latitude").toString());
            Longitude               = Double.parseDouble(location.get(i).get("Longitude").toString());
            String name             = location.get(i).get("LocationName").toString();
            MarkerOptions marker    = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);
//            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.bar));
            googleMap.addMarker(marker);
            builder.include(marker.getPosition());

        }

        //Add Current Location Marker on the Map
        MarkerOptions mp1 = new MarkerOptions();
        mp1.position(new LatLng(currentLatitude, currentLongitude));
        mp1.draggable(true);
        mp1.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green));
        mMap.addMarker(mp1);
        googleMap.getUiSettings().setMapToolbarEnabled(false);


        //Show Multiple Markers on Map on Front of User with 10% offset
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(cu);
                nextScreenIntent();
            }
        });


    }

    //  This method calling when we move to current location from a button click
    private void getMyLocation() {
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
        mMap.animateCamera(cameraUpdate);
    }

    //   Intent To Home Screen
    void nextScreenIntent(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ClubLocationActivity.this, HomeActivity.class));
                Animatoo.animateZoom(ClubLocationActivity.this);
                finish();
//                Animatoo.animateDiagonal(ClubLocationActivity.this);


            }
        }, MAP_TIME_OUT);
    }
}
