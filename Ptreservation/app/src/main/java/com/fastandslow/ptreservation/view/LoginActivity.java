package com.fastandslow.ptreservation.view;

import android.os.Bundle;
import android.view.View;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.view.common.BaseActivity;

/**
 * Created by zuby on 2016. 4. 5..
 */
public class LoginActivity extends BaseActivity{
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    public void init(){

    }
}
