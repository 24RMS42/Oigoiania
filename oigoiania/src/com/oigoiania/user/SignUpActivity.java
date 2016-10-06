package com.oigoiania.user;

import org.json.JSONException;
import org.json.JSONObject;

import com.br.oigoiania.BaseActivity;
import com.br.oigoiania.R;
import com.oigoiania.util.Net;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends BaseActivity implements View.OnClickListener{

    private EditText m_fullName;
    private EditText m_phone;
    private EditText m_email;
    private EditText m_password;
    private EditText m_confirmPwd;

    private final String EMAIL_PATTERN = "[\\w-]+@([\\w-]+\\.)+[\\w-]+";

    static ProgressDialog pd;

    public String fullname;
    public String phone ;
    public String email ;
    public String password ;
    public String confirmPwd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        m_fullName = (EditText)findViewById(R.id.edtName);
        m_phone = (EditText)findViewById(R.id.edtPhone);
        m_email = (EditText)findViewById(R.id.edtEmail);
        m_password = (EditText)findViewById(R.id.edtPassword);
        m_confirmPwd = (EditText)findViewById(R.id.edtConfirm);

        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                String result = validate();
                if (result == null){

                    new SignupAsync().execute();
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnBack:
            	startActivity(new Intent(SignUpActivity.this, SRActivity.class));
            	break;

        }
    }


    private String validate() {

        fullname = m_fullName.getText().toString();
        phone = m_phone.getText().toString();
        email = m_email.getText().toString();
        password = m_password.getText().toString();
        confirmPwd = m_confirmPwd.getText().toString();
 

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(fullname)  || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(password) ) {
            return "Make sure fill out all fields";
        }
        if (!email.matches(EMAIL_PATTERN)) {
            return "Please input correct email";
        }
        if (!password.equals(confirmPwd)){
            return  "Please confirm password";
        }

        return null;
    }

    class SignupAsync extends AsyncTask<Void, Integer, String> {

        private ProgressDialog pd;
        protected void onPreExecute() {
            pd = ProgressDialog.show(SignUpActivity.this,null,"Confirming details...");
        }

        protected String doInBackground(Void... params) {

            String parameter = "&fullname=" + fullname + "&email=" + email + "&phone=" + phone 
            		+ "&password=" + password + "&nivel=1";
            Log.d("matata", parameter);
            String response = Net.excutePostString("register.php", parameter);

            return response;
        }

        protected void onPostExecute(String response) {

            pd.dismiss();
            if (response == null) {
                Toast.makeText(SignUpActivity.this,"Sorry",Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("matata", response);
            try {
                JSONObject responseobject = null;
                responseobject = new JSONObject(response);

                if (responseobject.getString("message").equals("Success")) {
                    Toast.makeText(SignUpActivity.this,"Thank you for registering. Please login and so you can leave comment",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this, SigninActivity.class));
                }else
                    Toast.makeText(SignUpActivity.this,"Registeration Error",Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                Log.d("matata:json parsing", "fail");
                e.printStackTrace();
                Toast.makeText(SignUpActivity.this,"Sorry",Toast.LENGTH_LONG).show();
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
