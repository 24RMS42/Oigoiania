package com.br.oigoiania;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.br.oigoiania.R;
import com.crittercism.app.CritterCallback;
import com.crittercism.app.CritterUserData;
import com.crittercism.app.CritterUserDataRequest;
import com.crittercism.app.Crittercism;
import com.crittercism.app.CrittercismConfig;
import com.oigoiania.logger.Logger;
import com.oigoiania.util.Util;

public class SplashActivity extends SensorActivity implements OnClickListener {
	private RelativeLayout mainLayout = null;
	private RelativeLayout enterButton = null;
	private final String tag = "SplashActivity";
	private boolean shouldIncludeVersionCode = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		CrittercismConfig config = new CrittercismConfig();
		config.setVersionCodeToBeIncludedInVersionString(shouldIncludeVersionCode);
		config.setLogcatReportingEnabled(true);
		Crittercism.initialize(getApplicationContext(),
				"51de71cc8b2e335925000002", config);

		// Crittercism.initialize(getApplicationContext(),
		// "51de71cc8b2e335925000002");
		if (Util.remoteLoggingEnabled)
			Logger.setRemoteLoggingEnabled(getApplicationContext());
		Logger.d(tag, "Enter onCreate");
		setContentView(R.layout.splashactivity);
		//********Check Camera Availableity*************//
		PackageManager pm = getPackageManager();

		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			showNoCameraDilaog();
		}
		
		mainLayout = (RelativeLayout) findViewById(R.id.rl_splash);
		enterButton = (RelativeLayout) findViewById(R.id.enterbutton);
		enterButton.setOnClickListener(this);
		mainLayout.setOnClickListener(this);
		Logger.d(tag, "Exit onCreate");
		crittercism();

		// //SharedPreferences pref = getSharedPreferences("settings",
		// MODE_PRIVATE);
		// float distance = pref.getFloat("diatance", 10000);
		// Util.current_zoomlevel = distance / 1000;

	}

	@Override
	public void onClick(View v) {
		Logger.d(tag, "Clicke To go to Home");
		startActivity(new Intent(this, Home.class));
		overridePendingTransition(R.anim.incoming, R.anim.outgoing);
	}

	public void crittercism() {
		// Instantiate callback object.
		CritterCallback cb = new CritterCallback() {
			// CritterCallback is an interface that requires you to implement
			// onCritterDataReceived(CritterUserData).
			@Override
			public void onCritterDataReceived(CritterUserData userData) {
				boolean crashedOnLastLoad = userData.crashedOnLastLoad();
				// ...do something with crashedOnLastLoad
			}
		};

		// Instantiate data request object, and specify that it should include
		// information on whether the previous app session crashed.
		CritterUserDataRequest request = new CritterUserDataRequest(cb)
				.requestDidCrashOnLastLoad();
		Logger.d(tag, "crittercism()");
		// Fire off the request.
		request.makeRequest();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onSensorChange(SensorEvent event) {
	}
	private void showNoCameraDilaog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("Mensagem");
		builder.setMessage("A câmera não está disponível");
		builder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						mActivity.finish();
					}
				});
		builder.setCancelable(false);
		builder.create().show();
	}

}
