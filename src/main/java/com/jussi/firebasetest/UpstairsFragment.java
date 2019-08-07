package com.jussi.firebasetest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;


public class UpstairsFragment extends Fragment {
    private static final String TAG = "UpstairsFragment";
    private FirebaseDatabase database; //realtime database
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestoredb; //firestore database
    private DocumentReference docRef;

    public UpstairsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_upstairs, container, false);
        final TextView temperatureTextView = rootView.findViewById(R.id.upstairsTemperatureTextView);
        final TextView humidityTextView = rootView.findViewById(R.id.upstairsHumidityTextView);
        final TextView pressureTextView = rootView.findViewById(R.id.upstairsPressureTextView);
        final TextView altitudeTextView = rootView.findViewById(R.id.upstairsAltitudeTextView);


        //for firestore maybe someday.....
        firestoredb = FirebaseFirestore.getInstance();
        Toast.makeText(getActivity(), firestoredb.toString(), Toast.LENGTH_LONG).show();
        firestoredb.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    Log.d(TAG,documentChange.getDocument().getData().get("name").toString());

                }
            }
        });






        mAuth = FirebaseAuth.getInstance();



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("upstairs_sensor_bme280");

        //realtime database listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  textView.setText(dataSnapshot.getValue(String.class));
                //String data = dataSnapshot.getValue().toString();
                JSONObject sensorData = null;
                try {
                    sensorData = new JSONObject(dataSnapshot.getValue().toString());
                    temperatureTextView.setText("Temperature: " +sensorData.getString("temperature") + "°c");
                    humidityTextView.setText("Humidity: " + sensorData.get("humidity") + " %");
                    pressureTextView.setText("Pressure: " +sensorData.get("pressure") + " hPa");
                    altitudeTextView.setText("Altitude: " +sensorData.get("altitude") + " m");
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
