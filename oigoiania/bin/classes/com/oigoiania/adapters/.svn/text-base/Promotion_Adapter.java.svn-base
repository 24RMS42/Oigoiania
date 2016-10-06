package com.oigoiania.adapters;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.GlobalFunctions;

public class Promotion_Adapter extends BaseAdapter {
	private static final String tag = "Promotion_Adapter";

	// private static final String tag = "ResearchAdapter";
	private List<MyDataHolder> data = null;//new ArrayList<MyDataHolder>();
	private FragmentActivity mActivity = null;
	private LayoutInflater inflater = null;
	private Location desiredLocation = null;

	public Promotion_Adapter(FragmentActivity gContext, List<MyDataHolder> data2) {
		Logger.d(tag, "Promotion_Adapter()");
		this.mActivity = gContext;
		if (data2 != null)
			this.data=data2;//.addAll(data2);
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		Logger.d(tag, " Data size is :" + data.size());
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
		convertView = inflater.inflate(R.layout.promotion_listview_row, null);
		final MyDataHolder todayInoigoianiaObject = (MyDataHolder) data
				.get(position);
		TextView title = (TextView) convertView
				.findViewById(R.id.title_txtview);
		TextView description = (TextView) convertView
				.findViewById(R.id.descripton_txtview);
		TextView distance = (TextView) convertView
				.findViewById(R.id.distance_txtview);
		title.setText("" + todayInoigoianiaObject.getName() + "");
		description.setText("(" + todayInoigoianiaObject.getLoc_title() + ")");
		desiredLocation.setLatitude(todayInoigoianiaObject.getLoc_latitude());
		desiredLocation.setLongitude(todayInoigoianiaObject.getLoc_longitude());
		distance.setText(""
				+ GlobalFunctions.formatDistance(GlobalFunctions
						.distanceFrom(desiredLocation)));
		convertView.setTag(todayInoigoianiaObject);
		return convertView;
	}

}
