package com.jwetherell.augmented_reality.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MyTestMarker extends Marker {
	private final String tag = "MyTestMarker";

	public MyTestMarker(String loc_id, String name, double latitude,
			double longitude, double altitude, int color, Bitmap bitmap) {
		super(loc_id, name, latitude, longitude, altitude, color, bitmap);
	}

	@Override
	public synchronized void draw(Canvas canvas, boolean isjusthowDot) {
		super.draw(canvas, isjusthowDot);
	}

}
