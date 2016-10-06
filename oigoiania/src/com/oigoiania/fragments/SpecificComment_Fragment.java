package com.oigoiania.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.br.oigoiania.R;
import com.oigoiania.user.SRActivity;
import com.oigoiania.user.SignUpActivity;
import com.oigoiania.user.SigninActivity;
import com.oigoiania.util.Net;
import com.oigoiania.util.Preference;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;/////////////////
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public  class SpecificComment_Fragment extends Fragment implements View.OnClickListener{
	
	public String TAG = "matata:commentFragment";
    Context context;
    ListView lv;
    Button btnWrite;
    EditText edtComment;
    RatingBar rating;
    RatingBar average_rating;
    
    JSONArray commentArray;
    SpecificComment_adapter adapter;
    private static SpecificComment_Fragment m_SpecificComment_Fragment = null;
	
    public static SpecificComment_Fragment getInstance() {
		return m_SpecificComment_Fragment;
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_SpecificComment_Fragment = this;
    }
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.comment_fragment, container, false);
        context = this.getActivity();
		lv = (ListView)view.findViewById(R.id.listView);
		average_rating = (RatingBar) view.findViewById(R.id.ratingBarAverage);
		btnWrite = (Button)view.findViewById(R.id.btnWrite);
		btnWrite.setOnClickListener(this);
		
		commentArray = new JSONArray();
		
		new GetCommentAsync().execute("");
		
        return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String session = Preference.restoreText(context, "login");
		if(session.equals("yes")){
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.comment_rate_dialog);
			dialog.setTitle("Please write your comment");

			// set the custom dialog components - text, image and button
			edtComment = (EditText) dialog.findViewById(R.id.editText1);
			rating = (RatingBar) dialog.findViewById(R.id.ratingBar1);
			rating.setRating((float) 3.5);
			
			Button dialogButton = (Button) dialog.findViewById(R.id.button1);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new WriteCommentAsync().execute("");
					dialog.dismiss();
				}
			});

			dialog.show();
		}else {
			Activity castActivity = (Activity)context;
			castActivity.startActivity(new Intent(context, SRActivity.class));
		}		
		
	}
	
	public void tab_click() {
		if (lv != null)
			lv.setVisibility(View.VISIBLE);
			
	}
	
	class GetCommentAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog pd;
        protected void onPreExecute() {
            pd = ProgressDialog.show(context, "", "Loading...", true);
        }

        protected String doInBackground(String... params) {

            String response = "";
            
            String localId = Preference.restoreText(context, "localId");
            response = Net.excutePostString("getComment.php", "localId=" + localId);          

            return response;
        }

        protected void onPostExecute(String response) {

            pd.dismiss();
            if (response == null) {
                Toast.makeText(context, "Sorry Network error", Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("matata", response);
            try {
                Log.d(TAG, "success");
                JSONObject commentObj = new JSONObject(response);
                
                if (commentObj.getString("success").equals("no"))
                    Toast.makeText(context,"There is no Comment",Toast.LENGTH_LONG).show();
                else{
                	commentArray = commentObj.getJSONArray("data");
                	adapter=new SpecificComment_adapter(context, commentArray);
                    lv.setAdapter(adapter);
                    
                    //count average rating and user 
                    int total_rating = 0;
                    String userId = Preference.restoreText(context, "userId");
                    for (int i = 0;i < commentArray.length();i++){
                    	int rating = commentArray.getJSONObject(i).getInt("avaliacao");
                    	String item_userid = commentArray.getJSONObject(i).getString("usuario");
                    	
                    	total_rating += rating;
                    	if (userId.equals(item_userid))
                    		btnWrite.setVisibility(View.INVISIBLE);
                    }
                    average_rating.setRating(total_rating/commentArray.length());
                   
                }                              


            } catch (JSONException e) {
                Log.d(TAG, "fail");
                e.printStackTrace();
                Toast.makeText(context,"Sorry",Toast.LENGTH_LONG).show();
            }
        }
    }
	
	class WriteCommentAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog pd;
        protected void onPreExecute() {
            pd = ProgressDialog.show(context, "", "Submitting...", true);
        }

        protected String doInBackground(String... params) {

            String response = "";
            
            String localId = Preference.restoreText(context, "localId");
            String userId = Preference.restoreText(context, "userId");
            String comment = edtComment.getText().toString();
            float nrating = rating.getRating();
            
            String parameter = "localId=" + localId + "&userId=" + userId + "&comment=" + comment + "&rating=" + String.valueOf(nrating);
            response = Net.excutePostString("writeComment.php", parameter);          

            return response;
        }

        protected void onPostExecute(String response) {

            pd.dismiss();
            if (response == null) {
                Toast.makeText(context, "Sorry Network Error", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                Log.d(TAG, "success");
                JSONObject commentObj = new JSONObject(response);
                
                if (commentObj.getString("success").equals("writeyes"))
                	Toast.makeText(context,"Comment sent successfully! Please wait for approval",Toast.LENGTH_LONG).show();                         


            } catch (JSONException e) {
                Log.d(TAG, "fail");
                e.printStackTrace();
            }
        }
    }

}
