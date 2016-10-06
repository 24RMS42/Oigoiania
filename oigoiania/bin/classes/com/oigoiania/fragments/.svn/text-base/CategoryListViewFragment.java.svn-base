package com.oigoiania.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
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
import com.br.oigoiania.SubCategory_Activity;
import com.crittercism.app.Crittercism;
import com.oigoiania.adapters.Category_Adapter;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.Cat_SubCatDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

@SuppressLint("InlinedApi")
public class CategoryListViewFragment extends Fragment implements
		OnItemClickListener {
	private final String tag = "CategoryListViewFragment";
	private FragmentActivity mActvity;
	private ListView cstegory_lv = null;
	private static List<Cat_SubCatDataHolder> data = new ArrayList<Cat_SubCatDataHolder>();
	private Category_Adapter adapter = null;
	private static CategoryListViewFragment mCategoryListView = null;

	public static CategoryListViewFragment getInstance() {
		return mCategoryListView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d(tag, "onCreate()");
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
		View view = inflater.inflate(R.layout.category_activity, null);
		cstegory_lv = (ListView) view.findViewById(R.id.listView1);
		cstegory_lv.setOnItemClickListener(this);
		Logger.d(tag, "Data on CreateView" + data.toString());
		return view;
	}
@Override
public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	adapter = new Category_Adapter(getActivity(), data);
	cstegory_lv.setAdapter(adapter);
}
	public void notifyDataSetChange() {

		try {
			Logger.d(tag, "notifyDataSetChange");
			if (Util.rootJsonObject != null) {
				data.clear();
				data.addAll(Parser
						.parseCategoryResponse(Util.rootJsonObject_catSubcat));
				//adapter = new Category_Adapter(getActivity(), data);
				//todayinog_lv.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			Logger.d(tag, "data size :" + Util.rootJsonObject.toString());
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		Logger.d(tag, "onItemClick");
		Cat_SubCatDataHolder subcat = (Cat_SubCatDataHolder) view.getTag();
		String id = subcat.getCatId();
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(getActivity(), SubCategory_Activity.class);
		intent.putExtra("catid", id);
		intent.putExtra("title", subcat.getTitle());
		mActvity.startActivity(intent);
		mActvity.overridePendingTransition(R.anim.incoming, R.anim.outgoing);

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		data.clear();
	}
}
