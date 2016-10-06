package com.oigoiania.util;

import java.text.DecimalFormat;

import org.json.JSONObject;

import android.location.Location;

/*
 * Contains all data that is used globally.
 */
public abstract class Util {
	/*
	 * Augmented reality related data
	 */
	public static final String exitCode = "close1122";
	public static final String noInterNetCode = "notInternet1122";
	public static int nmberOfTries = 0;
	public static float currentBearing = 0;
	public static Location currentLocation = null;
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("@#");
	public static JSONObject rootJsonObject = null;
	public static JSONObject rootJsonObject_catSubcat = null;

	public static final boolean useDefaultLocation = false;
	public static final double defaultLatitude = -16.690654;
	public static final double defaultLongitude = -49.280307;
	public static final boolean isDebugMode = false;
	public static final boolean remoteLoggingEnabled = false;
	public static float radius = /* 15000 */100 /*
												 * Radius used to get Locations
												 * with in specified radius in
												 * Km
												 */;
	public static float current_zoomlevel = /* 15000 */100 /*
															 * Zoom level is
															 * used to show
															 * augmented view,if
															 * distance is
															 * between given
															 * zoom level. This
															 * is in Km
															 */;
	public static final float maximum_zoomlevel = /* 15000 */100 /*
																 * Zoom level is
																 * used to show
																 * augmented
																 * view,if
																 * distance is
																 * between given
																 * zoom level.
																 * This is in Km
																 */;

	public static final int portNo = 5000;
	public static final String REMOTE_LOGGING_SERVER = "66.29.157.155" /* "192.168.2.110" */;// IP
																								// Address
																								// for
																								// Remote
																								// Logging
																								// */
	public static final String distanceChanged = "com.oigoiania.br.distanceChanged";

}
