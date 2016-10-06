package com.oigoiania.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.ParseException;
import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;
import ch.boye.httpclientandroidlib.params.BasicHttpParams;
import ch.boye.httpclientandroidlib.params.HttpConnectionParams;
import ch.boye.httpclientandroidlib.params.HttpParams;
import ch.boye.httpclientandroidlib.protocol.BasicHttpContext;
import ch.boye.httpclientandroidlib.util.EntityUtils;

import com.crittercism.app.Crittercism;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.util.Util;

public abstract class BaseRequestListener implements Runnable {
	protected static final String baseUrl = "http://www.oigoiania.com.br/services/";
	// protected static final String baseUrl =
	// "http://192.168.2.125/oigoiania/services/";

	protected String url = "";
	protected ArrayList<BasicNameValuePair> array = null;// new
															// ArrayList<BasicNameValuePair>();
	protected RequestCallBack mRequestCallBack = null;
	private HttpURLConnection conn = null;
	private InputStream is = null;
	private InputStreamReader reader = null;
	private static final String tag = "BaseRequestListener";

	// ////////////////////////////////
	private HttpPost httppost = null;
	private HttpResponse mHttpResponse = null;
	private HttpParams httpParams = null;
	private DefaultHttpClient mDefaultHttpClient = null;
	private BasicHttpContext mBasicHttpContext = null;

	// private HttpHost targetHost = null;

	/*
	 * Abstarct methods
	 */
	protected abstract void returnResponse(String resonse);

	@Override
	public void run() {
		// HttpURLConnection.setFollowRedirects(true);
		// HttpURLConnection con = null;
		// try {
		// con = (HttpURLConnection) new URL("http://www.google.com")
		// .openConnection();
		// con.setReadTimeout(5 * 1000 /* milliseconds */);
		// con.setConnectTimeout(5 * 1000 /* milliseconds */);
		// con.setInstanceFollowRedirects(true);
		// con.setRequestMethod("HEAD");
		// con.connect();
		// int status = con.getResponseCode();
		// if ((status == HttpURLConnection.HTTP_OK)
		// || (status == HttpURLConnection.HTTP_MOVED_TEMP)) {
		for (int i = 0; i < 3; i++) {
			try {
				Logger.d(tag, "loop iteration # " + (i + 1));
				makehttpRequest();
				// makeConnection();
				break;
			} catch (Exception e) {
				if (i == 2) {
					Logger.d(tag, "Going to Exit requist....");
					//mRequestCallBack.onRequestFail(Util.exitCode);
					mRequestCallBack.onRequestFail(Util.noInterNetCode);
				} else {
					Logger.d(tag, "Request Failed trying again....");
					mRequestCallBack
							.onRequestFail("Error occur during connecting with server.Trying again.!");
				}
				Crittercism.logHandledException(e);
				e.printStackTrace();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					Crittercism.logHandledException(e);
					e1.printStackTrace();
				}
			}
		}
		// }
		// else {
		// Logger.d(tag, "Else - Status is " + status);
		// mRequestCallBack.onRequestFail(Util.noInterNetCode);
		// }
		// } catch (MalformedURLException e) {
		// Logger.d(tag, "MalformedURLException " + e.getMessage());
		// mRequestCallBack.onRequestFail(Util.noInterNetCode);
		// e.printStackTrace();
		// } catch (IOException e) {
		// Logger.d(tag, "IOException " + e.getMessage());
		// mRequestCallBack.onRequestFail(Util.noInterNetCode);
		// e.printStackTrace();
		// }
		// finally {
		// if (con != null)
		// con.disconnect();
		// }

	}

	@SuppressWarnings("unused")
	private void makeConnection() throws Exception {
		try {

			URL urll = new URL(url.replace(" ", "+"));
			Logger.d(tag, "Uri" + urll.toString());
			conn = (HttpURLConnection) urll.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Type", "application/json");
			// conn.setRequestProperty("Content-Length",
			// Integer.toString(mms.length));
			conn.connect();
			is = conn.getInputStream();
			reader = new InputStreamReader(is);
			char[] buffer = new char[1024];
			String str = "";
			while (reader.read(buffer) > 0) {
				str = str + new String(buffer);

			}
			returnResponse(str.trim());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
				if (reader != null)
					reader.close();
				if (conn != null)
					conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void makehttpRequest() throws Exception {
		Logger.d(tag, "makehttpRequest()");
		httppost = new HttpPost(url);
		Logger.d(tag, "URL IS :" + url);
		// targetHost = new HttpHost("www.oigoiania.com.br", 80, "http");

		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
		if (array != null)
			httppost.setEntity(new UrlEncodedFormEntity(array));
		mDefaultHttpClient = new DefaultHttpClient(httpParams);
		mBasicHttpContext = new BasicHttpContext();
		try {
			mHttpResponse = mDefaultHttpClient.execute(httppost,
					mBasicHttpContext);
			String responseBody = EntityUtils.toString(mHttpResponse
					.getEntity());
			returnResponse(responseBody.trim());
		} catch (ParseException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (ch.boye.httpclientandroidlib.client.ClientProtocolException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
