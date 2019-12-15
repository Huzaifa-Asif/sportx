package com.sportx.pk.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sportx.pk.activities.customerActivities.BookingManagementActivity;
import com.sportx.pk.activities.customerActivities.TournamentActivity;
import com.sportx.pk.activities.sharedActivities.OngoingStreamsActivity;
import com.sportx.pk.activities.sharedActivities.SplashActivity;
import com.sportx.pk.Misc.Misc;
import com.sportx.pk.R;

public class MyService extends FirebaseMessagingService {

Misc misc;
int x=0;
    public MyService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        try
        {
            showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
        }
     catch (Exception e) {
        e.printStackTrace();
    }
//        showAlertDialog(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

    }

    @Override
    public void onNewToken(String s) {
        misc=new Misc(this);
        super.onNewToken(s);
        misc.addToken(s);

    }

    public void showNotification(String title, String body)
    {
        Intent intent=new Intent(this, SplashActivity.class);
        if(title.equalsIgnoreCase("Booking Accepted"))
        {
            intent=new Intent(this, BookingManagementActivity.class);
            intent.putExtra("position",1);
        }
        else if(title.equalsIgnoreCase("Booking Completed"))
        {
            intent=new Intent(this, BookingManagementActivity.class);
            intent.putExtra("position",2);
        }
        else if(title.equalsIgnoreCase("Booking Cancelled"))
        {
            intent=new Intent(this, BookingManagementActivity.class);
        }
        else if(title.equalsIgnoreCase("New Booking Notification"))
        {
            intent=new Intent(this, com.sportx.pk.activities.serviceProviderActivities.BookingManagementActivity.class);
            intent.putExtra("position",0);
        }
        else if(title.equalsIgnoreCase("New Live Stream Notification"))
        {
            intent=new Intent(this, OngoingStreamsActivity.class);

        }
        else if(title.equalsIgnoreCase("New Tournament Started"))
        {
            intent=new Intent(this, TournamentActivity.class);

        }
        // Set the Activity to start in a new, empty task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle(title)
                .setContentText(body).setAutoCancel(true)
                .setContentIntent(notifyPendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(x, builder.build());
        x++;
    }
//    private void showAlertDialog(String title,String message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }




}