package com.oigoiania.fragments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.GlobalFunctions;
import com.oigoiania.util.Util;

public class SpecificGoogleMapviewFragment extends Fragment implements
		OnClickListener {
	private GoogleMap mMap;
	private float previousBearing = 0;
	protected static final String tag = "SpecificGoogleMapviewFragment";
	private MyDataHolder dataholder;
	private TextView distance;
	private TextView standred_btn, satallite_btn,tvcar,tvtrain,tvwalk,tvcycle;
	Intent intent;
	Polyline line = null;
	public double src_lat = 0;
	public double src_lng = 0;
	private LinearLayout car, train, walk, cycle;
	public double des_lat = 0;
	public double des_lng = 0;
	private List<LatLng> pontos;
	public ProgressDialog dialog = null;
	LocationManager mlocationManager;
	String duration = "";
	String dist = "";
	MapView m;
	private ImageView arrowIimage = null;
	String str_car,str_train,str_walk,str_cycle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		dataholder = (MyDataHolder) bundle.getSerializable("poi");
		if (dataholder == null)
			throw new NullPointerException(
					"SpecificGoogleMapviewFragment: data sent is null");
		try {
			MapsInitializer.initialize(this.getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.specifice_mapview, container, false);
		m = (MapView) v.findViewById(R.id.mapView);
		distance = (TextView) v.findViewById(R.id.distance_txtview);
		standred_btn = (TextView) v.findViewById(R.id.standerd_button);
		satallite_btn = (TextView) v.findViewById(R.id.satallite_button);
		arrowIimage = (ImageView) v.findViewById(R.id.image_arrow);
		car = (LinearLayout) v.findViewById(R.id.drivingmode);
		train = (LinearLayout) v.findViewById(R.id.train);
		walk = (LinearLayout) v.findViewById(R.id.walkinggmode);
		cycle = (LinearLayout) v.findViewById(R.id.cyclemode);
		
		tvcar = (TextView) v.findViewById(R.id.car_time);
		tvtrain = (TextView) v.findViewById(R.id.train_time);
		tvwalk = (TextView) v.findViewById(R.id.walk_time);
		tvcycle = (TextView) v.findViewById(R.id.cycle_time);

		standred_btn.setOnClickListener(this);
		satallite_btn.setOnClickListener(this);

		car.setOnClickListener(this);
		train.setOnClickListener(this);
		walk.setOnClickListener(this);
		cycle.setOnClickListener(this);

		m.onCreate(savedInstanceState);
		loadMap();
		return v;

	}

	@Override
	public void onResume() {
		super.onResume();
		m.onResume();
		// setupMap();

	}

	@Override
	public void onPause() {

		super.onPause();

		m.onPause();

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		m.onDestroy();

	}

	@Override
	public void onLowMemory() {

		super.onLowMemory();

		m.onLowMemory();

	}

	// Maps is initialized and Set the Marker.
	public void loadMap() {
		mMap = m.getMap();
		// adding the Marker to the Google map
		mMap.addMarker(new MarkerOptions()
				.position(
						new LatLng(dataholder.getLoc_latitude(), dataholder
								.getLoc_longitude()))
				.title(dataholder.getLoc_title() != null ? dataholder
						.getLoc_title() : dataholder.getName())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_1234)));
		/*
		 * mMap.addMarker(new MarkerOptions() .position(new
		 * LatLng(-16.677224,-49.267698)) .title("text")
		 * .icon(BitmapDescriptorFactory
		 * .fromResource(R.drawable.marker_1234)));
		 */

		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			private final View window = inflater.inflate(
					R.layout.custom_infowindow, null);

			@Override
			public View getInfoWindow(Marker marker) {

				String title = marker.getTitle();
				TextView txtTitle = ((TextView) window
						.findViewById(R.id.title_txtview));
				if (title != null) {
					Logger.d(tag, " Title Name:" + title.toString());
					txtTitle.setText(title);
				} else {
					txtTitle.setText("");
				}
				return window;
			}

			@Override
			public View getInfoContents(Marker arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition position) {
				Logger.d(tag, "Camera is rotated: " + position.bearing);
				animateArrow(position.bearing);

			}
		});

		// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
		// -16.660846, -49.279111), 13.0f));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Util.currentLocation.getLatitude(),
						Util.currentLocation.getLongitude()), 13.0f));

		// for enabling/disenabling the View // compass,zoomView, User Location
		UiSettings obj = mMap.getUiSettings();
		obj.setZoomControlsEnabled(false);
		obj.setCompassEnabled(true);
		mMap.setMyLocationEnabled(true);
		Location desiredLocation = new Location(LocationManager.GPS_PROVIDER);
		desiredLocation.setLatitude(dataholder.getLoc_latitude());
		desiredLocation.setLongitude(dataholder.getLoc_longitude());
		Logger.d(tag, "Latitude :" + dataholder.getLoc_latitude()
				+ "Logitude : " + dataholder.getLoc_longitude());
		distance.setText("Distância :"
				+ GlobalFunctions.formatDistance(GlobalFunctions
						.distanceFrom(desiredLocation)));

		// for the user Current location to calculate the The Angle between the
		// current and Marker position
		Location desiredLocation2 = new Location(LocationManager.GPS_PROVIDER);
		desiredLocation.setLatitude(Util.currentLocation.getLatitude());
		desiredLocation.setLongitude(Util.currentLocation.getLongitude());
		animateArrow(desiredLocation.bearingTo(desiredLocation2));
		// List<MyDataHolder> markerlist = new ArrayList<MyDataHolder>();
		// markerlist.add(dataholder);
		// fitZoomAndPositionToMapByMarkers(markerlist);

		// user current location(source location) edited by
		// lipuhossain67@gmail.com
		// Getting Current Location From GPS
		Location location = getLastKnownLocation();
		if (location != null) {
			// onLocationChanged(location);
			src_lat = location.getLatitude();
			src_lng = location.getLongitude();
		} else {
			src_lat = Util.currentLocation.getLatitude();
			src_lng = Util.currentLocation.getLongitude();
		}
		// destination location
		des_lat = dataholder.getLoc_latitude();
		des_lng = dataholder.getLoc_longitude();

//		new GetDirection().execute("bicycling");
//		new GetDirection().execute("walking");
//		new GetDirection().execute("train");
//		new GetDirection().execute("driving");
//		
//		dialog = new ProgressDialog(getActivity());
//		dialog.setMessage("Drawing the route, please wait!");
//		dialog.setIndeterminate(false);
//		dialog.setCancelable(false);
//		dialog.show();
		
		
		
		 String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr="+des_lat+","+des_lng+" (%s)", 12f, 2f, "Where the party is at");
	        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
	        try
	        {
	            startActivity(intent);
	        }
	        catch(ActivityNotFoundException ex)
	        {
	            try
	            {
	                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	                startActivity(unrestrictedIntent);
	            }
	            catch(ActivityNotFoundException innerEx)
	            {
	                Toast.makeText(getActivity(), "Please install a maps application", Toast.LENGTH_LONG).show();
	            }
	        }

	}

	private Location getLastKnownLocation() {
		mlocationManager = (LocationManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.LOCATION_SERVICE);
		List<String> providers = mlocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			Location l = mlocationManager.getLastKnownLocation(provider);
			if (l == null) {
				continue;
			}
			if (bestLocation == null
					|| l.getAccuracy() < bestLocation.getAccuracy()) {
				// Found best last known location: %s", l);
				bestLocation = l;
			}
		}
		return bestLocation;
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
		// LatLng toCenter = new LatLng(latitudeToGo, longitudeToGo);

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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.standerd_button) {
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		} else if (v.getId() == R.id.satallite_button) {
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		} else if (v.getId() == R.id.drivingmode) {
			new GetDirection().execute("driving");
		} else if (v.getId() == R.id.train) {
			new GetDirection().execute("train");
		} else if (v.getId() == R.id.walkinggmode) {
			new GetDirection().execute("walking");
		} else if (v.getId() == R.id.cyclemode) {
			new GetDirection().execute("bicycling");
		}
	}

	// this Method Rotate the Image According to the map Rotation to the Current
	// postioon
	private void animateArrow(float bearingRelative) {
		if (arrowIimage != null) {
			RotateAnimation an = new RotateAnimation(-previousBearing,
					-bearingRelative, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			previousBearing = bearingRelative;
			an.setDuration(500);
			an.setRepeatCount(0);
			an.setFillAfter(true);
			arrowIimage.startAnimation(an);
		}
	}

	class GetDirection extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		protected String doInBackground(String... args) {
			String origin = src_lat + "," + src_lng;
			String destination = des_lat + "," + des_lng;
			String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ origin
					+ "&destination="
					+ destination
					+ "&sensor=true&mode=" + args[0];
			StringBuilder response = new StringBuilder();
			try {
				URL url = new URL(stringUrl);
				HttpURLConnection httpconn = (HttpURLConnection) url
						.openConnection();
				if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader input = new BufferedReader(
							new InputStreamReader(httpconn.getInputStream()),
							8192);
					String strLine = null;

					while ((strLine = input.readLine()) != null) {
						response.append(strLine);
					}
					input.close();
				}

				String jsonOutput = response.toString();

				JSONObject jsonObject = new JSONObject(jsonOutput);

				// routesArray contains ALL routes
				JSONArray routesArray = jsonObject.getJSONArray("routes");

				// Grab the first route
				JSONObject route = routesArray.getJSONObject(0);

				// get legs
				JSONArray legArray = route.getJSONArray("legs");

				JSONObject leg = legArray.getJSONObject(0);

				duration = leg.getJSONObject("duration").getString("text");
				dist = leg.getJSONObject("distance").getString("text");
				
				if(args[0].equals("driving")){
					str_car = duration;
				}else if(args[0].equals("train")){
					str_train = duration;

				}else if(args[0].equals("walking")){
					str_walk = duration;

				}else if(args[0].equals("bicycling")){
					str_cycle = duration;

				}

				JSONObject poly = route.getJSONObject("overview_polyline");
				String polyline = poly.getString("points");
				pontos = decodePoly(polyline);

			} catch (Exception e) {

			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			if (pontos != null) {
				tvcar.setText(str_car);
				tvtrain.setText(str_train);
				tvwalk.setText(str_walk);
				tvcycle.setText(str_cycle);
				mMap.clear();
				reinitiate();
				for (int i = 0; i < pontos.size() - 1; i++) {
					LatLng src = pontos.get(i);
					LatLng dest = pontos.get(i + 1);
					try {
						// here is where it will draw the polyline in your map
						line = mMap.addPolyline(new PolylineOptions()
								.add(new LatLng(src.latitude, src.longitude),
										new LatLng(dest.latitude,
												dest.longitude)).width(10)
								.color(Color.parseColor("#468CE5"))
								.geodesic(true));
						distance.setText("Distância :" + duration+" ("+dist+")");

					} catch (NullPointerException e) {
						Log.e("Error", "NullPointerException onPostExecute: "
								+ e.toString());
					} catch (Exception e2) {
						Log.e("Error",
								"Exception onPostExecute: " + e2.toString());
					}

				}
			} else {
				Toast.makeText(getActivity(), "no valid routes found",
						Toast.LENGTH_LONG).show();
			}
            if(dialog.isShowing())
            	dialog.dismiss();

		}
		
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}
     public void reinitiate(){
    	 mMap.addMarker(new MarkerOptions()
			.position(
					new LatLng(dataholder.getLoc_latitude(), dataholder
							.getLoc_longitude()))
			.title(dataholder.getLoc_title() != null ? dataholder
					.getLoc_title() : dataholder.getName())
			.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.marker_1234)));

	mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		private final View window = inflater.inflate(
				R.layout.custom_infowindow, null);

		@Override
		public View getInfoWindow(Marker marker) {

			String title = marker.getTitle();
			TextView txtTitle = ((TextView) window
					.findViewById(R.id.title_txtview));
			if (title != null) {
				Logger.d(tag, " Title Name:" + title.toString());
				txtTitle.setText(title);
			} else {
				txtTitle.setText("");
			}
			return window;
		}

		@Override
		public View getInfoContents(Marker arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	});
	mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

		@Override
		public void onCameraChange(CameraPosition position) {
			Logger.d(tag, "Camera is rotated: " + position.bearing);
			animateArrow(position.bearing);

		}
	});


	
     }
}
