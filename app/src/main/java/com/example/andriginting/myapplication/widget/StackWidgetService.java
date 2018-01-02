package com.example.andriginting.myapplication.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Andri Ginting on 1/3/2018.
 */

public class StackWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemote(this.getApplicationContext(),intent);
    }
}
