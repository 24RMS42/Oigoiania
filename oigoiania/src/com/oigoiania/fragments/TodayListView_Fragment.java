package com.oigoiania.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.br.oigoiania.Events_SingleDetail;
import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.adapters.TodayInOigoianiaListAdapter;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Preference;
import com.oigoiania.util.Util;

public class TodayListView_Fragment extends Fragment implements
		OnItemClickListener {
	private final String tag = "TodayListView_Fragment";
	private ListView todayinog_lv = null;
	private static List<MyDataHolder> data = new ArrayList<MyDataHolder>();
	private TodayInOigoianiaListAdapter adapter = null;
	private static TodayListView_Fragment mTodayListView = null;

	private FragmentActivity mActivity = null;

	public static TodayListView_Fragment getInstance() {
		return mTodayListView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTodayListView = this;
		mActivity = getActivity();
		Logger.d(tag, "onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.d(tag, "onCreateView()");
		if (inflater == null)
			inflater = (LayoutInflater) getLayoutInflater(savedInstanceState);
		View view = inflater.inflate(R.layout.todaylistview, null);
		todayinog_lv = (ListView) view.findViewById(R.id.listView1);
		todayinog_lv.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Logger.d(tag, "onActivityCreated()");
		adapter = new TodayInOigoianiaListAdapter(getActivity(), data);
		todayinog_lv.setAdapter(adapter);
	}

	public void notifyDataSetChange() {

		try {
			Logger.d(tag, "notifyDataSetChange");
			if (Util.rootJsonObject != null) {
				data.clear();
				data.addAll(Parser.parse(Util.rootJsonObject));
				// adapter = new TodayInOigoianiaListAdapter(getActivity(),
				// data);
				// todayinog_lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			Logger.d(tag, "notifyDataSetChange :data size :" + data.size());
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyDataHolder dataholder = (MyDataHolder) view.getTag();
		Intent intent = new Intent(getActivity(), Events_SingleDetail.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.putExtra("poi", dataholder);
		intent.putExtra("type", "events");
		startActivity(intent);
		mActivity.overridePendingTransition(R.anim.incoming, R.anim.outgoing);
		
		Preference.saveText(mActivity, "localId", dataholder.getLoc_id()); //by matata

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		data.clear();
	}
}
