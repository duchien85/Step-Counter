package com.pedja1.stepcounter.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        context.startActivity(new Intent(context, StepCounterService.class).setAction(StepCounterService.INTENT_ACTION_START_COUNTER));
    }
}
