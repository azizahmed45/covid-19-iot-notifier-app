package com.mrgreenapps.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mrgreenapps.notification.model.NotificationModel;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FCMR extends FirebaseMessagingService {

    public static final String mypreference = "mypref";
    public static final String tokenKeyFCMR = "tokenKeyApp";
    SharedPreferences sharedpreferences;

    List<NotificationModel> notificationModels;

    @Override
    public void onNewToken(@NonNull String s) {
        //save token
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(tokenKeyFCMR, s);
        editor.commit();

        Log.d("TAG", "onNewToken: ");



    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //receive call
        getData();
        createNotificationChannel();

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        if(title != null || body != null){
            saveData(title, body);
            showNotification(title, body);
        }

        Log.d("TAG", "onMessageReceived: " + remoteMessage.getData().toString());
    }

    public  void  saveData(String title,String body){

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateS  = simpleDateFormat.format(currentTime);

        notificationModels.add(new NotificationModel(title,body,dateS));

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationModels);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();

    }

    public  void  getData(){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");
        Type type = new TypeToken<List<NotificationModel>>(){}.getType();
        notificationModels = gson.fromJson(json, type);
        if (notificationModels == null){
            notificationModels  = new ArrayList<>();
        }
    }

    public void showNotification(String title, String body){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(102, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification channel";
            String description = "notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
