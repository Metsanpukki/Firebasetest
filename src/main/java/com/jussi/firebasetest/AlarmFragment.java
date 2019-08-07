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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {
    private Switch alarmSwitch;
    private FirebaseDatabase database;
    private DatabaseReference alarmRef;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        alarmSwitch = rootView.findViewById(R.id.alarmSwitch);

        database = FirebaseDatabase.getInstance();
        alarmRef = database.getReference("alarm");
        alarmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int status = dataSnapshot.getValue(Integer.class);
                if (status == 1) {
                    alarmSwitch.setChecked(true);
                } else {
                    alarmSwitch.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmRef.setValue((isChecked) ? 1 : 0);
            }
        });

        return rootView;
    }

}
