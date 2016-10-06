package com.oigoiania.adapters;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.Cat_SubCatDataHolder;

public class Category_Adapter extends BaseAdapter {

	private static final String tag = "Category_Adapter";
	private List<Cat_SubCatDataHolder> data = null;// new
													// ArrayList<Cat_SubCatDataHolder>();
	private FragmentActivity mActivity = null;
	private LayoutInflater inflater = null;

	public Category_Adapter(FragmentActivity gContext,
			List<Cat_SubCatDataHolder> data2) {
		this.mActivity = gContext;
		if (data2 != null)
			this.data = data2;// .addAll(data2);

		Logger.d(tag, " Category_Adapter() Data size :" + data.size());
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
		Logger.d(tag, " getView() and position is: " + position);
		convertView = inflater.inflate(R.layout.category_listitem_row, null);
		final Cat_SubCatDataHolder todayInoigoianiaObject = (Cat_SubCatDataHolder) data
				.get(position);
		TextView title = (TextView) convertView.findViewById(R.id.textView1);
		title.setText("" + todayInoigoianiaObject.getTitle());
		convertView.setTag(todayInoigoianiaObject);
		return convertView;
	}

}
