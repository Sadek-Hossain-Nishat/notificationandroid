package com.example.draggerinjectionapp;


import static com.example.draggerinjectionapp.NotificationApp.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;




public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationmanager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationmanager = NotificationManagerCompat.from(this);

    }


    public void showCustomNotification(View view) {

        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.notification_collapsed);

        RemoteViews expandedView = new RemoteViews(getPackageName(),
                R.layout.notification_expanded);


        Intent clickIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);

        collapsedView.setTextViewText(R.id.id_text_view_collapsed_1, "Hello World");


        expandedView.setImageViewResource(R.id.id_image_view_expanded, R.drawable.beautifulimage);

        expandedView.setOnClickPendingIntent(R.id.id_image_view_expanded, clickPendingIntent);


        Notification notification = new NotificationCompat.Builder(

                this, CHANNEL_ID

        ).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationmanager.notify(1, notification);

    }
}

















































































