package com.fastandslow.ptreservation.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.Common;
import com.fastandslow.ptreservation.domain.User;
import com.fastandslow.ptreservation.network.RestApi;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.SessionUtils;
import com.fastandslow.ptreservation.view.trainer.TrainerMainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 4. 5..
 */
public class LoginActivity extends BaseActivity {
    EditText mEmailEditText, mPasswordEditText;
    CheckBox autoLogin;
    boolean isAutoLogin;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            Toast.makeText(getBaseContext(), "로그인 중입니다.", Toast.LENGTH_SHORT).show();
            login(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        }
    }

    public void startTrainerMainActivity() {
        if (!isAutoLogin) {
            SessionUtils.putBoolean(this, CodeDefinition.AUTO_LOGIN, autoLogin.isChecked());
            if (autoLogin.isChecked()) {
                SessionUtils.putString(this, CodeDefinition.EMAIL, mEmailEditText.getText().toString());
                SessionUtils.putString(this, CodeDefinition.PASSWORD, mPasswordEditText.getText().toString());
            }
        }
        Intent intent = new Intent(this, TrainerMainActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(String email, String password) {
        try {
            RestApi.getInstance(this).login(email, password,
                    new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200 || response.code() == 201) {
                                if (response.code() == 200) {
                                    SessionUtils.putString(getBaseContext(), CodeDefinition.USER_STATE, CodeDefinition.TRAINER);
                                    SessionUtils.putInt(getBaseContext(), CodeDefinition.TRAINER_ID, response.body().getId());
                                    Toast.makeText(getBaseContext(), response.body().getName() + "님 \n관리자로 로그인했습니다.", Toast.LENGTH_SHORT).show();
                                    startTrainerMainActivity();
                                } else {
                                    SessionUtils.putString(getBaseContext(), CodeDefinition.USER_STATE, CodeDefinition.CUSTOMER);
                                    SessionUtils.putInt(getBaseContext(), CodeDefinition.CUSTOMER_ID, response.body().getId());
                                    Toast.makeText(getBaseContext(), response.body().getName() + "님 \n회원으로 로그인했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        mEmailEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);

        isAutoLogin = SessionUtils.getBoolean(this, CodeDefinition.AUTO_LOGIN, false);
        if (isAutoLogin) {
            String email = SessionUtils.getString(this, CodeDefinition.EMAIL, "asdf@asdf.com");
            String password = SessionUtils.getString(this, CodeDefinition.PASSWORD, "password");
            mEmailEditText.setText(email);
            mPasswordEditText.setText(password);
            login(email, password);
        }
        findViewById(R.id.login_button).setOnClickListener(this);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);
    }
}
