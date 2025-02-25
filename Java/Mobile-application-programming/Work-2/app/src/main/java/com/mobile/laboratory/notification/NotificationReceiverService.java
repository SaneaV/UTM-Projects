package com.mobile.laboratory.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import org.json.JSONObject;

public class NotificationReceiverService extends Service {

    private static final String TAG = "NotificationReceiverService";
    private static final String CHANNEL_ID = "notification_channel";
    private AsyncHttpServer server;
    private AsyncServer mAsyncServer;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startServer();
    }

    private void startServer() {
        server = new AsyncHttpServer();
        mAsyncServer = new AsyncServer();

        server.post("/notification", (AsyncHttpServerRequest request, AsyncHttpServerResponse response) -> {
            response.send("{\"status\":\"success\"}");
            Log.d(TAG, "Received notification request");

            JSONObject jsonBody = ((JSONObjectBody) request.getBody()).get();
            String title = jsonBody.optString("title", "Default Title");
            String description = jsonBody.optString("description", "Default Description");
            showNotification(title, description);
        });

        server.listen(mAsyncServer, 9090);
    }

    private void showNotification(String title, String description) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Notification permission not granted");
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for Notification Receiver Service");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
            mAsyncServer.stop();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}