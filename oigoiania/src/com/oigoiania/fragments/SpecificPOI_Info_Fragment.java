package com.oigoiania.fragments;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.br.oigoiania.Events_SingleDetail;
import com.br.oigoiania.R;
import com.crittercism.app.Crittercism;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oigoiania.logger.Logger;
import com.oigoiania.parsers.MyDataHolder;

public class SpecificPOI_Info_Fragment extends Fragment implements
		OnClickListener {
	private static final String tag = "SpecificPOI_Info_Fragment";
	private Button about_button, promotion_button, event_button,
			comment_button;
	private LinearLayout aboutlayout, promolayout, eventlayout, commentlayout,
			main_layout;
	private HashMap<String, List<MyDataHolder>> mHashMap = null;

	private RelativeLayout re_layout;
	private static SpecificPOI_Info_Fragment mSpecificPOI_ListviewFragment = null;
	private MyDataHolder mData = null;
	private String type = "";
	ImageLoader imageLoager = null;
	private Activity mActivity = null;

	public static SpecificPOI_Info_Fragment getInstance() {
		return mSpecificPOI_ListviewFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpecificPOI_ListviewFragment = this;
		imageLoager = ImageLoader.getInstance();
		Log.d(tag, " OnCreate()");
		mActivity = getActivity();
		Bundle bundle = getArguments();
		if (bundle == null)
			throw new NullPointerException("Bundle sent is null...");
		mData = (MyDataHolder) bundle.getSerializable("poi");
		if (mData == null)
			throw new NullPointerException("mData sent is null...");
		type = bundle.getString("type");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (inflater == null)
			inflater = (LayoutInflater) getLayoutInflater(savedInstanceState);
		View view = inflater.inflate(R.layout.specificpoi_listviewfragment,
				null);
		about_button = (Button) view.findViewById(R.id.about_button);
		promotion_button = (Button) view.findViewById(R.id.promo_button);
		event_button = (Button) view.findViewById(R.id.event_button);
		comment_button = (Button) view.findViewById(R.id.comment_button);
		aboutlayout = (LinearLayout) view.findViewById(R.id.aboutlayout);
		promolayout = (LinearLayout) view.findViewById(R.id.promotionlayout);
		eventlayout = (LinearLayout) view.findViewById(R.id.eventlayout);
		commentlayout = (LinearLayout) view.findViewById(R.id.commentlayout);
		re_layout = (RelativeLayout) view.findViewById(R.id.re_layout);
		main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
		about_button.setOnClickListener(this);
		promotion_button.setOnClickListener(this);
		event_button.setOnClickListener(this);
		comment_button.setOnClickListener(this);
		Log.d(tag, " OnCreateView()");
		if (type != null && type.equalsIgnoreCase("promotions")) {
			promotion_button.performClick();
			promtion_Click();
		} else if (type != null && type.equalsIgnoreCase("sobre")) {
			about_button.performClick();
			sobre_Click();
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.about_button) {

			aboutlayout.removeAllViews();
			aboutlayout.setVisibility(View.VISIBLE);
			View convertView = aboutview_Layout();
			aboutlayout.addView(convertView);

			sobre_Click();
		} else if (v.getId() == R.id.promo_button) {
			promolayout.removeAllViews();
			promolayout.setVisibility(View.VISIBLE);
			TextView textview = new TextView(getActivity());
			textview.setGravity(Gravity.CENTER_HORIZONTAL);
			if (mHashMap != null) {
				if (mHashMap.get("promotions").size() > 0) {
					Logger.d(tag, "Promtion are existing");
					List<MyDataHolder> list = mHashMap.get("promotions");
					for (int i = 0; i < list.size(); i++) {
						View convertView = promotionview_Layout(list.get(i));
						promolayout.addView(convertView);
					}
				} else {
					Logger.d(tag, " No Promotions");
					textview.setText("Não Informado");
					promolayout.addView(textview);
				}
			} else {
				textview.setText("Não Informado");
				promolayout.addView(textview);
			}
			promtion_Click();
		} else if (v.getId() == R.id.event_button) {
			eventlayout.removeAllViews();
			eventlayout.setVisibility(View.VISIBLE);
			TextView textview = new TextView(getActivity());
			textview.setGravity(Gravity.CENTER_HORIZONTAL);
			if (mHashMap != null) {
				if (mHashMap.get("events").size() > 0) {
					List<MyDataHolder> list = mHashMap.get("events");
					for (int i = 0; i < list.size(); i++) {
						View convertView = EventView_Layout(list.get(i));
						eventlayout.addView(convertView);
					}
				} else {
					textview.setText("Não Informado");
					eventlayout.addView(textview);
				}
			} else {
				textview.setText("Não Informado");
				eventlayout.addView(textview);
			}
			evento_Click();
		} else if (v.getId() == R.id.comment_button) {
			commentlayout.removeAllViews();
			commentlayout.setVisibility(View.VISIBLE);
			TextView textview = new TextView(getActivity());
			textview.setGravity(Gravity.CENTER_HORIZONTAL);
			if (mHashMap != null) {
				if (mHashMap.get("comments").size() > 0) {
					List<MyDataHolder> list = mHashMap.get("comments");
					for (int i = 0; i < list.size(); i++) {
						View convertView = comment_Layout(list.get(i));
						commentlayout.addView(convertView);
					}
				} else {
					textview.setText(mActivity.getResources().getString(
							R.string.nocomment));
					commentlayout.addView(textview);
				}
			} else {
				textview.setText(mActivity.getResources().getString(
						R.string.nocomment));
				commentlayout.addView(textview);
			}
			comment_Click();
		}

	}

	public View aboutview_Layout() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = inflater.inflate(R.layout.about_view_layout, null);
		ImageView topimage = (ImageView) convertView
				.findViewById(R.id.topImage_imagview);
		TextView adress = (TextView) convertView
				.findViewById(R.id.address_txtview);
		TextView timedate = (TextView) convertView
				.findViewById(R.id.time_date_textview);
		TextView telephone = (TextView) convertView
				.findViewById(R.id.telephone_txtview);
		ImageView call_image = (ImageView) convertView
				.findViewById(R.id.call_image);
		TextView weblink = (TextView) convertView
				.findViewById(R.id.weblink_txtview);
		if (mData.getLoc_address() != null)
			adress.setText("" + mData.getLoc_address());
		else
			adress.setText("Não Informado");
		// if (mData.getTime() != null)
		// timedate.setText("" + mData.getTime());
		// else
		if (mData.getLoc_time() != null) {
			timedate.setText("" + mData.getLoc_time());
		} else
			timedate.setText("Não Informado");
		if (mData.getLoc_telephone() != null)
			telephone.setText("" + mData.getLoc_telephone());
		else
			telephone.setText("Não Informado");
		if (mData.getLoc_site() != null)
			weblink.setText("" + mData.getLoc_site());
		else
			weblink.setText("Não Informado");
		if (mData.getLoc_image() != null)
			imageLoager.displayImage(mData.getLoc_image(), topimage);
		call_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// String no="";
				if (mData.getLoc_telephone() != null) {
					String number = "tel:"
							+ mData.getLoc_telephone().toString().trim();
					/*
					 * String number = "tel:" +no.trim();
					 */
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse(number));
					startActivity(callIntent);
				}

			}
		});
		weblink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mData.getLoc_site() != null) {
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(mData.getLoc_site()));
					startActivity(myIntent);
				}
			}
		});

		return convertView;
	}

	public View promotionview_Layout(MyDataHolder myDataHolder) {

		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = inflater.inflate(R.layout.promotion_view_layout,
				null);
		Logger.d(tag, "Data in promotion :" + "\n Name " + mData.getName()
				+ " \n Desrciption  " + mData.getDescription() + " time  \n"
				+ mData.getTime() + " \n Intial Date " + mData.getDateInitail()
				+ " \n final Date " + mData.getDateFinal());
		ImageView image = (ImageView) convertView
				.findViewById(R.id.promotion_imagview);
		TextView title = (TextView) convertView
				.findViewById(R.id.title_txtview);
		TextView description = (TextView) convertView
				.findViewById(R.id.description_textview);
		TextView time = (TextView) convertView.findViewById(R.id.time_txtview);
		TextView validtill = (TextView) convertView
				.findViewById(R.id.valid_txtview);

		// Set the Texts
		if (myDataHolder.getName() != null)
			title.setText("" + myDataHolder.getName());
		else {
			title.setText("Não Informado");
		}

		if (myDataHolder.getDescription() != null)
			description.setText(Html.fromHtml("<b><font size=20>"
					+ "Descricao : " + "</font></b>"
					+ myDataHolder.getDescription()));
		else
			description.setText(Html.fromHtml("<b><font size=20>"
					+ "Descricao : " + "</font></b>" + "Não Informado"));

		if (myDataHolder.getTime() != null)
			time.setText(" " + myDataHolder.getTime());
		else if (myDataHolder.getLoc_time() != null)
			time.setText(" " + myDataHolder.getLoc_time());
		else {
			time.setText("Não Informado");
		}
		if (myDataHolder.getValid() != null) {
			validtill.setText(Html.fromHtml("<b><font size=20>"
					+ "Valido para : " + "</font></b>"
					+ myDataHolder.getValid()));
		} else {
			validtill.setText(Html.fromHtml("<b><font size=20>"
					+ "Valido para : " + "</font></b>" + "Não Informado"));
		}
		imageLoager.displayImage(
				myDataHolder.getImage() != null ? myDataHolder.getImage()
						: mData.getLoc_image(), image);
		return convertView;
	}

	public View EventView_Layout(final MyDataHolder myDataHolder) {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View convertView = inflater.inflate(R.layout.event_view_layout,
				null);
		TextView event_txtview = (TextView) convertView
				.findViewById(R.id.event_txtview);
		String text = myDataHolder.getDate() + " - " + myDataHolder.getName()
				+ " - ";
		SpannableStringBuilder ssb = new SpannableStringBuilder(text
				+ "Saiba Mais");
		ssb.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Intent intent = new Intent(getActivity(),
						Events_SingleDetail.class);
				//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("poi", myDataHolder);
				intent.putExtra("type", "sobre");
				startActivity(intent);
				about_button.performClick();
				getActivity().overridePendingTransition(R.anim.incoming,
						R.anim.outgoing);
			}
		}, text.length(), text.length() + 10, 0);
		event_txtview.setMovementMethod(LinkMovementMethod.getInstance());
		event_txtview.setText(ssb, BufferType.SPANNABLE);
		return convertView;
	}

	public View comment_Layout(MyDataHolder myDataHolder) {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = inflater.inflate(R.layout.comment_view_layout, null);
		TextView name = (TextView) convertView.findViewById(R.id.name_textview);
		TextView commentdate = (TextView) convertView
				.findViewById(R.id.date_txtview);
		TextView comment = (TextView) convertView
				.findViewById(R.id.commet_txtview);
		name.setText("" + myDataHolder.getName());
		commentdate.setText("" + myDataHolder.getDate());
		if (myDataHolder.getComment() != null)
			comment.setText(myDataHolder.getComment());
		else
			comment.setText("");
		return convertView;
	}

	public String getDayName(String date) {
		String input_date = date;
		Logger.d(tag, "Date fromat is :" + date);
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		Date dt1 = null;
		try {
			dt1 = format1.parse(input_date);
		} catch (ParseException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
		DateFormat format2 = new SimpleDateFormat("EEEE");
		String InitialDay = format2.format(dt1);
		return InitialDay;
	}

	public void sobre_Click() {
		main_layout.setGravity(Gravity.TOP);
		about_button.setVisibility(View.GONE);
		promotion_button.setVisibility(View.GONE);
		event_button.setVisibility(View.GONE);
		comment_button.setVisibility(View.GONE);
		aboutlayout.setVisibility(View.VISIBLE);
		promolayout.setVisibility(View.GONE);
		eventlayout.setVisibility(View.GONE);
		commentlayout.setVisibility(View.GONE);
	}

	public void promtion_Click() {
		about_button.setVisibility(View.GONE);
		promotion_button.setVisibility(View.VISIBLE);
		event_button.setVisibility(View.GONE);
		comment_button.setVisibility(View.GONE);
		aboutlayout.setVisibility(View.GONE);
		promolayout.setVisibility(View.VISIBLE);
		eventlayout.setVisibility(View.GONE);
		commentlayout.setVisibility(View.GONE);
	}

	public void evento_Click() {

		about_button.setVisibility(View.GONE);
		promotion_button.setVisibility(View.GONE);
		event_button.setVisibility(View.VISIBLE);
		comment_button.setVisibility(View.GONE);
		aboutlayout.setVisibility(View.GONE);
		promolayout.setVisibility(View.GONE);
		eventlayout.setVisibility(View.VISIBLE);
		commentlayout.setVisibility(View.GONE);
	}

	public void comment_Click() {

		about_button.setVisibility(View.GONE);
		promotion_button.setVisibility(View.GONE);
		event_button.setVisibility(View.GONE);
		comment_button.setVisibility(View.VISIBLE);
		aboutlayout.setVisibility(View.GONE);
		promolayout.setVisibility(View.GONE);
		eventlayout.setVisibility(View.GONE);
		commentlayout.setVisibility(View.VISIBLE);
	}

	public void tab_click() {
		if (re_layout != null)
			re_layout.setGravity(Gravity.CENTER);
		if (about_button != null)
			about_button.setVisibility(View.VISIBLE);
		if (promotion_button != null)
			promotion_button.setVisibility(View.VISIBLE);
		if (event_button != null)
			event_button.setVisibility(View.VISIBLE);
		if (comment_button != null)
			comment_button.setVisibility(View.VISIBLE);
		if (aboutlayout != null)
			aboutlayout.setVisibility(View.GONE);
		if (promolayout != null)
			promolayout.setVisibility(View.GONE);
		if (eventlayout != null)
			eventlayout.setVisibility(View.GONE);
		if (commentlayout != null)
			commentlayout.setVisibility(View.GONE);
	}

	public void setHashMap(HashMap<String, List<MyDataHolder>> mHashMap) {
		this.mHashMap = mHashMap;
		Logger.d(tag, "Hash Map is updated");

	}

	public void Perfomring_click() {
		Handler handle = new Handler();
		if (type != null && type.equalsIgnoreCase("promotions")) {
			handle.postDelayed((Runnable) getActivity(), 2000);
			promotion_button.performClick();
			promtion_Click();
		}
	}

	public void notifyDataSetChange() {
		Logger.d(tag, "Data Change is Notified");
		if (type != null && type.equalsIgnoreCase("promotions")) {
			Logger.d(tag, "Data Change is Notified : Promotion Clicked");
			promotion_button.performClick();
			promtion_Click();
		}
	}

}
