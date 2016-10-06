package com.oigoiania.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.jwetherell.augmented_reality.data.ARData;
import com.oigoiania.util.Util;

public abstract class SensorForArrowFragment extends Fragment implements
		SensorEventListener, LocationListener {

	protected LocationManager locationManager;
	protected SensorManager mSensorManager;
	protected Sensor mSensor;
	protected FragmentActivity mActivity = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		locationManager = (LocationManager) mActivity
				.getSystemService(Context.LOCATION_SERVICE);

		mSensorManager = (SensorManager) mActivity
				.getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null)
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		onLocationChanged(location);

	}

	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null)
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		onLocationChanged(location);
	}

	@Override
	public void onLocationChanged(Location location) {
		if (Util.useDefaultLocation) {
			location.setLatitude(Util.defaultLatitude);
			location.setLongitude(Util.defaultLongitude);
		}
		Util.currentLocation = location;
		ARData.setCurrentLocation(location);
		onLocationChange(location);

	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Util.currentBearing = event.values[0];
		onSensorChange(event);
	}

	protected abstract void onSensorChange(SensorEvent event);

	protected void onLocationChange(Location location) {
	};

}
