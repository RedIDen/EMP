package com.example.grow;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

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
        habit.put("User", FirebaseAuth.getInstance().getCurrentUser().getUid());
        habit.put("Title", ((EditText)findViewById(R.id.create_habit)).getText().toString());
        habit.put("Date", new SimpleDateFormat("MM.dd.yyyy").format(Calendar.getInstance().getTime()));
        db.collection("Habits")
                .add(habit)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
     .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
     });
    }
}