package com.example.grow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grow.Models.Habit;
import com.example.grow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    Context context;
    List<Habit> habits;

    public HabitAdapter(Context context, List<Habit> habits) {
        this.habits = habits;
        this.context = context;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View habitViewHolder = LayoutInflater.from(context).inflate(R.layout.habit_layout, parent, false);
        return new HabitAdapter.HabitViewHolder(habitViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = this.habits.get(position);
        holder.habitTitle.setText(habit.getTitle());
        holder.habitNumb.setText(" " + (position + 1) + " ");

        char[] daysArray = habit.getDaysResults().toCharArray();

        int count = 0;
        for (char day: daysArray) {
            count += day == '+' ? 1 : 0;
        }

        int fullPercents = (int)(habit.getDeltaDays() + 1);

        holder.habitFullProgressBar.setProgress(fullPercents);
        holder.habitFullProgressBar.setMax(28);

        holder.habitProgressBar.setProgress(count);
        holder.habitProgressBar.setMax(28);

        holder.habitPercents.setText((int)(count * 100 / 28.) + "%");

        holder.habitCheckBox.setOnClickListener(view -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference document = db.collection(mAuth.getCurrentUser().getUid()).document(habit.getUid());

            daysArray[(int)habit.getDeltaDays()] = '+';

            int newProgress = holder.habitProgressBar.getProgress() + 1;
            holder.habitProgressBar.setProgress(newProgress);
            holder.habitPercents.setText((int)(newProgress * 100 / 28.) + "%");

            document.update("DaysResults", new String(daysArray));

            this.checkCheckBox(holder.habitCheckBox);
        });

        if (daysArray[(int)habit.getDeltaDays()] == '+') {
            this.checkCheckBox(holder.habitCheckBox);
        }
    }

    @Override
    public int getItemCount() {
        return this.habits.size();
    }

    public static final class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitTitle, habitNumb, habitPercents;
        ProgressBar habitProgressBar, habitFullProgressBar;
        ImageButton habitInfo;
        CheckBox habitCheckBox;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            this.habitCheckBox = itemView.findViewById(R.id.habit_checkbox);
            this.habitInfo = itemView.findViewById(R.id.habit_info);
            this.habitProgressBar = itemView.findViewById(R.id.habit_progressBar);
            this.habitFullProgressBar = itemView.findViewById(R.id.habit_fullProgressBar);
            this.habitTitle = itemView.findViewById(R.id.habit_title);
            this.habitNumb = itemView.findViewById(R.id.habit_num);
            this.habitPercents = itemView.findViewById(R.id.habit_percents);
        }
    }

    private void checkCheckBox(@NonNull CheckBox checkBox) {
        checkBox.setChecked(true);
        checkBox.setClickable(false);
    }
}
