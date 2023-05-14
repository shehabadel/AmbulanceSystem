package com.example.ambulancesystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ambulancesystem.databinding.ActivityMaps2Binding;

import java.util.List;

public class MapsActivity2 extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;
    Marker mCurrentLocationMarker;
    FusedLocationProviderClient mFusedLocation;
    LocationManager mLocationManager;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    TextView lat;
    TextView lang;
    ImageButton backbtn;
    ImageView avatar;
    TextView cancelRequestButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_text);
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        lat = (TextView) findViewById(R.id.lat);
        lang = (TextView) findViewById(R.id.lang);
        // Initialize location manager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        cancelRequestButton = findViewById(R.id.cancelRequestButton);
        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: cancel request from database
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
            }
        });

        backbtn = findViewById(R.id.backButton);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity2.this, Homepage.class);
                startActivity(intent);
            }
        });
//        avatar = findViewById(R.id.avatar_image);
//        avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MapsActivity2.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
        // Request location updates
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location currentLocation = locationList.get(locationList.size() - 1);

                mLastLocation = currentLocation;
                if (mCurrentLocationMarker != null) {
                    mCurrentLocationMarker.remove();
                }

                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); //current location

                Log.d("latlng", latLng.toString());
                mMap.clear();

//                lat.setText(String.valueOf(latLng.latitude));
//                lang.setText(String.valueOf(latLng.longitude));
                //circle = mGMap.addCircle(new CircleOptions().center(latLng).radius(100).strokeWidth(5).strokeColor(Color.BLUE).fillColor(Color.argb(128, 0, 200, 0)));
                mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();

        if (mFusedLocation != null) {
            mFusedLocation.removeLocationUpdates(mLocationCallback);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        int locationInterval = 5000, locationFastestInterval = 5000, locationMaxWaitTime = 5000;

        mLocationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, locationInterval)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(locationFastestInterval)
                .setMaxUpdateDelayMillis(locationMaxWaitTime)
                .build();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(true);

            } else {   //checking permission
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                        new AlertDialog.Builder(this).setTitle("We need your location permission").setMessage("please allow the location permission to enable location app functionalities")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ActivityCompat.requestPermissions(MapsActivity2.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
                                    }
                                }).create().show();
                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
                    }
                }
            }
        } else {
            mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
            if(mLastLocation!=null){
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                lat.setText(String.valueOf(currentLocation.latitude));
                lang.setText(String.valueOf(currentLocation.longitude));
        }
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        // Update text views with new location
        lat.setText(String.valueOf(location.getLongitude()));
        lang.setText(String.valueOf(location.getLatitude()));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(this, "sorry! permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public LatLng getCurrentLocation() {
        if (mLastLocation != null) {
            return new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            return null;
        }
    }
}