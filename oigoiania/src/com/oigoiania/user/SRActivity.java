package com.oigoiania.user;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;

import com.br.oigoiania.BaseActivity;
import com.br.oigoiania.R;

public class SRActivity extends BaseActivity implements View.OnClickListener{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

    }
	
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
            	startActivity(new Intent(SRActivity.this, SigninActivity.class));
                break;
            case R.id.button2:
            	startActivity(new Intent(SRActivity.this, SignUpActivity.class));
                break;

        }
    }

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSensorChange(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

}
