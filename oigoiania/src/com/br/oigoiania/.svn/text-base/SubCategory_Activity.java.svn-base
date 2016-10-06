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
import com.oigoiania.fragments.GeneralARFragment;
import com.oigoiania.fragments.GeneralGoogleMapviewFragment;
import com.oigoiania.fragments.SubcategoryListView_Fragment;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.util.Util;

public class SubCategory_Activity extends BaseActivity implements
		RequestCallBack, Runnable {
	private static final String TAG = "SubCategory_Activity";
	//private Activity mActivity;
	// private ExecutorService executor;
	private RequestListener mRequestListener = null;
	private Bundle bundle = null;
	private String headingTitle = "";
	private ProgressDialog progress_dialog = null;
	private int catId = 0;
	// /**************************BroadCast-Receiver*************************//
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(Util.distanceChanged)) {
				if (!isOnPause) {
					mRequestListener = new RequestListener();
					mRequestListener.requestFOrSubCategory(
							SubCategory_Activity.this,
							Util.currentLocation.getLatitude(),
							Util.currentLocation.getLongitude(), Util.radius,
							catId);
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
		bundle = new Bundle();
		getmTabHost().addTab(
				getmTabHost().newTabSpec("0").setIndicator("Hidden"),
				SubcategoryListView_Fragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("1").setIndicator("",
						getResources().getDrawable(R.drawable.listicon)),
				SubcategoryListView_Fragment.class, bundle);

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
		String str = getIntent().getExtras().getString("catid");
		headingTitle = getIntent().getExtras().getString("title");
		if (str != null)
			catId = Integer.parseInt(str);
		// if (savedInstanceState == null) {
		// executor = MyThreadPoolExecutor.getInstance();
		mRequestListener = new RequestListener();
		mRequestListener.requestFOrSubCategory(this,
				Util.currentLocation.getLatitude(),
				Util.currentLocation.getLongitude(), (int) Util.radius, catId);
		new Thread(mRequestListener).start();
		// executor.submit(mRequestListener);
		// }
		progress_dialog = new ProgressDialog(mActivity);
		progress_dialog.setMessage("Please wait..");
		progress_dialog.setCancelable(true);
		progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress_dialog.show();
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);
		getmTabHost().setOnTabChangedListener(this);
		if (headingTitle != null)
			setHeadingText(headingTitle);
		Logger.d(TAG, "onCreate()");
	}

	@Override
	protected void onStart() {
		Logger.d(TAG, "onStart()");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Logger.d(TAG, "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onStop() {
		Logger.d(TAG, "onStop()");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Logger.d(TAG, "onDestroy()");
		if (mBroadcastReceiver != null)
			mActivity.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Logger.d(TAG, "Today Events are :" + jsonOnject.toString());
		try {
			Util.rootJsonObject = jsonOnject.getJSONObject("data");
			Util.rootJsonObject_catSubcat = jsonOnject
					.getJSONObject("subcategories");
			bundle.putString("json", Util.rootJsonObject.toString());
			mActivity.runOnUiThread(this);
		} catch (Exception e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
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
		if (SubcategoryListView_Fragment.getInstance() != null) {
			Logger.d("imran", "dsfsdfsfdgfdfsg");
			SubcategoryListView_Fragment.getInstance().notifyDataSetChange();
		} else {
			Logger.d(TAG, "nullll");
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
		// TODO Auto-generated method stub

	}
}
