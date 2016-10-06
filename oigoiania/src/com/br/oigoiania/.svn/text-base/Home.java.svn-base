package com.br.oigoiania;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.oigoiania.logger.Logger;

public class Home extends SensorActivity implements OnClickListener {
	private ImageView Research_image, contactus_image, today_image,
			category_image, promotion_image, news_image;
	private String tag = "Home";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Logger.d(tag, "Enter onCreate");
		setContentView(R.layout.home);
		// initialization of the views
		initial();
		ListenerCollection();
		try {
			Logger.d(
					tag,
					"Code : "
							+ (getPackageManager().getPackageInfo(
									getPackageName(), 0).versionCode));
			Logger.d(
					tag,
					"Name : "
							+ (getPackageManager().getPackageInfo(
									getPackageName(), 0).versionName));
		} catch (NameNotFoundException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
		Logger.d(tag, "Enter onCreate");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Logger.d(tag, " onPause");
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onDestroy() {
		Logger.d(tag, " onDestroy");
		super.onDestroy();
	}

	// function for initialing the Views of Activity
	public void initial() {
		Research_image = (ImageView) findViewById(R.id.img_research);
		contactus_image = (ImageView) findViewById(R.id.img_contact);
		today_image = (ImageView) findViewById(R.id.img_today);
		category_image = (ImageView) findViewById(R.id.img_categories);
		promotion_image = (ImageView) findViewById(R.id.img_promotions);
		news_image = (ImageView) findViewById(R.id.img_news);
	}

	// function for making the Listener
	public void ListenerCollection() {
		Research_image.setOnClickListener(this);
		contactus_image.setOnClickListener(this);
		today_image.setOnClickListener(this);
		category_image.setOnClickListener(this);
		promotion_image.setOnClickListener(this);
		news_image.setOnClickListener(this);
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_research:
			Logger.d(tag, " click on search Button");
			intent = new Intent(getApplicationContext(), SearchActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			break;
		case R.id.img_contact:
			Logger.d(tag, " click on contact-us Button");
			intent = new Intent(getApplicationContext(),
					ContactusActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			// overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			break;
		case R.id.img_today:
			Logger.d(tag, " click on Today Button");
			intent = new Intent(getApplicationContext(),
					com.br.oigoiania.TodayActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			// overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			break;
		case R.id.img_categories:
			Logger.d(tag, " click on Category Button");
			intent = new Intent(getApplicationContext(), CategoryActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			break;
		case R.id.img_promotions:
			Logger.d(tag, " click on Promotion Button");
			intent = new Intent(getApplicationContext(),
					Promotions_Activity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			break;
		case R.id.img_news:
			Logger.d(tag, " click on News Button");
			intent = new Intent(getApplicationContext(), News_Activity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			overridePendingTransition(R.anim.incoming, R.anim.outgoing);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onSensorChange(SensorEvent event) {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
}
