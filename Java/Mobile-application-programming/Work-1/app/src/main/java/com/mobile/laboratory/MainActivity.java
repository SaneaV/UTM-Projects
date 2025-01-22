package com.mobile.laboratory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.laboratory.news.NetworkHelper;
import com.mobile.laboratory.news.News;
import com.mobile.laboratory.news.NewsAdapter;
import com.mobile.laboratory.news.NewsDetailActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkHelper.Callback {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private static final String CHANNEL_ID = "news_channel";
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        Button subscribeButton = findViewById(R.id.subscribeButton);

        createNotificationChannel();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);
        new NetworkHelper().fetchNews("https://bas-tv.md/aktualno/", this);

        subscribeButton.setOnClickListener(v -> {
            Log.d("MainActivity", "Subscribe button clicked");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                showSubscribeNotification();
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onSuccess(List<News> newsList) {
        progressBar.setVisibility(View.GONE);
        NewsAdapter adapter = new NewsAdapter(this, newsList, news -> {
            Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
            intent.putExtra("news", news);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onError() {
        progressBar.setVisibility(View.GONE);
    }

    private void createNotificationChannel() {
        CharSequence name = "News Channel";
        String description = "Channel for news notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
            Log.d("MainActivity", "Notification channel created");
        } else {
            Log.e("MainActivity", "NotificationManager is null");
        }
    }

    private void showSubscribeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Подписка на новости")
                .setContentText("Вы успешно подписались на новости!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                notificationManager.notify(1, builder.build());
                Log.d("MainActivity", "Notification sent after 10 seconds");
            }, 10000);
        } else {
            Log.e("MainActivity", "Notification permission not granted");
        }
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            Log.e("MainActivity", "Search query is empty");
            return;
        }

        String searchUrl = "https://bas-tv.md/?s=" + query;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));
        startActivity(browserIntent);
        Log.d("MainActivity", "Search URL: " + searchUrl);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Notification permission granted");
                showSubscribeNotification();
            } else {
                Log.e("MainActivity", "Notification permission denied");
            }
        }
    }
}