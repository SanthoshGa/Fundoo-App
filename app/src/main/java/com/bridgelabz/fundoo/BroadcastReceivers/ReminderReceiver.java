package com.bridgelabz.fundoo.BroadcastReceivers;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.bridgelabz.fundoo.Dashboard.View.AddNoteActivity;
import com.bridgelabz.fundoo.R;

import static com.bridgelabz.fundoo.MyApplicationClass.CHANNEL_1_ID;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String TAG = "ReminderReceiver.class";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, AddNoteActivity.class);


        TaskStackBuilder  taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(AddNoteActivity.class);
        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(100,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID);
        Notification notification = builder.setContentTitle("app notification")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("title")
                .setContentText("description")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
}
