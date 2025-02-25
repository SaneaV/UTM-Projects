package com.mobile.laboratory.notification;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationSender {

    private static final String BASE_URL = "http://10.0.2.2:8080/notifications/create";

    public static void sendNotification(String title, String description, long timeInMillis) throws JSONException {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        json.put("timeInMillis", timeInMillis);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    System.out.println("Notification sent successfully");
                } else {
                    System.out.println("Failed to send notification");
                }
            }
        });
    }
}