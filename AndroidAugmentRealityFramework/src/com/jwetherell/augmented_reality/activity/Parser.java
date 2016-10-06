package com.jwetherell.augmented_reality.activity;

import org.json.JSONException;
import org.json.JSONObject;

public class Parser {
	public static MyTempDataHolder parseResponse(
			JSONObject jsonObject) throws JSONException {
		MyTempDataHolder mDataHolder = new MyTempDataHolder();
		JSONObject re_JsonObject =jsonObject;// (JSONObject) jsnArray.get(i);
		if (!re_JsonObject.isNull("id"))
			mDataHolder.setId(re_JsonObject.getString("id"));
		if (!re_JsonObject.isNull("loc_id")) // Location id
			mDataHolder.setLoc_id(re_JsonObject.getString("loc_id"));
		if (!re_JsonObject.isNull("name"))
			mDataHolder.setName(re_JsonObject.getString("name"));
		if (!re_JsonObject.isNull("loc_title"))
			mDataHolder.setLoc_title(re_JsonObject.getString("loc_title"));
		if (!re_JsonObject.isNull("loc_description"))
			mDataHolder.setLoc_description(re_JsonObject.getString("loc_description"));
		if (!re_JsonObject.isNull("description"))
			mDataHolder.setDescription(re_JsonObject.getString("description"));
		if (!re_JsonObject.isNull("time")) //
			mDataHolder.setTime(re_JsonObject.getString("time"));
		if (!re_JsonObject.isNull("loc_time")) //
			mDataHolder.setLoc_time(re_JsonObject.getString("loc_time"));
		if (!re_JsonObject.isNull("image"))
			mDataHolder.setImage(re_JsonObject.getString("image").replace("\\",
					""));
		if (!re_JsonObject.isNull("loc_image"))
			mDataHolder.setLoc_image(re_JsonObject.getString("loc_image").replace("\\",
					""));
		if (!re_JsonObject.isNull("date"))
			mDataHolder.setDate(re_JsonObject.getString("date").replace("\\",
					""));
		if (!re_JsonObject.isNull("longitude"))
			mDataHolder.setLoc_longitude(re_JsonObject.getDouble("longitude"));
		if (!re_JsonObject.isNull("latitude"))
			mDataHolder.setLoc_latitude(re_JsonObject.getDouble("latitude"));
		if (!re_JsonObject.isNull("site"))
			mDataHolder.setLoc_site(re_JsonObject.getString("site").replace(
					"\\", ""));
		if (!re_JsonObject.isNull("link"))
			mDataHolder.setLoc_link(re_JsonObject.getString("link").replace(
					"\\", ""));
		if (!re_JsonObject.isNull("loc_address"))
			mDataHolder.setLoc_address(re_JsonObject.getString("loc_address"));
		if (!re_JsonObject.isNull("loc_description"))
			mDataHolder.setLoc_description(re_JsonObject
					.getString("loc_description"));
		if (!re_JsonObject.isNull("telephone"))
			mDataHolder.setLoc_telephone(re_JsonObject.getString("telephone"));
		if (!re_JsonObject.isNull("cell"))
			mDataHolder.setLoc_cell(re_JsonObject.getString("cell"));


		return mDataHolder;

	}
}
