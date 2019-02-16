package com.example.aca.findyourplace.controller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

public class EventMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int eventId;
    private LatLng eventLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        eventId=1;
        Event event=null;

        try {
            event = Event.loadEvent(eventId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(event != null)
        {

            Place place=null;
            try {
                place = Place.loadPlace(event.getPlaceId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(place!=null)
            {

                eventLatLng=new LatLng(place.getLatitude(),place.getLongitude());
            }
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        Log.d("mapReady", "izvrsilo se");
        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(eventLatLng).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
