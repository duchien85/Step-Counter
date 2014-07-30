package com.pedja1.stepcounter.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by pedja on 30.7.14. 11.10.
 * This class is part of the StepCounter
 * Copyright © 2014 ${OWNER}
 */
public class MainApp extends Application
{
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }
}
