package com.example.andriginting.myapplication.util;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by Andri Ginting on 1/4/2018.
 */

public class UpComingTask  {

    private GcmNetworkManager gcmNetworkManager;

    public UpComingTask(Context context) {
        gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask(){
        Task periodicTask = new PeriodicTask.Builder()
                .setService(UpComingReminder.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(UpComingReminder.TAG_TASK_UPCOMING_LOG)
                .setPersisted(true)
                .build();

        gcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask(){
        if (gcmNetworkManager != null){
            gcmNetworkManager.cancelTask(UpComingReminder.TAG_TASK_UPCOMING_LOG,UpComingReminder.class);
        }
    }
}
