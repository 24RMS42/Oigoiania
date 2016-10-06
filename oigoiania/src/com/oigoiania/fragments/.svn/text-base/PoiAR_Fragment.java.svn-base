package com.oigoiania.fragments;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.jwetherell.augmented_reality.activity.AugmentedRealityUtil;
import com.jwetherell.augmented_reality.camera.CameraSurface;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.oigoiania.augmentedreality.AugmentedReality;
import com.oigoiania.augmentedreality.AugmentedRedarView;
import com.oigoiania.augmentedreality.NewSensorsFragment;
import com.oigoiania.augmentedreality.OrientationManager;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.Util;

public class PoiAR_Fragment extends NewSensorsFragment {
	public static final DecimalFormat FORMAT = new DecimalFormat("#.##");
	private static final String tag = "PoiAR_Fragment";
	private static float currectAzimuth = 0;
	CameraSurface camScreen = null;
	private AugmentedRedarView redarView = null;
	private ImageView arrowView = null;
	private TextView distanceTv = null;
	private MyDataHolder mData = null;
	private Marker marker = null;
	@SuppressWarnings("unused")
	private float degrees = 0;
	private Location target = null;
	public static float MAX_ZOOM = 100; // in KM
	public static float ONE_PERCENT = MAX_ZOOM / 100f;
	public static float TEN_PERCENT = 10f * ONE_PERCENT;
	public static float TWENTY_PERCENT = 2f * TEN_PERCENT;
	public static float EIGHTY_PERCENTY = 4f * TWENTY_PERCENT;
	private static int progressSet = (int) (Util.current_zoomlevel * 1000);
	private OrientationManager mOrientationManager = new OrientationManager();
	private Timer timerOrientation = null;

	@Override
	public void onCreate(Bundle arg) {
		super.onCreate(arg);
		AugmentedRealityUtil.setContext(mActivity);
		Bundle bundle = getArguments();
		mData = (MyDataHolder) bundle.getSerializable("poi");
		if (mData == null)
			throw new NullPointerException("Data sent is null...");
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);
		target = new Location("reverseGeocoded");

		target.setLongitude(mData.getLoc_longitude());
		target.setLatitude(mData.getLoc_latitude());
		timerOrientation = new Timer();
		mOrientationManager.startSensorMonitoring(mActivity);
		timerOrientation.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (Util.currentLocation != null && arrowView != null) {
					updateArrow(arrowView);
				} else {
					Log.d(tag,
							"WARNING: No location found when getting angle...");
				}
			}
		}, 0, 500);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.d(tag, "not null");
		marker = new Marker(mData.getLoc_title() != null ? mData.getLoc_title()
				: mData.getName(), mData.getLoc_latitude(),
				mData.getLoc_longitude(), 0, Color.RED);
		marker.calcRelativePosition(ARData.getCurrentLocation());
		// List<Marker> list = new ArrayList<Marker>();
		// list.add(marker);
		// ARData.addMarkers(list);
		// marker.setDistance(GlobalFunctions.distFrom(
		// Util.currentLocation.getLatitude(),
		// Util.currentLocation.getLongitude(), mData.getLoc_latitude(),
		// mData.getLoc_longitude(), false));
		if (inflater == null)
			inflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.specific_poi_ar_view, null);
		RelativeLayout camscren_ll = (RelativeLayout) view
				.findViewById(R.id.camscreen);
		RelativeLayout arview = (RelativeLayout) view.findViewById(R.id.arview);
		camScreen = new CameraSurface(mActivity);
		camscren_ll.addView(camScreen);
		redarView = new AugmentedRedarView(mActivity, marker);
		arrowView = (ImageView) view.findViewById(R.id.imageView1);
		distanceTv = (TextView) view.findViewById(R.id.distance);
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth(); // deprecated
		RelativeLayout.LayoutParams augLayout = new RelativeLayout.LayoutParams(
				width, width + 20);
		redarView.setLayoutParams(augLayout);
		arview.addView(redarView);
		updateDataOnZoom();
		// getAngel(mData.getLoc_latitude(), mData.getLoc_longitude());
		// getAngleNew(mData.getLoc_latitude(), mData.getLoc_longitude());
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		if (mBroadcastReceiver != null)
			mActivity.unregisterReceiver(mBroadcastReceiver);
		timerOrientation.cancel();
		timerOrientation.purge();
		timerOrientation = null;
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent evt) {
		super.onSensorChanged(evt);

		if (evt.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			if (redarView != null)
				redarView.postInvalidate();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		super.onLocationChanged(location);
		Util.currentLocation = location;
		ARData.setCurrentLocation(location);
		if (marker != null)
			marker.calcRelativePosition(ARData.getCurrentLocation());
		// getAngel(mData.getLoc_latitude(), mData.getLoc_longitude());
		// getAngleNew(mData.getLoc_latitude(), mData.getLoc_longitude());
	}

	private synchronized void updateArrow(final ImageView imageView) {
		if (target == null || Util.currentLocation == null)
			return;

		int[] out = new int[3];
		mOrientationManager.getOrientation(out);
		float azimuth = out[0];
		GeomagneticField geoField = new GeomagneticField(
				Double.valueOf(Util.currentLocation.getLatitude()).floatValue(),
				Double.valueOf(Util.currentLocation.getLongitude())
						.floatValue(),
				Double.valueOf(Util.currentLocation.getAltitude()).floatValue(),
				System.currentTimeMillis());

		azimuth -= geoField.getDeclination(); // converts magnetic north into
												// true north

		// Store the bearingTo in the bearTo variable
		float bearTo = Util.currentLocation.bearingTo(target);

		// If the bearTo is smaller than 0, add 360 to get the rotation
		// clockwise.
		if (bearTo < 0) {
			bearTo = bearTo + 360;
		}

		// This is where we choose to point it
		float direction = bearTo - azimuth;

		// If the direction is smaller than 0, add 360 to get the rotation
		// clockwise.
		if (direction < 0) {
			direction = direction + 360;
		}

		final float realDirection = ((direction + 90) % 360);

		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String dist = "";
				float distance = 0;
				try {
					distance = (float) marker.getDistance();
					String tempStr = String.format(Locale.US, "%.2f", distance);
					if (tempStr.contains(","))
						tempStr.replaceAll("", ".");
					distance = Float.valueOf(tempStr);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				if (distance > 1000) {
					distance = distance / 1000;
					dist = String.format(Locale.US, "%.02f", distance);
					distanceTv.setText("Distância : " + dist + " Kms");
				} else if (distance == 1000) {
					distance = distance / 1000;
					dist = String.format(Locale.US, "%.02f", distance);
					distanceTv.setText("Distância : " + dist + " Km");
				} else {
					dist = String.format(Locale.US, "%.02f", distance);
					distanceTv.setText("Distância : " + dist
							+ (distance > 1 ? "metros" : "metro"));
				}
				rotateImageView(imageView, R.drawable.arrow, realDirection);
			}
		});
	}

	private void rotateImageView(ImageView imageView, int drawable, float rotate) {

		Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
				drawable);

		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = bitmapOrg.getWidth(), height = bitmapOrg.getHeight();

		// Initialize a new Matrix
		Matrix matrix = new Matrix();
		// Decide on how much to rotate
		rotate = rotate % 360;
		// Actually rotate the image
		matrix.postRotate(rotate, width, height);
		// recreate the new Bitmap via a couple conditions
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		// BitmapDrawable bmd = new BitmapDrawable( rotatedBitmap );
		// imageView.setImageBitmap( rotatedBitmap );
		imageView.setImageDrawable(new BitmapDrawable(getResources(),
				rotatedBitmap));
		imageView.setScaleType(ScaleType.CENTER);
	}

	@SuppressWarnings("unused")
	private synchronized void updateArrowold(float currentAzimuth) {
		Location desiredLocation;
		if (mData == null || arrowView == null || distanceTv == null)
			return;
		double lat = mData.getLoc_latitude();
		double lng = mData.getLoc_latitude();
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		desiredLocation.setLatitude(lat);
		desiredLocation.setLongitude(lng);
		float bearing = Util.currentLocation.bearingTo(desiredLocation);
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		// float distance = Float.valueOf(twoDForm.format(Util.currentLocation
		// .distanceTo(desiredLocation)));
		String dist = "";
		float distance = 0;
		try {
			distance = (float) marker.getDistance();// Util.currentLocation.distanceTo(desiredLocation);
			String tempStr = String.format(Locale.US, "%.2f", distance);// twoDForm.format(distance);
			if (tempStr.contains(","))
				tempStr.replaceAll("", ".");
			distance = Float.valueOf(tempStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (distance > 1000) {
			distance = distance / 1000;
			dist = String.format(Locale.US, "%.02f", distance);
			distanceTv.setText("Distância : " + dist + " Kms");
		} else if (distance == 1000) {
			distance = distance / 1000;
			dist = String.format(Locale.US, "%.02f", distance);
			distanceTv.setText("Distância : " + dist + " Km");
		} else {
			dist = String.format(Locale.US, "%.02f", distance);
			distanceTv.setText("Distância : " + dist
					+ (distance > 1 ? "metros" : "metro"));
		}
		// distanceTv.setText(marker.getDistance() + "");
		// ((String.format("%.02f", yourFLoatValue))
		float bearingRelative = bearing - currentAzimuth;// Util.currentBearing;
		while (bearingRelative < 0)
			bearingRelative = bearingRelative + 360;
		while (bearingRelative > 360)
			bearingRelative = bearingRelative - 360;
		// Logger.d(tag, "currectAzimuth, bearingRelative :" + currectAzimuth
		// + " : " + bearingRelative);
		Animation an = new RotateAnimation(-currectAzimuth, -bearingRelative,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// float temp = getAngle3(currentAzimuth, desiredLocation);
		// Animation an = new RotateAnimation(-currectAzimuth,
		// -(temp), Animation.RELATIVE_TO_SELF, 0.5f,
		// Animation.RELATIVE_TO_SELF, 0.5f);
		currectAzimuth = /* temp; */bearingRelative;
		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);
		arrowView.startAnimation(an);
		arrowView.invalidate();
		// arrowView.setRotation((-ARData.getAzimuth()));

	}

	@SuppressWarnings("unused")
	private double getAngel(double locLat, double locLon) {
		double lat1 = Double.parseDouble(Util.currentLocation.getLatitude()
				+ "");
		double lng1 = Double.parseDouble(Util.currentLocation.getLongitude()
				+ "");

		// destination
		double lat2 = locLat;
		double lng2 = locLon;

		double dLon = (lng2 - lng1);
		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(dLon);
		double brng = Math.toDegrees((Math.atan2(y, x)));
		brng = (360 - ((brng + 360) % 360));
		Logger.d(tag, "GetAngle : " + brng);
		return brng;
	}

	@SuppressWarnings("unused")
	private void getAngleNew(double locLat, double locLon) {
		double x1 = Util.currentLocation.getLatitude();
		double y1 = Util.currentLocation.getLongitude();
		double result;
		double x2 = locLat;// [annLatitude floatValue];
		double y2 = locLon;// [annLongitude floatValue];

		double dx = (x2 - x1);
		double dy = (y2 - y1);

		if (dx == 0) {
			if (dy > 0) {
				result = 90;
			} else {
				result = 270;
			}
		} else {
			result = (Math.atan(dy / dx)) * 180 / Math.PI;
		}

		if (dx < 0) {
			result = result + 180;
		}

		if (result < 0) {
			result = result + 360;
		}
		Logger.d(tag, "GetAngleNew : " + result);
		degrees = (float) result;
	}

	private static float calcZoomLevel() {
		float myZoomLevel = progressSet;// myZoomBar.getProgress();
		float myout = 0;
		myout = (float) (myZoomLevel / 1000);
		// float percent = 0;
		// if (myZoomLevel <= 25) {
		// percent = myZoomLevel / 25f;
		// myout = ONE_PERCENT * percent;
		// } else if (myZoomLevel > 25 && myZoomLevel <= 50) {
		// percent = (myZoomLevel - 25f) / 25f;
		// myout = ONE_PERCENT + (TEN_PERCENT * percent);
		// } else if (myZoomLevel > 50 && myZoomLevel <= 75) {
		// percent = (myZoomLevel - 50f) / 25f;
		// myout = TEN_PERCENT + (TWENTY_PERCENT * percent);
		// } else {
		// percent = (myZoomLevel - 75f) / 25f;
		// myout = TWENTY_PERCENT + (EIGHTY_PERCENTY * percent);
		// }

		return myout;
	}

	/**
	 * Called when the zoom bar has changed.
	 */
	protected void updateDataOnZoom() {
		float zoomLevel = Util.current_zoomlevel;
		ARData.setRadius(zoomLevel);
		ARData.setZoomLevel(AugmentedReality.FORMAT.format(zoomLevel));
		ARData.setZoomProgress(100);
	}

	public void updateDatChange() {
		float zoomLevel = /* Util.current_zoomlevel; */calcZoomLevel();
		ARData.setRadius(zoomLevel);
		ARData.setZoomLevel(FORMAT.format(zoomLevel));
		// ARData.setZoomProgress(myZoomBar.getProgress());
		camScreen.invalidate();
		redarView.invalidate();
	}

	// /**************************BroadCast-Receiver*************************//
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(Util.distanceChanged)) {
				progressSet = intent.getExtras().getInt("progress");
				updateDatChange();
			}
		}
	};

}
