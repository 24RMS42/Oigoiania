package com.jwetherell.augmented_reality.activity;

import android.content.Context;
import android.content.res.Configuration;

public class AugmentedRealityUtil {
	public static final boolean portrait=true;
	private static Context context=null;
	public static void setContext(Context c){
		context=c;
	}
	public static boolean isLargeSize() {
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
