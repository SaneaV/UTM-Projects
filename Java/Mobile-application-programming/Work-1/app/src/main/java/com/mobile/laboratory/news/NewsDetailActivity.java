package com.mobile.laboratory.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobile.laboratory.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView descriptionTextView;
    private NewsRepository newsRepository;
    private News news;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        news = (News) getIntent().getSerializableExtra("news");
        TextView nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView imageView = findViewById(R.id.imageView);
        Button openLinkButton = findViewById(R.id.openLinkButton);

        nameTextView.setText(news.getName());
        Picasso.get().load(news.getImage()).into(imageView);

        openLinkButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getLink()));
            startActivity(browserIntent);
        });

        loadDescription();
    }

    private void loadDescription() {
        newsRepository = new NewsRepositoryImpl(news.getLink());
        new Thread(() -> {
            String description = newsRepository.getDescription();
            runOnUiThread(() -> {
                if (description != null) {
                    descriptionTextView.setText(description);
                } else {
                    descriptionTextView.setText("Error fetching article description");
                }
            });
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}