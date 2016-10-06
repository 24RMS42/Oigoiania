package com.oigoiania.logger;

/**
 * @author imran
 * 
 */
import android.content.Context;
import android.util.Log;

import com.br.oigoiania.Oigoiania;
import com.oigoiania.util.Util;

public abstract class Logger {

	// private static RemoteLoggerTask remoteLoggerTask = null;
	private static Context context;

	/**
	 * @return the remoteLoggingEnabled
	 */
	public static boolean isRemoteLoggingEnabled() {
		return Util.remoteLoggingEnabled;

	}

	/**
	 * @param remoteLoggingEnabled
	 *            the remoteLoggingEnabled to set
	 */
	public static void setRemoteLoggingEnabled(Context ctx) {
		Log.d("logging", "Logger->setRemoteLoggingEnabled enter");
		context = ctx;

	}

	public static void d(String tag, String msg) {
		if (Util.isDebugMode) {
			if (isRemoteLoggingEnabled()) {
				msg = tag + "\t" + msg + "\r\n";
				((Oigoiania) context).remoteLoggerTask.log(msg);
			} // else
			Log.d(tag, msg);
		}

	}

	public static void i(String tag, String msg) {
		if (Util.isDebugMode) {
			if (isRemoteLoggingEnabled()) {
				msg = tag + "\t" + msg + "\r\n";
				((Oigoiania) context).remoteLoggerTask.log(msg);
			} // else
			Log.i(tag, msg);
		}

	}

	public static void e(String tag, String msg) {
		if (Util.isDebugMode) {
			if (isRemoteLoggingEnabled()) {
				msg = tag + "\t" + msg + "\r\n";
				((Oigoiania) context).remoteLoggerTask.log(msg);
			} // else
			Log.e(tag, msg);
		}

	}

	public static void w(String tag, String msg) {
		if (Util.isDebugMode) {
			if (isRemoteLoggingEnabled()) {
				msg = tag + "\t" + msg + "\r\n";
				((Oigoiania) context).remoteLoggerTask.log(msg);
			} // else
			Log.w(tag, msg);
		}

	}

}
