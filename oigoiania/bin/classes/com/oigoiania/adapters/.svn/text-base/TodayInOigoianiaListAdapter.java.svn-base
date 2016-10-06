package com.oigoiania.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.GlobalFunctions;
import com.oigoiania.util.Util;

public class TodayInOigoianiaListAdapter extends BaseAdapter {
	private static final String tag = "TodayInOigoianiaListAdapter";
	private List<MyDataHolder> data = null;//new ArrayList<MyDataHolder>();
	private Activity mActivity = null;
	private LayoutInflater inflater = null;
	ImageLoader imageLoager = null;
	private Location desiredLocation = null;

	public TodayInOigoianiaListAdapter(Activity activity,
			List<MyDataHolder> data) {
		Logger.d(tag, "Enter TodayInOigoianiaListAdapter()");
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		this.mActivity = activity;
		this.data=data;
		//this.data.addAll(data);
		inflater = (LayoutInflater) this.mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoager = ImageLoader.getInstance();
		Logger.d(tag, "Constructor anddata size :" + this.data.size());
		Logger.d(tag, "Exit TodayInOigoianiaListAdapter()");
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
		Logger.d(tag, "Enter getView() and position is :"+position);
		convertView = inflater.inflate(R.layout.todaylistview_row_item, null);
		MyDataHolder todayInoigoianiaObject = (MyDataHolder) data.get(position);

		TextView title = (TextView) convertView
				.findViewById(R.id.title_txtview);
		TextView date = (TextView) convertView.findViewById(R.id.time_txtview);
		TextView desel = (TextView) convertView.findViewById(R.id.des_txtview);
		TextView distance = (TextView) convertView
				.findViewById(R.id.distance_txtview);

		title.setText(todayInoigoianiaObject.getName());
		date.setText(todayInoigoianiaObject.getTime());
		desiredLocation.setLatitude(todayInoigoianiaObject.getLoc_latitude());
		desiredLocation.setLongitude(todayInoigoianiaObject.getLoc_longitude());
		distance.setText(""
				+ GlobalFunctions.formatDistance(GlobalFunctions
						.distanceFrom(desiredLocation)));
		desel.setText(todayInoigoianiaObject.getLoc_title());
		ImageView image = (ImageView) convertView
				.findViewById(R.id.image_listimage);
		imageLoager.displayImage(todayInoigoianiaObject.getImage(), image);
		convertView.setTag(todayInoigoianiaObject);
		Logger.d(tag, "Exit getView()");
		return convertView;
	}

	@SuppressWarnings("unused")
	private synchronized void UpdateDistance(TextView distanceView,
			MyDataHolder todayInoigoianiaObject) {
		Location desiredLocation;
		double lat = todayInoigoianiaObject.getLoc_latitude();
		double lng = todayInoigoianiaObject.getLoc_longitude();
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		desiredLocation.setLatitude(lat);
		desiredLocation.setLongitude(lng);
		// float bearing = Util.currentLocation.bearingTo(desiredLocation);
		float distance = Util.currentLocation.distanceTo(desiredLocation);

		if (distance > 1) {

		}
	}

}
