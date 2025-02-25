package com.mobile.laboratory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.laboratory.notification.NotificationReceiverService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Planner> plannerList;
    ArrayList<Object> combinedList;
    ArrayList<Planner> overdueList, todayList, tomorrowList, weekList, monthList, laterList;
    MainAdapter adapter;

    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        loadPlannerList();

        findViewById(R.id.addButton).setOnClickListener(view -> add());
        findViewById(R.id.viewButton).setOnClickListener(view -> viewCalendar());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
            }
        }

        Intent serviceIntent = new Intent(this, NotificationReceiverService.class);
        startService(serviceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            }
        }
    }

    private void loadPlannerList() {
        plannerList = XMLOperator.parseXml(MainActivity.this);
        groupTasksByCategories();
        combinedList = new ArrayList<>();
        addSection(combinedList, "Overdue", overdueList);
        addSection(combinedList, "Today", todayList);
        addSection(combinedList, "Tomorrow", tomorrowList);
        addSection(combinedList, "This Week", weekList);
        addSection(combinedList, "This Month", monthList);
        addSection(combinedList, "Later", laterList);

        adapter = new MainAdapter(combinedList, plannerList, recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void groupTasksByCategories() {
        overdueList = new ArrayList<>();
        todayList = new ArrayList<>();
        tomorrowList = new ArrayList<>();
        weekList = new ArrayList<>();
        monthList = new ArrayList<>();
        laterList = new ArrayList<>();

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrowDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date weekEndDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthEndDate = calendar.getTime();

        for (Planner planner : plannerList) {
            try {
                Date taskDate = dateTimeFormat.parse(planner.date + " " + planner.time);
                assert taskDate != null;
                if (taskDate.before(currentDate)) {
                    overdueList.add(planner);
                } else if (isSameDay(taskDate, currentDate)) {
                    todayList.add(planner);
                } else if (isSameDay(taskDate, tomorrowDate)) {
                    tomorrowList.add(planner);
                } else if (taskDate.after(currentDate) && taskDate.before(weekEndDate)) {
                    weekList.add(planner);
                } else if (taskDate.after(currentDate) && taskDate.before(monthEndDate)) {
                    monthList.add(planner);
                } else {
                    laterList.add(planner);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        overdueList.sort(Comparator.comparing((Planner planner) -> planner.date).thenComparing((Planner planner) -> planner.time));
        todayList.sort(Comparator.comparing((Planner planner) -> planner.time));
        tomorrowList.sort(Comparator.comparing((Planner planner) -> planner.time));
        weekList.sort(Comparator.comparing((Planner planner) -> planner.date).thenComparing((Planner planner) -> planner.time));
        monthList.sort(Comparator.comparing((Planner planner) -> planner.date).thenComparing((Planner planner) -> planner.time));
        laterList.sort(Comparator.comparing((Planner planner) -> planner.date).thenComparing((Planner planner) -> planner.time));
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private void addSection(ArrayList<Object> combinedList, String sectionName, ArrayList<Planner> list) {
        if (!list.isEmpty()) {
            combinedList.add(sectionName);
            combinedList.addAll(list);
        }
    }

    private void add() {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra("date", System.currentTimeMillis());
        startActivity(intent);
    }

    private void viewCalendar() {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPlannerList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTasks(newText);
                return true;
            }
        });

        return true;
    }

    private void filterTasks(String query) {
        ArrayList<Object> filteredList = new ArrayList<>();
        for (Object item : combinedList) {
            if (item instanceof String section) {
                ArrayList<Planner> tasksInSection = getTasksInSection(section);
                ArrayList<Planner> filteredTasks = new ArrayList<>();
                for (Planner planner : tasksInSection) {
                    if (planner.title.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                        filteredTasks.add(planner);
                    }
                }
                if (!filteredTasks.isEmpty()) {
                    filteredList.add(section);
                    filteredList.addAll(filteredTasks);
                }
            }
        }
        adapter.updateData(filteredList);
    }

    private ArrayList<Planner> getTasksInSection(String section) {
        return switch (section) {
            case "Overdue" -> overdueList;
            case "Today" -> todayList;
            case "Tomorrow" -> tomorrowList;
            case "This Week" -> weekList;
            case "This Month" -> monthList;
            case "Later" -> laterList;
            default -> new ArrayList<>();
        };
    }
}