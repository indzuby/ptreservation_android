package com.fastandslow.ptreservation.service;

import com.fastandslow.ptreservation.domain.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zuby on 2016. 5. 14..
 */
public interface CustomerService {

    @GET("reservations/customer/date/{date}")
    Call<List<Reservation>> getListByDate(@Path("date") String date);

    @GET("reservations/customer/week/{date}")
    Call<List<Reservation>> getListByWeek(@Path("date") String date);

    @GET("reservations/customer/month/{year}/{month}")
    Call<List<Reservation>> getistByMonth(@Path("year") String year,@Path("month") String month);
}
