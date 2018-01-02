package com.example.andriginting.myapplication.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.andriginting.myapplication.BuildConfig;
import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.database.MovieHelper;
import com.example.andriginting.myapplication.model.MovieItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andri Ginting on 1/3/2018.
 */

public class StackRemote implements RemoteViewsService.RemoteViewsFactory{
    private List<MovieItems> widgetItems = new ArrayList<>();
    private Context context;
    private int widgetID;
    private MovieHelper movieHelper;

    public StackRemote(Context context, Intent intent) {
        this.context = context;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
        ,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        movieHelper = new MovieHelper(context);
        movieHelper.open();
        widgetItems.addAll(movieHelper.getAllData());
    }

    @Override
    public void onDestroy() {
        if (movieHelper!=null){
            movieHelper.close();
        }
    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_items);
        Bitmap bitmap = null;

        try{
            bitmap = Picasso.with(context)
                    .load(BuildConfig.API_KEY+widgetItems.get(i).getPoster_path())
                    .fit()
                    .centerCrop()
                    .get();
        }catch (Exception e){

        }

        remoteViews.setImageViewBitmap(R.id.imaage_widget_item,bitmap);

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_ITEM,i);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imaage_widget_item,intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
