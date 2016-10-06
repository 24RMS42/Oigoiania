package com.oigoiania.augmentedreality;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.hardware.GeomagneticField;
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
import android.util.FloatMath;

import com.crittercism.app.Crittercism;
import com.jwetherell.augmented_reality.common.LowPassFilter;
import com.jwetherell.augmented_reality.common.Matrix;
import com.jwetherell.augmented_reality.data.ARData;
import com.oigoiania.logger.Logger;
import com.oigoiania.util.GlobalFunctions;
import com.oigoiania.util.Util;

/**
 * This class extends Activity and processes sensor data and location data.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public abstract class SensorsFragment extends Fragment implements
		SensorEventListener, LocationListener {

	private static final String TAG = "SensorsActivity";
	private AtomicBoolean computing = new AtomicBoolean(false);

	private int MIN_TIME = 30 * 1000;
	private  int MIN_DISTANCE = 10;

	private  float temp[] = new float[9]; // Temporary rotation
														// matrix in Android
														// format
	private float rotation[] = new float[9]; // Final rotation
															// matrix in Android
															// format
	private float grav[] = new float[3]; // Gravity (a.k.a
														// accelerometer data)
	private float mag[] = new float[3]; // Magnetic
	/*
	 * Using Matrix operations instead. This was way too inaccurate, private
	 * static final float apr[] = new float[3]; //Azimuth, pitch, roll
	 */

	private Matrix worldCoord = new Matrix();
	private Matrix magneticCompensatedCoord = new Matrix();
	private Matrix xAxisRotation = new Matrix();
	private Matrix yAxisRotation = new Matrix();
	private Matrix mageticNorthCompensation = new Matrix();

	private  GeomagneticField gmf = null;
	private  float smooth[] = new float[3];
	private  SensorManager sensorMgr = null;
	private  List<Sensor> sensors = null;
	private  Sensor sensorGrav = null;
	private  Sensor sensorMag = null;
	private  Sensor sensorOrietation = null;
	private  LocationManager locationMgr = null;
	protected FragmentActivity mActivity = null;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivity = getActivity();
		float neg90rads = (float) Math.toRadians(-90);

		// Counter-clockwise rotation at -90 degrees around the x-axis
		// [ 1, 0, 0 ]
		// [ 0, cos, -sin ]
		// [ 0, sin, cos ]
		xAxisRotation.set(1f, 0f, 0f, 0f, FloatMath.cos(neg90rads),
				-FloatMath.sin(neg90rads), 0f, FloatMath.sin(neg90rads),
				FloatMath.cos(neg90rads));

		// Counter-clockwise rotation at -90 degrees around the y-axis
		// [ cos, 0, sin ]
		// [ 0, 1, 0 ]
		// [ -sin, 0, cos ]
		yAxisRotation.set(FloatMath.cos(neg90rads), 0f,
				FloatMath.sin(neg90rads), 0f, 1f, 0f,
				-FloatMath.sin(neg90rads), 0f, FloatMath.cos(neg90rads));

		try {
			sensorMgr = (SensorManager) mActivity
					.getSystemService(Context.SENSOR_SERVICE);

			sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if (sensors.size() > 0)
				sensorGrav = sensors.get(0);

			sensors = sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
			if (sensors.size() > 0)
				sensorMag = sensors.get(0);
			sensors = sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION);
			if (sensors.size() > 0)
				sensorOrietation = sensors.get(0);
			sensorMgr.registerListener(this, sensorGrav,
					SensorManager.SENSOR_DELAY_NORMAL);
			sensorMgr.registerListener(this, sensorMag,
					SensorManager.SENSOR_DELAY_NORMAL);
			sensorMgr.registerListener(this, sensorOrietation,
					SensorManager.SENSOR_DELAY_NORMAL);
			locationMgr = (LocationManager) mActivity
					.getSystemService(Context.LOCATION_SERVICE);
			locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					MIN_TIME, MIN_DISTANCE, this);

			try {

				try {
					Location gps = locationMgr
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					Location network = locationMgr
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (gps != null)
						onLocationChanged(gps);
					else if (network != null)
						onLocationChanged(network);
					else
						onLocationChanged(ARData.hardFix);
				} catch (Exception ex2) {
					Crittercism.logHandledException(ex2);
					onLocationChanged(ARData.hardFix);
				}

				gmf = new GeomagneticField((float) ARData.getCurrentLocation()
						.getLatitude(), (float) ARData.getCurrentLocation()
						.getLongitude(), (float) ARData.getCurrentLocation()
						.getAltitude(), System.currentTimeMillis());

				float dec = (float) Math.toRadians(-gmf.getDeclination());

				synchronized (mageticNorthCompensation) {
					// Identity matrix
					// [ 1, 0, 0 ]
					// [ 0, 1, 0 ]
					// [ 0, 0, 1 ]
					mageticNorthCompensation.toIdentity();

					// Counter-clockwise rotation at negative declination around
					// the y-axis
					// note: declination of the horizontal component of the
					// magnetic field
					// from true north, in degrees (i.e. positive means the
					// magnetic
					// field is rotated east that much from true north).
					// note2: declination is the difference between true north
					// and magnetic north
					// [ cos, 0, sin ]
					// [ 0, 1, 0 ]
					// [ -sin, 0, cos ]
					mageticNorthCompensation.set(FloatMath.cos(dec), 0f,
							FloatMath.sin(dec), 0f, 1f, 0f,
							-FloatMath.sin(dec), 0f, FloatMath.cos(dec));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex1) {
			try {
				if (sensorMgr != null) {
					sensorMgr.unregisterListener(this, sensorGrav);
					sensorMgr.unregisterListener(this, sensorMag);
					sensorMgr.unregisterListener(this, sensorOrietation);
					sensorMgr = null;
				}
				if (locationMgr != null) {
					locationMgr.removeUpdates(this);
					locationMgr = null;
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onStart() {
		super.onStart();

		// float neg90rads = (float) Math.toRadians(-90);

		// // Counter-clockwise rotation at -90 degrees around the x-axis
		// // [ 1, 0, 0 ]
		// // [ 0, cos, -sin ]
		// // [ 0, sin, cos ]
		// xAxisRotation.set(1f, 0f, 0f, 0f, FloatMath.cos(neg90rads),
		// -FloatMath.sin(neg90rads), 0f, FloatMath.sin(neg90rads),
		// FloatMath.cos(neg90rads));
		//
		// // Counter-clockwise rotation at -90 degrees around the y-axis
		// // [ cos, 0, sin ]
		// // [ 0, 1, 0 ]
		// // [ -sin, 0, cos ]
		// yAxisRotation.set(FloatMath.cos(neg90rads), 0f,
		// FloatMath.sin(neg90rads), 0f, 1f, 0f,
		// -FloatMath.sin(neg90rads), 0f, FloatMath.cos(neg90rads));

		try {
			sensorMgr = (SensorManager) mActivity
					.getSystemService(Context.SENSOR_SERVICE);

			sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if (sensors.size() > 0)
				sensorGrav = sensors.get(0);

			sensors = sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
			if (sensors.size() > 0)
				sensorMag = sensors.get(0);
			sensors = sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION);
			if (sensors.size() > 0)
				sensorOrietation = sensors.get(0);
			sensorMgr.registerListener(this, sensorGrav,
					SensorManager.SENSOR_DELAY_NORMAL);
			sensorMgr.registerListener(this, sensorMag,
					SensorManager.SENSOR_DELAY_NORMAL);
			sensorMgr.registerListener(this, sensorOrietation,
					SensorManager.SENSOR_DELAY_NORMAL);
			locationMgr = (LocationManager) mActivity
					.getSystemService(Context.LOCATION_SERVICE);
			locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					MIN_TIME, MIN_DISTANCE, this);

			try {

				try {
					Location gps = locationMgr
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					Location network = locationMgr
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (gps != null)
						onLocationChanged(gps);
					else if (network != null)
						onLocationChanged(network);
					else
						onLocationChanged(ARData.hardFix);
				} catch (Exception ex2) {
					Crittercism.logHandledException(ex2);
					onLocationChanged(ARData.hardFix);
				}

				gmf = new GeomagneticField((float) ARData.getCurrentLocation()
						.getLatitude(), (float) ARData.getCurrentLocation()
						.getLongitude(), (float) ARData.getCurrentLocation()
						.getAltitude(), System.currentTimeMillis());

				float dec = (float) Math.toRadians(-gmf.getDeclination());

				synchronized (mageticNorthCompensation) {
					// Identity matrix
					// [ 1, 0, 0 ]
					// [ 0, 1, 0 ]
					// [ 0, 0, 1 ]
					mageticNorthCompensation.toIdentity();

					// Counter-clockwise rotation at negative declination around
					// the y-axis
					// note: declination of the horizontal component of the
					// magnetic field
					// from true north, in degrees (i.e. positive means the
					// magnetic
					// field is rotated east that much from true north).
					// note2: declination is the difference between true north
					// and magnetic north
					// [ cos, 0, sin ]
					// [ 0, 1, 0 ]
					// [ -sin, 0, cos ]
					mageticNorthCompensation.set(FloatMath.cos(dec), 0f,
							FloatMath.sin(dec), 0f, 1f, 0f,
							-FloatMath.sin(dec), 0f, FloatMath.cos(dec));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex1) {
			try {
				if (sensorMgr != null) {
					sensorMgr.unregisterListener(this, sensorGrav);
					sensorMgr.unregisterListener(this, sensorMag);
					sensorMgr.unregisterListener(this, sensorOrietation);
					sensorMgr = null;
				}
				if (locationMgr != null) {
					locationMgr.removeUpdates(this);
					locationMgr = null;
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStop() {
		super.onStop();

		try {
			try {
				if (sensorMgr != null)
					sensorMgr.unregisterListener(this, sensorGrav);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (sensorMgr != null)
					sensorMgr.unregisterListener(this, sensorMag);
				if (sensorOrietation != null && sensorMgr != null)
					sensorMgr.unregisterListener(this, sensorOrietation);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			sensorMgr = null;

			try {
				if (locationMgr != null)
					locationMgr.removeUpdates(this);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			locationMgr = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSensorChanged(SensorEvent evt) {
		if (!computing.compareAndSet(false, true))
			return;

		if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			smooth = LowPassFilter.filter(0.5f, 1.0f, evt.values, grav);
			grav[0] = smooth[0];
			grav[1] = smooth[1];
			grav[2] = smooth[2];
		} else if (evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			smooth = LowPassFilter.filter(2.0f, 4.0f, evt.values, mag);
			mag[0] = smooth[0];
			mag[1] = smooth[1];
			mag[2] = smooth[2];
		}

		// // Find real world position relative to phone location ////
		// Get rotation matrix given the gravity and geomagnetic matrices
		SensorManager.getRotationMatrix(temp, null, grav, mag);

		// Translate the rotation matrices from Y and -Z (landscape)
		// SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Y,
		// SensorManager.AXIS_MINUS_X, rotation);
		// SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_X,
		// SensorManager.AXIS_MINUS_Z, rotation);

		// below code was used previously
		if (GlobalFunctions.isLargeSize(mActivity)) {
//			SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Z,
//					SensorManager.AXIS_MINUS_X, rotation);
			
			 SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Z, SensorManager.AXIS_Y, rotation);
		} else {
			SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_Y,
					SensorManager.AXIS_MINUS_Z, rotation);
		}

		/*
		 * Using Matrix operations instead. This was way too inaccurate, //Get
		 * the azimuth, pitch, roll SensorManager.getOrientation(rotation,apr);
		 * float floatAzimuth = (float)Math.toDegrees(apr[0]); if
		 * (floatAzimuth<0) floatAzimuth+=360; ARData.setAzimuth(floatAzimuth);
		 * ARData.setPitch((float)Math.toDegrees(apr[1]));
		 * ARData.setRoll((float)Math.toDegrees(apr[2]));
		 */

		// Convert from float[9] to Matrix
		worldCoord
				.set(rotation[0], rotation[1], rotation[2], rotation[3],
						rotation[4], rotation[5], rotation[6], rotation[7],
						rotation[8]);

		// // Find position relative to magnetic north ////
		// Identity matrix
		// [ 1, 0, 0 ]
		// [ 0, 1, 0 ]
		// [ 0, 0, 1 ]
		magneticCompensatedCoord.toIdentity();

		synchronized (mageticNorthCompensation) {
			// Cross product the matrix with the magnetic north compensation
			magneticCompensatedCoord.prod(mageticNorthCompensation);
		}

		// The compass assumes the screen is parallel to the ground with the
		// screen pointing
		// to the sky, rotate to compensate.
		magneticCompensatedCoord.prod(xAxisRotation);

		// Cross product with the world coordinates to get a mag north
		// compensated coords
		magneticCompensatedCoord.prod(worldCoord);

		// Y axis
		magneticCompensatedCoord.prod(yAxisRotation);

		// Invert the matrix since up-down and left-right are reversed in
		// landscape mode
		magneticCompensatedCoord.invert();

		// Set the rotation matrix (used to translate all object from lat/lon to
		// x/y/z)
		ARData.setRotationMatrix(magneticCompensatedCoord);

		computing.set(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// Ignore
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// Ignore
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Ignore
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onLocationChanged(Location location) {
		if (Util.useDefaultLocation) {
			location.setLatitude(Util.defaultLatitude);
			location.setLongitude(Util.defaultLongitude);
		}

		Util.currentLocation = location;
		ARData.setCurrentLocation(location);
		// Util.currentLocation = location;
		gmf = new GeomagneticField((float) ARData.getCurrentLocation()
				.getLatitude(), (float) ARData.getCurrentLocation()
				.getLongitude(), (float) ARData.getCurrentLocation()
				.getAltitude(), System.currentTimeMillis());

		float dec = (float) Math.toRadians(-gmf.getDeclination());

		synchronized (mageticNorthCompensation) {
			mageticNorthCompensation.toIdentity();

			mageticNorthCompensation.set(FloatMath.cos(dec), 0f,
					FloatMath.sin(dec), 0f, 1f, 0f, -FloatMath.sin(dec), 0f,
					FloatMath.cos(dec));
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		if (sensor == null)
			throw new NullPointerException();

		if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
				&& accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			Logger.e(TAG, "Compass data unreliable");
		}
	}
}
