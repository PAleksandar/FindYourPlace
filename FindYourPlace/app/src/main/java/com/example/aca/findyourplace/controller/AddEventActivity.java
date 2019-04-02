package com.example.aca.findyourplace.controller;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aca.findyourplace.BuildConfig;
import com.example.aca.findyourplace.EventRabbit;
import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.model.Event;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
//import com.example.aca.findyourplace.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
//import com.google.android.libraries.places.api.Places;

public class AddEventActivity extends AppCompatActivity {
    private double latitude=0;
    private double longitude=0;
    private EditText txtName;
    private EditText txtDescription;
    private EditText txtTag;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Thread publishThread;
    private EventRabbit eventRabbit = new EventRabbit();
    String date;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Button btnAdd = (Button) findViewById(R.id.buttonAdd);
        txtName=(EditText) findViewById(R.id.editTextName);
        txtDescription =(EditText) findViewById(R.id.editTextDescription);
        txtTag =(EditText) findViewById(R.id.editTextTag);
        mDisplayDate = (TextView) findViewById(R.id.editDate);
        userId = (int) getIntent().getExtras().get("UserId");
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyAVeCeJDgT2muwO37mapN9cACZ2Cf9yBYU");

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("eeee", "Place: " + place.getName() + ", " + place.getId());
                LatLng latLng = place.getLatLng();
                if(latLng==null)
                    Log.i("null", "Place: " + place.getName() + ", " + place.getId());
                else
                    Log.i("notnull", String.valueOf(latLng.latitude));
                String placeId = place.getId();

// Specify the fields to return (in this example all fields are returned).
                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

// Construct a request object, passing the place ID and fields array.
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();

                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place2 = response.getPlace();
                    latitude=place2.getLatLng().latitude;
                    longitude=place2.getLatLng().longitude;
                    Log.i("drugi", "Place found: " + place2.getLatLng().latitude);
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        int statusCode = apiException.getStatusCode();
                        // Handle error with given status code.
                        Log.e("sss", "Place not found: " + exception.getMessage());
                    }
                });

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //Log.i(TAG, "An error occurred: " + status);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(latitude!=0&&longitude!=0)
                {
                    com.example.aca.findyourplace.model.Place p = new com.example.aca.findyourplace.model.Place();
                    p.setLatitude(latitude);
                    p.setLongitude(longitude);


                    String json = p.savePlace();
                    com.example.aca.findyourplace.model.Place pReturned = new com.example.aca.findyourplace.model.Place();
                    Gson gson= new Gson();
                    pReturned = gson.fromJson(json,com.example.aca.findyourplace.model.Place.class);

                    Event e = new Event();
                    e.setPlaceId(pReturned.getId());
                    e.setName(txtName.getText().toString());
                    e.setDescription(txtDescription.getText().toString());
                    e.setTag(txtTag.getText().toString());
                    e.setOwnerUserId(userId);
                    e.setDate(new java.util.Date(date));

                    e.saveEvent();
                    eventRabbit.publishToAMQP(publishThread,"event",e);

                    String s=gson.toJson(e);
                    eventRabbit.publishMessage(s);



                }
                else
                {
                    ///Nije moguce dodati treba da se napise obavestenje
                }

            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("ttt", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
