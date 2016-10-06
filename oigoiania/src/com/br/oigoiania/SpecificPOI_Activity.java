package com.br.oigoiania;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.fragments.PoiAR_Fragment;
import com.oigoiania.fragments.SpecificComment_Fragment;
import com.oigoiania.fragments.SpecificPOI_ImagesFragment;
import com.oigoiania.fragments.SpecificPOI_Info_Fragment;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

public class SpecificPOI_Activity extends BaseActivity implements
		RequestCallBack, Runnable {
	private static final String tag = "SpecificPOI_Activity";
	private Bundle bundle = null;
	private RequestListener mRequestListener = null;
	private HashMap<String, List<MyDataHolder>> mHashMap = new HashMap<String, List<MyDataHolder>>();
	private ProgressDialog progress_dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d(tag, "onCreate");
		bundle = getIntent().getExtras();
		if (bundle == null)
			throw new NullPointerException("Bundle sent is null...");
		getmTabHost().addTab(getmTabHost().newTabSpec("0").setIndicator("",getResources().getDrawable(R.drawable.home)),SpecificPOI_Info_Fragment.class, bundle);
		getmTabHost().addTab(getmTabHost().newTabSpec("1").setIndicator("",getResources().getDrawable(R.drawable.listicon)),SpecificPOI_ImagesFragment.class, bundle);
		getmTabHost().addTab(getmTabHost().newTabSpec("2").setIndicator("",getResources().getDrawable(R.drawable.location_map)),null, bundle);
		getmTabHost().addTab(getmTabHost().newTabSpec("3").setIndicator("",getResources().getDrawable(R.drawable.star_icon)),SpecificComment_Fragment.class, bundle);
		setTabsColor(Color.BLACK);
		if (bundle != null)
			setHeadingText(((MyDataHolder) bundle.getSerializable("poi"))
					.getLoc_title());
		
		getmTabHost().getTabWidget().getChildAt(0)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						getmTabHost().setCurrentTab(0);
						SpecificPOI_Info_Fragment.getInstance().tab_click();

					}
				});
		getmTabHost().getTabWidget().getChildAt(3)
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				getmTabHost().setCurrentTab(3);
				//SpecificComment_Fragment.getInstance().tab_click();
			}
		});
		
		getmTabHost().getTabWidget().getChildAt(2)
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
						
				 MyDataHolder dataholder = (MyDataHolder) bundle.getSerializable("poi");
				 double des_lat = dataholder.getLoc_latitude();
				 double des_lng = dataholder.getLoc_longitude();
				 String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr="+des_lat+","+des_lng+" (%s)",dataholder.getLoc_title() != null ? dataholder.getLoc_title() : dataholder.getName());
			        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			        try
			        {
			            startActivity(intent);
			        }
			        catch(ActivityNotFoundException ex)
			        {
			            try
			            {
			                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			                startActivity(unrestrictedIntent);
			            }
			            catch(ActivityNotFoundException innerEx)
			            {
			                Toast.makeText(SpecificPOI_Activity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
			            }
			        }

			}
		});
		getmTabHost().getTabWidget().getChildAt(1)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						getmTabHost().setCurrentTab(1);
						if (SpecificPOI_ImagesFragment.getInstance() != null)
							SpecificPOI_ImagesFragment.getInstance()
									.Tab_click();

					}
				});
		String loc_id = ((MyDataHolder) bundle.getSerializable("poi"))
				.getLoc_id();
		if (mHashMap.size() == 0 && loc_id != null) {
			mRequestListener = new RequestListener();
			mRequestListener.requestFOrPOI_Info(this, loc_id);
			new Thread(mRequestListener).start();
			progress_dialog = new ProgressDialog(mActivity);
			progress_dialog.setMessage("Please wait..");
			progress_dialog.setCancelable(true);
			progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress_dialog.show();
		}
	}

	@Override
	public void onTabChanged(final String tabId) {
		if (tabId.equalsIgnoreCase("1")) {
			setHeadingText("Fotos");
		} else {
			if (bundle != null)
				setHeadingText(((MyDataHolder) bundle.getSerializable("poi"))
						.getLoc_title());
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		SpecificPOI_ImagesFragment.isDialogOnceShown = false;
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}

	@Override
	protected void onSensorChange(SensorEvent event) {

	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Logger.d(tag,
				"onRequestSuccess and response is :" + jsonOnject.toString());
		try {
			Util.nmberOfTries = 0;
			JSONObject promotions = (JSONObject) jsonOnject
					.getJSONObject("promotions");
			JSONObject events = (JSONObject) jsonOnject.getJSONObject("events");
			JSONObject comments = (JSONObject) jsonOnject
					.getJSONObject("comments");
			List<MyDataHolder> promotionsList = Parser.parse2(promotions,true);
			List<MyDataHolder> eventsList = Parser.parse2(events,false);
			List<MyDataHolder> commentsList = Parser.parse2(comments,true);
			mHashMap.put("promotions", promotionsList);
			mHashMap.put("events", eventsList);
			mHashMap.put("comments", commentsList);
			mActivity.runOnUiThread(this);

		} catch (Exception e) {
			Crittercism.logHandledException(e);
			Logger.e(tag, "Exception occure :" + e.getMessage());
		}
	}

	@Override
	public void onRequestFail(final String messaget) {
		Logger.d(tag, " Request Failed :" + messaget);
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
	public void onJSONException(String message) {
		if (Util.nmberOfTries < 1) {
			Util.nmberOfTries++;
			if (mRequestListener != null)
				new Thread(mRequestListener).start();
		} else
			Util.nmberOfTries = 0;
	}

	@Override
	public void run() {
		if (mHashMap.size() > 0)
			SpecificPOI_Info_Fragment.getInstance().setHashMap(mHashMap);
		if (!mActivity.isFinishing())
			progress_dialog.dismiss();
		SpecificPOI_Info_Fragment.getInstance().notifyDataSetChange();

	}

	@Override
	protected void onDestroy() {
		SpecificPOI_ImagesFragment.isDialogOnceShown = false;
		bundle = null;
		mHashMap.clear();
		super.onDestroy();
	}

}
