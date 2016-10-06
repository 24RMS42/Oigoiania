package com.oigoiania.fragments;

import org.json.JSONArray;
import org.json.JSONException;

import com.br.oigoiania.R;
import com.oigoiania.util.Preference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;



public class SpecificComment_adapter extends BaseAdapter {

	   public static String TAG = "matata:CommentAdapter";
	   private JSONArray json_array;
	  
	   Context context;
	   private static LayoutInflater inflater=null;

	    public SpecificComment_adapter(Context mainActivity, JSONArray jsonArray){
	        // TODO Auto-generated constructor stub
			context = mainActivity;

	        json_array = jsonArray;

	        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	    @Override
	    public int getCount() {
	        // TODO Auto-generated method stub
	        return json_array.length();
	    }

	    @Override
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }

	    @Override
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }

	    public class Holder
	    {
	        TextView txtName,txtComment,txtDate;
			RatingBar ratingbar ;
		}

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	        Holder holder=new Holder();
	        View rowView = null;

			rowView = inflater.inflate(R.layout.comment_list, null);
			holder.txtName   = (TextView)rowView.findViewById(R.id.textName);
			holder.txtComment= (TextView)rowView.findViewById(R.id.textComment);
			holder.txtDate   = (TextView)rowView.findViewById(R.id.textDate);
			holder.ratingbar = (RatingBar)rowView.findViewById(R.id.ratingBar1);

			try {			
				
				holder.txtName.setText(json_array.getJSONObject(position).getString("nome").toString());
				holder.txtComment.setText(json_array.getJSONObject(position).getString("comentario").toString());
				holder.txtDate.setText(json_array.getJSONObject(position).getString("data").toString());
				int rating = json_array.getJSONObject(position).getInt("avaliacao");
				holder.ratingbar.setRating(rating);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			rowView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					
				}
			});

	        return rowView;
	    }
}
