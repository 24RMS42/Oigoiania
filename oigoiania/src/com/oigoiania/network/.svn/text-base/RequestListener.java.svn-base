package com.oigoiania.network;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import ch.boye.httpclientandroidlib.message.BasicNameValuePair;

import com.crittercism.app.Crittercism;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;

public class RequestListener extends BaseRequestListener {
	private final String tag = "RequestListener";

	public void requestFOrResearch(RequestCallBack mRequestCallBack, String key) {
		Logger.d(tag, "requestFOrResearch");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_search_.php?param=" + key;
	}

	// Request for today events
	public void requestFOrTodayEvents(RequestCallBack mRequestCallBack,
			String key) {
		Logger.d(tag, "requestFOrTodayEvents");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_TodayEventNow_.php";
	}

	// Request for category names
	public void requestFOrCategory(RequestCallBack mRequestCallBack, String key) {
		Logger.d(tag, "requestFOrCategory");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "CategoriesName.php";
	}

	/*
	 * Radius is in KM
	 */
	// _Categories_Haversine.php?radius=30&latitude=31.501148&longitude=74.322432
	public void requestFOrCategory(RequestCallBack mRequestCallBack,
			double latitude, double longitude, float radius) {
		Logger.d(tag, "requestFOrCategory : Haversine");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_Categories_Haversine.php?radius=" + radius
				+ "&latitude=" + latitude + "&longitude=" + longitude;
	}

	public void requestFOrSubCategoryPOIs(RequestCallBack mRequestCallBack,
			int catId, int subCatId) {
		Logger.d(tag, "requestFOrSubCategoryPOIs");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_SubCategoryPOIs_.php?catId=" + catId + "&subCatId="
				+ subCatId;
	}

	public void requestFOrSubCategory(RequestCallBack mRequestCallBack,
			double latitude, double longitude, float radius, int catId) {
		Logger.d(tag, "requestFOrCategory : Haversine");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_SubCategories_Haversine.php?radius=" + radius
				+ "&latitude=" + latitude + "&longitude=" + longitude
				+ "&catId=" + catId;
	}

	public void requestFOrPromotion_Data(RequestCallBack mRequestCallBack,
			String key) {
		Logger.d(tag, "requestFOrPromotion");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_TodayPromotions_.php";
	}

	public void requestFOrImages(RequestCallBack mRequestCallBack, String key) {
		Logger.d(tag, "requestFOrImages ");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_SpecificPOIImages_.php?loc_id=" + key;
	}

	public void requestFOrNews(RequestCallBack mRequestCallBack, String key) {
		Logger.d(tag, "requestFOrNews ");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_NewEntries_.php";
	}

	public void requestFOrPOI_Info(RequestCallBack mRequestCallBack, String key) {
		Logger.d(tag, "requestFOrNews ");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "_POI_Info_.php?loc_id=" + key;
	}
	public void requestFOrEmailSend(RequestCallBack mRequestCallBack, ArrayList<BasicNameValuePair> array) {
		Logger.d(tag, "requestFOrNews ");
		this.mRequestCallBack = mRequestCallBack;
		this.array=array;
		url = baseUrl + "_SendMail_.php";
	}
	/*
	 * Tem request used to request testing...
	 */
	public void tempRequest(RequestCallBack mRequestCallBack, String key) {
		Logger.d(tag, "requestFOrNews ");
		this.mRequestCallBack = mRequestCallBack;
		url = baseUrl + "temp.php";
	}
	/*
	 * (non-Javadoc)
	 * @see com.oigoiania.network.BaseRequestListener#returnResponse(java.lang.String)
	 */
	@Override
	protected void returnResponse(String response) {
		try {
			JSONObject json = this.getJsonObj(response);
			this.mRequestCallBack.onRequestSuccess(json);
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			this.mRequestCallBack.onJSONException(e.getMessage());
			e.printStackTrace();
		}
	}

	private JSONObject getJsonObj(String response) throws JSONException {
		JSONObject jsonObject = null;
		Logger.d(tag, "RECEIVED: " + response);
		byte[] converttoBytes;
		String convertedString = null;
		try {
			converttoBytes = response.getBytes("UTF-8");
			convertedString = new String(converttoBytes, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			Crittercism.logHandledException(e1);
			e1.printStackTrace();
		}
		try {
			jsonObject = new JSONObject(convertedString.toString().trim());
		} catch (JSONException e) {
			Crittercism.logHandledException(e);
			Logger.e(tag, "JSON EXception at this response : "
					+ convertedString.toString().trim());
			jsonObject = new JSONObject(convertedString.toString().trim());
		}
		return jsonObject;
	}

}
