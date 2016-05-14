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

    @GET("reservations/customer/date/{date}")
    Call<List<Reservation>> getCustomerListByDate(@Path("date") String date);

    @GET("reservations/customer/week/{date}")
    Call<List<Reservation>> getCustomerListByWeek(@Path("date") String date);

    @GET("reservations/customer/month/{year}/{month}")
    Call<List<Reservation>> getCustomerListByMonth(@Path("year") String year,@Path("month") String month);


    @GET("reservations/trainer/date/{date}")
    Call<List<Reservation>> getTrainerListByDate(@Path("date") String date);

    @GET("reservations/trainer/week/{date}")
    Call<List<Reservation>> getTrainerListByWeek(@Path("date") String date);

    @GET("reservations/trainer/month/{year}/{month}")
    Call<List<Reservation>> getTrainerListByMonth(@Path("year") String year,@Path("month") String month);


    @POST("reservations/add")
    Call<Void> addReservations(@Body Reservation reservation);

}
