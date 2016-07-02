package com.fastandslow.ptreservation.service;

import com.fastandslow.ptreservation.domain.Customer;
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
public interface CustomerService {

    @GET("reservations/customer/{id}/date/{date}")
    Call<List<Reservation>> getListByDate(@Path("id") int id,@Path("date") String date);

    @GET("reservations/customer/{id}/week/{date}")
    Call<List<Reservation>> getListByWeek(@Path("id") int id,@Path("date") String date);

    @GET("reservations/customer/{id}/month/{year}/{month}")
    Call<List<Reservation>> getistByMonth(@Path("id") int id,@Path("year") String year,@Path("month") String month);

    @GET("trainers/{trainer_id}/customers")
    Call<List<Customer>> getCustomer(@Path("trainer_id") int trainerId);
}

