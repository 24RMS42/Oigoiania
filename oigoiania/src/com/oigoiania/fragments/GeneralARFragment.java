package com.oigoiania.fragments;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.br.oigoiania.Events_SingleDetail;
import com.br.oigoiania.R;
import com.br.oigoiania.SpecificPOI_Activity;
import com.crittercism.app.Crittercism;
import com.jwetherell.augmented_reality.activity.AugmentedRealityUtil;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.data.NetworkDataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import com.oigoiania.augmentedreality.AugmentedReality;
import com.oigoiania.augmentedreality.OigoianiaDataSource;
import com.oigoiania.interfaces.DownloadImage;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;

/**
 * This class extends the AugmentedReality and is designed to be an example on
 * how to extends the AugmentedReality class to show multiple data sources.
 * 
 * @author M.IMRAN
 */
public class GeneralARFragment extends AugmentedReality implements
		DownloadImage {

	private static final String tag = "GeneralARFragment";
	// private static final String locale = Locale.getDefault().getLanguage();
	private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(
			1);
	private static final ThreadPoolExecutor exeService = new ThreadPoolExecutor(
			1, 1, 20, TimeUnit.SECONDS, queue);
	private final Map<String, NetworkDataSource> sources = new ConcurrentHashMap<String, NetworkDataSource>();

	public static Marker lastClickedMarker = null;
	private JSONObject jsonRoot = null;
	private ProgressDialog progress_dialog = null;
	private String type = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d(tag, "onCreate()");
		String jsnString = null;
		Bundle bundle = getArguments();
		if (bundle != null)
			type = bundle.getString("type");
		jsnString = bundle.getString("json");
		if (jsnString != null) {// if (Util.rootJsonObject != null) {
			Logger.d("testtt", "json :" + jsnString);
			try {
				jsonRoot = new JSONObject(jsnString);
			} catch (JSONException e) {
				Crittercism.logHandledException(e);
				e.printStackTrace();
			}
			// jsonRoot = Util.rootJsonObject;
			AugmentedRealityUtil.setContext(mActivity);
			ARData.clearMarkers();
			NetworkDataSource mOigoianiaDataSource = new OigoianiaDataSource(
					this.getResources(), mActivity, GeneralARFragment.this);
			sources.put("oigoianiaPlaces", mOigoianiaDataSource);
			progress_dialog = new ProgressDialog(mActivity);
			progress_dialog.setMessage("Please wait...");
			progress_dialog.setCancelable(true);
			progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress_dialog.show();
			// sources.put("googlePlaces", mOigoianiaDataSource);
		} else {
			Logger.d("testtt", "Null");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStart() {
		super.onStart();
		Logger.d(tag, "onStart()");
		// Location last = ARData.getCurrentLocation();
		updateData(jsonRoot);
		// updateData(last.getLatitude(), last.getLongitude(),
		// last.getAltitude());
	}

	@Override
	public void onDestroy() {
		progress_dialog = null;
		Logger.d(tag, "onDestroy()");
		sources.clear();
		super.onDestroy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onLocationChanged(Location location) {
		super.onLocationChanged(location);

		// updateData(location.getLatitude(), location.getLongitude(),
		// location.getAltitude());
		updateData(jsonRoot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void markerTouched(Marker marker) {
		Logger.d(tag, "enter markerTouched()");
		MyDataHolder holder = new MyDataHolder();
		holder.setAddress(marker.getData().getAddress());
		holder.setDate(marker.getData().getDate());
		holder.setDateFinal(marker.getData().getDateFinal());
		holder.setDateInitail(marker.getData().getDateInitail());
		holder.setDescription(marker.getData().getDescription());
		holder.setDistance(marker.getDistance());
		holder.setId(marker.getData().getId());
		holder.setImage(marker.getData().getImage());
		holder.setName(marker.getData().getName());
		holder.setLoc_address(marker.getData().getLoc_address());
		holder.setLoc_catId(marker.getData().getLoc_catId());
		holder.setLoc_cell(marker.getData().getLoc_cell());
		holder.setLoc_description(marker.getData().getLoc_description());
		holder.setLoc_id(marker.getData().getLoc_id());
		holder.setLoc_image(marker.getData().getLoc_image());
		holder.setLoc_latitude(marker.getData().getLoc_latitude());
		holder.setLoc_longitude(marker.getData().getLoc_longitude());
		holder.setLoc_link(marker.getData().getLoc_link());
		holder.setLoc_promotionId(marker.getData().getLoc_promotionId());
		holder.setLoc_site(marker.getData().getLoc_site());
		holder.setLoc_subCateId(marker.getData().getLoc_subCateId());
		holder.setLoc_telephone(marker.getData().getLoc_telephone());
		holder.setLoc_time(marker.getData().getLoc_time());
		holder.setLoc_title(marker.getData().getLoc_title());
		if (type.equalsIgnoreCase("events")) {
			Intent intent = new Intent(getActivity(), Events_SingleDetail.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.putExtra("poi", holder);
			intent.putExtra("type", "events");
			startActivity(intent);
			mActivity.overridePendingTransition(R.anim.incoming,
					R.anim.outgoing);
		} else if (type.equalsIgnoreCase("promotions")) {
			Intent intent = new Intent(getActivity(),
					SpecificPOI_Activity.class);
			intent.putExtra("poi", holder);
			intent.putExtra("type", "promotions");
			startActivity(intent);
			mActivity.overridePendingTransition(R.anim.incoming,
					R.anim.outgoing);
		} else {
			Intent intent = new Intent(getActivity(),
					SpecificPOI_Activity.class);
			intent.putExtra("poi", holder);
			intent.putExtra("type", "sobre");
			startActivity(intent);
			mActivity.overridePendingTransition(R.anim.incoming,
					R.anim.outgoing);
		}
		Logger.d(tag, "Exit markerTouched()");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateDataOnZoom() {
		super.updateDataOnZoom();
		// Location last = ARData.getCurrentLocation();
		updateData(jsonRoot);
		// updateData(last.getLatitude(), last.getLongitude(),
		// last.getAltitude());
	}

	// private void updateData(final double lat, final double lon, final double
	// alt) {
	// try {
	// exeService.execute(new Runnable() {
	//
	// @Override
	// public void run() {
	// for (NetworkDataSource source : sources.values())
	// download(source, lat, lon, alt);
	// }
	// });
	// } catch (RejectedExecutionException rej) {
	// Log.w(TAG, "Not running new download Runnable, queue is full.");
	// } catch (Exception e) {
	// Log.e(TAG, "Exception running download Runnable.", e);
	// }
	// }
	private void updateData(final JSONObject root) {
		try {
			exeService.execute(new Runnable() {

				@Override
				public void run() {
					for (NetworkDataSource source : sources.values())
						parse(source, root);
					// download(source, lat, lon, alt);
				}
			});
		} catch (RejectedExecutionException rej) {
			// Crittercism.logHandledException(rej);
			Log.w(tag, "Not running new download Runnable, queue is full.");
		} catch (Exception e) {
			Crittercism.logHandledException(e);
			Log.e(tag, "Exception running download Runnable.", e);
		}
	}

	private static boolean parse(NetworkDataSource source, JSONObject root) {
		if (source == null)
			return false;
		if (root == null)
			return false;
		List<Marker> markers = null;
		try {
			markers = source.parse(root);
		} catch (NullPointerException e) {
			return false;
		}

		ARData.addMarkers(markers);
		return true;
	}

	@Override
	public void notifyDownloadComplete() {
		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (!mActivity.isFinishing() && progress_dialog != null)
					progress_dialog.dismiss();
			}
		});
	}
}
