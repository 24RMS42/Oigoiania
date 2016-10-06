package com.br.oigoiania;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;

import com.br.oigoiania.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.oigoiania.logger.RemoteLoggerTask;
import com.oigoiania.util.Util;

public class Oigoiania extends Application {

	public RemoteLoggerTask remoteLoggerTask = null;

	@Override
	public void onCreate() {
		super.onCreate();
		// ExceptionHandler.register(this, "http://192.168.2.110");
		initImageLoader(getApplicationContext());
		if (Util.remoteLoggingEnabled) {
			if (remoteLoggerTask == null) {
				Log.d("logging", "Logger was null, creating new...");
				remoteLoggerTask = new RemoteLoggerTask();
				Log.d("logging", "created logger " + remoteLoggerTask);
				remoteLoggerTask.start();
				Log.d("logging", "started remoteLoggerTask...");
			} else if (remoteLoggerTask != null && !remoteLoggerTask.isAlive()) {
				Log.d("logging",
						"logger was not null, and it was not started...");
				remoteLoggerTask.interrupt();
				remoteLoggerTask.start();
			}
		}
		Locale locale = new Locale("pt_BR");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
		      getBaseContext().getResources().getDisplayMetrics());
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	public static void initImageLoader(Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.placeholder)
				.showImageOnFail(R.drawable.splash_bg)
				.showImageForEmptyUri(R.drawable.placeholder)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .enableLogging()// Not necessary in common
				.defaultDisplayImageOptions(options).build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		Locale locale = new Locale("pt_BR");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
		      getBaseContext().getResources().getDisplayMetrics());
	}
}
