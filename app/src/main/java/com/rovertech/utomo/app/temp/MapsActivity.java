package com.rovertech.utomo.app.temp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rovertech.utomo.app.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng ahmd = new LatLng(23.03, 72.40);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney).title("Marker in Sydney");

        MarkerOptions markerOptionsAhmd = new MarkerOptions();
        markerOptionsAhmd.position(ahmd).title("Ahmedabad");
        mMap.addMarker(markerOptions);
        mMap.addMarker(markerOptionsAhmd);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ahmd));


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.marker_window_layout, null);
                TextView markerTextView = (TextView) v.findViewById(R.id.markerTextView);
                markerTextView.setText(marker.getTitle());


                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        if (marker.getTitle().equalsIgnoreCase("Ahmedabad")) {
                            Toast.makeText(MapsActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                return v;
            }
        });


    }
}
