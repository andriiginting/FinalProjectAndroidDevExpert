package com.example.andriginting.myapplication.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.andriginting.myapplication.BuildConfig;
import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.activity.DetailMovieActivity;
import com.example.andriginting.myapplication.model.MovieItems;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Andri Ginting on 1/4/2018.
 */

public class UpComingReminder extends GcmTaskService {
    public static final String TAG = "UpComing_Service";
    public static String TAG_TASK_UPCOMING_LOG = "UpcomingTask";
    private int notifId = 100;

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING_LOG)) {
            getUpComingMovie();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    public void getUpComingMovie() {
        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + BuildConfig.API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    int listSize = list.length();
                    Random r = new Random();
                    int randomJsonIndex = r.nextInt(listSize-0) + 0;

                    JSONObject movie = list.getJSONObject(randomJsonIndex);
                    MovieItems movieItems = new MovieItems(movie);
                    movieItemses.add(movieItems);

                    int id_movie = movieItemses.get(0).getId();
                    String title = movieItemses.get(0).getTitle();
                    String message = "Today "+title+" has been release ";
                    showMovieNotification(getApplicationContext(),id_movie , title , message , ++notifId);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showMovieNotification(Context context, int id, String judul, String pesan, int notificationID) {
        Intent notification = new Intent(context, DetailMovieActivity.class);
        notification.putExtra(DetailMovieActivity.EXTRA_ID,id);
        notification.putExtra(DetailMovieActivity.EXTRA_TITLE,judul);

        //untuk intent ketika notif di click
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addParentStack(DetailMovieActivity.class)
                .addNextIntent(notification)
                .getPendingIntent(notificationID,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(judul)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(pesan)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000});

        notificationManager.notify(notificationID,builder.build());
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        UpComingTask upComingTask = new UpComingTask(this);
        upComingTask.createPeriodicTask();
    }
}
