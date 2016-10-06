package com.br.oigoiania;

import org.json.JSONException;
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
import com.oigoiania.fragments.Subcategory_Item_ListViewFragment;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

public class Subcategory_POIS extends BaseActivity implements Runnable,
		RequestCallBack {

	private static final String TAG = "Subcategory_POIS";
	private Activity mActivity;
	// private ExecutorService executor;
	private RequestListener mRequestListener = null;
	private static Bundle bundle = null;
	private ProgressDialog progress_dialog = null;
	private int catId = 0, subCatId = 0;
	// /**************************BroadCast-Receiver*************************//
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(Util.distanceChanged)) {
				if (!isOnPause) {
					mRequestListener = new RequestListener();
					mRequestListener.requestFOrSubCategoryPOIs(
							Subcategory_POIS.this, catId, subCatId);
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
		getmTabHost().addTab(
				getmTabHost().newTabSpec("0").setIndicator("Hidden"),
				Subcategory_Item_ListViewFragment.class, bundle);
		getmTabHost().addTab(
				getmTabHost().newTabSpec("1").setIndicator("",
						getResources().getDrawable(R.drawable.listicon)),
				Subcategory_Item_ListViewFragment.class, bundle);

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
		String title = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			catId = Integer.parseInt(extras.getString("catId"));
			subCatId = Integer.parseInt(extras.getString("subCatId"));
			title = extras.getString("subtitle");
			// if (savedInstanceState == null) {
			// executor = MyThreadPoolExecutor.getInstance();
			mRequestListener = new RequestListener();
			mRequestListener.requestFOrSubCategoryPOIs(this, catId, subCatId);
			new Thread(mRequestListener).start();
			// executor.submit(mRequestListener);
			// }
			progress_dialog = new ProgressDialog(mActivity);
			progress_dialog.setMessage("Please wait..");
			progress_dialog.setCancelable(true);
			progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress_dialog.show();
		} else
			throw new NullPointerException("Data sent is null...");
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Util.distanceChanged);
		mActivity.registerReceiver(mBroadcastReceiver, mIntentFilter);
		getmTabHost().setOnTabChangedListener(this);
		setHeadingText(title);
	}

	@Override
	protected void onDestroy() {
		if (mBroadcastReceiver != null)
			mActivity.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onTabChanged(String tabId) {

	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Logger.d(TAG, "Today Events are :" + jsonOnject.toString());
		try {
			Util.rootJsonObject_catSubcat = jsonOnject;
			bundle.putString("json", Util.rootJsonObject_catSubcat.toString());
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
		try {
			if (Parser.parse(Util.rootJsonObject_catSubcat).size() == 0) {
				Toast.makeText(mActivity, "No data", Toast.LENGTH_LONG).show();
			}
			if (Subcategory_Item_ListViewFragment.getInstance() != null) {
				Logger.d("imran", "dsfsdfsfdgfdfsg");
				Subcategory_Item_ListViewFragment.getInstance()
						.notifyDataSetChange();
			} else {
				Logger.d(TAG, "nullll");
			}
			if (GeneralGoogleMapviewFragment.getInstance() != null) {
				GeneralGoogleMapviewFragment.getInstance()
						.dataSetNotifyChanged(Util.rootJsonObject.toString());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progress_dialog.dismiss();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Util.rootJsonObject_catSubcat = null;
		bundle = null;
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
