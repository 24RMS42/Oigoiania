package com.oigoiania.augmentedreality;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.crittercism.app.Crittercism;
import com.jwetherell.augmented_reality.R;
import com.jwetherell.augmented_reality.data.NetworkDataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.ui.MyTestMarker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.oigoiania.interfaces.DownloadImage;
import com.oigoiania.logger.Logger;

/**
 * This class extends DataSource to fetch data from Google Places.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class OigoianiaDataSource extends NetworkDataSource implements
		ImageLoadingListener {

	private static final String URL = "http://192.168.2.110/imran/MyTestService.php";
	private static final String TYPES = "airport|amusement_park|aquarium|art_gallery|bus_station|campground|car_rental|city_hall|embassy|establishment|hindu_temple|local_governemnt_office|mosque|museum|night_club|park|place_of_worship|police|post_office|stadium|spa|subway_station|synagogue|taxi_stand|train_station|travel_agency|University|zoo";

	private static String key = null;
	private Bitmap icon = null;
	private Activity activity = null;
	private static final String tag = "OigoianiaDataSource";
	private ImageSize targetSize = null;
	private DisplayImageOptions options = null;
	private DownloadImage mDownloadImage = null;

	public OigoianiaDataSource(Resources res, Activity activity,
			DownloadImage mDownloadImage) {
		if (res == null || activity == null)
			throw new NullPointerException();
		this.mDownloadImage = mDownloadImage;
		this.activity = activity;
		key = res.getString(R.string.google_places_api_key);
		targetSize = new ImageSize(50, 50);
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		createIcon(res);
	}

	protected void createIcon(Resources res) {
		if (res == null)
			throw new NullPointerException();

		// icon = BitmapFactory.decodeResource(res, R.drawable.badshaimosque);
	}

	@Override
	public String createRequestURL(double lat, double lon, double alt,
			float radius, String locale) {
		try {
			return URL;// +
						// "location="+lat+","+lon+"&radius="+(radius*1000.0f)+"&types="+TYPES+"&sensor=true&key="+key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<Marker> parse(String URL) {
		if (URL == null)
			throw new NullPointerException();
		Logger.d(tag, "Enter parse(URl)");
		HttpPost postMethod = new HttpPost(URL);
		DefaultHttpClient hc = new DefaultHttpClient();
		String string = null;
		try {
			HttpResponse response = hc.execute(postMethod);
			HttpEntity entity = response.getEntity();
			string = EntityUtils.toString(entity);
			Logger.d(tag, "JSON iS :" + string);
		} catch (ClientProtocolException e1) {
			Crittercism.logHandledException(e1);
			e1.printStackTrace();
		} catch (IOException e1) {
			Crittercism.logHandledException(e1);
			e1.printStackTrace();
		}

		if (string == null)
			throw new NullPointerException();

		JSONObject json = null;
		try {
			json = new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json == null)
			throw new NullPointerException();

		return parse(json);
	}

	@Override
	public List<Marker> parse(JSONObject root) {
		if (root == null)
			throw new NullPointerException();
		Logger.d(tag, "Enter parse(JSOnObject)");
		JSONObject jo = null;
		JSONArray dataArray = null;
		List<Marker> markers = new ArrayList<Marker>();
		try {
			if (root.has("data"))
				dataArray = root.getJSONArray("data");
			if (dataArray == null)
				return markers;
			int top =dataArray.length();//Math.min(MAX, dataArray.length());
			for (int i = 0; i < top; i++) {
				jo = dataArray.getJSONObject(i);
				Marker ma = processJSONObject(jo);
				if (ma != null)
					markers.add(ma);
			}
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
		if (mDownloadImage != null)
			mDownloadImage.notifyDownloadComplete();
		return markers;
	}

	synchronized private Marker processJSONObject(JSONObject jo) {
		if (jo == null)
			throw new NullPointerException();
		// Logger.d(tag, "Enter processJSONObject(JSONObject)");
		if (!jo.has("longitude"))
			throw new NullPointerException();

		Marker ma = null;
		String title = "";
		String loc_id = null;
		String imgUrl = "";
		try {
			Double lat = null, lon = null;
			if (!jo.isNull("title"))
				title = jo.getString("title");
			if (!jo.isNull("loc_title"))
				title = jo.getString("loc_title");
			if (!jo.isNull("loc_id"))
				loc_id = jo.getString("loc_id");
			if (!jo.isNull("longitude"))
				lon = jo.getDouble("longitude");
			if (!jo.isNull("latitude"))
				lat = jo.getDouble("latitude");
			if (!jo.isNull("loc_image")) {
				imgUrl = jo.getString("loc_image");
			}

			if (lat != null) {

				Logger.d(tag, "Before Loading Image... " + imgUrl);

				if (activity == null)
					throw new IllegalStateException("Activity is NULL!");

				final Map<String, Object> params = new HashMap<String, Object>();

				params.put("imgUrl", imgUrl);
				// params.put("targetSize", targetSize);
				params.put("listener", this);
				// params.put("options", options);

				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ImageLoader.getInstance().loadImage(
								((String) params.get("imgUrl")), targetSize,
								options,
								((OigoianiaDataSource) params.get("listener")));
					}
				});

				Logger.d(tag, "Going into wait for ... " + imgUrl);

				wait();
				Logger.d(tag, "Notified for " + imgUrl);

				ma = new MyTestMarker(loc_id, title, lat, lon, 0, Color.RED,
						icon);
				ma.setData(com.jwetherell.augmented_reality.activity.Parser
						.parseResponse(jo));
				// ma = new IconMarker(user + ": " + jo.getString("name"), lat,
				// lon, 0, Color.RED, icon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ma;
	}

	@Override
	synchronized public void onLoadingStarted(String imageUri, View view) {
		// TODO Auto-generated method stub
		Logger.d(tag, "onLoadingStarted: " + imageUri);
	}

	@Override
	synchronized public void onLoadingFailed(String imageUri, View view,
			FailReason failReason) {
		// TODO Auto-generated method stub
		Logger.d(
				tag,
				"onLoadingFailed: " + imageUri + " Reason: "
						+ failReason.getCause());
		notifyAll();
	}

	@Override
	synchronized public void onLoadingComplete(String imageUri, View view,
			Bitmap loadedImage) {
		// TODO Auto-generated method stub
		Logger.d(tag, "onLoadingComplete: " + imageUri);
		icon = loadedImage;
		notifyAll();
	}

	@Override
	synchronized public void onLoadingCancelled(String imageUri, View view) {
		// TODO Auto-generated method stub
		Logger.d(tag, "onLoadingCancelled: " + imageUri);
		notifyAll();
	}

}