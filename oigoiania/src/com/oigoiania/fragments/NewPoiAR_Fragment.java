package com.oigoiania.fragments;

import java.text.DecimalFormat;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.br.oigoiania.R;
import com.jwetherell.augmented_reality.camera.CameraSurface;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.widget.VerticalSeekBar;
import com.jwetherell.augmented_reality.widget.VerticalTextView;
import com.oigoiania.augmentedreality.AugmentedRedarView;
import com.oigoiania.augmentedreality.AugmentedView;
import com.oigoiania.augmentedreality.SensorsFragment;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.Util;

/**
 * This class extends the SensorsActivity and is designed tie the AugmentedView
 * and zoom bar together.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class NewPoiAR_Fragment extends SensorsFragment implements
		OnTouchListener {

	private static final String TAG = "AugmentedReality";
	public static final DecimalFormat FORMAT = new DecimalFormat("#.##");
	private static final int ZOOMBAR_BACKGROUND_COLOR = Color.argb(125, 55, 55,
			55);
	// private String END_TEXT = FORMAT.format(MAX_ZOOM) + " km";
	// private static final int END_TEXT_COLOR = Color.WHITE;

	protected static WakeLock wakeLock = null;
	protected static CameraSurface camScreen = null;
	protected static VerticalSeekBar myZoomBar = null;
	protected static VerticalTextView endLabel = null;
	protected static LinearLayout zoomLayout = null;
	protected static AugmentedView augmentedView = null;

	public float MAX_ZOOM = 100; // in KM
	public float ONE_PERCENT = MAX_ZOOM / 100f;
	public float TEN_PERCENT = 10f * ONE_PERCENT;
	public float TWENTY_PERCENT = 2f * TEN_PERCENT;
	public float EIGHTY_PERCENTY = 4f * TWENTY_PERCENT;

	public static boolean portrait = true;
	public static boolean useCollisionDetection = false;
	public static boolean showRadar = true;
	public static boolean showZoomBar = false;

	private static RelativeLayout camscren_ll = null;

	private static int progressSet = (int) (Util.current_zoomlevel * 1000);

	private MyDataHolder mData = null;
	private AugmentedRedarView redarView = null;
	private ImageView arrowView = null;
	private TextView distanceTv = null;
	private Marker marker = null;
	private static float currectAzimuth = 0;
	

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		mData = (MyDataHolder) bundle.getSerializable("poi");
		if (mData == null)
			throw new NullPointerException("Data sent is null...");
		PowerManager pm = (PowerManager) mActivity
				.getSystemService(Context.POWER_SERVICE);
		wakeLock = pm
				.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.d(TAG, "not null");
		marker = new Marker(mData.getLoc_title() != null ? mData.getLoc_title()
				: mData.getName(), mData.getLoc_latitude(),
				mData.getLoc_longitude(), 0, Color.RED);
		marker.calcRelativePosition(ARData.getCurrentLocation());

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
	public void onStart() {
		super.onStart();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResume() {
		super.onResume();

		wakeLock.acquire();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		super.onPause();

		wakeLock.release();
	}

	@Override
	public void onDestroy() {
		if (mBroadcastReceiver != null)
			mActivity.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSensorChanged(SensorEvent evt) {
		super.onSensorChanged(evt);

		if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER
				|| evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			if (augmentedView != null)
				augmentedView.postInvalidate();
		}
		if (evt.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			if (redarView != null) {
				redarView.postInvalidate();
				Logger.d(TAG, "evt.values[0] : " + evt.values[0]);
				Util.currentBearing = evt.values[0];// (float) getAngel();//
				updateArrow(evt.values[0]);
			}
		}
	}

	private synchronized void updateArrow(float currentAzimuth) {
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
		Logger.d(TAG, "currectAzimuth, bearingRelative :" + currectAzimuth
				+ " : " + bearingRelative);
//		Animation an = new RotateAnimation(currectAzimuth, bearingRelative,
//				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//				0.5f);
		Animation an = new RotateAnimation(currectAzimuth, -ARData.getAzimuth(),
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		currectAzimuth = -ARData.getAzimuth();

		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);
		arrowView.startAnimation(an);
		arrowView.invalidate();

	}

	private static float calcZoomLevel() {
		float myZoomLevel = progressSet;// myZoomBar.getProgress();

		float myout = 0;
		myout = (float) (myZoomLevel / 1000);
		// float percent = 0;
		// if (myZoomLevel <= 2500) {
		// percent = myZoomLevel / 2500f;
		// myout = ONE_PERCENT * percent;
		// } else if (myZoomLevel > 2500 && myZoomLevel <= 5000) {
		// percent = (myZoomLevel - 2500f) / 2500f;
		// myout = ONE_PERCENT + (TEN_PERCENT * percent);
		// } else if (myZoomLevel > 5000 && myZoomLevel <= 7500) {
		// percent = (myZoomLevel - 5000f) / 2500f;
		// myout = TEN_PERCENT + (TWENTY_PERCENT * percent);
		// } else {
		// percent = (myZoomLevel - 7500f) / 2500f;
		// myout = TWENTY_PERCENT + (EIGHTY_PERCENTY * percent);
		// }

		return myout;
	}

	/**
	 * Called when the zoom bar has changed.
	 */
	protected void updateDataOnZoom() {
		float zoomLevel = Util.current_zoomlevel; /* calcZoomLevel() */
		;
		ARData.setRadius(zoomLevel);
		ARData.setZoomLevel(FORMAT.format(zoomLevel));
		// ARData.setZoomProgress(myZoomBar.getProgress());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onTouch(View view, MotionEvent me) {
		// See if the motion event is on a Marker
		for (Marker marker : ARData.getMarkers()) {
			if (marker.handleClick(me.getX(), me.getY())) {
				if (me.getAction() == MotionEvent.ACTION_UP)
					markerTouched(marker);
				return true;
			}
		}

		return false;// super.onTouchEvent(me);
	};

	protected void markerTouched(Marker marker) {
		Logger.w(TAG, "markerTouched() not implemented.");
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
