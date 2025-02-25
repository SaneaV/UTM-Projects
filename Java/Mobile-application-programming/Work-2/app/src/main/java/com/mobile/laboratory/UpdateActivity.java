package com.mobile.laboratory;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobile.laboratory.notification.NotificationSender;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {
    String title;
    String date;
    String time;
    String priority;
    EditText editTextTitle;
    EditText selectedDate;
    EditText selectedTime;
    Button pressUpdate;
    RadioGroup priorityRadioGroup;
    RadioButton lowPriorityButton, mediumPriorityButton, highPriorityButton;
    ArrayList<Planner> plannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = getIntent().getStringExtra("Title");
        date = getIntent().getStringExtra("Date");
        time = getIntent().getStringExtra("Time");
        priority = getIntent().getStringExtra("Priority");

        editTextTitle = findViewById(R.id.editTextTitle);
        selectedDate = findViewById(R.id.editTextDate);
        selectedTime = findViewById(R.id.editTextTime);
        selectedDate.setFocusable(false);
        selectedDate.setClickable(true);
        selectedTime.setFocusable(false);
        selectedTime.setClickable(true);

        selectedDate.setOnClickListener(view -> showDateDialog());
        selectedTime.setOnClickListener(view -> showTimeDialog());

        pressUpdate = findViewById(R.id.update);
        pressUpdate.setOnClickListener(view -> returnHome());

        priorityRadioGroup = findViewById(R.id.priorityRadioGroup);
        lowPriorityButton = findViewById(R.id.lowPriority);
        mediumPriorityButton = findViewById(R.id.mediumPriority);
        highPriorityButton = findViewById(R.id.highPriority);

        if (priority != null) {
            switch (priority) {
                case "Low":
                    lowPriorityButton.setChecked(true);
                    break;
                case "High":
                    highPriorityButton.setChecked(true);
                    break;
                default:
                    mediumPriorityButton.setChecked(true);
                    break;
            }
        }

        editTextTitle.setText(title);
        selectedDate.setText(date);
        selectedTime.setText(time);

        plannerList = XMLOperator.parseXml(UpdateActivity.this);
    }

    private void returnHome() {
        try {
            update();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to update reminder", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, i, i1) -> {
            Log.println(Log.ASSERT, "time", "clicked");
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            selectedTime.setText(simpleDateFormat.format(calendar.getTime()));
        };
        new TimePickerDialog(UpdateActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            selectedDate.setText(simpleDateFormat.format(calendar.getTime()));
        };
        new DatePickerDialog(UpdateActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void update() throws IOException, JSONException {
        String newTitle = editTextTitle.getText().toString().trim();
        if (newTitle.isEmpty()) {
            Toast.makeText(this, "Enter task title", Toast.LENGTH_SHORT).show();
            return;
        }

        String newDate = selectedDate.getText().toString().trim();
        if (newDate.isEmpty()) {
            Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
            return;
        }

        String newTime = selectedTime.getText().toString().trim();
        if (newTime.isEmpty()) {
            Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPriority = getSelectedPriority();

        for (Planner planner : plannerList) {
            if (planner.title.equals(title) && planner.date.equals(date) && planner.time.equals(time)) {
                planner.title = newTitle;
                planner.date = newDate;
                planner.time = newTime;
                planner.priority = newPriority;
                break;
            }
        }
        XMLOperator.writeXml(UpdateActivity.this, plannerList);

        sendNotificationToServer(newTitle, newDate, newTime);
    }

    private void sendNotificationToServer(String newTitle, String newDate, String newTime) throws JSONException {
        String stringDate = newDate + " " + newTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateNotif = LocalDateTime.parse(stringDate, formatter);
        long timeInMillis = dateNotif.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        NotificationSender.sendNotification(newTitle, "Event is starting now", timeInMillis);
    }

    private String getSelectedPriority() {
        int selectedId = priorityRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.lowPriority) {
            return "Low";
        } else if (selectedId == R.id.highPriority) {
            return "High";
        } else {
            return "Medium";
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}