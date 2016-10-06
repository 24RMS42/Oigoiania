package com.br.oigoiania;

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
import android.widget.Toast;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.fragments.CategoryListViewFragment;
import com.oigoiania.fragments.GeneralARFragment;
import com.oigoiania.fragments.GeneralGoogleMapviewFragment;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.util.Util;

public class CategoryActivity extends BaseActivity implements RequestCallBack,
		Runnable {
	private static final String TAG = "CategoryActivity";
	//private Activity mActivity;
	// private ExecutorService executor;
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
					mRequestListener.requestFOrCategory(CategoryActivity.this,
							Util.currentLocation.getLatitude(),
							Util.currentLocation.getLongitude(), Util.radius);
					Thread t = new Thread(mRequestListener);
					t.setPriority(Thread.MAX_PRIORITY);
					// t.start();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d(TAG, "Enter onCreate");
		mActivity = this;
		bundle = new Bundle();
		getmTabHost().addTab(
				getmTabHost().newTabSpec("0").setIndicator("Hidden"),
				CategoryListViewFragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("1").setIndicator("",
						getResources().getDrawable(R.drawable.listicon)),
				CategoryListViewFragment.class, bundle);

		getmTabHost().addTab(
				getmTabHost().newTabSpec("2").setIndicator("",
						getResources().getDrawable(R.drawable.location_map)),
				GeneralGoogleMapviewFragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("3").setIndicator("",
						getResources().getDrawable(R.drawable.ar)),
				GeneralARFragment.class, bundle);
		setTabEnabledAt(0, false);
		setTabEnabledAt(2, false);
		setTabEnabledAt(3, false);
		setTabsColor(Color.BLACK);
		getmTabHost().getTabWidget().getChildAt(2)
				.setBackgroundColor(Color.rgb(50, 50, 50));
		getmTabHost().getTabWidget().getChildAt(3)
				.setBackgroundColor(Color.rgb(50, 50, 50));
		if (savedInstanceState == null) {
			// executor = MyThreadPoolExecutor.getInstance();
			mRequestListener = new RequestListener();
			mRequestListener.requestFOrCategory(this,
					Util.currentLocation.getLatitude(),
					Util.currentLocation.getLongitude(), (int) Util.radius);
			new Thread(mRequestListener).start();
			// executor.submit(mRequestListener);
			progress_dialog = new ProgressDialog(mActivity);
			progress_dialog.setMessage("Please wait..");
			progress_dialog.setCancelable(true);
			progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress_dialog.show();
		}
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);
		getmTabHost().setOnTabChangedListener(this);
		setHeadingText(getResources().getString(R.string.categories));
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Logger.d(TAG,
				"onRequestSuccess and Response is :" + jsonOnject.toString());
		Util.nmberOfTries = 0;
		try {

			Util.rootJsonObject = jsonOnject.getJSONObject("data");
			Util.rootJsonObject_catSubcat = jsonOnject
					.getJSONObject("categories");
			bundle.putString("json", Util.rootJsonObject.toString());

			Logger.d(TAG, "rootJsonObject : " + Util.rootJsonObject.toString());
			Logger.d(TAG, "rootJsonObject_catSubcat : "
					+ Util.rootJsonObject_catSubcat.toString());
			mActivity.runOnUiThread(this);
		} catch (Exception e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Logger.d(TAG, "onResume()");
	}

	@Override
	protected void onDestroy() {
		if (mBroadcastReceiver != null)
			mActivity.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onRequestFail(final String messaget) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(mActivity, messaget,
						Toast.LENGTH_SHORT);
				toast.setDuration(2000);
				if (messaget.equalsIgnoreCase(Util.exitCode)) {
					progress_dialog.dismiss();
					toast.setDuration(3000);
					toast.setText("Data is not available due to Internet issue.");
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
	public void run() {
		progress_dialog.dismiss();
		if (CategoryListViewFragment.getInstance() != null) {
			Logger.d(TAG, "Data is not Null");
			CategoryListViewFragment.getInstance().notifyDataSetChange();
		} else {
			Logger.d(TAG, "CategoryListViewFragment.getInstance() is NULL");
		}
		if (GeneralGoogleMapviewFragment.getInstance() != null) {
			GeneralGoogleMapviewFragment.getInstance().dataSetNotifyChanged(
					Util.rootJsonObject.toString());
		}
	}

	@Override
	public void onTabChanged(String tabId) {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}

	@Override
	public void onJSONException(String messaget) {
		if (Util.nmberOfTries < 1) {
			Util.nmberOfTries++;
			if (mRequestListener != null)
				new Thread(mRequestListener).start();
		} else
			Util.nmberOfTries = 0;
		Logger.d(TAG, "onRequestFail " + messaget);

	}

	@Override
	protected void onSensorChange(SensorEvent event) {

	}

}
