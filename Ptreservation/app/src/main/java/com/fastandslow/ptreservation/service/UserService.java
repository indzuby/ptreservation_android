package com.fastandslow.ptreservation.service;

import com.fastandslow.ptreservation.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by zuby on 2016. 7. 3..
 */
public interface UserService  {

    @GET("users/{id}.json")
    Call<User> getUser(@Path("id") int id);

    @PUT("users/update/info")
    Call<Void> update(@Body User user);

    @PUT("users/update/withPassword")
    Call<Void> updateWithPassword(@Body User user);
}
