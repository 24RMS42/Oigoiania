package com.oigoiania.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;

import com.oigoiania.util.GlobalFunctions;
import com.oigoiania.util.Util;

public abstract class Parser {
	// public static List<MyDataHolder> parseResearchResponse(JSONObject
	// jsonObject)
	// throws JSONException {
	// List<MyDataHolder> researchList = new ArrayList<MyDataHolder>();
	// if (!jsonObject.isNull("data")) {
	// JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
	// for (int i = 0; i < jsnArray.length(); i++) {
	// MyDataHolder research = new MyDataHolder();
	// JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
	// if (!re_JsonObject.isNull("id"))
	// research.setLoc_id(re_JsonObject.getString("id"));
	// if (!re_JsonObject.isNull("title"))
	// research.setLoc_title(re_JsonObject.getString("title"));
	// if (!re_JsonObject.isNull("address"))
	// research.setLoc_address(re_JsonObject.getString("address"));
	// if (!re_JsonObject.isNull("latitude"))
	// research.setLoc_latitude(re_JsonObject
	// .getDouble("latitude"));
	// if (!re_JsonObject.isNull("longitude"))
	// research.setLoc_longitude(re_JsonObject
	// .getDouble("longitude"));
	// if (!re_JsonObject.isNull("schedule"))
	// research.setLoc_time(re_JsonObject.getString("schedule"));
	// if (!re_JsonObject.isNull("imgurl"))
	// research.setLoc_image(re_JsonObject.getString("imgurl")
	// .replace("\\", ""));
	// if (!re_JsonObject.isNull("site"))
	// research.setLoc_site(re_JsonObject.getString("site")
	// .replace("\\", ""));
	// if (!re_JsonObject.isNull("subcategory"))
	// research.getSubCategory().setName(
	// re_JsonObject.getString("subcategory").replace(
	// "\\", ""));
	// researchList.add(research);
	// }
	// }
	//
	// return researchList;
	//
	// }

	// public static List<MyDataHolder> parseSubCatergoryPOIsResponse(
	// JSONObject jsonObject) throws JSONException {
	// List<MyDataHolder> researchList = new ArrayList<MyDataHolder>();
	// if (!jsonObject.isNull("data")) {
	// JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
	// for (int i = 0; i < jsnArray.length(); i++) {
	// MyDataHolder mPOI = new MyDataHolder();
	// JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
	// if (!re_JsonObject.isNull("loc_id"))
	// mPOI.setLoc_id(re_JsonObject.getString("loc_id"));
	// if (!re_JsonObject.isNull("loc_title"))
	// mPOI.setLoc_title(re_JsonObject.getString("loc_title"));
	// if (!re_JsonObject.isNull("loc_address"))
	// mPOI.setLoc_address(re_JsonObject.getString("loc_address"));
	// if (!re_JsonObject.isNull("latitude"))
	// mPOI.setLoc_latitude(re_JsonObject.getDouble("latitude"));
	// if (!re_JsonObject.isNull("longitude"))
	// mPOI.setLoc_longitude(re_JsonObject.getDouble("longitude"));
	// if (!re_JsonObject.isNull("loc_description"))
	// mPOI.setLoc_description(re_JsonObject
	// .getString("loc_description"));
	// if (!re_JsonObject.isNull("loc_time"))
	// mPOI.setLoc_time(re_JsonObject.getString("loc_time"));
	// if (!re_JsonObject.isNull("loc_imgurl"))
	// mPOI.setLoc_image(re_JsonObject.getString("loc_imgurl")
	// .replace("\\", ""));
	// if (!re_JsonObject.isNull("site"))
	// mPOI.setLoc_site(re_JsonObject.getString("site").replace(
	// "\\", ""));
	// if (!re_JsonObject.isNull("telephone"))
	// mPOI.setLoc_telephone(re_JsonObject.getString("telephone"));
	// if (!re_JsonObject.isNull("cell"))
	// mPOI.setLoc_cell(re_JsonObject.getString("cell"));
	// if (!re_JsonObject.isNull("catId"))
	// mPOI.getCategory().setId(re_JsonObject.getString("catId"));
	// if (!re_JsonObject.isNull("subCatID"))
	// mPOI.getSubCategory().setId(
	// re_JsonObject.getString("subCatID"));
	// if (!re_JsonObject.isNull("catname"))
	// mPOI.getCategory().setName(
	// re_JsonObject.getString("catname"));
	//
	// researchList.add(mPOI);
	// }
	// }
	//
	// return researchList;
	//
	// }

	@SuppressLint("UseValueOf")
	public static List<MyDataHolder> parseTodayInOigoiaResponse(
			JSONObject jsonObject) throws JSONException {
		List<MyDataHolder> researchList = new ArrayList<MyDataHolder>();
		if (!jsonObject.isNull("data")) {
			JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
			for (int i = 0; i < jsnArray.length(); i++) {
				MyDataHolder mDataHolder = new MyDataHolder();
				JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
				if (!re_JsonObject.isNull("id"))
					mDataHolder.setId(re_JsonObject.getString("id"));
				if (!re_JsonObject.isNull("name"))
					mDataHolder.setName(re_JsonObject.getString("name"));
				if (!re_JsonObject.isNull("title"))
					mDataHolder.setLoc_title(re_JsonObject.getString("title"));
				if (!re_JsonObject.isNull("description"))
					mDataHolder.setDescription(re_JsonObject
							.getString("description"));
				if (!re_JsonObject.isNull("loc_id")) // Location id
					mDataHolder.setLoc_id(re_JsonObject.getString("loc_id"));
				if (!re_JsonObject.isNull("time")) //
					mDataHolder.setTime(re_JsonObject.getString("time"));
				if (!re_JsonObject.isNull("image"))
					mDataHolder.setImage(re_JsonObject.getString("image")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("date"))
					mDataHolder.setDate(re_JsonObject.getString("date")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("longitude"))
					mDataHolder.setLoc_longitude(re_JsonObject
							.getDouble("longitude"));
				if (!re_JsonObject.isNull("latitude"))
					mDataHolder.setLoc_latitude(re_JsonObject
							.getDouble("latitude"));
				if (!re_JsonObject.isNull("site"))
					mDataHolder.setLoc_site(re_JsonObject.getString("site")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("link"))
					mDataHolder.setLoc_link(re_JsonObject.getString("link")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("loc_address"))
					mDataHolder.setLoc_address(re_JsonObject
							.getString("loc_address"));
				if (!re_JsonObject.isNull("loc_description"))
					mDataHolder.setLoc_description(re_JsonObject
							.getString("loc_description"));
				if (!re_JsonObject.isNull("telephone"))
					mDataHolder.setLoc_telephone(re_JsonObject
							.getString("telephone"));
				if (!re_JsonObject.isNull("cell"))
					mDataHolder.setLoc_cell(re_JsonObject.getString("cell"));

				Location desiredLocation = new Location(
						LocationManager.GPS_PROVIDER);
				desiredLocation.setLatitude(mDataHolder.getLoc_latitude());
				desiredLocation.setLongitude(mDataHolder.getLoc_longitude());
				mDataHolder.setDistance(GlobalFunctions
						.distanceFrom(desiredLocation));
				researchList.add(mDataHolder);
			}
		}
		Collections.sort(researchList, new Comparator<MyDataHolder>() {
			@SuppressLint("UseValueOf")
			public int compare(MyDataHolder s1, MyDataHolder s2) {
				return new Double(s1.getDistance()).compareTo(new Double(s2
						.getDistance()));
			}
		});

		return researchList;

	}

	@SuppressLint("UseValueOf")
	public static List<Cat_SubCatDataHolder> parseCategoryResponse(
			JSONObject jsonObject) throws JSONException {
		List<Cat_SubCatDataHolder> mObject = new ArrayList<Cat_SubCatDataHolder>();
		if (!jsonObject.isNull("categories")) {
			JSONArray jsnArray = (JSONArray) jsonObject
					.getJSONArray("categories");
			for (int i = 0; i < jsnArray.length(); i++) {
				Cat_SubCatDataHolder research = new Cat_SubCatDataHolder();
				JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
				if (!re_JsonObject.isNull("id"))
					research.setCatId(re_JsonObject.getString("id"));
				if (!re_JsonObject.isNull("title"))
					research.setTitle(re_JsonObject.getString("title"));

				/*
				 * if (!re_JsonObject.isNull("address"))
				 * research.setAddress(re_JsonObject.getString("address")); if
				 * (!re_JsonObject.isNull("description"))
				 * research.setDescription
				 * (re_JsonObject.getString("description")); if
				 * (!re_JsonObject.isNull("link"))
				 * research.setLink(re_JsonObject.getString("link")); if
				 * (!re_JsonObject.isNull("latitude"))
				 * research.setLatitude(re_JsonObject.getDouble("latitude")); if
				 * (!re_JsonObject.isNull("longitude"))
				 * research.setLongitude(re_JsonObject.getDouble("longitude"));
				 * if (!re_JsonObject.isNull("schedule"))
				 * research.setSchedule(re_JsonObject.getString("schedule")); if
				 * (!re_JsonObject.isNull("image"))
				 * research.setImage(re_JsonObject.getString("image").replace(
				 * "\\", "")); if (!re_JsonObject.isNull("site"))
				 * research.setSiteUrl(re_JsonObject.getString("site")
				 * .replace("\\", "")); if (!re_JsonObject.isNull("date"))
				 * research.setDate(re_JsonObject.getString("date")
				 * .replace("\\", ""));
				 * research.setDistance(GlobalFunctions.distFrom(
				 * Util.currentLocation.getLatitude(),
				 * Util.currentLocation.getLongitude(), research.getLatitude(),
				 * research.getLongitude(), false)); researchList.add(research);
				 * } } Collections.sort(researchList, new
				 * Comparator<Category_holder>() {
				 * 
				 * @SuppressLint("UseValueOf") public int
				 * compare(Category_holder s1, Category_holder s2) { return new
				 * Double(s1.getDistance()) .compareTo(new
				 * Double(s2.getDistance())); } });
				 */

				mObject.add(research);
			}
		}

		return mObject;

	}

	public static List<Cat_SubCatDataHolder> parseSubcategoryResponse(
			JSONObject jsonObject) throws JSONException {
		List<Cat_SubCatDataHolder> researchList = new ArrayList<Cat_SubCatDataHolder>();
		if (!jsonObject.isNull("subcategories")) {
			JSONArray jsnArray = (JSONArray) jsonObject
					.getJSONArray("subcategories");
			for (int i = 0; i < jsnArray.length(); i++) {
				Cat_SubCatDataHolder research = new Cat_SubCatDataHolder();
				JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
				if (!re_JsonObject.isNull("id"))
					research.setCatId(re_JsonObject.getString("id"));
				if (!re_JsonObject.isNull("title"))
					research.setTitle(re_JsonObject.getString("title"));
				if (!re_JsonObject.isNull("subCatId"))
					research.setSubCatId(re_JsonObject.getString("subCatId"));
				/*
				 * if (!re_JsonObject.isNull("address"))
				 * research.setAddress(re_JsonObject.getString("address")); if
				 * (!re_JsonObject.isNull("description"))
				 * research.setDescription
				 * (re_JsonObject.getString("description")); if
				 * (!re_JsonObject.isNull("link"))
				 * research.setLink(re_JsonObject.getString("link")); if
				 * (!re_JsonObject.isNull("latitude"))
				 * research.setLatitude(re_JsonObject.getDouble("latitude")); if
				 * (!re_JsonObject.isNull("longitude"))
				 * research.setLongitude(re_JsonObject.getDouble("longitude"));
				 * if (!re_JsonObject.isNull("schedule"))
				 * research.setSchedule(re_JsonObject.getString("schedule")); if
				 * (!re_JsonObject.isNull("image"))
				 * research.setImage(re_JsonObject.getString("image").replace(
				 * "\\", "")); if (!re_JsonObject.isNull("site"))
				 * research.setSiteUrl(re_JsonObject.getString("site")
				 * .replace("\\", "")); if (!re_JsonObject.isNull("date"))
				 * research.setDate(re_JsonObject.getString("date")
				 * .replace("\\", ""));
				 * research.setDistance(GlobalFunctions.distFrom(
				 * Util.currentLocation.getLatitude(),
				 * Util.currentLocation.getLongitude(), research.getLatitude(),
				 * research.getLongitude(), false)); researchList.add(research);
				 * } } Collections.sort(researchList, new
				 * Comparator<Category_holder>() {
				 * 
				 * @SuppressLint("UseValueOf") public int
				 * compare(Category_holder s1, Category_holder s2) { return new
				 * Double(s1.getDistance()) .compareTo(new
				 * Double(s2.getDistance())); } });
				 */

				researchList.add(research);
			}
		}

		return researchList;

	}

	// @SuppressLint("UseValueOf")
	// public static List<MyDataHolder> parsePromotion_DataResponse(
	// JSONObject jsonObject) throws JSONException {
	// List<MyDataHolder> promotionsList = new ArrayList<MyDataHolder>();
	// if (!jsonObject.isNull("data")) {
	// JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
	// for (int i = 0; i < jsnArray.length(); i++) {
	// MyDataHolder mDataHolder = new MyDataHolder();
	// JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
	// if (!re_JsonObject.isNull("loc_id"))
	// mDataHolder.setLoc_id(re_JsonObject.getString("loc_id"));
	// if (!re_JsonObject.isNull("loc_title"))
	// mDataHolder.setLoc_title(re_JsonObject
	// .getString("loc_title"));
	// if (!re_JsonObject.isNull("promotiontitle")) {
	// mDataHolder.setName(re_JsonObject
	// .getString("promotiontitle"));
	// }
	// if (!re_JsonObject.isNull("time"))
	// mDataHolder.setTime(re_JsonObject.getString("time"));
	// if (!re_JsonObject.isNull("description"))
	// mDataHolder.setDescription(re_JsonObject
	// .getString("description"));
	// if (!re_JsonObject.isNull("image"))
	// mDataHolder.setImage(re_JsonObject.getString("image")
	// .replace("\\", ""));
	// if (!re_JsonObject.isNull("latitude"))
	// mDataHolder.setLoc_latitude(re_JsonObject
	// .getDouble("latitude"));
	// if (!re_JsonObject.isNull("longitude"))
	// mDataHolder.setLoc_longitude(re_JsonObject
	// .getDouble("longitude"));
	// if (!re_JsonObject.isNull("site"))
	// mDataHolder.setLoc_site(re_JsonObject.getString("site")
	// .replace("\\", ""));
	//
	// if (!re_JsonObject.isNull("link"))
	// mDataHolder.setLoc_link(re_JsonObject.getString("link"));
	// if (!re_JsonObject.isNull("loc_address"))
	// mDataHolder.setLoc_address(re_JsonObject
	// .getString("loc_address"));
	// if (!re_JsonObject.isNull("loc_description"))
	// mDataHolder.setLoc_description(re_JsonObject
	// .getString("loc_description"));
	// if (!re_JsonObject.isNull("telephone"))
	// mDataHolder.setLoc_telephone(re_JsonObject
	// .getString("telephone"));
	// if (!re_JsonObject.isNull("cell"))
	// mDataHolder.setLoc_cell(re_JsonObject.getString("cell"));
	//
	// Location desiredLocation = new Location(
	// LocationManager.GPS_PROVIDER);
	// desiredLocation.setLatitude(mDataHolder.getLoc_latitude());
	// desiredLocation.setLongitude(mDataHolder.getLoc_longitude());
	// mDataHolder.setDistance(GlobalFunctions.distanceFrom(
	// Util.currentLocation, desiredLocation));
	// promotionsList.add(mDataHolder);
	// }
	// }
	// Collections.sort(promotionsList, new Comparator<MyDataHolder>() {
	// @SuppressLint("UseValueOf")
	// public int compare(MyDataHolder s1, MyDataHolder s2) {
	// return new Double(s1.getDistance()).compareTo(new Double(s2
	// .getDistance()));
	// }
	// });
	//
	// return promotionsList;
	//
	// }

	// //////////////////// general parser ////////////////////////////////
	public static List<MyDataHolder> parse(JSONObject jsonObject)
			throws JSONException {
		List<MyDataHolder> promotionsList = new ArrayList<MyDataHolder>();
		if (!jsonObject.isNull("data")) {
			JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
			for (int i = 0; i < jsnArray.length(); i++) {
				MyDataHolder mDataHolder = new MyDataHolder();
				JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
				if (!re_JsonObject.isNull("loc_id"))
					mDataHolder.setLoc_id(re_JsonObject.getString("loc_id"));
				if (!re_JsonObject.isNull("loc_title"))
					mDataHolder.setLoc_title(re_JsonObject
							.getString("loc_title"));
				if (!re_JsonObject.isNull("loc_description"))
					mDataHolder.setLoc_description(re_JsonObject
							.getString("loc_description"));
				if (!re_JsonObject.isNull("latitude"))
					mDataHolder.setLoc_latitude(re_JsonObject
							.getDouble("latitude"));
				if (!re_JsonObject.isNull("longitude"))
					mDataHolder.setLoc_longitude(re_JsonObject
							.getDouble("longitude"));
				if (!re_JsonObject.isNull("loc_time"))
					mDataHolder
							.setLoc_time(re_JsonObject.getString("loc_time"));
				if (!re_JsonObject.isNull("site"))
					mDataHolder.setLoc_site(re_JsonObject.getString("site")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("loc_image"))
					mDataHolder.setLoc_image(re_JsonObject.getString(
							"loc_image").replace("\\", ""));
				if (!re_JsonObject.isNull("link"))
					mDataHolder.setLoc_link(re_JsonObject.getString("link"));
				if (!re_JsonObject.isNull("loc_address"))
					mDataHolder.setLoc_address(re_JsonObject
							.getString("loc_address"));
				if (!re_JsonObject.isNull("telephone"))
					mDataHolder.setLoc_telephone(re_JsonObject
							.getString("telephone"));
				if (!re_JsonObject.isNull("cell"))
					mDataHolder.setLoc_cell(re_JsonObject.getString("cell"));
				if (!re_JsonObject.isNull("id"))
					mDataHolder.setId(re_JsonObject.getString("id"));
				if (!re_JsonObject.isNull("name"))
					mDataHolder.setName(re_JsonObject.getString("name"));
				if (!re_JsonObject.isNull("comment"))
					mDataHolder.setComment(re_JsonObject.getString("comment"));
				if (!re_JsonObject.isNull("tabela_precos"))
					mDataHolder.setTabela_precos(re_JsonObject
							.getString("tabela_precos"));
				if (!re_JsonObject.isNull("valid"))
					mDataHolder.setValid(re_JsonObject.getString("valid"));
				if (!re_JsonObject.isNull("description"))
					mDataHolder.setDescription(re_JsonObject
							.getString("description"));
				if (!re_JsonObject.isNull("dateInitail"))
					mDataHolder.setDateInitail(re_JsonObject
							.getString("dateInitail"));
				if (!re_JsonObject.isNull("dateFinal"))
					mDataHolder.setDateFinal(re_JsonObject
							.getString("dateFinal"));
				if (!re_JsonObject.isNull("time"))
					mDataHolder.setTime(re_JsonObject.getString("time"));
				if (!re_JsonObject.isNull("image"))
					mDataHolder.setImage(re_JsonObject.getString("image")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("address"))
					mDataHolder.setAddress(re_JsonObject.getString("address"));
				if (!re_JsonObject.isNull("date"))
					mDataHolder.setDate(re_JsonObject.getString("date"));

				if (!re_JsonObject.isNull("catId"))
					mDataHolder.getCategory().setId(
							re_JsonObject.getString("catId"));
				if (!re_JsonObject.isNull("catName"))
					mDataHolder.getCategory().setName(
							re_JsonObject.getString("catName"));
				if (!re_JsonObject.isNull("subCatId"))
					mDataHolder.getSubCategory().setId(
							re_JsonObject.getString("subCatId"));
				if (!re_JsonObject.isNull("subCatName"))
					mDataHolder.getSubCategory().setName(
							re_JsonObject.getString("subCatName"));
				if (!re_JsonObject.isNull("type"))
					mDataHolder.setType(re_JsonObject.getString("type"));
				Location desiredLocation = new Location(
						LocationManager.GPS_PROVIDER);
				desiredLocation.setLatitude(mDataHolder.getLoc_latitude());
				desiredLocation.setLongitude(mDataHolder.getLoc_longitude());
				double distance = GlobalFunctions.distanceFrom(desiredLocation);
				if ((float) distance <= (Util.current_zoomlevel * 1000)) {
					mDataHolder.setDistance(distance);
					promotionsList.add(mDataHolder);
				}

			}
		}
		if (promotionsList.size() > 1) {
			Collections.sort(promotionsList, new Comparator<MyDataHolder>() {
				@SuppressLint("UseValueOf")
				public int compare(MyDataHolder s1, MyDataHolder s2) {
					return new Double(s1.getDistance()).compareTo(new Double(s2
							.getDistance()));
				}
			});
		}
		return promotionsList;

	}

	public static List<MyDataHolder> parse2(JSONObject jsonObject,
			boolean shouldSort) throws JSONException {
		List<MyDataHolder> promotionsList = new ArrayList<MyDataHolder>();
		if (!jsonObject.isNull("data")) {
			JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
			for (int i = 0; i < jsnArray.length(); i++) {
				MyDataHolder mDataHolder = new MyDataHolder();
				JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
				if (!re_JsonObject.isNull("loc_id"))
					mDataHolder.setLoc_id(re_JsonObject.getString("loc_id"));
				if (!re_JsonObject.isNull("loc_title"))
					mDataHolder.setLoc_title(re_JsonObject
							.getString("loc_title"));
				if (!re_JsonObject.isNull("loc_description"))
					mDataHolder.setLoc_description(re_JsonObject
							.getString("loc_description"));
				if (!re_JsonObject.isNull("latitude"))
					mDataHolder.setLoc_latitude(re_JsonObject
							.getDouble("latitude"));
				if (!re_JsonObject.isNull("longitude"))
					mDataHolder.setLoc_longitude(re_JsonObject
							.getDouble("longitude"));
				if (!re_JsonObject.isNull("loc_time"))
					mDataHolder
							.setLoc_time(re_JsonObject.getString("loc_time"));
				if (!re_JsonObject.isNull("site"))
					mDataHolder.setLoc_site(re_JsonObject.getString("site")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("loc_image"))
					mDataHolder.setLoc_image(re_JsonObject.getString(
							"loc_image").replace("\\", ""));
				if (!re_JsonObject.isNull("link"))
					mDataHolder.setLoc_link(re_JsonObject.getString("link"));
				if (!re_JsonObject.isNull("loc_address"))
					mDataHolder.setLoc_address(re_JsonObject
							.getString("loc_address"));
				if (!re_JsonObject.isNull("telephone"))
					mDataHolder.setLoc_telephone(re_JsonObject
							.getString("telephone"));
				if (!re_JsonObject.isNull("cell"))
					mDataHolder.setLoc_cell(re_JsonObject.getString("cell"));
				if (!re_JsonObject.isNull("id"))
					mDataHolder.setId(re_JsonObject.getString("id"));
				if (!re_JsonObject.isNull("name"))
					mDataHolder.setName(re_JsonObject.getString("name"));
				if (!re_JsonObject.isNull("comment"))
					mDataHolder.setComment(re_JsonObject.getString("comment"));
				if (!re_JsonObject.isNull("tabela_precos"))
					mDataHolder.setTabela_precos(re_JsonObject
							.getString("tabela_precos"));
				if (!re_JsonObject.isNull("valid"))
					mDataHolder.setValid(re_JsonObject.getString("valid"));
				if (!re_JsonObject.isNull("description"))
					mDataHolder.setDescription(re_JsonObject
							.getString("description"));
				if (!re_JsonObject.isNull("dateInitail"))
					mDataHolder.setDateInitail(re_JsonObject
							.getString("dateInitail"));
				if (!re_JsonObject.isNull("dateFinal"))
					mDataHolder.setDateFinal(re_JsonObject
							.getString("dateFinal"));
				if (!re_JsonObject.isNull("time"))
					mDataHolder.setTime(re_JsonObject.getString("time"));
				if (!re_JsonObject.isNull("image"))
					mDataHolder.setImage(re_JsonObject.getString("image")
							.replace("\\", ""));
				if (!re_JsonObject.isNull("address"))
					mDataHolder.setAddress(re_JsonObject.getString("address"));
				if (!re_JsonObject.isNull("date"))
					mDataHolder.setDate(re_JsonObject.getString("date"));

				if (!re_JsonObject.isNull("catId"))
					mDataHolder.getCategory().setId(
							re_JsonObject.getString("catId"));
				if (!re_JsonObject.isNull("catName"))
					mDataHolder.getCategory().setName(
							re_JsonObject.getString("catName"));
				if (!re_JsonObject.isNull("subCatId"))
					mDataHolder.getSubCategory().setId(
							re_JsonObject.getString("subCatId"));
				if (!re_JsonObject.isNull("subCatName"))
					mDataHolder.getSubCategory().setName(
							re_JsonObject.getString("subCatName"));
				if (!re_JsonObject.isNull("type"))
					mDataHolder.setType(re_JsonObject.getString("type"));
				Location desiredLocation = new Location(
						LocationManager.GPS_PROVIDER);
				desiredLocation.setLatitude(mDataHolder.getLoc_latitude());
				desiredLocation.setLongitude(mDataHolder.getLoc_longitude());
				double distance = GlobalFunctions.distanceFrom(desiredLocation);
				// if((float)distance<=(Util.current_zoomlevel*1000)){
				mDataHolder.setDistance(distance);
				promotionsList.add(mDataHolder);
				// }

			}
		}
		//if (shouldSort) {
			if (promotionsList.size() > 1) {
				Collections.sort(promotionsList,
						new Comparator<MyDataHolder>() {
							@SuppressLint("UseValueOf")
							public int compare(MyDataHolder s1, MyDataHolder s2) {
								return new Double(s1.getDistance())
										.compareTo(new Double(s2.getDistance()));
							}
						});
			}
		//}
		return promotionsList;

	}

	@SuppressLint("UseValueOf")
	public static List<ImagesHolder> parseImagesURLs(JSONObject jsonObject)
			throws JSONException {
		List<ImagesHolder> mObject = new ArrayList<ImagesHolder>();
		if (!jsonObject.isNull("data")) {
			JSONArray jsnArray = (JSONArray) jsonObject.getJSONArray("data");
			for (int i = 0; i < jsnArray.length(); i++) {
				ImagesHolder research = new ImagesHolder();
				JSONObject re_JsonObject = (JSONObject) jsnArray.get(i);
				if (!re_JsonObject.isNull("id"))
					research.setId(re_JsonObject.getString("id"));
				if (!re_JsonObject.isNull("image"))
					research.setUrl(re_JsonObject.getString("image").replace(
							"\\", "/"));
				if (!re_JsonObject.isNull("datetime"))
					research.setDateTime(re_JsonObject.getString("datetime")
							.replace("\\", "/"));

				mObject.add(research);
			}
		}

		return mObject;

	}

}
