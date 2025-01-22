package com.mobile.laboratory.news;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

public class NetworkHelper {

    private final Handler handler = new Handler(Looper.getMainLooper());

    public void fetchNews(final String siteLink, final Callback callback) {
        new Thread(() -> {
            List<News> newsList;
            try {
                NewsRepository newsRepository = new NewsRepositoryImpl(siteLink);
                newsList = newsRepository.getLastNews();
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(callback::onError);
                return;
            }

            List<News> finalNewsList = newsList;
            handler.post(() -> {
                if (finalNewsList != null) {
                    callback.onSuccess(finalNewsList);
                } else {
                    callback.onError();
                }
            });
        }).start();
    }

    public interface Callback {
        void onSuccess(List<News> newsList);
        void onError();
    }
}