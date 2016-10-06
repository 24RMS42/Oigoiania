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

import com.br.oigoiania.R;
import com.br.oigoiania.SpecificPOI_Activity;
import com.crittercism.app.Crittercism;
import com.oigoiania.adapters.Promotion_Adapter;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

public class Promotion_ListViewFragment extends Fragment implements
		OnItemClickListener {
	private final String tag = "Promotion_ListViewFragment";
	private FragmentActivity mActvity;
	private ListView promotions_lv = null;
	private static List<MyDataHolder> data = new ArrayList<MyDataHolder>();
	private Promotion_Adapter adapter = null;
	private static Promotion_ListViewFragment mCategoryListView = null;

	public static Promotion_ListViewFragment getInstance() {
		return mCategoryListView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCategoryListView = this;
		mActvity = getActivity();
		Logger.d(tag, "onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.d(tag, "onCreateView()");
		if (inflater == null)
			inflater = (LayoutInflater) getLayoutInflater(savedInstanceState);
		View view = inflater.inflate(R.layout.promotion_listview, null);
		promotions_lv = (ListView) view.findViewById(R.id.listView1);
		promotions_lv.setOnItemClickListener(this);
		Logger.d(tag, "Data on CreateView" + data.toString());
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new Promotion_Adapter(getActivity(), data);
		promotions_lv.setAdapter(adapter);
	}

	public void notifyDataSetChange() {

		try {
			Logger.d(tag, "notifyDataSetChange");
			if (Util.rootJsonObject != null) {
				data.clear();

				// data.addAll(Parser
				// .parsePromotion_DataResponse(Util.rootJsonObject));
				data.addAll(Parser.parse(Util.rootJsonObject));
				// adapter = new Promotion_Adapter(getActivity(), data);
				// todayinog_lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			Logger.d(tag, "data size :" + Util.rootJsonObject.toString());
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyDataHolder dathodler = (MyDataHolder) view.getTag();
		Intent intent = new Intent(getActivity(), SpecificPOI_Activity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("poi", dathodler);
		bundle.putString("type", "promotions");
		intent.putExtras(bundle);
		startActivity(intent);
		mActvity.overridePendingTransition(R.anim.incoming, R.anim.outgoing);
	}
@Override
public void onDestroy() {
	super.onDestroy();
	data.clear();
}
}
