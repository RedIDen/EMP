package com.example.grow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grow.Models.Habit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProgressFragment extends Fragment {

    View view;
    List<Habit> habits;
    private int index = 0;
    private int[] flowers = {
            R.drawable.denis3,
            R.drawable.ira3,
            R.drawable.korch3,
            R.drawable.nastya3,
            R.drawable.kate3,
            R.drawable.victor3,
    };


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProgressFragment() {
    }

    public static ProgressFragment newInstance(String param1, String param2) {
        ProgressFragment fragment = new ProgressFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_progress, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        habits = new ArrayList<>();
        View loading = this.view.findViewById(R.id.loading_progress_bar_info);
        loading.setVisibility(View.VISIBLE);

        db.collection(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (!((String) document.get("DaysResults")).contains("-")) {
                        Habit habit = new Habit(
                                document.getId(),
                                (String) document.get("Title"),
                                Integer.parseInt((String) document.get("Flower")),
                                (String) document.get("StartDate"),
                                (String) document.get("DaysResults")
                        );

                        habits.add(habit);
                    }
                }

                this.habits = habits.stream()
                        .sorted(Comparator
                                .comparing(Habit::getDeltaDays)
                                .thenComparing(Habit::getTitle))
                        .collect(Collectors.toList());

                TextView title = view.findViewById(R.id.habit_title_info);
                ImageView image = view.findViewById(R.id.flower_info);
                View left = view.findViewById(R.id.button_left_info);
                View right = view.findViewById(R.id.button_right_info);

                if (habits.size() != 0) {
                    left.setOnClickListener(view -> {
                        this.index = --this.index == -1 ? this.habits.size() - 1 : this.index;
                        setFlower(title, image);
                    });

                    right.setOnClickListener(view -> {
                        this.index = ++this.index == this.habits.size() ?  0 : this.index;
                        setFlower(title, image);
                    });

                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.VISIBLE);

                    setFlower(title, image);
                }
                else {
                    view.findViewById(R.id.no_flowers_text).setVisibility(View.VISIBLE);

                }

                loading.setVisibility(View.GONE);
            }
        });

        return this.view;
    }

    private void setFlower(TextView title, ImageView image){
        Habit habit = this.habits.get(this.index);
        title.setText(habit.getTitle());
        image.setImageResource(this.flowers[habit.getFlowerId()]);
        title.setSelected(true);
    }
}