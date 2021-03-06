package com.br.oigoiania;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jwetherell.augmented_reality.data.ARData;
import com.oigoiania.util.Util;

public abstract class SensorActivity extends FragmentActivity implements
		SensorEventListener, LocationListener {

	protected LocationManager locationManager;
	protected SensorManager mSensorManager;
	protected Sensor mSensor;
	protected Activity mActivity = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		
		Util.currentLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (Util.currentLocation == null) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
			Util.currentLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (Util.currentLocation == null)
				showSettingsAlert();
		}
		onLocationChanged(Util.currentLocation);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// boolean isGPSEnabled = locationManager
		// .isProviderEnabled(LocationManager.GPS_PROVIDER);
		//
		// Logger.i("isGPSEnabled", "=" + isGPSEnabled);
		//
		// boolean isNetworkEnabled = locationManager
		// .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		//
		// Logger.i("isNetworkEnabled", "=" + isNetworkEnabled);
		//
		// if (isGPSEnabled == false && isNetworkEnabled == false) {
		// showSettingsAlert();
		// } else {
		// if (isNetworkEnabled) {
		// locationManager.requestLocationUpdates(
		// LocationManager.NETWORK_PROVIDER, 0, 0, this);
		// if (locationManager != null) {
		// Location location = locationManager
		// .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		// if (location != null) {
		// Util.currentLocation = location;
		// }
		// }
		// }
		// if (isGPSEnabled) {
		// if (Util.currentLocation == null) {
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 0,
		// 0, this);
		// if (locationManager != null) {
		// Location location = locationManager
		// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// if (location != null) {
		// Util.currentLocation=location;
		// }
		// }
		// }
		// }
		// }

		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);

		Util.currentLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (Util.currentLocation == null) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
			Util.currentLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (Util.currentLocation == null)
				showSettingsAlert();
		}
		onLocationChanged(Util.currentLocation);

	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location location) {
		if (Util.useDefaultLocation) {
			location.setLatitude(Util.defaultLatitude);
			location.setLongitude(Util.defaultLongitude);
		}
		Log.d("matata:","location" + location);
		Util.currentLocation = location;
		ARData.setCurrentLocation(location);

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

	protected void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);

		alertDialog.setTitle("GPS is settings");

		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						mActivity.finish();
					}
				});

		alertDialog.show();
	}

	protected abstract void onSensorChange(SensorEvent event);

}
