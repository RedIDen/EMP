package com.example.grow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.grow.Adapters.HabitAdapter;
import com.example.grow.Models.Habit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InfoActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Habit habit = HabitAdapter.habitForInfo;
    int[][] flowers = {
            { R.drawable.root, R.drawable.denis1, R.drawable.denis2, R.drawable.denis3 },
            { R.drawable.root, R.drawable.ira1, R.drawable.ira2, R.drawable.ira3 },
            { R.drawable.root, R.drawable.korch1, R.drawable.korch2, R.drawable.korch3 },
            { R.drawable.root, R.drawable.nastya1, R.drawable.nastya2, R.drawable.nastya3 },
            { R.drawable.root, R.drawable.kate1, R.drawable.kate2, R.drawable.kate3 },
            { R.drawable.root, R.drawable.victor1, R.drawable.victor2, R.drawable.victor3 },
    };

    int[] buttons = { R.id.day0, R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7,
            R.id.day8, R.id.day9, R.id.day10, R.id.day11, R.id.day12, R.id.day13, R.id.day14, R.id.day15, R.id.day16,
            R.id.day17, R.id.day18, R.id.day19, R.id.day20, R.id.day21, R.id.day22, R.id.day23, R.id.day24, R.id.day25,
            R.id.day26, R.id.day27 };

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activify_inf);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        for (int i = 0; i <= habit.getDeltaDays(); i++){
            findViewById(buttons[i]).setBackgroundTintList(getColorStateList(R.color.my_red));
        }

        char[] daysArray = this.habit.getDaysResults().toCharArray();

        int count = 0;
        for (int i = 0; i < daysArray.length; i++) {
            if (daysArray[i] == '+'){
                count++;
                findViewById(buttons[i]).setBackgroundTintList(getColorStateList(R.color.my_white));
            }
        }

        ((ImageView)findViewById(R.id.flower_info_image)).setImageResource(this.flowers[habit.getFlowerId()][(count - 1) / 9]);

        EditText title = findViewById(R.id.edit_habit);
        title.setText(habit.getTitle());
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String title = s.toString();
                DocumentReference document = db.collection(mAuth.getCurrentUser().getUid()).document(habit.getUid());
                document.update("Title", title);
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(view -> {
            openContextMenu(delete);
        });

        registerForContextMenu(delete);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Delete this habit?");
        menu.add(Menu.NONE, 0, Menu.NONE, "Delete");
        menu.add(Menu.NONE, 1, Menu.NONE, "Cancel");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 0:
                delete();
                break;
            case 1:
                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }

    private void delete(){
        DocumentReference document = db.collection(mAuth.getCurrentUser().getUid()).document(habit.getUid());
        document.delete();
        finish();
    }
}