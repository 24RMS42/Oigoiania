package com.jwetherell.augmented_reality.ui.objects;

import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.ui.Radar;

import android.graphics.Canvas;
import android.util.Log;

/**
 * This class extends PaintableObject to draw all the Markers at their
 * appropriate locations.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class PaintableRadarPoint extends PaintableObject {

	private static final String tag = "PaintableRadarPoint";
	private final float[] locationArray = new float[3];
	private PaintablePoint paintablePoint = null;
	private PaintablePosition pointContainer = null;
	private Marker marker = null;

	public PaintableRadarPoint(Marker marker) {
		this.marker = marker;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Canvas canvas) {
		if (canvas == null)
			throw new NullPointerException();
		// Log.d(tag, "==============ENTER paint()==================");
		float radius = ARData.getRadius();
		// Draw the markers in the circle
		float range = radius * 1000;
		float scale = range / Radar.RADIUS;
		// Log.d(tag, "range is  " + range + " scale is " + scale +
		// " radius is " + radius + " Radar.RADIUS is " + Radar.RADIUS);
		// for (Marker pm : ARData.getMarkers()) {
		// Log.d(tag, "Marker is " + marker.getName() + ", Location is " +
		// marker.getLocation());
		marker.getLocation().get(locationArray);
		float x = locationArray[0] / scale;
		float y = locationArray[2] / scale;
		// Log.d(tag, "Vector x,y,z {" + locationArray[0] + "," +
		// locationArray[1] + "," + locationArray[2] +
		// "}  and scale coords are: {" + x + "," + y + "}");
		// Log.d(tag, "X*X is " + (x*x) + ", y*y is " + (y*y) +
		// ", (x*x + y*y) is " + (x*x + y*y) + ", Radar.RADIUS Squared is " +
		// (Radar.RADIUS * Radar.RADIUS));
		if ((x * x + y * y) < (Radar.RADIUS * Radar.RADIUS)) {
			if (paintablePoint == null)
				paintablePoint = new PaintablePoint(marker.getColor(), true);
			else
				paintablePoint.set(marker.getColor(), true);

			if (pointContainer == null)
				pointContainer = new PaintablePosition(paintablePoint, (x
						+ Radar.RADIUS - 1), (y + Radar.RADIUS - 1), 0, 1);
			else
				pointContainer.set(paintablePoint, (x + Radar.RADIUS - 1), (y
						+ Radar.RADIUS - 1), 0, 1);

			pointContainer.paint(canvas);
		}
		// }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getWidth() {
		return Radar.RADIUS * 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getHeight() {
		return Radar.RADIUS * 2;
	}
}
