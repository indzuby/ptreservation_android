package com.fastandslow.ptreservation.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fastandslow.ptreservation.service.LoginService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zuby on 2016. 4. 30..
 */
public class RestApi {




//    final static String url = "http://localhost:8000/";
    final static String url = "http://192.168.201.16:3000/";

    Context mContext;
    private static RestApi instance ;
    Retrofit retrofit;


    LoginService loginService;

    public RestApi(Context mContext) {
        this.mContext = mContext;
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
    }

    public static synchronized RestApi getInstance(Context context) {
        if(instance ==null) {
            instance = new RestApi(context);
        }
        return instance;
    }

    private void initLoginService(){
        if(loginService==null)
            loginService = retrofit.create(LoginService.class);
    }
    public void login(String email, String password,Callback<Void> callback){
        initLoginService();
        Call<Void> logins = loginService.login(email, password);
        logins.enqueue(callback);
    }

}
