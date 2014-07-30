package com.pedja1.stepcounter.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class StepCounterService extends Service implements SensorEventListener
{
    public static final String INTENT_ACTION_START_COUNTER = "start_counter";
    private final IBinder mBinder;
    private boolean running;
    private SensorManager mSensorManager;

    public StepCounterService()
    {
        mBinder = new StepCounterServiceBinder(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        System.out.println("StepCounterService create");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(intent != null && INTENT_ACTION_START_COUNTER.equals(intent.getAction()))
        {
            startStepCounter();
        }
        System.out.println("StepCounterService started");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.out.println("StepCounterService destroyed");
    }

    public void startStepCounter()
    {
        System.out.println("StepCounterService step counter started");
        running = true;
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null)
        {
            mSensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        new PrefsManager(this).setServiceRunning(true);
    }

    public void stopStepCounter()
    {
        System.out.println("StepCounterService step counter stopped");
        running = false;
        mSensorManager.unregisterListener(this);
        new PrefsManager(this).setServiceRunning(false);
    }

    public boolean isRunning()
    {
        return running;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        int steps = (int) event.values[0];
        new PrefsManager(this).setStepsTaken(steps);
        Intent intent = new Intent(MainActivity.INTENT_ACTION_UPDATE_STEP_COUNTER);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
