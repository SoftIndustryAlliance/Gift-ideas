package com.soft_industry.findgift.presentation.pages.randomgift;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeHandler {


    private static final String TAG = ShakeHandler.class.getSimpleName();


    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private OnShakeListener mOnShakeListener;

    private boolean mIsRegistered = false;

    public ShakeHandler(Context context) {

        mContext = context;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public boolean isAccelerometerAvalible(){
        return mAccelerometer != null;
    }

    private SensorEventListener mAccListener = new SensorEventListener() {


        final float MAX_DELTA = 15;
        final int ARR_CNT = 3;

        boolean initVar = true;
        float[] mVal = {0, 0, 0};  // x, y, z

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (initVar) {
                //first init var
                for (int i = 0; i < ARR_CNT; i++) {
                    mVal[i] = event.values[i];
                }
                initVar = false;

            } else {

                for (int i = 0; i < ARR_CNT; i++) {

                    float delta = mVal[i] - event.values[i];
                    mVal[i] = event.values[i];

                    if (Math.abs(delta) > MAX_DELTA) {
                        onShake();
                        break;
                    }
                }

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void registerListener() {
        if (!mIsRegistered){
            mSensorManager.registerListener(mAccListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mIsRegistered = true;
        }
    }

    public void unregisterListener() {
        if (mIsRegistered) {
            mSensorManager.unregisterListener(mAccListener);
            mIsRegistered = false;
        }
    }

    public boolean isRegisteredListener() {
        return mIsRegistered;
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mOnShakeListener = listener;
    }

    private void onShake() {
        if (mOnShakeListener != null) {
            mOnShakeListener.onShake();
        }
    }

    public Sensor getAccelerometer() {
        return mAccelerometer;
    }


    public interface OnShakeListener {
        void onShake();
    }


}