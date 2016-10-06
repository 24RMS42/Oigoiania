package com.oigoiania.augmentedreality;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.ui.Radar;

/**
 * This class extends the View class and is designed draw the zoom bar, radar
 * circle, and markers on the View.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class AugmentedRedarView extends View {

	private AtomicBoolean drawing = new AtomicBoolean(false);

	private Radar radar = new Radar();
	@SuppressWarnings("unused")
	private float[] locationArray = new float[3];
	private List<Marker> cache = new ArrayList<Marker>();
	@SuppressWarnings("unused")
	private TreeSet<Marker> updated = new TreeSet<Marker>();
	@SuppressWarnings("unused")
	private int COLLISION_ADJUSTMENT = 100;
	private Marker marker = null;

	public AugmentedRedarView(Context context, Marker marker) {
		super(context);
		this.marker = marker;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas == null)
			return;

		if (drawing.compareAndSet(false, true)) {

			// going to uncomment code////////
			// Get all the markers
			List<Marker> collection = null;// ARData.getMarkers();

			// Prune all the markers that are out of the radar's radius (speeds
			// up drawing and collision detection)
			cache.clear();
			if (marker != null) {
				// for (Marker m : collection) {
				marker.update(canvas, 0, 0);
				if (marker.isOnRadar() && marker.isInView())
					cache.add(marker);
				// }
			}
			collection = cache;

//			if (AugmentedReality.useCollisionDetection)
//				adjustForCollisions(canvas, collection);

			// Draw AR markers in reverse order since the last drawn should be
			// the closest
			// ListIterator<Marker> iter = collection.listIterator(collection
			// .size());
			// while (iter.hasPrevious()) {
			// Marker marker = iter.previous();
			// marker.draw(canvas);
			// }
			marker.draw(canvas, true);
			// Radar circle and radar markers
			// till this//////////////
			if (AugmentedReality.showRadar)
				radar.draw(canvas, marker);
			drawing.set(false);
		}
	}

//	private static void adjustForCollisions(Canvas canvas,
//			List<Marker> collection) {
//		updated.clear();
//
//		// Update the AR markers for collisions
//		for (Marker marker1 : collection) {
//			if (updated.contains(marker1) || !marker1.isInView())
//				continue;
//
//			int collisions = 1;
//			for (Marker marker2 : collection) {
//				if (marker1.equals(marker2) || updated.contains(marker2)
//						|| !marker2.isInView())
//					continue;
//
//				if (marker1.isMarkerOnMarker(marker2)) {
//					marker2.getLocation().get(locationArray);
//					float y = locationArray[1];
//					float h = collisions * COLLISION_ADJUSTMENT;
//					locationArray[1] = y + h;
//					marker2.getLocation().set(locationArray);
//					marker2.update(canvas, 0, 0);
//					collisions++;
//					updated.add(marker2);
//				}
//			}
//			updated.add(marker1);
//		}
//	}
}
