package com.pedja1.stepcounter.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener, ServiceConnection
{
    public static final String INTENT_ACTION_UPDATE_STEP_COUNTER = "com.pedja1.stepcounter.app.UPDATE_STEP_COUNTER";
    StepCounterService mService;
    boolean mBound = false;
    TextView tvSteps, tvStepsText;
    ToggleButton tbToggleService;
    ProgressBar pbLoading;
    StepUpdateReceiver stepUpdateReceiver;
    boolean performToggleAction = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSteps = (TextView) findViewById(R.id.tvSteps);
        tvStepsText = (TextView) findViewById(R.id.tvStepsText);
        tbToggleService = (ToggleButton) findViewById(R.id.tbServiceToggle);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        int steps = new PrefsManager(this).getStepsTaken();
        tvSteps.setText(steps < 0 ? "-" : steps + "");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor == null)
        {
            tvStepsText.setText(getString(R.string.no_sensor));
            tvSteps.setVisibility(View.GONE);
        }
        else
        {
            // Bind to StepCounterService
            Intent intent = new Intent(this, StepCounterService.class);
            startService(intent);
            bindService(intent, this, Context.BIND_AUTO_CREATE);
            stepUpdateReceiver = new StepUpdateReceiver();
            IntentFilter filter = new IntentFilter(INTENT_ACTION_UPDATE_STEP_COUNTER);
            filter.setPriority(1);
            registerReceiver(stepUpdateReceiver, filter);
        }

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        // Unbind from the service
        if (mBound)
        {
            unbindService(this);
            mBound = false;
        }
        if(stepUpdateReceiver != null)
        {
            unregisterReceiver(stepUpdateReceiver);
        }
    }


    @Override
    public void onServiceConnected(ComponentName className, IBinder service)
    {
        // We've bound to StepCounterService, cast the IBinder and get LocalService instance
        StepCounterServiceBinder binder = (StepCounterServiceBinder) service;
        mService = binder.getService();
        mBound = true;
        pbLoading.setVisibility(View.GONE);
        tbToggleService.setVisibility(View.VISIBLE);
        performToggleAction = false;
        tbToggleService.setChecked(mService.isRunning());
        performToggleAction = true;
        tbToggleService.setOnCheckedChangeListener(this);
        NotificationUtils.clearNotification(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0)
    {
        mBound = false;
        tbToggleService.setOnCheckedChangeListener(null);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if(mService != null && mBound && performToggleAction)
        {
            if(isChecked)
            {
                mService.startStepCounter();
            }
            else
            {
                mService.stopStepCounter();
            }
        }
    }

    public class StepUpdateReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent != null && INTENT_ACTION_UPDATE_STEP_COUNTER.equals(intent.getAction()))
            {
                tvSteps.setText(new PrefsManager(MainActivity.this).getStepsTaken() + new PrefsManager(MainActivity.this).getStepStorageCount() + "");
                abortBroadcast();
            }
        }
    }
}
