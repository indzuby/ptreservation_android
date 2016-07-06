package com.fastandslow.ptreservation.view.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.Customer;
import com.fastandslow.ptreservation.domain.User;
import com.fastandslow.ptreservation.network.RestApi;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.utils.SessionUtils;
import com.fastandslow.ptreservation.utils.StateUtils;
import com.fastandslow.ptreservation.view.customer.CustomerProfileActivity;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 7. 3..
 */
public class ProfileEditActivity extends BaseActivity {
    User mUser;
    ImageView profileImage;
    EditText profileName;
    EditText profileTel;
    EditText password;
    EditText passwordRe;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:

                save();
                break;
            case R.id.profile_image:
                // gallery
                Toast.makeText(getBaseContext(),"프로필 이미지 기능이 준비중입니다.",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    Callback<Void> callback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.code() == 200) {
                Toast.makeText(getBaseContext(), "수정 되었습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(getBaseContext(),"수정에 실패하였습니다.",Toast.LENGTH_SHORT).show();
        }
    };
    public void save(){
        String password = this.password.getText().toString();
        String passwordRe = this.passwordRe.getText().toString();
        String name = profileName.getText().toString();
        String tel = profileTel.getText().toString();
        mUser.setName(name);
        mUser.setTel(tel);


        if (password.length() > 0) {
            if( password.equals(passwordRe)) {
                //withPassword
                mUser.setPassword(password);
                RestApi.getInstance(this).updateUserWithPassword(mUser,callback);
            }else {
                Toast.makeText(getBaseContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                return ;
            }
        }else {
            if(name.length()>0 && tel.length()>0) {
                RestApi.getInstance(this).updateUser(mUser,callback);
            }else {
                Toast.makeText(getBaseContext(),"빈칸을 입력해주세요.",Toast.LENGTH_SHORT).show();
                return ;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        initActionbar();
        getUserInfo();
    }
    public void initActionbar(){
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_profile_edit);
        v.findViewById(R.id.back).setOnClickListener(this);
        v.findViewById(R.id.save).setOnClickListener(this);
    }
    public void init(){
        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileName = (EditText) findViewById(R.id.profile_name);
        profileTel = (EditText) findViewById(R.id.profile_tel);
        password = (EditText) findViewById(R.id.profile_password);
        passwordRe = (EditText) findViewById(R.id.profile_password_re);
        if(mUser.getProfileUrl() !=null)
            Glide.with(this).load(RestApi.imageUrl+mUser.getProfileUrl()).into(profileImage);
        profileName.setText(mUser.getName());
        profileTel.setText(mUser.getTel());

        profileImage.setOnClickListener(this);
    }
    public void getUserInfo(){
        int id = SessionUtils.getInt(this, CodeDefinition.USER_ID,0);
        try {
            RestApi.getInstance(this).getUser(id, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        mUser = response.body();
                        init();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("FAILURE", "FAILURE");
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
