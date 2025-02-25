package com.mobile.laboratory;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobile.laboratory.notification.NotificationSender;

import org.json.JSONException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {
    Button add;
    String storeDate;
    String time;
    String title;
    EditText editTextTitle;
    EditText selectedDate;
    EditText selectedTime;
    RadioGroup priorityRadioGroup;
    RadioButton lowPriorityButton, mediumPriorityButton, highPriorityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextTitle = findViewById(R.id.editTextTitle);
        selectedDate = findViewById(R.id.editTextDate);
        selectedTime = findViewById(R.id.editTextTime);
        priorityRadioGroup = findViewById(R.id.priorityRadioGroup);
        lowPriorityButton = findViewById(R.id.lowPriority);
        mediumPriorityButton = findViewById(R.id.mediumPriority);
        highPriorityButton = findViewById(R.id.highPriority);

        Calendar calendar = Calendar.getInstance();
        storeDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime());
        time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());

        selectedDate.setText(storeDate);
        selectedTime.setText(time);

        selectedDate.setOnClickListener(view -> showDateDialog());
        selectedTime.setOnClickListener(view -> showTimeDialog());

        lowPriorityButton.setChecked(true);

        add = findViewById(R.id.addB);
        add.setOnClickListener(view -> {
            try {
                title = editTextTitle.getText().toString().trim();
                if (title.isEmpty()) {
                    Toast.makeText(this, "Enter task title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (storeDate.isEmpty()) {
                    Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (time.isEmpty()) {
                    Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();
                    return;
                }
                writeToXml();
                Toast.makeText(this, "Reminder added to calendar", Toast.LENGTH_SHORT).show();
                sendNotificationToServer();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to add reminder", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid date or time format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            storeDate = simpleDateFormat.format(calendar.getTime());
            selectedDate.setText(storeDate);
        };
        new DatePickerDialog(AddActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            time = simpleDateFormat.format(calendar.getTime());
            selectedTime.setText(time);
        };
        new TimePickerDialog(AddActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false).show();
    }

    private void writeToXml() throws IOException {
        title = editTextTitle.getText().toString().trim();
        String priority = getSelectedPriority();

        FileOutputStream fileOutputStream;
        File file = getBaseContext().getFileStreamPath("data.xml");
        boolean exists = file.exists();
        fileOutputStream = openFileOutput("data.xml", exists ? MODE_APPEND : MODE_PRIVATE);
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);

        if (!exists) {
            xmlSerializer.startDocument("UTF-8", true);
        }
        xmlSerializer.startTag("", "doc");
        xmlSerializer.startTag("", "title");
        xmlSerializer.text(title);
        xmlSerializer.endTag("", "title");
        xmlSerializer.startTag("", "date");
        xmlSerializer.text(storeDate);
        xmlSerializer.endTag("", "date");
        xmlSerializer.startTag("", "time");
        xmlSerializer.text(time);
        xmlSerializer.endTag("", "time");
        xmlSerializer.startTag("", "priority");
        xmlSerializer.text(priority);
        xmlSerializer.endTag("", "priority");
        xmlSerializer.endTag("", "doc");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
        String dataWrite = writer.toString();
        fileOutputStream.write(dataWrite.getBytes());
        fileOutputStream.close();
    }

    private void sendNotificationToServer() throws JSONException {
        String stringDate = storeDate + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateNotif = LocalDateTime.parse(stringDate, formatter);
        long timeInMillis = dateNotif.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        NotificationSender.sendNotification(title, "Event is starting now", timeInMillis);
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