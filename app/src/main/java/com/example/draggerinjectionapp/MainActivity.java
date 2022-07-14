package com.example.draggerinjectionapp;


import static com.example.draggerinjectionapp.NotificationApp.CHANNEL_1_ID;
import static com.example.draggerinjectionapp.NotificationApp.CHANNEL_2_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText titleET, messageET;
    private NotificationManagerCompat notificationManagerCompat;

    static List<Message> MESSAGES = new ArrayList<>() ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManagerCompat =NotificationManagerCompat.from(this);
        titleET = findViewById(R.id.id_edit_text_title);
        messageET = findViewById(R.id.id_edit_text_message);


        Person jim = new Person.Builder().setName("Jim").build();
        Person jenny = new Person.Builder().setName("Jenny").build();

        MESSAGES.add(new Message("Good morning",jim));
        MESSAGES.add(new Message("Hello",null));
        MESSAGES.add(new Message("Hi",jenny));


    }

    public  void sendOnChannel1(View view){
        sendChannel1Notification(this);

    }


    public static void sendChannel1Notification(Context context) {


        Intent activityIntent =new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0,activityIntent,0);


        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();


        Intent replyIntent ;
        PendingIntent replyPendingIntent =null;

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            replyIntent  = new Intent(context,DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context,
                    0,replyIntent,0);

        }else {
            // start chat activity instead (PendingIntent.getActivity)
            //cancel notification with notificationManagerCompat.Cancel(id)
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent


        ).addRemoteInput(remoteInput).build();
        Person sender = new Person.Builder().setName("Me").build();




        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle(sender);
        messagingStyle.setConversationTitle("Group Chat");


        for (Message chatMessage:MESSAGES) {


            NotificationCompat.MessagingStyle.Message notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(chatMessage.getText(),
                            chatMessage.getTimestamp(),
                            chatMessage.getSender());


            messagingStyle.addMessage(notificationMessage);


        }




        Notification notification =new NotificationCompat.Builder(context,
                CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)

                .setStyle(
                        messagingStyle

                )
                .addAction(replyAction)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)

                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();


        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1,notification);
    }

    public void sendOnChannel2(View view) {
        final int progressMax = 100;


        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,
                CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                // false for determinate progress
                .setProgress(progressMax, 0, false);
        // inderterminate progress
        // it will be true
        // and setprogress will be commented out

        notificationManagerCompat.notify(2, notification.build());

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        for (int progress = 0; progress <= progressMax; progress += 10) {

                            notification.setProgress(progressMax, progress, false);
                            notificationManagerCompat.notify(2, notification.build());
                            SystemClock.sleep(1000);

                        }
                        notification.setContentText("Download finished")
                                .setProgress(0, 0, false)
                                .setOngoing(false);
                        notificationManagerCompat.notify(2, notification.build());

                    }
                }
        ).start();

    }
}

















































































