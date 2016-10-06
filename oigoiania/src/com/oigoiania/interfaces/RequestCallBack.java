package com.oigoiania.interfaces;

import org.json.JSONObject;

public interface RequestCallBack {
	public void onRequestSuccess(JSONObject jsonOnject);
	public void onRequestFail(String messaget);
	public void onJSONException(String message);

}
