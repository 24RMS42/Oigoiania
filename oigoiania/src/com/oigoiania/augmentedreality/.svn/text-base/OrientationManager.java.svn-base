/*
 * Copyright (c) 2013 zedvoid.
 *
 * This file is part of Indico.
 *
 * Indico is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Indico is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Indico.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.oigoiania.augmentedreality;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;


public class OrientationManager implements SensorEventListener {
// ------------------------------ FIELDS ------------------------------

    private static final float PI_BY_TWO = (float) (Math.PI / 2);
    @SuppressWarnings("unused")
	private static final float PI = (float) (Math.PI);

    //- 31 micro tesla in +Y at 0 degree equator
    private final float[] mMagValues = new float[]{0f, 31f, 0f};

    // -9.81 Kg/m2 in -Z on earth
    private final float[] mAccValues = new float[]{0f, 0f, -9.81f};

    //----holder matrices----
    private final float[] mR = new float[9];
    private final float[] mI = new float[9];
    private final float[] mO = new float[3];
    private float[] mR2 = new float[9];
    private int[] mOut = new int[3];

    private final int[] mResults = new int[3];
    private int mDispRotation = Surface.ROTATION_0;
    private Processor mProcessor;
    private final Object mLock = new Object();
	private static final String tag = "OrientationManager";

// --------------------- GETTER / SETTER METHODS ---------------------

    public void setDispRotation(int dispRotation) {
        mDispRotation = dispRotation;
    }

// --------------------- INTERFACE SensorEventListener ---------------------

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mAccValues[0] = sensorEvent.values[0];
                mAccValues[1] = sensorEvent.values[1];
                mAccValues[2] = sensorEvent.values[2];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagValues[0] = sensorEvent.values[0];
                mMagValues[1] = sensorEvent.values[1];
                mMagValues[2] = sensorEvent.values[2];
                break;
        }

        synchronized (mLock) {
            mLock.notifyAll();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //--ignored--
    }

// -------------------------- OTHER METHODS --------------------------

    public void getOrientation(int[] out) {
        out[0] = mResults[0];
        out[1] = mResults[1];
        out[2] = mResults[2];
    }

    public void startSensorMonitoring(Context context) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor accSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (accSensor != null) {
            manager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (magSensor != null) {
            manager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        mProcessor = new Processor();
        mProcessor.setDaemon(true);
        mProcessor.start();
        Log.i(tag , "Started monitoring");
    }

    public void stopSensorMonitoring(Context context) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this);

        if (mProcessor != null) {
            mProcessor.cancel();
        }

        Log.i(tag, "Stopped monitoring.");
    }

    private void updateData() {
        SensorManager.getRotationMatrix(mR, mI, mAccValues, mMagValues);

        /**
         * arg 2: what world(according to app) axis , device's x axis aligns with
         * arg 3: what world(according to app) axis , device's y axis aligns with
         * world x = app's x = app's east
         * world y = app's y = app's north
         * device x = device's left side = device's east
         * device y = device's top side  = device's north
         */

        switch (mDispRotation) {
            case Surface.ROTATION_90:
                SensorManager.remapCoordinateSystem(mR, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, mR2);
                break;
            case Surface.ROTATION_270:
                SensorManager.remapCoordinateSystem(mR, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, mR2);
                break;
            case Surface.ROTATION_180:
                SensorManager.remapCoordinateSystem(mR, SensorManager.AXIS_MINUS_X, SensorManager.AXIS_MINUS_Y, mR2);
                break;
            case Surface.ROTATION_0:
            default:
                mR2 = mR;
        }

        SensorManager.getOrientation(mR2, mO);


        //--upside down when abs roll > 90--
        if (Math.abs(mO[2]) > PI_BY_TWO) {
            //--fix, azimuth always to true north, even when device upside down, realistic --
            mO[0] = -mO[0];

            //--fix, roll never upside down, even when device upside down, unrealistic --
            //mO[2] = mO[2] > 0 ? PI - mO[2] : - (PI - Math.abs(mO[2]));

            //--fix, pitch comes from opposite , when device goes upside down, realistic --
            mO[1] = -mO[1];
        }

        CircleUtils.convertRadToDegrees(mO, mOut);
        CircleUtils.normalize(mOut);

        //--write--
        mResults[0] = mOut[0];
        mResults[1] = mOut[1];
        mResults[2] = mOut[2];
    }

// -------------------------- INNER CLASSES --------------------------

    private final class Processor extends Thread {
        private boolean mHalt;

        // ------------------------ OVERRIDES ------------------------
        @Override
        public void run() {
            super.run();

            while (!mHalt && !isInterrupted()) {
                updateData();
                try {
                    synchronized (mLock) {
                        mLock.wait();
                    }
                } catch (InterruptedException e) {
                    Log.e(tag, "Processor thread interrupted");
                    return;
                }
            }
        }

        public void cancel() {
            mHalt = true;
            interrupt();
        }
    }
}