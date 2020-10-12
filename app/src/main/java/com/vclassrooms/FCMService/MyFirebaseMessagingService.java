package com.vclassrooms.FCMService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vclassrooms.Activity.MainActivity;
import com.vclassrooms.Activity.NotificationActivity;
import com.vclassrooms.Common.Constatnts;
import com.vclassrooms.Database.NotificationDetails;
import com.vclassrooms.Database.DatabaseHelper;
import com.vclassrooms.R;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static com.gun0912.tedpermission.TedPermission.TAG;


/**
 * Created by haripal on 7/25/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private DatabaseHelper db;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.e("dataChat", remoteMessage.getData().toString());
        {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON_OBJECT", object.toString());
          String  title = params.get("title");
            String message = params.get("message");
            onInsert(title,message);
            sendNotification(title,message);
        }

    }
    public void onInsert(String title, String message){
        Constatnts constatnts=new Constatnts();
        String date = new SimpleDateFormat("EEE,dd MMM yyyy", Locale.getDefault()).format(new Date());
        db = new DatabaseHelper(getApplicationContext());
        NotificationDetails notificationDetails = new NotificationDetails();
        notificationDetails.setTittle(title);
        notificationDetails.setDetails(message);
        notificationDetails.setDate(date);
        notificationDetails.setIsread(constatnts.Unread);
        if(title.contentEquals(constatnts.Title_Announcement)){
            notificationDetails.setFlag(constatnts.AnnouncementFlag);
        }else {
            notificationDetails.setFlag(constatnts.NotificationFlag);
        }
        db.insertNotification(notificationDetails);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification(String title,
                                  String message){
        Constatnts constatnts=new Constatnts();
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        TaskStackBuilder stackBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(getApplicationContext());
        }
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }


}