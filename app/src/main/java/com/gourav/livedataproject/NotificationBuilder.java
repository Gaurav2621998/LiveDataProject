package com.gourav.livedataproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationBuilder extends AppCompatActivity {


    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";


    private static final int NOTIFICATION_ID = 0;

    private NotificationReceiver mReceiver = new NotificationReceiver();





    Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_builder);



        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));


        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
            // Create a NotificationChannel
        }

        b1=(Button)findViewById(R.id.notifyme);
        b2=(Button)findViewById(R.id.updateme);
        b3=(Button)findViewById(R.id.cancal);

        setNotificationButtonState(true, false, false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
                mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

                setNotificationButtonState(false, true, true);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mNotifyManager.cancel(NOTIFICATION_ID);
                setNotificationButtonState(true, false, false);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updatenotification();



            }
        });


    }

    public void updatenotification()
    {
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!"));

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false, false, true);

    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        b1.setEnabled(isNotifyEnabled);
        b2.setEnabled(isUpdateEnabled);
        b3.setEnabled(isCancelEnabled);
    }


    private NotificationCompat.Builder getNotificationBuilder(){

//
//        Intent intent=new Intent(this,MainActivity.class);
//        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
//                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);





        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_save_black_24dp)
                .setContentIntent(updatePendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        notifyBuilder.addAction(R.drawable.ic_update_black_24dp, "Update Notification", updatePendingIntent);

        return notifyBuilder;

    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification
            NotificationBuilder n=new NotificationBuilder();
            n.updatenotification();
        }


    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }






}
