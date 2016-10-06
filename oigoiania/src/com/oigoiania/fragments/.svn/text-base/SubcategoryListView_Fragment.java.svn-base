package com.oigoiania.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.br.oigoiania.R;
import com.br.oigoiania.Subcategory_POIS;
import com.crittercism.app.Crittercism;
import com.oigoiania.adapters.Category_Adapter;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.Cat_SubCatDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

public class SubcategoryListView_Fragment extends Fragment implements
		OnItemClickListener {
	private final String tag = "SubcategoryListView_Fragment";
	private ListView subCat_lv = null;
	private static List<Cat_SubCatDataHolder> data = new ArrayList<Cat_SubCatDataHolder>();
	private Category_Adapter adapter = null;
	private Activity mActvity;
	private static SubcategoryListView_Fragment mCategoryListView = null;

	public static SubcategoryListView_Fragment getInstance() {
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
		View view = inflater.inflate(R.layout.category_activity, null);
		subCat_lv = (ListView) view.findViewById(R.id.listView1);
		subCat_lv.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new Category_Adapter(getActivity(), data);
		subCat_lv.setAdapter(adapter);
	}

	public void notifyDataSetChange() {

		try {
			Logger.d(tag, "notifyDataSetChange");
			if (Util.rootJsonObject != null) {
				data.clear();
				data.addAll(Parser
						.parseSubcategoryResponse(Util.rootJsonObject_catSubcat));
				// adapter = new Category_Adapter(getActivity(), data);
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
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		Logger.d(tag, "onItemClick");
		Cat_SubCatDataHolder subcat = (Cat_SubCatDataHolder) view.getTag();
		String catId = subcat.getCatId();
		String subCatId = subcat.getSubCatId();
		Intent intent = new Intent();
		intent.setClass(getActivity(), Subcategory_POIS.class);
		intent.putExtra("catId", catId);
		intent.putExtra("subCatId", subCatId);
		intent.putExtra("subtitle", subcat.getTitle());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		mActvity.overridePendingTransition(R.anim.incoming, R.anim.outgoing);

	}

	@Override
	public void onPause() {
		super.onPause();
		Logger.d(tag, "onPause");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		data.clear();
		Logger.d(tag, "onDestroy");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Logger.d(tag, "onDestroyView");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Logger.d(tag, "onDetach");
	}
}
