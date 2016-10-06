package com.oigoiania.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.br.oigoiania.BaseActivity;
import com.br.oigoiania.Home;
import com.br.oigoiania.R;
import com.oigoiania.util.Net;
import com.oigoiania.util.Preference;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;



public class SigninActivity extends BaseActivity implements View.OnClickListener{

    String TAG = "matata:SignIn";
    private EditText m_fullName;
    private EditText m_password;

    public String email;
    public String password ;

    private final String EMAIL_PATTERN = "[\\w-]+@([\\w-]+\\.)+[\\w-]+";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        context = this;
        m_fullName = (EditText)findViewById(R.id.edtEmail);
        m_password = (EditText)findViewById(R.id.edtPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);

        findViewById(R.id.btnForgotPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomAlert();
            }
        });

       

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

                String result = validate();
                if (result == null){
                
                    new SigninAsync().execute();

                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnBack:
            	startActivity(new Intent(SigninActivity.this, SRActivity.class));
            	break;
        }
    }

    private void showCustomAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Forgot Password?");
        alertDialog.setMessage("Please Enter your Email");

        final EditText input = new EditText(SigninActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String str_email;
                        str_email = input.getText().toString();
                        /*if (!str_email.matches(EMAIL_PATTERN)) {
                            Toast.makeText(SigninActivity.this,"Please enter correct email for resetting password!",Toast.LENGTH_LONG).show();
                        }else {
                            new ForgetPwdAsync().execute(str_email);
                            dialog.dismiss();
                        }*/
                        new ForgetPwdAsync().execute(str_email);
                        dialog.dismiss();

                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    private String validate() {

        email = m_fullName.getText().toString();

        password = m_password.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
            return "Make sure fill out all fields";
        }
        /*if (!email.matches(EMAIL_PATTERN)) {
            return "Please input correct email";
        }*/
        return null;
    }

    class SigninAsync extends AsyncTask<Void, Integer, String> {

        private ProgressDialog pd;
        protected void onPreExecute() {
            pd = ProgressDialog.show(SigninActivity.this,null,"Confirming details...");
        }

        protected String doInBackground(Void... params) {


            String parameter = "email=" + email + "&password=" + password ;
            Log.d("matata", parameter);
            String response = Net.excutePostString("login.php", parameter);

            return response;
        }

        protected void onPostExecute(String response) {

            pd.dismiss();
            if (response == null) {
                Toast.makeText(SigninActivity.this,"You are not registered . Please register.",Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONObject responseobject = null;
                responseobject = new JSONObject(response);

                if (responseobject.getString("message").equals("Success")) {
                	Preference.saveText(context, "login", "yes");
                    Preference.saveText(context, "userId", responseobject.getString("userId"));
                    startActivity(new Intent(SigninActivity.this, Home.class));
                }else
                    Toast.makeText(SigninActivity.this,"You are not registered. Please register or try again later",Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                Log.d("matata:json parsing", "fail");
                e.printStackTrace();
                Toast.makeText(SigninActivity.this,"You are not registered. Please register or try again later.",Toast.LENGTH_LONG).show();
            }
        }
    }

    class ForgetPwdAsync extends AsyncTask<String, Integer, String> {

        private ProgressDialog pd;
        protected void onPreExecute() {
            pd = ProgressDialog.show(SigninActivity.this,null,"Validating Email...");
        }

        protected String doInBackground(String... params) {

            String parameter = "email=" + params[0] ;
            Log.d("matata", parameter);
            String response = Net.excutePostString("forgotpassword.php", parameter);

            return response;
        }

        protected void onPostExecute(String response) {

            pd.dismiss();
            if (response == null) {
                Toast.makeText(SigninActivity.this,"You are not registered or your registration is pending administrator's approval. Please register or try again later.",Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONObject responseobject = null;
                responseobject = new JSONObject(response);

                if (responseobject.getString("message").equals("Success")) {
                    Toast.makeText(SigninActivity.this,"Please check your email for resetting the password",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(SigninActivity.this,"Email doesn't exist in our database",Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                Log.d("matata:json parsing", "fail");
                e.printStackTrace();
                Toast.makeText(SigninActivity.this,"You are not registered or your registration is pending administrator's approval. Please register or try again later.",Toast.LENGTH_LONG).show();
            }
        }
    }

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onSensorChange(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

}
