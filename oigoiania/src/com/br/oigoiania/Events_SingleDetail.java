package com.br.oigoiania;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.util.GlobalFunctions;

public class Events_SingleDetail extends Activity implements OnClickListener {

	private static final String tag = "Events_SingleDetail";

	private ImageView image, upcarate = null;
	private TextView title_txt, des_txt, date_txt, distance_txt, heading_txt;
	RelativeLayout rel_distance;
	ImageLoader imageLoager = null;
	private TextView locationName = null;

	MyDataHolder extra = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Logger.d(tag, "Enter onCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.events_singledetail);
		iniilization();
		imageLoager = ImageLoader.getInstance();
		
		Logger.d(tag, "Going to exit onCreate()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		extra = (MyDataHolder) getIntent().getExtras().getSerializable("poi");// getSerializableExtra("object");
		if (extra != null) {
			Logger.d(tag, " data by Previous Activity " + extra.getName());
			title_txt.setText(" " + extra.getName());
			des_txt.setText(" " + extra.getDescription());
			date_txt.setText("Date : " + extra.getDate() + "  "
					+ extra.getTime());
			// distance_txt.setText(" "+extra.getDistance());
			imageLoager.displayImage(extra.getImage(), image);
			Location desiredLocation = new Location(
					LocationManager.GPS_PROVIDER);
			desiredLocation.setLatitude(extra.getLoc_latitude());
			desiredLocation.setLongitude(extra.getLoc_longitude());
			distance_txt.setText(""
					+ GlobalFunctions.formatDistance(GlobalFunctions
							.distanceFrom(desiredLocation)));
			heading_txt.setText(extra.getName() + "");
			locationName.setText(extra.getLoc_title());
			rel_distance.setOnClickListener(this);
			// heading_txt.setOnClickListener(this);
			upcarate.setOnClickListener(this);
		} else {
			Logger.d(tag, "Data is Null");
		}
		Logger.d(tag, "Enter onStart()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Logger.d(tag, "Enter onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Logger.d(tag, "Enter onDestroy()");
	}

	public void iniilization() {
		rel_distance = (RelativeLayout) findViewById(R.id.relativeLayout_distance);
		heading_txt = (TextView) findViewById(R.id.heading_txt);
		image = (ImageView) findViewById(R.id.event_image);
		title_txt = (TextView) findViewById(R.id.title_textview);
		des_txt = (TextView) findViewById(R.id.description_txtview);
		date_txt = (TextView) findViewById(R.id.date_time_textview);
		distance_txt = (TextView) findViewById(R.id.distance_txt);
		locationName = (TextView) findViewById(R.id.textView4);

		upcarate = (ImageView) findViewById(R.id.imageView2);
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView2:
			// case R.id.heading_txt:
			startActivity(new Intent(Events_SingleDetail.this, Home.class));
			// overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			this.finish();
			break;

		case R.id.relativeLayout_distance:
			Intent intent = new Intent(getApplicationContext(),
					SpecificPOI_Activity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (extra != null)
				intent.putExtra("poi", extra);
			intent.putExtra("type", "sobre");
			startActivity(intent);
			// overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			break;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
}
