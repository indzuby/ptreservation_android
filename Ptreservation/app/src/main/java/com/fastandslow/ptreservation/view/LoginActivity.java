package com.fastandslow.ptreservation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.network.RestApi;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.SessionUtils;
import com.fastandslow.ptreservation.view.common.BaseActivity;

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

    public void startMainActivity() {
        if (!isAutoLogin) {
            SessionUtils.putBoolean(this, CodeDefinition.AUTO_LOGIN, autoLogin.isChecked());
            if (autoLogin.isChecked()) {
                SessionUtils.putString(this, CodeDefinition.EMAIL, mEmailEditText.getText().toString());
                SessionUtils.putString(this, CodeDefinition.PASSWORD, mPasswordEditText.getText().toString());
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(String email, String password) {

        RestApi.getInstance(this).login(email, password,
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            startMainActivity();

                        } else {
//                            Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        isAutoLogin = SessionUtils.getBoolean(this, CodeDefinition.AUTO_LOGIN, false);
        if (isAutoLogin) {
            login(SessionUtils.getString(this, CodeDefinition.EMAIL, "asdf@asdf.com"),
                    SessionUtils.getString(this, CodeDefinition.PASSWORD, "password"));
        }

        mEmailEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);

        findViewById(R.id.login_button).setOnClickListener(this);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);
    }
}
