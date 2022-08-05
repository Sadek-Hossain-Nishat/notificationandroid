package com.example.draggerinjectionapp;


import static com.example.draggerinjectionapp.NotificationApp.CHANNEL_ID;

import android.app.Notification;
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

        RemoteViews collapseView = new RemoteViews(getPackageName(),
                R.layout.notification_collapsed);

        RemoteViews expandedView = new RemoteViews(getPackageName(),
                R.layout.notification_expanded);


        Notification notification = new NotificationCompat.Builder(

                this, CHANNEL_ID

        ).setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(collapseView)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationmanager.notify(1, notification);

    }
}

















































































