package com.fastandslow.ptreservation.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zuby on 2016. 4. 30..
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("login/login")
    Call<Void> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login/dupEmail")
    Call<Void> dupEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("login/updatePassword")
    Call<Void> updatePassword(@Field("password") String password);
}
