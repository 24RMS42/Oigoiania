package com.jwetherell.augmented_reality.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.jwetherell.augmented_reality.activity.AugmentedRealityUtil;
import com.jwetherell.augmented_reality.common.Calculator;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.objects.PaintableIcon;
import com.jwetherell.augmented_reality.ui.objects.PaintablePosition;

public class MyCustomRedar extends View {
	private static PaintablePosition circleContainer = null;
	private static final int PAD_X = 100;//100;
	private static final int PAD_Y = 100;//100;
	public static final int RADIUS = 100;//48;
	private static final int RADAR_COLOR = Color.argb(100, 0, 0, 200);
	private Bitmap bitmap = null;

	public MyCustomRedar(Context context, Bitmap bitmap) {
		super(context);
		this.bitmap = bitmap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas == null)
			throw new NullPointerException();

		// Update the pitch and bearing using the phone's rotation matrix
		Calculator.calcPitchBearing(ARData.getRotationMatrix());
		ARData.setAzimuth(Calculator.getAzimuth());

		if (AugmentedRealityUtil.portrait) {
			canvas.save();
			canvas.translate(5, canvas.getHeight() - 5);
			canvas.rotate(-90);
		}
		drawCustomCompas(canvas);
	}

	private void drawCustomCompas(Canvas canvas) {
		if (canvas == null)
			throw new NullPointerException();

		if (circleContainer == null) {
			PaintableIcon paintAbleObject = new PaintableIcon(bitmap, 190
					, 190);
			circleContainer = new PaintablePosition(paintAbleObject, PAD_X
					, PAD_Y , 0, 1);
		}
		circleContainer.paint(canvas);
	}

}
