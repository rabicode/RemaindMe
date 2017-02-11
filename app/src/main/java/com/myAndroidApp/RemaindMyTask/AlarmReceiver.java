package com.myAndroidApp.RemaindMyTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static android.app.Notification.PRIORITY_HIGH;
import static android.app.Notification.VISIBILITY_PUBLIC;


/**
 * Created by dinislam on 1/2/17.
 */
public class AlarmReceiver extends BroadcastReceiver {
    String restoredText;
    String restoredname;
    String str1;
    String str2;


    @Override
    public void onReceive(Context context, Intent intent) {
        add_Notification(context);


    }

    public void add_Notification(Context context) {
        int id;
        Log.d("ME", "Notification started");
        checked(context);
        String myString = str1 + " " + str2;
        String str = "Do you want...?";

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, MediaActivity.class);
        final  int RQS_1 = (int) System.currentTimeMillis();
        PendingIntent pIntent = PendingIntent.getActivity(context, RQS_1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent qintent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //context.sendBroadcast(qintent);
        PendingIntent Intent = PendingIntent.getActivity(context, RQS_1, qintent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder;
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("New RemindMe Message.")

              .setContentText(myString)
                .setSubText(str)
                .setVisibility(VISIBILITY_PUBLIC)
                .setPriority(PRIORITY_HIGH)
                 .setAutoCancel(true)
                .setColor(16711936)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .addAction(R.drawable.ic_prev, "Yes", pIntent)
                .addAction(R.drawable.ic_prev, "No",Intent);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }




    //fetch data from sharedpreferrences

    public void get_pref(Context context) {

        SharedPreferences sp = context.getSharedPreferences("RemaindMyTask", context.MODE_PRIVATE);
        restoredText = sp.getString("purpose", "");
        restoredname = sp.getString("name", "");

    }

    public void checked(Context context){
        get_pref(context);
        if (restoredText.equalsIgnoreCase("Birthday") && restoredname != null){
             str1 = "Today is " + restoredname+"'s" +restoredText+ ".";
             str2 ="You can wish.";
        }else if (restoredText.equalsIgnoreCase("Marriage Day") && restoredname != null){
            str1 = "Today is " + restoredname+"'s" +restoredText+ ".";
            str2 ="You can wish.";
        }else if (restoredText.equalsIgnoreCase("Email") && restoredname != null){
            str1 = "You need to " +restoredText+ " " +restoredname+ ".";
            str2 ="";
        }else if (restoredText.equalsIgnoreCase("Contact") && restoredname != null){
            str1 = "You need to " +restoredText+ " with " +restoredname+ ".";
            str2 ="";
        }else if (restoredText.equalsIgnoreCase("Visit") && restoredname != null){
            str1 = "You need to " +restoredText+ " " +restoredname+".";
            str2 ="";
        }else if (restoredText.equalsIgnoreCase("Meeting") && restoredname != null){
            str1 = "You have a " +restoredText+ " with " +restoredname+".";
            str2 ="";
        }
    }



}


