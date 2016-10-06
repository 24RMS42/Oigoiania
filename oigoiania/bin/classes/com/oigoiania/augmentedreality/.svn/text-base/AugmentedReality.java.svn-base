package com.oigoiania.augmentedreality;

import java.text.DecimalFormat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.br.oigoiania.R;
import com.jwetherell.augmented_reality.camera.CameraSurface;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.widget.VerticalSeekBar;
import com.jwetherell.augmented_reality.widget.VerticalTextView;
import com.oigoiania.logger.Logger;
import com.oigoiania.util.Util;

/**
 * This class extends the SensorsActivity and is designed tie the AugmentedView
 * and zoom bar together.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class AugmentedReality extends NewSensorsFragment implements
		OnTouchListener {

	private static final String TAG = "AugmentedReality";
	public static final DecimalFormat FORMAT = new DecimalFormat("#.##");
	private static final int ZOOMBAR_BACKGROUND_COLOR = Color.argb(125, 55, 55,
			55);
	//private String END_TEXT = FORMAT.format(MAX_ZOOM) + " km";
	//private static final int END_TEXT_COLOR = Color.WHITE;

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

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		if (inflater == null)
			inflater = (LayoutInflater) mActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.general_ar_activity, null);
		camscren_ll = (RelativeLayout) view.findViewById(R.id.camscreen);
		RelativeLayout arview = (RelativeLayout) view.findViewById(R.id.arview);
		camScreen = new CameraSurface(mActivity);
		camscren_ll.addView(camScreen);
		augmentedView = new AugmentedView(mActivity);
		augmentedView.setOnTouchListener(AugmentedReality.this);
		WindowManager wm = (WindowManager) mActivity
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth(); // deprecated
//		@SuppressWarnings("deprecation")
//		int height=display.getHeight();
		RelativeLayout.LayoutParams augLayout = new RelativeLayout.LayoutParams(
				width,width + 20);
		augmentedView.setLayoutParams(augLayout);
		arview.addView(augmentedView);

		// zoomLayout = new LinearLayout(mActivity);
		// zoomLayout.setVisibility((showZoomBar) ? LinearLayout.VISIBLE
		// : LinearLayout.GONE);
		// zoomLayout.setOrientation(LinearLayout.VERTICAL);
		// zoomLayout.setPadding(5, 5, 5, 5);
		// zoomLayout.setBackgroundColor(ZOOMBAR_BACKGROUND_COLOR);
		//
		// endLabel = new VerticalTextView(mActivity);
		// endLabel.setText(END_TEXT);
		// endLabel.setTextColor(END_TEXT_COLOR);
		// LinearLayout.LayoutParams zoomTextParams = new
		// LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// zoomTextParams.gravity = Gravity.CENTER;
		// zoomLayout.addView(endLabel, zoomTextParams);

		// myZoomBar = new VerticalSeekBar(mActivity);
		// myZoomBar.setMax(100);
		// myZoomBar.setProgress(100);
		// myZoomBar.setOnSeekBarChangeListener(myZoomBarOnSeekBarChangeListener);

		// @SuppressWarnings("deprecation")
		// LinearLayout.LayoutParams zoomBarParams = new
		// LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		// zoomBarParams.gravity = Gravity.CENTER_HORIZONTAL;
		// zoomLayout.addView(myZoomBar, zoomBarParams);

		updateDataOnZoom();
		view.invalidate();
		return view;// super.onCreateView(inflater, container,
					// savedInstanceState);
	}

	// @Override
	// protected void setSelectedContentView() {
	// setContentView(R.layout.general_ar_activity);
	// camscren_ll = (RelativeLayout) findViewById(R.id.camscreen);
	// RelativeLayout arview = (RelativeLayout) findViewById(R.id.arview);
	// camScreen = new CameraSurface(AugmentedReality.this);
	// camscren_ll.addView(camScreen);
	// // setContentView(camScreen);
	//
	// augmentedView = new AugmentedView(AugmentedReality.this);
	// augmentedView.setOnTouchListener(AugmentedReality.this);
	// arview.addView(augmentedView);
	// // LayoutParams augLayout = new LayoutParams(
	// // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	// // addContentView(augmentedView, augLayout);
	//
	// zoomLayout = new LinearLayout(AugmentedReality.this);
	// zoomLayout.setVisibility((showZoomBar) ? LinearLayout.VISIBLE
	// : LinearLayout.GONE);
	// zoomLayout.setOrientation(LinearLayout.VERTICAL);
	// zoomLayout.setPadding(5, 5, 5, 5);
	// zoomLayout.setBackgroundColor(ZOOMBAR_BACKGROUND_COLOR);
	//
	// endLabel = new VerticalTextView(AugmentedReality.this);
	// endLabel.setText(END_TEXT);
	// endLabel.setTextColor(END_TEXT_COLOR);
	// LinearLayout.LayoutParams zoomTextParams = new LinearLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	// zoomTextParams.gravity = Gravity.CENTER;
	// zoomLayout.addView(endLabel, zoomTextParams);
	//
	// myZoomBar = new VerticalSeekBar(AugmentedReality.this);
	// myZoomBar.setMax(100);
	// myZoomBar.setProgress(100);
	// myZoomBar.setOnSeekBarChangeListener(myZoomBarOnSeekBarChangeListener);
	// @SuppressWarnings("deprecation")
	// LinearLayout.LayoutParams zoomBarParams = new LinearLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
	// zoomBarParams.gravity = Gravity.CENTER_HORIZONTAL;
	// zoomLayout.addView(myZoomBar, zoomBarParams);
	//
	// @SuppressWarnings("deprecation")
	// FrameLayout.LayoutParams frameLayoutParams = new
	// FrameLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT,
	// Gravity.RIGHT);
	// addContentView(zoomLayout, frameLayoutParams);
	//
	// updateDataOnZoom();
	// }

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
	}

	// private OnSeekBarChangeListener myZoomBarOnSeekBarChangeListener = new
	// OnSeekBarChangeListener() {
	//
	// public void onProgressChanged(SeekBar seekBar, int progress,
	// boolean fromUser) {
	// updateDataOnZoom();
	// camScreen.invalidate();
	// camscren_ll.invalidate();
	//
	// }
	//
	// public void onStartTrackingTouch(SeekBar seekBar) {
	// // Ignore
	// }
	//
	// public void onStopTrackingTouch(SeekBar seekBar) {
	// updateDataOnZoom();
	// camScreen.invalidate();
	// camscren_ll.invalidate();
	// }
	// };

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
		float zoomLevel =  Util.current_zoomlevel; /*calcZoomLevel()*/;
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
		augmentedView.invalidate();
		camscren_ll.invalidate();
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
