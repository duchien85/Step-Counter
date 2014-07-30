package com.pedja1.stepcounter.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by pedja on 30.7.14. 11.09.
 * This class is part of the StepCounter
 * Copyright © 2014 ${OWNER}
 */
public class PrefsManager
{
    enum Key
    {
        steps, service_running, total_steps
    }

    SharedPreferences prefs;

    public PrefsManager(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getStepsTaken()
    {
        return prefs.getInt(Key.steps.toString(), 0);
    }

    public void setStepsTaken(int steps)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Key.steps.toString(), steps);
        editor.apply();
    }

    public void resetSteps()
    {
        int totalSteps = getStepStorageCount();
        int steps = getStepsTaken();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Key.total_steps.toString(), steps + totalSteps);
        editor.remove(Key.steps.toString());
        editor.apply();
    }

    public boolean isServiceRunning()
    {
        return prefs.getBoolean(Key.steps.toString(), false);
    }

    public void setServiceRunning(boolean running)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Key.service_running.toString(), running);
        editor.apply();
    }

    public int getStepStorageCount()
    {
        return prefs.getInt(Key.total_steps.toString(), 0);
    }
}
