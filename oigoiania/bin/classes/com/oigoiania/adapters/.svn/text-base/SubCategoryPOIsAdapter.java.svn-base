package com.oigoiania.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.oigoiania.parsers.SubCatergoryPOIsDataHolder;
import com.oigoiania.util.GlobalFunctions;
import com.oigoiania.util.Util;

/*
 * Unused class...
 * helpful fro arrow rotation etc...
 */

public class SubCategoryPOIsAdapter extends BaseAdapter {
	// private static final String tag = "ResearchAdapter";
	private List<SubCatergoryPOIsDataHolder> data = new ArrayList<SubCatergoryPOIsDataHolder>();
	private Activity mActivity = null;
	private LayoutInflater inflater = null;
	Location desiredLocation = null;
	private Bitmap compassImageNorth;
	private Bitmap compassImageNorthEast;
	private Bitmap compassImageNorthWest;
	private Bitmap compassImageEast;
	private Bitmap compassImageWest;
	private Bitmap compassImageSouth;

	// private float previous = 0;

	public SubCategoryPOIsAdapter(Activity activity,
			List<SubCatergoryPOIsDataHolder> data) {
		this.mActivity = activity;
		this.data.addAll(data);
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
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
		convertView = inflater.inflate(R.layout.search_listview_row, null);
		SubCatergoryPOIsDataHolder POIObject = (SubCatergoryPOIsDataHolder) data
				.get(position);
		TextView subcategory = (TextView) convertView
				.findViewById(R.id.subcategory);
		subcategory.setText(POIObject.getCategoryName());
		TextView title = (TextView) convertView
				.findViewById(R.id.title_txtview);
		title.setText(POIObject.getTitle());
		TextView description = (TextView) convertView
				.findViewById(R.id.dest_txtview);
		description.setText(POIObject.getAddress());
		TextView website = (TextView) convertView
				.findViewById(R.id.weblink_txtview);
		website.setVisibility(View.GONE);

		TextView distance = (TextView) convertView
				.findViewById(R.id.distance_txtview);
		desiredLocation.setLatitude(POIObject.getLatitude());
		desiredLocation.setLongitude(POIObject.getLongitude());

		distance.setText("" + GlobalFunctions.formatDistance(GlobalFunctions.distanceFrom(desiredLocation)));
		ImageView arrowImage = (ImageView) convertView
				.findViewById(R.id.image_arrow);
		updateArrow(arrowImage, POIObject);
		return convertView;
	}

	private synchronized void updateArrow(ImageView imageView,
			SubCatergoryPOIsDataHolder POIObject) {
		Location desiredLocation;
		double lat = POIObject.getLatitude();
		double lng = POIObject.getLongitude();
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
			if (bearingRelative <= 22) {
				imageView.setImageBitmap(compassImageNorth);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			} else if (22 < bearingRelative && bearingRelative <= 68) {
				imageView.setImageBitmap(compassImageNorthEast);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			} else if (68 < bearingRelative && bearingRelative <= 135) {
				imageView.setImageBitmap(compassImageEast);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			} else if (138 < bearingRelative && bearingRelative <= 225) {
				imageView.setImageBitmap(compassImageSouth);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			} else if (225 < bearingRelative && bearingRelative <= 295) {
				imageView.setImageBitmap(compassImageWest);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			} else if (295 < bearingRelative && bearingRelative <= 338) {
				imageView.setImageBitmap(compassImageNorthWest);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			} else if (338 < bearingRelative) {
				imageView.setImageBitmap(compassImageNorth);
				imageView.setVisibility(View.VISIBLE);
				animateArrow(bearingRelative, imageView);
			}

		}
	}

	private void animateArrow(float bearingRelative, ImageView imageView) {
		// RotateAnimation an = new RotateAnimation(-previous, -bearingRelative,
		// Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		// 0.5f);
		// previous = bearingRelative;
		// an.setDuration(500);
		// an.setRepeatCount(0);
		// an.setFillAfter(true);
		// imageView.startAnimation(an);
	}

	@SuppressWarnings("unused")
	private Bitmap getRotatedBitmap(Bitmap bitmapOrg, float angle) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		int newWidth = 200;
		int newHeight = 200;

		// calculate the scale - in this case = 0.4f
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// createa matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// rotate the Bitmap
		matrix.postRotate(angle);

		// recreate the new Bitmap
		// Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
		// width, height, matrix, true);

		// make a Drawable from Bitmap to allow to set the BitMap
		// to the ImageView, ImageButton or what ever
		// BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
		return Bitmap
				.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
	}
}
