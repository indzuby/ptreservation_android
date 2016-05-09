package com.fastandslow.ptreservation.service;

import com.fastandslow.ptreservation.domain.Reservation;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zuby on 2016. 4. 30..
 */
public interface ReservationService {

    @GET("reservations/date/{date}")
    Call<List<Reservation>> getListByDate(@Path("date") String date);

    @GET("reservations/week/{date}")
    Call<List<Reservation>> getListByWeek(@Path("date") String date);

    @GET("reservations/month/{year}/{month}")
    Call<List<Reservation>> getListByMonth(@Path("year") String year,@Path("month") String month);

    @POST("reservations/add")
    Call<Void> addReservations(@Body Reservation reservation);

}
