package com.oigoiania.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.oigoiania.Events_SingleDetail;
import com.br.oigoiania.R;
import com.br.oigoiania.SpecificPOI_Activity;
import com.crittercism.app.Crittercism;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

public class GeneralGoogleMapviewFragment extends Fragment {

	private GoogleMap mMap;
	protected static final String tag = "matata";
	private List<MyDataHolder> list = new ArrayList<MyDataHolder>();
	private JSONObject jsonObj;
	private MapView m;
	private String extra = null;
	private String type = null;
	private Activity mActivity = null;
	private static GeneralGoogleMapviewFragment instance = null;

	public static GeneralGoogleMapviewFragment getInstance() {
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		mActivity = getActivity();
		Logger.d(tag, "Enter onCreate()");
		Bundle bundle = getArguments();
		extra = bundle.getString("json");
		type = bundle.getString("type");
		try {
			MapsInitializer.initialize(this.getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
		Logger.d(tag, "Exit onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(tag, "Enter onCreateView()");
		if (extra != null) {
			try {
				jsonObj = new JSONObject(extra);
				Log.d(tag, "Extra value is :" + extra.toString());
				list.clear();
				list.addAll(Parser.parse(jsonObj));
				for (int i = 0; i < list.size(); i++) {
					Log.e(tag, "Distance: " + list.get(i).getDistance());
				}
			} catch (JSONException e) {
				Crittercism.logHandledException(e);
				e.printStackTrace();
			}
		}
		View v = inflater.inflate(R.layout.mapview, container, false);
		m = (MapView) v.findViewById(R.id.mapView);
		m.onCreate(savedInstanceState);
		loadMap(list);
		return v;

	}

	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
		Logger.d(tag, "onResume()");
		// setupMap();

	}

	@Override
	public void onPause() {

		super.onPause();
		Logger.d(tag, "onPause()");
		m.onPause();

	}

	@Override
	public void onDestroy() {
		Logger.d(tag, "onDestroy()");
		m = null;
		// m.onDestroy();
		super.onDestroy();

	}

	@Override
	public void onLowMemory() {

		super.onLowMemory();

		m.onLowMemory();

	}

	public void loadMap(List<MyDataHolder> markerlist) {
		Log.d(tag, "Enter loadMap()");
		mMap = m.getMap();
		MyDataHolder todaymarker;
		if (markerlist != null) {
			Log.d(tag, "google map=======================" + markerlist.size());
			for (int i = 0; i < markerlist.size(); i++) {
				todaymarker = markerlist.get(i);
				Log.d(tag, "adding marker=============" + todaymarker.getLoc_latitude());
				// Logger.d(
				// TAG,
				// " Marker Detail : " + " Latitude "
				// + todaymarker.getLocation().getLatitude() + " Longitude"
				// + todaymarker.getLocation().getLongitude() + " title "
				// + todaymarker.getEvents().getTitle());
				try {
					MapsInitializer.initialize(getActivity());
				} catch (GooglePlayServicesNotAvailableException e) {
					Crittercism.logHandledException(e);
					e.printStackTrace();
				}
				mMap.addMarker(new MarkerOptions()
						.position(
								new LatLng(todaymarker.getLoc_latitude(),
										todaymarker.getLoc_longitude()))
						.title(todaymarker.getLoc_title())
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.marker_icon)));
			}
		}

		// mMap.addMarker(new MarkerOptions()
		// .position(
		// new LatLng(Util.currentLocation.getLatitude(),
		// Util.currentLocation.getLongitude()))
		// .title("My location")
		// .icon(BitmapDescriptorFactory
		// .fromResource(R.drawable.mylocation)));
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				MyDataHolder todaymarker1 = null;
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {

						todaymarker1 = (MyDataHolder) list.get(i);
						if (todaymarker1.getLoc_title().equals(
								marker.getTitle()))
							break;
					}
				}
				Log.d(tag, "======type = " + type);
				if (type == null) //by matata 20160307
					type = "";
				
				if (type.equalsIgnoreCase("events")) {
					Intent intent = new Intent(getActivity(),
							Events_SingleDetail.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.putExtra("poi", todaymarker1);
					intent.putExtra("type", "events");
					startActivity(intent);
					mActivity.overridePendingTransition(R.anim.incoming,
							R.anim.outgoing);
				} else if (type.equalsIgnoreCase("promotions")) {
					Intent intent = new Intent(getActivity(),
							SpecificPOI_Activity.class);
					intent.putExtra("poi", todaymarker1);
					intent.putExtra("type", "promotions");
					startActivity(intent);
					mActivity.overridePendingTransition(R.anim.incoming,
							R.anim.outgoing);
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(), SpecificPOI_Activity.class);
					intent.putExtra("poi", todaymarker1);
					intent.putExtra("type", "sobre");
					startActivity(intent);
					mActivity.overridePendingTransition(R.anim.incoming,
							R.anim.outgoing);
				}
				return true;
			}
		});

		UiSettings obj = mMap.getUiSettings();
		obj.setZoomControlsEnabled(false);

		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Util.currentLocation.getLatitude(),
						Util.currentLocation.getLongitude()), 13.0f));

		// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
		// -16.660846, -49.279111), 13.0f));
		mMap.setMyLocationEnabled(true);
		// fitZoomAndPositionToMapByMarkers(markerlist);

	}

	public void fitZoomAndPositionToMapByMarkers(List<MyDataHolder> markerlist) {

		int minLat = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int minLon = Integer.MAX_VALUE;
		int maxLon = Integer.MIN_VALUE;

		for (MyDataHolder item : markerlist) {

			int lat = (int) (item.getLoc_latitude() * 1E6);
			int lon = (int) (item.getLoc_longitude() * 1E6);

			maxLat = Math.max(lat, maxLat);
			minLat = Math.min(lat, minLat);
			maxLon = Math.max(lon, maxLon);
			minLon = Math.min(lon, minLon);
		}

		double latitudeToGo = (maxLat + minLat) / 1E6 / 2;
		double longitudeToGo = (maxLon + minLon) / 1E6 / 2;
		LatLng toCenter = new LatLng(latitudeToGo, longitudeToGo);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(toCenter));
		// centerCameraToProperPosition(toCenter);

		LatLng southWestLatLon = new LatLng(minLat / 1E6, minLon / 1E6);
		LatLng northEastLatLon = new LatLng(maxLat / 1E6, maxLon / 1E6);

		zoomInUntilAllMarkersAreStillVisible(southWestLatLon, northEastLatLon);
	}

	private void zoomInUntilAllMarkersAreStillVisible(
			final LatLng southWestLatLon, final LatLng northEastLatLon) {

		mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition arg0) {

				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
						new LatLngBounds(southWestLatLon, northEastLatLon), 50));
				mMap.setOnCameraChangeListener(null);

			}
		});

	}

	public void dataSetNotifyChanged(String json) {
		Logger.e(tag, "enter dataSetNotifyChanged");
		if (m != null) {
			Logger.e(tag, "enter if");
			extra = json;
			list.clear();
			try {
				list.addAll(Parser.parse(jsonObj));
				m.invalidate();
				loadMap(list);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
