package com.br.oigoiania;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.fragments.GeneralARFragment;
import com.oigoiania.fragments.GeneralGoogleMapviewFragment;
import com.oigoiania.fragments.TodayListView_Fragment;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.util.Util;

public class TodayActivity extends BaseActivity implements RequestCallBack,
		Runnable {
	private static final String tag = "TodayActivity";
	// private ExecutorService executor;
	private RequestListener mRequestListener = null;
	private FragmentActivity mActivity = null;
	private ProgressDialog progress_dialog = null;
	private Bundle bundle = null;
	// /**************************BroadCast-Receiver*************************//
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(Util.distanceChanged)) {
				if (!isOnPause) {
					mRequestListener = new RequestListener();
					mRequestListener.requestFOrTodayEvents(TodayActivity.this,
							null);
					Thread t = new Thread(mRequestListener);
					t.setPriority(Thread.MAX_PRIORITY);
					t.start();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		bundle = new Bundle();
		bundle.putString("type", "events");
		Logger.d(tag, "Enter onCreate()");
		getmTabHost().addTab(
				getmTabHost().newTabSpec("0").setIndicator("ListView"),
				TodayListView_Fragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("1").setIndicator("",
						getResources().getDrawable(R.drawable.listicon)),
				TodayListView_Fragment.class, bundle);

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
		progress_dialog = new ProgressDialog(mActivity);
		progress_dialog.setMessage("Please wait..");
		progress_dialog.setCancelable(true);
		progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		if (savedInstanceState == null) {
			// executor = MyThreadPoolExecutor.getInstance();
			mRequestListener = new RequestListener();
			mRequestListener.requestFOrTodayEvents(this, null);
			Thread t = new Thread(mRequestListener);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
			// executor.submit(mRequestListener);
			progress_dialog.show();
		}
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);
		setHeadingText(getResources().getString(R.string.todayevents));
		Logger.d(tag, "Exit onCreate()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		isOnPause = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isOnPause = true;
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Logger.d(tag,
				"onRequestSuccess and Response is :" + jsonOnject.toString());
		try {
			Util.nmberOfTries = 0;
			Util.rootJsonObject = jsonOnject;
			bundle.putString("json", jsonOnject.toString());
			mActivity.runOnUiThread(this);
			// MyThreadPoolExecutor.shutDown();
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
				if (messaget.equalsIgnoreCase(Util.exitCode)) {
					progress_dialog.dismiss();
					toast.setText("Data is not available due to Internet issue.");
					toast.show();
				} else if (messaget.equalsIgnoreCase(Util.noInterNetCode)) {
					toast.setDuration(2000);
					toast.setText("Internet is not available.");
					toast.show();
					progress_dialog.dismiss();
				} else {
					toast.setDuration(2000);
					toast.show();
				}
			}
		});

	}

	@Override
	public void run() {
		Logger.d(tag, "run()");
		if (TodayListView_Fragment.getInstance() != null) {
			TodayListView_Fragment.getInstance().notifyDataSetChange();

		} else {
			Logger.d(tag, "TodayListView.getInstance() is null");
		}
		if (GeneralGoogleMapviewFragment.getInstance() != null) {
			GeneralGoogleMapviewFragment.getInstance().dataSetNotifyChanged(
					Util.rootJsonObject.toString());
		}
		if (!mActivity.isFinishing())
			progress_dialog.dismiss();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (Util.rootJsonObject != null)
			outState.putString("json", Util.rootJsonObject.toString());
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onTabChanged(String tabId) {
		Logger.d(tag, "Tab ID : " + tabId);
	}

	@Override
	public void onJSONException(String messaget) {
		if (Util.nmberOfTries < 1) {
			Util.nmberOfTries++;
			if (mRequestListener != null)
				new Thread(mRequestListener).start();
			// executor.submit(mRequestListener);
		} else
			Util.nmberOfTries = 0;

	}

	@Override
	protected void onSensorChange(SensorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		if (mBroadcastReceiver != null)
			mActivity.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}
}
