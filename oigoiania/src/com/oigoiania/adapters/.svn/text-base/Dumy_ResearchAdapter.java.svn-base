package com.oigoiania.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.br.oigoiania.R;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.Util;

public class Dumy_ResearchAdapter extends BaseAdapter {
	private static final String tag = "ResearchAdapter";
	private List<MyDataHolder> data = new ArrayList<MyDataHolder>();
	private Activity mActivity = null;
	private LayoutInflater inflater = null;
	// private Bitmap arrow = null;

	private Bitmap compassImageNorth;
	private Bitmap compassImageNorthEast;
	private Bitmap compassImageNorthWest;
	private Bitmap compassImageEast;
	private Bitmap compassImageWest;
	private Bitmap compassImageSouth;

	private float previous_1st = 0,previous_2nd=226,previous_3rd=139;

	public Dumy_ResearchAdapter(Activity activity, List<MyDataHolder> data) {
		this.mActivity = activity;
		this.data.addAll(data);
		inflater = (LayoutInflater) this.mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resources res = mActivity.getResources();
		compassImageNorth = BitmapFactory.decodeResource(res,
				R.drawable.n_arrow);
		compassImageNorthEast = BitmapFactory.decodeResource(res,
				R.drawable.ne_arrow);
		compassImageNorthWest = BitmapFactory.decodeResource(res,
				R.drawable.nw_arrow);
		compassImageEast = BitmapFactory
				.decodeResource(res, R.drawable.e_arrow);
		compassImageWest = BitmapFactory
				.decodeResource(res, R.drawable.w_arrow);
		compassImageSouth = BitmapFactory.decodeResource(res,
				R.drawable.s_arrow);
	}

	@Override
	public int getCount() {
		return 1;//data.size();
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
		convertView = inflater.inflate(R.layout.search_listview_row, null);
//		MyDataHolder researchObject = (MyDataHolder) data.get(position);
//		TextView subcategory = (TextView) convertView
//				.findViewById(R.id.subcategory);
//		subcategory.setText(researchObject.getSubcategory());
//		TextView title = (TextView) convertView
//				.findViewById(R.id.title_txtview);
//		title.setText(researchObject.getTitle());
//		TextView description = (TextView) convertView
//				.findViewById(R.id.dest_txtview);
//		description.setText(researchObject.getAddress());
//		TextView website = (TextView) convertView
//				.findViewById(R.id.weblink_txtview);
//		website.setText(researchObject.getSiteUrl());
//		TextView distance = (TextView) convertView
//				.findViewById(R.id.distance_txtview);
//		distance.setText(GlobalFunctions.distFrom(
//				Util.currentLocation.getLatitude(),
//				Util.currentLocation.getLongitude(),
//				researchObject.getLatitude(), researchObject.getLongitude()));
//		ImageView arrowImage = (ImageView) convertView
//				.findViewById(R.id.image_arrow);
//		updateArrow(arrowImage, researchObject);
		return convertView;
	}

	private synchronized void updateArrow(ImageView imageView,
			MyDataHolder researchObject) {
		Location desiredLocation;
		double lat = researchObject.getLoc_latitude();
		double lng = researchObject.getLoc_longitude();
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		desiredLocation.setLatitude(lat);
		desiredLocation.setLongitude(lng);
		float bearing = Util.currentLocation.bearingTo(desiredLocation);
		float distance = Util.currentLocation.distanceTo(desiredLocation);

		if (distance > 1) {
			float bearingRelative = bearing - Util.currentBearing;
			while (bearingRelative < 0)
				bearingRelative = bearingRelative + 360;
			while (bearingRelative > 360)
				bearingRelative = bearingRelative - 360;
			// imageView.setImageBitmap(compassImageNorth);
			// animateArrow(bearingRelative, imageView);
			if (bearingRelative <= 68) {
				imageView.setImageBitmap(compassImageNorth);
				imageView.setVisibility(View.VISIBLE);
				animateArrow_1st(bearingRelative, imageView);
//			} else if (22 < bearingRelative && bearingRelative <= 68) {
//				imageView.setImageBitmap(compassImageNorthEast);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
			} else if (68 < bearingRelative && bearingRelative <= 135) {
				imageView.setImageBitmap(compassImageEast);
				imageView.setVisibility(View.VISIBLE);
				//animateArrow(bearingRelative, imageView);
			} else if (138 < bearingRelative && bearingRelative <= 225) {
				imageView.setImageBitmap(compassImageSouth);
				imageView.setVisibility(View.VISIBLE);
				animateArrow_3rd(bearingRelative, imageView);
			} else if (225 < bearingRelative && bearingRelative <= 338 /*295*/) {
				imageView.setImageBitmap(compassImageWest);
				imageView.setVisibility(View.VISIBLE);
				//animateArrow(bearingRelative, imageView);
//			} else if (295 < bearingRelative && bearingRelative <= 338) {
//				imageView.setImageBitmap(compassImageNorthWest);
//				imageView.setVisibility(View.VISIBLE);
				animateArrow_2nd(bearingRelative, imageView);
			} else if (338 < bearingRelative) {
				imageView.setImageBitmap(compassImageNorth);
				imageView.setVisibility(View.VISIBLE);
				animateArrow_1st(bearingRelative, imageView);
			}
			Logger.d(tag, "bearingRelative :" + bearingRelative);

		}
	}

	private void animateArrow_1st(float bearingRelative, ImageView imageView) {
		RotateAnimation an = new RotateAnimation(previous_1st, bearingRelative,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		previous_1st = bearingRelative;
		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);
		imageView.startAnimation(an);
	}
	private void animateArrow_2nd(float bearingRelative, ImageView imageView) {
		RotateAnimation an = new RotateAnimation(-previous_2nd, -bearingRelative,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		previous_2nd = bearingRelative;
		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);
		imageView.startAnimation(an);
	}
	private void animateArrow_3rd(float bearingRelative, ImageView imageView) {
		RotateAnimation an = new RotateAnimation(-previous_3rd, -bearingRelative,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		previous_3rd = bearingRelative;
		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);
		imageView.startAnimation(an);
	}
}
