package com.oigoiania.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oigoiania.parsers.ImagesHolder;

public class POI_Images_Adapter extends BaseAdapter {

	private static final String tag = "POI_Images_Adapter";
	private List<ImagesHolder> mData = new ArrayList<ImagesHolder>();
	private Activity mActivity = null;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;

	public POI_Images_Adapter(Activity gContext, List<ImagesHolder> data2) {
		this.mActivity = gContext;
		if (data2 != null)
			this.mData = data2;
		inflater = (LayoutInflater) this.mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
			ImagesHolder imageholder_obj = (ImagesHolder) mData.get(position);

			if (convertView == null) {
				imageView = new ImageView(mActivity);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setLayoutParams(new GridView.LayoutParams(
						LayoutParams.MATCH_PARENT, 270));
				imageLoader.displayImage(imageholder_obj.getUrl(), imageView);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setTag(imageholder_obj);
			return imageView;
	}

}
