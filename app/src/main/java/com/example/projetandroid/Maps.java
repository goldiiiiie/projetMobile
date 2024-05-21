package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.Manifest;
import android.widget.Toast;


public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    MapView mapview;
    Location current;
    //----------------------------------------------
    private LocationManager locationManager;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapview = findViewById(R.id.mapView);
        mapview.onCreate(savedInstanceState);
        mapview.getMapAsync(this);
        //-------------------------------------------------
        // Initialize LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    current = location;
                    mapview = findViewById(R.id.mapView);
                    mapview.getMapAsync(Maps.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //initialiser la map
        map = googleMap;
////        LatLng coordonnes = new LatLng(33.591933194978466, -7.604683353971355);
//        LatLng coordonnes = new LatLng(current.getLatitude(), current.getLongitude());
//        map.addMarker(new MarkerOptions().position(coordonnes).title("My location"));
//        //map.animateCamera();
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordonnes, 12));
        if (current != null) {
            // Utiliser la localisation actuelle pour définir le marqueur et la caméra
            LatLng coordinates = new LatLng(current.getLatitude(), current.getLongitude());
            map.addMarker(new MarkerOptions().position(coordinates).title("My location"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 12));}
//        } else {
//            // Afficher un message d'erreur ou gérer le cas où la localisation actuelle est null
//            Toast.makeText(this, "Unable to retrieve current location", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"LOCATION PERMISSION IS DENIED, please allow the permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //---------------------------------------------------

        //to get current Location
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED) {
//            // Request runtime permissions if not granted
//            ActivityCompat.requestPermissions(this, new
//                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_LOCATION_PERMISSION);
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);



}


