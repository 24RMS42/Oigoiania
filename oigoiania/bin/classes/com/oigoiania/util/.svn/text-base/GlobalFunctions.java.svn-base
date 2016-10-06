package com.oigoiania.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;

@SuppressLint("UseValueOf")
public abstract class GlobalFunctions {
	private static DecimalFormat numberFormat = null;

	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2, boolean isFormated) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return new Float(dist * meterConversion).floatValue();
	}

	public static double distanceFrom(Location loc2) {
		// float[] results=new float[3];
		// Location.distanceBetween(Util.currentLocation.getLatitude(),
		// Util.currentLocation.getLongitude(), loc2.getLatitude(),
		// loc2.getLongitude(), results);
		// float d=results[0];
		return Util.currentLocation.distanceTo(loc2);
	}

	private static double roundTwoDecimals(double d) {
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		String formattedDouble = String.format(Locale.US, "%.2f", (float) d);
		// return Double.valueOf(twoDForm.format(d));
		return Double.valueOf(formattedDouble);
	}

	public static String formatDistance(double distance) {
		String str = "" + distance;
		// if (str.contains("E")) {
		// distance = Math.sqrt(distance * 10000);
		// }
		if (distance > 1000.0) {
			str = roundTwoDecimals(distance / 1000) + " Km";
		} else {
			str = roundTwoDecimals(distance) + " m";
		}

		return str;
	}

	public static NumberFormat getNumberFormatInstatnce() {
		if (numberFormat == null)
			numberFormat = (DecimalFormat) DecimalFormat.getInstance();
		return numberFormat;
	}

	public static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
	}

	public static boolean isLargeSize(Context context) {
		boolean isScreenSize_Xlarge = false;
		// Determine screen size
		if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			isScreenSize_Xlarge = true;
			// Toast.makeText(context, "Large screen",
			// Toast.LENGTH_LONG).show();

		} else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			// Toast.makeText(context, "Normal sized screen",
			// Toast.LENGTH_LONG).show();
			isScreenSize_Xlarge = true;
		} else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			// Toast.makeText(context, "Normal sized screen",
			// Toast.LENGTH_LONG).show();
			isScreenSize_Xlarge = false;
		} else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			// Toast.makeText(context, "Small sized screen",
			// Toast.LENGTH_LONG).show();
			isScreenSize_Xlarge = false;
		} else {
			// Toast.makeText(context,
			// "Screen size is neither large, normal or small",
			// Toast.LENGTH_LONG).show();
			isScreenSize_Xlarge = false;
		}

		return isScreenSize_Xlarge;

	}

}
