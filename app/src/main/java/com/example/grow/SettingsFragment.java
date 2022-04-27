package com.example.grow;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int hours, minutes;


    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.btn_logout).setOnClickListener(x -> {
            mAuth.signOut();
            getActivity().finish();
        });

        view.findViewById(R.id.btn_notification).setOnClickListener(x -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hoursGot, minutesGot) -> {
                hours = hoursGot;
                minutes = minutesGot;
                setAlarm(hours, minutes);
                Toast.makeText(getContext(), "New notification time has been set", Toast.LENGTH_LONG).show();
            };

            TimePickerDialog dialog = new TimePickerDialog(getContext(), onTimeSetListener, hours, minutes, true);
            dialog.setTitle("Select time");
            dialog.show();
        });

        return view;
    }

    private void setAlarm(int hours, int minutes){
        Intent _intent = new Intent(getContext(), AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, _intent, 0);
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}