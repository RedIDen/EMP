package com.example.grow;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    private int index = 0;
    private int[] flowers = {
            R.drawable.denis2,
            R.drawable.ira2,
            R.drawable.korch2,
            R.drawable.nastya2,
            R.drawable.kate2,
            R.drawable.victor2,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setFlower();

        findViewById(R.id.cancelCreate).setOnClickListener(x -> {
            finish();
        });

        findViewById(R.id.button_add_habit).setOnClickListener(x -> {
            addHabit();
            finish();
        });

        findViewById(R.id.button_left).setOnClickListener(x -> {
            this.index = --this.index == -1 ? this.flowers.length - 1 : this.index;
            setFlower();
        });

        findViewById(R.id.button_right).setOnClickListener(x -> {
            this.index = ++this.index == this.flowers.length ?  0 : this.index;
            setFlower();
        });

        if(findViewById(R.id.create_habit).requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void addHabit() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> habit = new HashMap<>();
        habit.put("Title", ((EditText)findViewById(R.id.create_habit)).getText().toString());
        habit.put("Flower", Integer.toString(index));
        habit.put("DaysResults", "----------------------------");
        habit.put("StartDate", new SimpleDateFormat("MM.dd.yyyy").format(new Date()));
        db.collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).add(habit);
    }

    private void setFlower() {
        ((ImageView)findViewById(R.id.flower_create)).setImageResource(this.flowers[this.index]);
    }
}