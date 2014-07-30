package com.pedja1.stepcounter.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StepCounterServiceNotificationReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent != null && MainActivity.INTENT_ACTION_UPDATE_STEP_COUNTER.equals(intent.getAction()))
        {
            NotificationUtils.sendNotification(new PrefsManager(context).getStepsTaken() + new PrefsManager(context).getStepStorageCount(), context);
        }
    }
}
