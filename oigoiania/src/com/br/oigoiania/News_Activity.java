package com.br.oigoiania;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.augmentedreality.OrientationManager;
import com.oigoiania.fragments.GeneralARFragment;
import com.oigoiania.fragments.GeneralGoogleMapviewFragment;
import com.oigoiania.fragments.News_ListviewFragment;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.util.Util;

public class News_Activity extends BaseActivity implements RequestCallBack,
		Runnable {
	private static final String tag = "News_Activity";
	private Activity mActivity;
	private RequestListener mRequestListener = null;
	private Bundle bundle = null;
	private ProgressDialog progress_dialog = null;
	// /**************************BroadCast-Receiver*************************//
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(Util.distanceChanged)) {
				if (!isOnPause) {
					mRequestListener = new RequestListener();
					mRequestListener.requestFOrNews(News_Activity.this, null);
					new Thread(mRequestListener).start();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		Logger.d(tag, "Enter onCreate()");
		bundle = new Bundle();
		getmTabHost().addTab(
				getmTabHost().newTabSpec("0").setIndicator("Hidden"),
				News_ListviewFragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("1").setIndicator("",
						getResources().getDrawable(R.drawable.listicon)),
				News_ListviewFragment.class, bundle);

		getmTabHost().addTab(
				getmTabHost().newTabSpec("2").setIndicator("",
						getResources().getDrawable(R.drawable.location_map)),
				GeneralGoogleMapviewFragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("3").setIndicator("",
						getResources().getDrawable(R.drawable.ar)),
				GeneralARFragment.class, bundle);
		setTabEnabledAt(0, false);
		setTabsColor(Color.BLACK);
		getmTabHost().setOnTabChangedListener(this);
		if (savedInstanceState == null) {
			// executor = MyThreadPoolExecutor.getInstance();
			mRequestListener = new RequestListener();
			mRequestListener.requestFOrNews(this, null);
			new Thread(mRequestListener).start();
			// executor.submit(mRequestListener);
			progress_dialog = new ProgressDialog(mActivity);
			progress_dialog.setMessage("Please wait..");
			progress_dialog.setCancelable(true);
			progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress_dialog.show();
		}
		setHeadingText(getResources().getString(R.string.news));
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);

	}

	@Override
	protected void onDestroy() {
		try {
			if (mBroadcastReceiver != null)
				mActivity.unregisterReceiver(mBroadcastReceiver);
		} catch (IllegalArgumentException e) {
			Crittercism.logHandledException(e);
		}
		super.onDestroy();
	}

	@Override
	public void onTabChanged(String tabId) {

	}

	@Override
	public void run() {
		progress_dialog.dismiss();
		if (News_ListviewFragment.getInstance() != null) {
			Logger.d(tag, "Data is not Null");
			News_ListviewFragment.getInstance().notifyDataSetChange();
		} else {
			Logger.d(tag, "nullll");
		}
		if (GeneralGoogleMapviewFragment.getInstance() != null) {
			GeneralGoogleMapviewFragment.getInstance().dataSetNotifyChanged(
					Util.rootJsonObject.toString());
		}
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Util.nmberOfTries = 0;
		Logger.d(tag, "Today Enteries are :" + jsonOnject.toString());
		try {
			Util.rootJsonObject = jsonOnject;
			bundle.putString("json", jsonOnject.toString());
			mActivity.runOnUiThread(this);
		} catch (Exception e) {
			Crittercism.logHandledException(e);
			Logger.d(tag, "Exception occure :" + e.getMessage());
		}

	}

	@Override
	public void onRequestFail(final String messaget) {
		Logger.d(tag, "onRequestFail " + messaget);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(mActivity, messaget,
						Toast.LENGTH_SHORT);
				toast.setDuration(2000);
				if (messaget.equalsIgnoreCase(Util.exitCode)) {
					progress_dialog.dismiss();
				} else if (messaget.equalsIgnoreCase(Util.noInterNetCode)) {
					toast.setText("Internet is not available.");
					toast.show();
					progress_dialog.dismiss();
				} else {
					toast.show();
				}
			}
		});
	}

	@Override
	public void onJSONException(String messaget) {
		if (Util.nmberOfTries < 1) {
			Util.nmberOfTries++;
			if (mRequestListener != null)
				new Thread(mRequestListener).start();
		} else
			Util.nmberOfTries = 0;

	}

	@Override
	protected void onSensorChange(SensorEvent event) {
		// TODO Auto-generated method stub

	}

}
