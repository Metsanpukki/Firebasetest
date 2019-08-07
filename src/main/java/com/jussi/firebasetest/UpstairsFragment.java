package com.jussi.firebasetest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class UpstairsFragment extends Fragment {
    private static final String TAG = "UpstairsFragment";
    private FirebaseDatabase database; //realtime database
    private DatabaseReference myRef;

    public UpstairsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_upstairs, container, false);
        final TextView temperatureTextView = rootView.findViewById(R.id.upstairsTemperatureTextView);
        final TextView humidityTextView = rootView.findViewById(R.id.upstairsHumidityTextView);
        final TextView pressureTextView = rootView.findViewById(R.id.upstairsPressureTextView);
        final TextView altitudeTextView = rootView.findViewById(R.id.upstairsAltitudeTextView);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("upstairs_sensor_bme280");

        //realtime database listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JSONObject sensorData;
                try {
                    sensorData = new JSONObject(dataSnapshot.getValue().toString());
                    temperatureTextView.setText(String.format("Temperature: %s%s", sensorData.getString("temperature"), "°c"));
                    humidityTextView.setText(String.format("Humidity: %s%s", sensorData.getString("humidity"), " %"));
                    pressureTextView.setText(String.format("Pressure: %s%s", sensorData.getString("pressure"), " hPa"));
                    altitudeTextView.setText(String.format("Altitude: %s%s", sensorData.getString("altitude"), " m"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value." + databaseError.toException());
            }
        });
        return rootView;
    }

}
