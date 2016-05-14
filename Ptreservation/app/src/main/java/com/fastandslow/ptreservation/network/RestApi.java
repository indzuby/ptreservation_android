package com.fastandslow.ptreservation.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fastandslow.ptreservation.domain.Customer;
import com.fastandslow.ptreservation.domain.Reservation;
import com.fastandslow.ptreservation.service.CustomerService;
import com.fastandslow.ptreservation.service.LoginService;
import com.fastandslow.ptreservation.service.ReservationService;
import com.fastandslow.ptreservation.service.TrainerService;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.SessionUtils;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zuby on 2016. 4. 30..
 */
public class RestApi {




//    final static String url = "http://localhost:8000/";
    final static String url = "http://192.168.0.82:3000/";

    Context mContext;
    private static RestApi instance ;
    Retrofit retrofit;


    LoginService loginService;

    ReservationService reservationService;

    TrainerService trainerService;

    CustomerService customerService;

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


    private void initReservationService(){
        if(reservationService==null)
            reservationService = retrofit.create(ReservationService.class);
    }


    private void initTrainerService(){
        if(trainerService==null)
            trainerService = retrofit.create(TrainerService.class);
    }


    private void initCustomerService(){
        if(customerService==null)
            customerService = retrofit.create(CustomerService.class);
    }

    public void login(String email, String password,Callback<Void> callback){
        initLoginService();
        Call<Void> logins = loginService.login(email, password);
        logins.enqueue(callback);
    }

    public void addReservation(Reservation reservation,Callback<Void> callback) {
        initReservationService();
        Call<Void> reservations = reservationService.addReservations(reservation);
        reservations.enqueue(callback);
    }

    public void getListByDateCustomer(DateTime date,Callback<List<Reservation>> callback) {
        String state = SessionUtils.getString(mContext, CodeDefinition.USER_STATE,CodeDefinition.CUSTOMER);
        if(state.equals(CodeDefinition.CUSTOMER)) {
            initCustomerService();
            Call<List<Reservation>> reservations = customerService.getListByDate(date.toString("yyyy-MM-dd"));
            reservations.enqueue(callback);
        }else {
            initTrainerService();
            Call<List<Reservation>> reservations = trainerService.getListByDate(date.toString("yyyy-MM-dd"));
            reservations.enqueue(callback);
        }
    }


}
