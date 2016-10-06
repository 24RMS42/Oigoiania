package com.oigoiania.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.br.oigoiania.R;
import com.br.oigoiania.SpecificPOI_Activity;
import com.crittercism.app.Crittercism;
import com.oigoiania.adapters.SubCategoryPOIs_Adapter;
import com.oigoiania.augmentedreality.OrientationManager;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Preference;
import com.oigoiania.util.Util;

public class Subcategory_Item_ListViewFragment extends SensorForArrowFragment
		implements OnItemClickListener {
	private ListView subCat_Items_lv = null;
	private static List<MyDataHolder> data = new ArrayList<MyDataHolder>();
	private SubCategoryPOIs_Adapter adapter = null;
	private static final String tag = "Subcategory_Item_ListViewFragment";
	private static Subcategory_Item_ListViewFragment mSubCat_itemListView = null;
	private OrientationManager mOrientationManager = new OrientationManager();
	private Timer timerOrientation = null;

	public static Subcategory_Item_ListViewFragment getInstance() {
		return mSubCat_itemListView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSubCat_itemListView = this;
		Logger.d(tag, "onCreate()");
		mOrientationManager.startSensorMonitoring(mActivity);
		timerOrientation = new Timer();
		timerOrientation.schedule(new TimerTask() {

			@Override
			public void run() {
				if (Util.currentLocation != null && adapter != null) {
					int[] out = new int[3];
					mOrientationManager.getOrientation(out);
					Util.currentBearing = out[0];
					mActivity.runOnUiThread(new Runnable() {
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
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
		Logger.d(tag, "onCreateView()");
		if (inflater == null)
			inflater = (LayoutInflater) getLayoutInflater(savedInstanceState);
		View view = inflater.inflate(R.layout.subcat_itemlistview_fragment,
				null);
		subCat_Items_lv = (ListView) view.findViewById(R.id.listView1);
		subCat_Items_lv.setOnItemClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new SubCategoryPOIs_Adapter(getActivity(), data);
		Logger.d(tag, "Data on CreateView" + data.toString());
		subCat_Items_lv.setAdapter(adapter);
	}

	public void notifyDataSetChange() {

		try {
			Logger.d(tag, "notifyDataSetChange");
			if (Util.rootJsonObject_catSubcat != null) {
				data.clear();
				// data.addAll(Parser
				// .parseSubCatergoryPOIsResponse(Util.rootJsonObject_catSubcat));
				data.addAll(Parser.parse(Util.rootJsonObject_catSubcat));
				adapter = new SubCategoryPOIs_Adapter(getActivity(), data);
				subCat_Items_lv.setAdapter(adapter);
			}
			Logger.d(tag, "data size :" + Util.rootJsonObject.toString());
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	@Override
	protected void onSensorChange(SensorEvent event) {
		// if (adapter != null) {
		// float deff = event.values[0] > timeElapsed ? (event.values[0] -
		// timeElapsed)
		// : (timeElapsed - event.values[0]);
		// if (timeElapsed == 0 || deff > 7) {
		// timeElapsed = event.values[0];
		// adapter.notifyDataSetChanged();
		// }
		// }
	}

	@Override
	protected void onLocationChange(Location location) {
		super.onLocationChange(location);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		MyDataHolder data = (MyDataHolder) view.getTag();
		Intent intent = new Intent(mActivity, SpecificPOI_Activity.class);
		intent.putExtra("poi", data);
		intent.putExtra("type", "sobre");
		startActivity(intent);
		mActivity.overridePendingTransition(R.anim.incoming, R.anim.outgoing);
		
		Preference.saveText(mActivity, "localId", data.getLoc_id()); //by matata

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		data.clear();
	}

}
