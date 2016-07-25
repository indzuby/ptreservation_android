package com.fastandslow.ptreservation.service;

import com.fastandslow.ptreservation.domain.Reservation;
import com.fastandslow.ptreservation.domain.Trainer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zuby on 2016. 5. 14..
 */
public interface TrainerService {

    @GET("trainers/list")
    Call<List<Trainer>> getTrainerList();

    @GET("reservations/trainer/{id}/date/{date}")
    Call<List<Reservation>> getListByDate(@Path("id") int id,@Path("date") String date);

    @GET("reservations/trainer/{id}/week/{date}")
    Call<List<Reservation>> getListByWeek(@Path("id") int id,@Path("date") String date);

    @GET("reservations/trainer/{id}/month/{year}/{month}")
    Call<List<Reservation>> getListByMonth(@Path("id") int id,@Path("year") String year,@Path("month") String month);
}
