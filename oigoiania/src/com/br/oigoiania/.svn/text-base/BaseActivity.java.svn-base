/*
 * A base class for all activities. In this class Header and custom tabs with their listeners are implemented.
 */
package com.br.oigoiania;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.br.oigoiania.R;
import com.oigoiania.util.Util;

public abstract class BaseActivity extends SensorActivity implements
		OnTabChangeListener, OnClickListener {

	private TextView mHeaderTv = null;
	private ImageView upcarate = null;
	private FragmentTabHost mTabHost = null;
	private ImageView settingBtn = null;
	protected boolean isOnPause = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabs_activity);
		mHeaderTv = (TextView) findViewById(R.id.heading_txt);
		upcarate = (ImageView) findViewById(R.id.imageView2);
		settingBtn = (ImageView) findViewById(R.id.btn_setting);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.setOnTabChangedListener(this);
		setmTabHost(mTabHost);
		setUpcarate(upcarate);
		mHeaderTv.setOnClickListener(this);
		upcarate.setOnClickListener(this);
		settingBtn.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		isOnPause = false;
	}

	@Override
	protected void onPause() {
		isOnPause = true;
		super.onPause();
	}

	// **************** ONCLICK LISTENER ********************//
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.heading_txt:
		case R.id.imageView2:
			startActivity(new Intent(BaseActivity.this, Home.class));
			overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			mActivity.finish();
			break;
		case R.id.btn_setting:
			showDialog(0);
			break;
		}

	}

	protected void setHeadingText(String headingText) {
		getmHeaderTv().setText(headingText);
	};

	protected void setTabVisibleAt(int position) {
		if (mTabHost.getTabWidget().getChildCount() < position)
			mTabHost.getTabWidget().getChildAt(position)
					.setVisibility(View.VISIBLE);
	}

	protected void setTabInVisibleAt(int position) {
		if (mTabHost.getTabWidget().getChildCount() < position)
			mTabHost.getTabWidget().getChildAt(position)
					.setVisibility(View.INVISIBLE);
	}

	protected void setTabSelectedAt(int position) {
		if (mTabHost.getTabWidget().getChildCount() < position)
			mTabHost.getTabWidget().getChildAt(position).setSelected(true);
	}

	protected void setTabEnabledAt(int position, boolean setEn_Dis) {
		mTabHost.getTabWidget().getChildAt(position).setEnabled(setEn_Dis);
	}

	protected void setTabsColor(int color) {
		for (int a = 0; a < getmTabHost().getTabWidget().getChildCount(); a++)
			getmTabHost().getTabWidget().getChildAt(a)
					.setBackgroundColor(color);
	}

	protected ImageView getSettingBtn() {
		return settingBtn;
	}

	// ////********************************************/////////////////////

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		LayoutInflater inflater = mActivity.getLayoutInflater();
		View view = inflater.inflate(R.layout.settingsdialog, null);
		final SeekBar mSeekBar = (SeekBar) view.findViewById(R.id.seekBar1);
		final TextView diatnce = (TextView) view.findViewById(R.id.distance);
		final TextView MaxDiatnce = (TextView) view.findViewById(R.id.maxdistance);
		MaxDiatnce.setText(((int)Util.maximum_zoomlevel)+" km");
		mSeekBar.setMax((int) (Util.maximum_zoomlevel * 1000));
		mSeekBar.setPressed(true);
		int progress = (int) (Util.current_zoomlevel * 1000);
		if (progress < 10)
			progress = 10;
		if (progress < 1000) {
			diatnce.setText(progress + " m");
		} else {
			diatnce.setText(((float) progress / 1000) + " km");
		}
		mSeekBar.setProgress((int) (Util.current_zoomlevel * 1000));
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress < 10)
					progress = 10;
				if (progress < 1000) {
					diatnce.setText(progress + " m");
				} else {
					diatnce.setText(((float) progress / 1000) + " km");
				}
				// if (def >= 100 || def < 0) {
				// SharedPreferences.Editor editor = mActivity.getPreferences(
				// MODE_PRIVATE).edit();
				// if (progress < 10)
				// progress = 10;
				// editor.putInt("diatance", progress);
				// editor.commit();
				// pre_progress = progress;
				// Util.maximum_zoomlevel = (float) progress / 1000;
				// Intent intent = new Intent();
				// intent.setAction(Util.distanceChanged);
				// mActivity.sendBroadcast(intent);
				// // AugmentedReality.updateDatChange();
				// }

			}
		});
		builder.setView(view).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						int progress = mSeekBar.getProgress();
						Intent intent = new Intent();
						intent.putExtra("progress", progress);
						// SharedPreferences pref = mActivity
						// .getSharedPreferences("settings", MODE_PRIVATE);
						// SharedPreferences.Editor editor = pref.edit();
						if (progress < 10)
							progress = 10;
						// editor.putFloat("diatance", progress);
						// editor.commit();
						Util.current_zoomlevel = (float) progress / 1000;
						Util.radius = (float) progress / 1000;

						intent.setAction(Util.distanceChanged);

						mActivity.sendBroadcast(intent);
						dialog.dismiss();
					}
				});
		builder.setCancelable(false);
		builder.setMessage(mActivity.getResources().getString(
				R.string.setdistane));
		return builder.create();

	}

	/**
	 * @return the mTabHost
	 */
	protected FragmentTabHost getmTabHost() {
		return mTabHost;
	}

	/**
	 * @param mTabHost
	 *            the mTabHost to set
	 */
	protected void setmTabHost(FragmentTabHost mTabHost) {
		this.mTabHost = mTabHost;
	}

	/**
	 * @return the mHeaderTv
	 */
	protected TextView getmHeaderTv() {
		return mHeaderTv;
	}

	/**
	 * @param mHeaderTv
	 *            the mHeaderTv to set
	 */
	protected void setmHeaderTv(TextView mHeaderTv) {
		this.mHeaderTv = mHeaderTv;
	}

	public ImageView getUpcarate() {
		return upcarate;
	}

	public void setUpcarate(ImageView upcarate) {
		this.upcarate = upcarate;
	}

}
