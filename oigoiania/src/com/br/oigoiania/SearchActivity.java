package com.br.oigoiania;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.adapters.ResearchAdapter;
import com.oigoiania.augmentedreality.OrientationManager;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.MyThreadPoolExecutor;
import com.oigoiania.network.RequestListener;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Preference;
import com.oigoiania.util.Util;

public class SearchActivity extends SensorActivity implements OnClickListener,
		RequestCallBack, OnItemClickListener, Runnable,
		TextView.OnEditorActionListener {

	private Activity mActivity = null;
	private EditText edtTxt = null;
	private static final String tag = "SearchActivity";
	private List<MyDataHolder> researchList = new ArrayList<MyDataHolder>();
	private ResearchAdapter mResearchAdapter = null;
	private ListView searchListView = null;
	private float timeElapsed = 0;
	private ProgressDialog progress_dialog = null;
	private boolean isRequestFailed = false;
	private Timer timerOrientation = null;
	private OrientationManager mOrientationManager = new OrientationManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d(tag, " Enter onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_activity);
		mActivity = this;
		edtTxt = (EditText) findViewById(R.id.search_edittext);
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		ImageView upcarate = (ImageView) findViewById(R.id.upcarate);
		TextView heading = (TextView) findViewById(R.id.heading_txt);
		upcarate.setOnClickListener(this);
		img.setOnClickListener(this);
		heading.setOnClickListener(this);
		searchListView = (ListView) findViewById(R.id.searchlistview);
		mResearchAdapter = new ResearchAdapter(mActivity, researchList);
		searchListView.setAdapter(mResearchAdapter);
		searchListView.setOnItemClickListener(this);
		progress_dialog = new ProgressDialog(mActivity);
		progress_dialog.setMessage("Searching...");
		progress_dialog.setCancelable(true);
		progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

//		if (savedInstanceState != null
//				&& savedInstanceState.getBoolean("isDataInlist"))
//			run();
		edtTxt.setOnEditorActionListener(this);
		mOrientationManager.startSensorMonitoring(getApplicationContext());
		timerOrientation = new Timer();
		timerOrientation.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (Util.currentLocation != null && mResearchAdapter != null) {
					int[] out = new int[3];
					mOrientationManager.getOrientation(out);
					Util.currentBearing = out[0];
					runOnUiThread(new Runnable() {
						public void run() {
							mResearchAdapter.notifyDataSetChanged();
						}
					});
				} else {
					Log.d(tag,
							"WARNING: No location found when getting angle...");
				}
			}
		}, 0, 500);
		Logger.d(tag, " Exit onCreate");
	}

	@Override
	protected void onDestroy() {
		timerOrientation.cancel();
		timerOrientation.purge();
		timerOrientation = null;
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.upcarate:
		case R.id.heading_txt:
			startActivity(new Intent(mActivity, Home.class));
			break;
		case R.id.imageView1:
			if (!edtTxt.getText().toString().trim().equalsIgnoreCase("")) {
				progress_dialog.show();
				Logger.d(tag, "search button clicked");
				ExecutorService executor = MyThreadPoolExecutor.getInstance();
				RequestListener mRequestListener = new RequestListener();
				mRequestListener.requestFOrResearch(this, edtTxt.getText()
						.toString().trim().replace(" ", "+"));
				// new Thread(mRequestListener).start();
				executor.submit(mRequestListener);
			}
			// executor.shutdown();
			break;
		}
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		isRequestFailed = false;
		Logger.d(tag,
				"Enter onSeuucess and response is :" + jsonOnject.toString());
		researchList.clear();
		try {
			researchList.addAll(Parser.parse(jsonOnject));
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
		Logger.d(tag, "List Size is :" + researchList.size());
		runOnUiThread(this);
	}

	@Override
	public void onRequestFail(String message) {
		isRequestFailed = true;
		runOnUiThread(this);
		Logger.d(tag, "RequestFail and Message is :" + message);
	}

	@Override
	protected void onSensorChange(SensorEvent event) {

		if (mResearchAdapter != null) {
			float deff = event.values[0] > timeElapsed ? (event.values[0] - timeElapsed)
					: (timeElapsed - event.values[0]);
			if (timeElapsed == 0 || deff > 7) {
				timeElapsed = event.values[0];
				mResearchAdapter.notifyDataSetChanged();
			}
		}
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int posotion, long longValue) {
		Logger.d(tag, "list item clicked....");
		MyDataHolder data = (MyDataHolder) view.getTag();
		Intent intent = null;
		if (data.getType().equalsIgnoreCase("events")) {
			intent = new Intent(SearchActivity.this, Events_SingleDetail.class);
			intent.putExtra("type", "sobre");
		} else {

			intent = new Intent(SearchActivity.this, SpecificPOI_Activity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			if (data.getType().equalsIgnoreCase("promotions")) {
				intent.putExtra("type", "promotions");
			} else {
				intent.putExtra("type", "sobre");
			}
		}
		intent.putExtra("poi", data);

		startActivity(intent);
		overridePendingTransition(R.anim.incoming, R.anim.outgoing);
		Logger.d(tag, "list item clicked....");
		
		Preference.saveText(mActivity, "localId", data.getLoc_id()); //by matata
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("isDataInlist", researchList.size() > 0);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}

	@Override
	public void run() {
		progress_dialog.dismiss();
		if (isRequestFailed) {
			Toast.makeText(mActivity, "Request Failed. Try again",
					Toast.LENGTH_SHORT).show();
		} else {

			Logger.d(tag, "run....");
			if (researchList != null && researchList.size() > 0) {
				mResearchAdapter = new ResearchAdapter(mActivity, researchList);
				searchListView.setAdapter(mResearchAdapter);
				Logger.d(tag, "serch List Size is : " + researchList.size());
				// mResearchAdapter.notifyDataSetChanged();
			} else {
				Toast.makeText(
						mActivity,
						"No data found against \" "
								+ edtTxt.getText().toString().trim()
								+ " \".Try another word.", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH
				&& !edtTxt.getText().toString().trim().equals("")) {
			Logger.d(tag, "search Key is clicked");
			progress_dialog.show();
			ExecutorService executor = MyThreadPoolExecutor.getInstance();
			RequestListener mRequestListener = new RequestListener();
			mRequestListener.requestFOrResearch(this, edtTxt.getText()
					.toString().trim().replace(" ", "+"));
			// new Thread(mRequestListener).start();
			executor.submit(mRequestListener);
			// return true;
		}
		return false;
	}

	@Override
	public void onJSONException(String messaget) {
		// TODO Auto-generated method stub

	}
}
