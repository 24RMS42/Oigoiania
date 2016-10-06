package com.br.oigoiania;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ch.boye.httpclientandroidlib.message.BasicNameValuePair;

import com.br.oigoiania.R;
import com.oigoiania.interfaces.RequestCallBack;
import com.oigoiania.logger.Logger;
import com.oigoiania.mail.MCrypt;
import com.oigoiania.network.RequestListener;

public class ContactusActivity extends Activity implements OnClickListener,
		RequestCallBack {
	private Activity mActivity = null;
	private ProgressDialog progress_dialog = null;
	private EditText name_edit, email_edit, massage_edit;
	private ImageView send_button;
	private RelativeLayout back_layout;
	private final String tag = "ContactusActivity";
	private final String key = "ASdf12HJ96oU61Er2E2F5V6N7E2c9a3A";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contactus_activity);
		initialization();
		send_button.setOnClickListener(this);
		back_layout.setOnClickListener(this);
	}

	public void initialization() {
		name_edit = (EditText) findViewById(R.id.name_editText);
		email_edit = (EditText) findViewById(R.id.email_editText);
		massage_edit = (EditText) findViewById(R.id.message_editText);
		send_button = (ImageView) findViewById(R.id.send_button);
		back_layout = (RelativeLayout) findViewById(R.id.rl_heading);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.send_button) {
			String name = null, email = null, mesg = null;
			if (!name_edit.getText().toString().isEmpty())
				name = name_edit.getText().toString();
			if (!email_edit.getText().toString().isEmpty())
				email = email_edit.getText().toString();
			if (!massage_edit.getText().toString().isEmpty())
				mesg = massage_edit.getText().toString();
			if (name_edit.getText().toString().isEmpty()) {
				name_edit.requestFocus();
				Toast.makeText(getApplicationContext(),
						"Você deve preencher o campo Nome.", Toast.LENGTH_SHORT)
						.show();
			} else if (email_edit.getText().toString().isEmpty()) {
				email_edit.requestFocus();
				Toast.makeText(getApplicationContext(),
						"Você deve preencher o campo de e-mail.",
						Toast.LENGTH_SHORT).show();
			} else if (!email_edit.getText().toString().contains("@")) {
				email_edit.requestFocus();
				Toast.makeText(getApplicationContext(),
						"Preencha um email válido.", Toast.LENGTH_SHORT).show();
			} else if (massage_edit.getText().toString().isEmpty()) {
				massage_edit.requestFocus();
				Toast.makeText(getApplicationContext(),
						"Você deve preencher o campo Mensagem.",
						Toast.LENGTH_SHORT).show();
			} else {
				Logger.d(tag, "Name: " + name + " Email :" + email
						+ " Message :" + mesg);
				MCrypt mcrypt = new MCrypt();
				String encryptedKey = "";
				try {
					encryptedKey = MCrypt.bytesToHex(mcrypt.encrypt(key));
				} catch (Exception e) {
					e.printStackTrace();
				}
				ArrayList<BasicNameValuePair> array = new ArrayList<BasicNameValuePair>();
				array.add(new BasicNameValuePair("name", name));
				array.add(new BasicNameValuePair("email", email));
				array.add(new BasicNameValuePair("mesg", mesg));
				array.add(new BasicNameValuePair("key", encryptedKey));
				// Intent email = new Intent(Intent.ACTION_SEND);
				// email.putExtra(Intent.EXTRA_EMAIL,
				// new String[] { "contato@oigoiania.com.br" }); //
				// contato@oigoiania.com.br
				// email.putExtra(Intent.EXTRA_SUBJECT, "Feed Back");
				// email.putExtra(Intent.EXTRA_TEXT, "" + str);
				// email.setType("text/plain");
				// startActivity(Intent.createChooser(email,
				// "Choose an Email client :" ));
				RequestListener mRequestListener = new RequestListener();

				mRequestListener.requestFOrEmailSend(this, array);
				new Thread(mRequestListener).start();
				progress_dialog = new ProgressDialog(this);
				progress_dialog.setMessage("Please wait..");
				progress_dialog.setCancelable(true);
				progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress_dialog.show();

			}
		} else if (v.getId() == R.id.rl_heading) {
			this.finish();
			overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		}

	}

	public void ClearText() {
		name_edit.setText("");
		email_edit.setText("");
		massage_edit.setText("");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}

	@Override
	public void onRequestSuccess(JSONObject jsonOnject) {
		Logger.d(tag,
				"onRequest Success() Response is :" + jsonOnject.toString());
		mActivity.runOnUiThread(new ShowMessgaes(jsonOnject.toString()));

	}

	@Override
	public void onRequestFail(String messaget) {
		Logger.d(tag, "onRequestFail() Message is :" + messaget.toString());
		mActivity.runOnUiThread(new ShowMessgaes("error"));

	}

	@Override
	public void onJSONException(String message) {
		mActivity.runOnUiThread(new ShowMessgaes("error"));
		Logger.d(tag, "onJSONException() Message is :" + message.toString());

	}

	private class ShowMessgaes implements Runnable {
		private String mesg = "";
		private String title = "";

		protected ShowMessgaes(String message) {
			if (message.contains("Error") || message.contains("error")) {
				mesg = "E-mail enviando failed.Try Novamente";
				title = "Error";
			} else {
				mesg = "E-mail enviado com sucesso.";
				title = "Message";
			}
		}

		@Override
		public void run() {
			showDialoge();
			ClearText();
			progress_dialog.dismiss();
		}

		private void showDialoge() {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ContactusActivity.this);
			builder.setMessage(mesg).setTitle(title);
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();

		}
	}

}
