package com.oigoiania.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.Subcategory_Holder;

public class Subcategory_Adapter extends BaseAdapter{

	
	private static final String tag = "Subcategory_Adapter";
		private List<Subcategory_Holder> data = new ArrayList<Subcategory_Holder>();
		private Activity mActivity = null;
		private LayoutInflater inflater = null;

	public Subcategory_Adapter(Activity gContext, List<Subcategory_Holder> data2){
		this.mActivity = gContext;
		if(data2 !=null)
		this.data=data2;
		Logger.d(tag, " Data is :"+ data2.toString());
		inflater = (LayoutInflater) this.mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.category_listitem_row, null);
		Subcategory_Holder todayInoigoianiaObject = (Subcategory_Holder) data
				.get(position);

		Logger.d(tag, " Title is : "+todayInoigoianiaObject.getTitle());
		TextView title = (TextView) convertView
				.findViewById(R.id.textView1);
		title.setText(""+todayInoigoianiaObject.getTitle());
		
		return convertView;
	}
	




}
