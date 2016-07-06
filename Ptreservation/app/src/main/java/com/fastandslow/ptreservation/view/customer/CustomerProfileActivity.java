package com.fastandslow.ptreservation.view.customer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.Customer;
import com.fastandslow.ptreservation.network.RestApi;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.utils.SessionUtils;
import com.fastandslow.ptreservation.utils.StateUtils;
import com.fastandslow.ptreservation.view.common.BaseActivity;
import com.fastandslow.ptreservation.view.common.ProfileEditActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016-07-02.
 */
public class CustomerProfileActivity extends BaseActivity {

    ImageView profileImage;
    TextView profileName;
    TextView trainerName;
    TextView trainerTel;
    TextView ptCount;
    Customer mCustomer;

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                Intent intent = new Intent(CustomerProfileActivity.this, ProfileEditActivity.class);
                startActivityForResult(intent,1001);
                break;
            case R.id.profile_trainer_tel:
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mCustomer.getTrainer().getUser().getTel()));
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        initActionbar();
        getUserInfo();
    }
    public void initActionbar(){
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_profile);
        v.findViewById(R.id.back).setOnClickListener(this);
        v.findViewById(R.id.edit).setOnClickListener(this);

    }
    public void init(){
        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileName = (TextView) findViewById(R.id.profile_name);
        trainerName = (TextView) findViewById(R.id.profile_trainer_name);
        trainerTel = (TextView) findViewById(R.id.profile_trainer_tel);
        ptCount = (TextView) findViewById(R.id.profile_pt_count);

        if(mCustomer.getUser().getProfileUrl() !=null)
            Glide.with(this).load(RestApi.imageUrl+mCustomer.getUser().getProfileUrl()).into(profileImage);
        profileName.setText(mCustomer.getUser().getName());
        trainerName.setText(mCustomer.getTrainer().getUser().getName()+"트레이너");
        trainerTel.setText(mCustomer.getTrainer().getUser().getTel());
        ptCount.setText(mCustomer.getPtCount()+"회");

        trainerTel.setOnClickListener(this);
    }
    public void getUserInfo(){
        int id = StateUtils.getUserId(this);
        try {
            RestApi.getInstance(this).getCustomer(id, new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.code() == 200) {
                        mCustomer = response.body();
                        init();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Log.e("FAILURE", "FAILURE");
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode == 1001) {
                getUserInfo();
            }
        }
    }
}
