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

    @GET("trainers.json")
    Call<List<Trainer>> getTrainerList(Callback<List<Trainer>> callback);

    @GET("reservations/trainer/date/{date}")
    Call<List<Reservation>> getListByDate(@Path("date") String date);

    @GET("reservations/trainer/week/{date}")
    Call<List<Reservation>> getListByWeek(@Path("date") String date);

    @GET("reservations/trainer/month/{year}/{month}")
    Call<List<Reservation>> getListByMonth(@Path("year") String year,@Path("month") String month);
}
