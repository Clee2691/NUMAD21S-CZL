package com.example.numad21s_czl;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class ActivityLocator extends AppCompatActivity {
    private static String TAG = "LocatorActivity";
    private static final int LOCATION_REQUEST_CODE = 50;

    private FusedLocationProviderClient fusedLocProv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);
        fusedLocProv = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();
    }

    public void onClickLocateMe(View view) {
        TextView longTextView = findViewById(R.id.textView_longitude);
        TextView latTextView = findViewById(R.id.textView_latitude);

        OnSuccessListener<Location> locSuccess = new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    longTextView.setText(String.format("%.4f",location.getLongitude()));
                    latTextView.setText(String.format("%.4f",location.getLatitude()));
                }
            }
        };

        if (checkLocationPermission()) { // If we have permission get location data
            fusedLocProv.getLastLocation().addOnSuccessListener(this, locSuccess);
        } else { // Request the location permission again.
            requestLocationPermission();
        };
    }

    private boolean checkLocationPermission() {
        Button btn_displayLocation = findViewById(R.id.btn_displayLocation);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            return true;
        }
        return false;
    }

    protected void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Button btn_displayLocation = findViewById(R.id.btn_displayLocation);
        if (reqCode == LOCATION_REQUEST_CODE) {
            // Permission denied
            if (grantResults.length == 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                btn_displayLocation.setClickable(false);
            } else { // Permission accepted
                btn_displayLocation.setClickable(true);
            }
        }
    }
}