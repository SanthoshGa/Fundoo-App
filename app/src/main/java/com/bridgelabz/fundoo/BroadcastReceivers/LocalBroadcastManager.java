package com.bridgelabz.fundoo.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LocalBroadcastManager {
    private static final String TAG = "LocalBroadcastManager";




    public static BroadcastReceiver addedNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: Local broadcasts are working ");
            if (intent.hasExtra("isNoteAdded")) {
                boolean isNoteAdded = intent.
                        getBooleanExtra("isNoteAdded", false);
                Log.e(TAG, "onReceive: Yup we got the data " + isNoteAdded);

            }
        }
    };
    public static BroadcastReceiver setArchiveNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra("isNoteArchived")){
                boolean isNoteArchived = intent.getBooleanExtra("isNoteArchived", false);
                Log.e(TAG, "onReceive:  we got the data " + isNoteArchived);
                if(isNoteArchived){


                }
            }

        }
    };
}
