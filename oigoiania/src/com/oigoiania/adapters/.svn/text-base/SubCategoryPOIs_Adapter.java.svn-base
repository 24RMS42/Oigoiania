package com.oigoiania.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.location.LocationManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyBaseDataHolder;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.GlobalFunctions;
import com.oigoiania.util.Util;

public class SubCategoryPOIs_Adapter extends BaseAdapter {
	private static final String tag = "SubCategoryPOIs_Adapter";
	private List<MyDataHolder> data = null;//new ArrayList<MyDataHolder>();
	private Activity mActivity = null;
	private LayoutInflater inflater = null;
	private Location desiredLocation =null;

//	private Bitmap compassImageNorth;
//	private Bitmap compassImageNorthEast;
//	private Bitmap compassImageNorthWest;
//	private Bitmap compassImageEast;
//	private Bitmap compassImageWest;
//	private Bitmap compassImageSouth;

	// private float previous = 0;

	public SubCategoryPOIs_Adapter(Activity activity, List<MyDataHolder> data) {
		Logger.d(tag, "SubCategoryPOIs_Adapter()");
		this.mActivity = activity;
		this.data=data;//.addAll(data);
		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		inflater = (LayoutInflater) this.mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		Resources res = mActivity.getResources();
//		compassImageNorth = BitmapFactory.decodeResource(res,
//				R.drawable.n_arrow);
//		compassImageNorthEast = BitmapFactory.decodeResource(res,
//				R.drawable.ne_arrow);
//		compassImageNorthWest = BitmapFactory.decodeResource(res,
//				R.drawable.nw_arrow);
//		compassImageEast = BitmapFactory
//				.decodeResource(res, R.drawable.e_arrow);
//		compassImageWest = BitmapFactory
//				.decodeResource(res, R.drawable.w_arrow);
//		compassImageSouth = BitmapFactory.decodeResource(res,
//				R.drawable.s_arrow);
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
		//Logger.d(tag, "getView() and position is: "+position);
		convertView = inflater.inflate(R.layout.subcategorypois_listview_row,
				null);
		MyDataHolder mPoiObject = (MyDataHolder) data.get(position);

		TextView title = (TextView) convertView
				.findViewById(R.id.title_txtview);
		title.setText(mPoiObject.getLoc_title());
		TextView category = (TextView) convertView.findViewById(R.id.category);
		category.setText(mPoiObject.getCategory().getName());
		TextView distance = (TextView) convertView
				.findViewById(R.id.distance_txtview);
		desiredLocation.setLatitude(mPoiObject.getLoc_latitude());
		desiredLocation.setLongitude(mPoiObject.getLoc_longitude());
		distance.setText("" + GlobalFunctions.formatDistance(GlobalFunctions.distanceFrom(desiredLocation)));
		ImageView arrowImage = (ImageView) convertView
				.findViewById(R.id.image_arrow);
		updateArrow(arrowImage, mPoiObject);
		convertView.setTag(mPoiObject);
		return convertView;
	}

	private synchronized void updateArrow(final ImageView imageView,
			MyBaseDataHolder location) {
		if (location == null || Util.currentLocation == null)
			return;
		Location target = new Location("reverseGeocoded");

		target.setLongitude(location.getLoc_longitude());
		target.setLatitude(location.getLoc_latitude());
		float azimuth = Util.currentBearing;
		GeomagneticField geoField = new GeomagneticField(
				Double.valueOf(Util.currentLocation.getLatitude()).floatValue(),
				Double.valueOf(Util.currentLocation.getLongitude())
						.floatValue(),
				Double.valueOf(Util.currentLocation.getAltitude()).floatValue(),
				System.currentTimeMillis());

		azimuth -= geoField.getDeclination(); // converts magnetic north into
												// true north

		// Store the bearingTo in the bearTo variable
		float bearTo = Util.currentLocation.bearingTo(target);

		// If the bearTo is smaller than 0, add 360 to get the rotation
		// clockwise.
		if (bearTo < 0) {
			bearTo = bearTo + 360;
		}

		// This is where we choose to point it
		float direction = bearTo - azimuth;

		// If the direction is smaller than 0, add 360 to get the rotation
		// clockwise.
		if (direction < 0) {
			direction = direction + 360;
		}

		final float realDirection = ((direction + 90) % 360);

		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				rotateImageView(imageView, R.drawable.n_arrow, realDirection);
			}
		});
	}
	private void rotateImageView(ImageView imageView, int drawable, float rotate) {

		Bitmap bitmapOrg = BitmapFactory.decodeResource(mActivity.getResources(),
				drawable);

		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = bitmapOrg.getWidth(), height = bitmapOrg.getHeight();

		// Initialize a new Matrix
		Matrix matrix = new Matrix();
		// Decide on how much to rotate
		rotate = rotate % 360;
		// Actually rotate the image
		matrix.postRotate(rotate, width, height);
		// recreate the new Bitmap via a couple conditions
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		// BitmapDrawable bmd = new BitmapDrawable( rotatedBitmap );
		// imageView.setImageBitmap( rotatedBitmap );
		imageView.setImageDrawable(new BitmapDrawable(mActivity.getResources(),
				rotatedBitmap));
		imageView.setScaleType(ScaleType.CENTER);
	}

	
//	private synchronized void updateArrow(ImageView imageView, MyDataHolder Poi) {
//		Location desiredLocation;
//		double lat = Poi.getLoc_latitude();
//		double lng = Poi.getLoc_longitude();
//		desiredLocation = new Location(LocationManager.GPS_PROVIDER);
//		desiredLocation.setLatitude(lat);
//		desiredLocation.setLongitude(lng);
//		float bearing = Util.currentLocation.bearingTo(desiredLocation);
//		float distance = Util.currentLocation.distanceTo(desiredLocation);
//
//		if (distance > 1) {
//			float bearingRelative = bearing - Util.currentBearing;
//			while (bearingRelative < 0)
//				bearingRelative = bearingRelative + 360;
//			while (bearingRelative > 360)
//				bearingRelative = bearingRelative - 360;
//			// imageView.setImageBitmap(compassImageNorth);
//			// animateArrow(bearingRelative, imageView);
//			if (bearingRelative <= 22) {
//				imageView.setImageBitmap(compassImageNorth);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			} else if (22 < bearingRelative && bearingRelative <= 68) {
//				imageView.setImageBitmap(compassImageNorthEast);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			} else if (68 < bearingRelative && bearingRelative <= 135) {
//				imageView.setImageBitmap(compassImageEast);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			} else if (138 < bearingRelative && bearingRelative <= 225) {
//				imageView.setImageBitmap(compassImageSouth);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			} else if (225 < bearingRelative && bearingRelative <= 295) {
//				imageView.setImageBitmap(compassImageWest);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			} else if (295 < bearingRelative && bearingRelative <= 338) {
//				imageView.setImageBitmap(compassImageNorthWest);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			} else if (338 < bearingRelative) {
//				imageView.setImageBitmap(compassImageNorth);
//				imageView.setVisibility(View.VISIBLE);
//				animateArrow(bearingRelative, imageView);
//			}
//
//		}
//	}

//	private void animateArrow(float bearingRelative, ImageView imageView) {
//		// RotateAnimation an = new RotateAnimation(-previous, -bearingRelative,
//		// Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//		// 0.5f);
//		// previous = bearingRelative;
//		// an.setDuration(500);
//		// an.setRepeatCount(0);
//		// an.setFillAfter(true);
//		// imageView.startAnimation(an);
//	}

}
