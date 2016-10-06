package com.oigoiania.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.oigoiania.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oigoiania.adapters.POI_Images_Adapter;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.network.RequestListener;
import com.oigoiania.parsers.ImagesHolder;
import com.oigoiania.parsers.MyDataHolder;
import com.oigoiania.parsers.Parser;
import com.oigoiania.util.Util;

public class SpecificPOI_ImagesFragment extends Fragment implements
		RequestCallBack, Runnable, OnItemClickListener {
	private static final String tag = "SpecificPOI_ImagesFragment";
	private GridView mGridView = null;
	private ImageView fullimage;
	private TextView empty_picture;
	private RelativeLayout image_layout;
	private POI_Images_Adapter mAdapter = null;
	private FragmentActivity mActivity = null;
	private static List<ImagesHolder> list = new ArrayList<ImagesHolder>();
	private RequestListener mRequestListener = null;
	private ImageLoader imageLoader;
	private MyDataHolder mData;
	private ProgressDialog progress_dialog = null;
	public static boolean isDialogOnceShown = false;
	private static SpecificPOI_ImagesFragment specificepoi_imagefragment = null;

	public static SpecificPOI_ImagesFragment getInstance() {
		return specificepoi_imagefragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.d(tag, " enter to onCreate()");
		specificepoi_imagefragment = this;
		mActivity = getActivity();
		Bundle bundle = getArguments();
		if (bundle == null)
			throw new NullPointerException("Bundle sent is null...");
		mData = (MyDataHolder) bundle.getSerializable("poi");
		progress_dialog = new ProgressDialog(getActivity());
		progress_dialog.setMessage("Please wait..");
		progress_dialog.setCancelable(true);
		progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		if (!isDialogOnceShown) {
			if (mData != null) {
				Logger.d(tag, " data is not null" + " Request is sending");
				// executor = MyThreadPoolExecutor.getInstance();
				mRequestListener = new RequestListener();
				mRequestListener.requestFOrImages(this, mData.getLoc_id());// "58");//
				new Thread(mRequestListener).start();
				// executor.submit(mRequestListener);
			}
			progress_dialog.show();
			isDialogOnceShown = true;
		}
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.d(tag, "onCreateView()");
		View view = inflater.inflate(R.layout.specificpoi_images, null);
		mGridView = (GridView) view.findViewById(R.id.grid_view);
		fullimage = (ImageView) view.findViewById(R.id.fullimage);
		empty_picture = (TextView) view.findViewById(R.id.textView1);
		image_layout = (RelativeLayout) view.findViewById(R.id.imagelayout);
		mGridView.setVisibility(View.VISIBLE);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		try {
			Util.nmberOfTries = 0;
			Logger.d(tag, "Data :" + jsonOnject.toString());
			List<ImagesHolder> mTempList = Parser.parseImagesURLs(jsonOnject);
			if(mTempList.size()==0){
				ImagesHolder mImagesHolder=new ImagesHolder();
				mImagesHolder.setUrl(mData.getLoc_image());
				mTempList.add(mImagesHolder);
			}
				
			list.addAll(mTempList);
			mActivity.runOnUiThread(this);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestFail(final String messaget) {
		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(mActivity, messaget,
						Toast.LENGTH_SHORT);
				toast.setDuration(2000);
				if (messaget.equalsIgnoreCase(Util.exitCode)) {
					progress_dialog.dismiss();
					toast.setDuration(3000);
					toast.setText("Data is not available due to Internet issue.");
				} else if (messaget.equalsIgnoreCase(Util.noInterNetCode)) {
					toast.setText("Internet is not available.");
					toast.show();
					progress_dialog.dismiss();
				} else {
					toast.show();
				}
			}
		});

	}

	@Override
	public void onJSONException(String messaget) {
		if (Util.nmberOfTries < 1) {
			Util.nmberOfTries++;
			if (mRequestListener != null)
				new Thread(mRequestListener).start();
		} else
			Util.nmberOfTries = 0;
		Logger.d(tag, "onRequestFail " + messaget);
	}

	@Override
	public void run() {
		isDialogOnceShown = false;
		progress_dialog.dismiss();
		if (list.size() != 0) {
			Logger.d(tag, " Data is not Empty");
			mAdapter = new POI_Images_Adapter(mActivity, list);
			mGridView.setAdapter(mAdapter);
		} else {
			Logger.d(tag, "Data is Empty ");
			empty_picture.setVisibility(View.VISIBLE);
			empty_picture.setText("Nenhuma imagem cadastrada.");
			mGridView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ImagesHolder image_obj = (ImagesHolder) view.getTag();
		mGridView.setVisibility(View.GONE);
		image_layout.setVisibility(View.VISIBLE);
		fullimage.setVisibility(View.VISIBLE);
		imageLoader.displayImage(image_obj.getUrl(), fullimage);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		list.clear();
	}

	public void Tab_click() {
		if (mGridView != null)
			mGridView.setVisibility(View.VISIBLE);
		if (fullimage != null)
			fullimage.setVisibility(View.GONE);
	}
}
