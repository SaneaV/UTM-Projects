package com.mobile.laboratory;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CalendarView calendarView;
    ArrayList<Planner> plannerList;
    ArrayList<Planner> tasksForSelectedDate;
    PlannerAdapter adapter;

    private final BroadcastReceiver dataChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadPlannerList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        calendarView = findViewById(R.id.calendarView);

        loadPlannerList();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDateString = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            tasksForSelectedDate = getTasksForDate(selectedDateString);
            updateRecyclerView();
        });

        long currentDateMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateString = sdf.format(new Date(currentDateMillis));
        tasksForSelectedDate = getTasksForDate(currentDateString);
        updateRecyclerView();
    }

    private void loadPlannerList() {
        plannerList = XMLOperator.parseXml(CalendarActivity.this);
        String selectedDateString = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(calendarView.getDate()));
        tasksForSelectedDate = getTasksForDate(selectedDateString);
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        adapter = new PlannerAdapter(this, tasksForSelectedDate, plannerList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Planner> getTasksForDate(String selectedDate) {
        ArrayList<Planner> tasks = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (Planner planner : plannerList) {
            try {
                Date taskDate = dateFormat.parse(planner.date);
                Date selectedTaskDate = dateFormat.parse(selectedDate);
                if (taskDate != null && taskDate.equals(selectedTaskDate)) {
                    tasks.add(planner);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        loadPlannerList();
        registerReceiver(dataChangedReceiver, new IntentFilter("com.mobile.laboratory.DATA_CHANGED"), Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dataChangedReceiver);
    }
}