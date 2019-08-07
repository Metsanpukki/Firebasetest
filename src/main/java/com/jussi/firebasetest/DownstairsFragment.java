package com.jussi.firebasetest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownstairsFragment extends Fragment {
    private static final String TAG = "DownstairsFragment";
    private FirebaseDatabase database; //realtime database
    private DatabaseReference myRef, lightsRef;
    private Switch lightsFirstSwitch;
    private Switch lightsSecondSwitch;
    private Switch lightsThirdSwitch;


    public DownstairsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_downstairs, container, false);

        lightsFirstSwitch = rootView.findViewById(R.id.downstairsLightSwitch1);
        lightsSecondSwitch = rootView.findViewById(R.id.downstairsLightSwitch2);
        lightsThirdSwitch = rootView.findViewById(R.id.downstairsLightSwitch3);

        final TextView temperatureTextView = rootView.findViewById(R.id.downstairsTemperatureTextView);
        final TextView pressureTextView = rootView.findViewById(R.id.downstairsPressureTextView);
        final TextView altitudeTextView = rootView.findViewById(R.id.downstairsAltitudeTextView);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("downstairs_sensor_bmp280");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JSONObject sensorData;
                try {
                    sensorData = new JSONObject(dataSnapshot.getValue().toString());
                    temperatureTextView.setText("Temperature: " + sensorData.getString("temperature") + "°c");
                    pressureTextView.setText("Pressure: " +sensorData.get("pressure") + " hPa");
                    altitudeTextView.setText("Altitude: " +sensorData.get("altitude") + " m");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lightsRef = database.getReference("downstairs_lights");
        lightsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JSONObject lightsData;
                try {
                    lightsData = new JSONObject(dataSnapshot.getValue().toString());
                    if (lightsData.getInt("light0") == 1) {
                        lightsFirstSwitch.setChecked(true);
                    } else {
                        lightsFirstSwitch.setChecked(false);
                    }

                    if (lightsData.getInt("light1") == 1) {
                        lightsSecondSwitch.setChecked(true);
                    } else {
                        lightsSecondSwitch.setChecked(false);
                    }

                    if (lightsData.getInt("light2") == 1) {
                        lightsThirdSwitch.setChecked(true);
                    } else {
                        lightsThirdSwitch.setChecked(false);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lightsFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightsRef.child("light0").setValue((isChecked==true) ? 1 : 0);

            }
        });

        lightsSecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightsRef.child("light1").setValue((isChecked==true) ? 1 : 0);

            }
        });

        lightsThirdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightsRef.child("light2").setValue((isChecked==true) ? 1 : 0);

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

}
