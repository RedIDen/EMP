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

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    Context context;
    List<Habit> habits;

    public HabitAdapter(Context context, List<Habit> habits)
    {
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

        int count = 0;
        for (boolean day: habit.getDaysResults()) {
            count += day ? 1 : 0;
        }

        int percents = (int)((count / (double)habit.getDaysResults().length) * 100);

        holder.habitProgressBar.setProgress(percents);
        holder.habitProgressBar.setMax(100);
        holder.habitPercents.setText(percents + "%");
    }

    @Override
    public int getItemCount() {
        return this.habits.size();
    }

    public static final class HabitViewHolder extends RecyclerView.ViewHolder {

        TextView habitTitle, habitNumb, habitPercents;
        ProgressBar habitProgressBar;
        ImageButton habitInfo;
        CheckBox habitCheckBox;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            this.habitCheckBox = itemView.findViewById(R.id.habit_checkbox);
            this.habitInfo = itemView.findViewById(R.id.habit_info);
            this.habitProgressBar = itemView.findViewById(R.id.habit_progressBar);
            this.habitTitle = itemView.findViewById(R.id.habit_title);
            this.habitNumb = itemView.findViewById(R.id.habit_num);
            this.habitPercents = itemView.findViewById(R.id.habit_percents);
        }
    }
}
