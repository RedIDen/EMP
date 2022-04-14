package com.example.grow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.cancelCreate).setOnClickListener(x -> {
            finish();
        });

        findViewById(R.id.button_add_habit).setOnClickListener(x -> {
            addHabit();
            finish();
        });

        if(findViewById(R.id.create_habit).requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void addHabit() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> habit = new HashMap<>();
        habit.put("Title", ((EditText)findViewById(R.id.create_habit)).getText().toString());
        habit.put("Flower", "flower.png");
        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).add(habit);
    }
}