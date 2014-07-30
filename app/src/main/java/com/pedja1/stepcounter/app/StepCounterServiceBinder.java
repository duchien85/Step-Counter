package com.pedja1.stepcounter.app;

import android.os.Binder;

/**
 * Created by pedja on 30.7.14. 09.32.
 * This class is part of the StepCounter
 * Copyright © 2014 ${OWNER}
 */
public class StepCounterServiceBinder extends Binder
{
    private final StepCounterService service;

    public StepCounterServiceBinder(StepCounterService service)
    {
        this.service = service;
    }

    public StepCounterService getService()
    {
        return service;
    }
}
