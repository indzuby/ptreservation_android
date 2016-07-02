package com.fastandslow.ptreservation.network;

import android.content.Context;

import com.fastandslow.ptreservation.domain.Customer;
import com.fastandslow.ptreservation.domain.Reservation;
import com.fastandslow.ptreservation.domain.User;
import com.fastandslow.ptreservation.service.CustomerService;
import com.fastandslow.ptreservation.service.LoginService;
import com.fastandslow.ptreservation.service.ReservationService;
import com.fastandslow.ptreservation.service.TrainerService;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.SessionUtils;

import org.joda.time.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuby on 2016. 4. 30..
 */
public class RestApi {




//    final static String url = "http://localhost:8000/";
    final static String url = "http://192.168.0.13:3000/";

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
                .addConverterFactory(GsonConverterFactory.create())
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

    public void login(String email, String password,Callback<User> callback) throws Exception{
        initLoginService();
        Call<User> logins = loginService.login(email, password);
        logins.enqueue(callback);
    }

    public void addReservation(Reservation reservation,Callback<Void> callback) throws Exception {
        initReservationService();
        Call<Void> reservations = reservationService.addReservations(reservation);
        reservations.enqueue(callback);
    }

    public void editReservation(Reservation reservation,Callback<Void> callback) throws Exception {
        initReservationService();
        Call<Void> reservations = reservationService.editReservations(reservation.getId(), reservation);
        reservations.enqueue(callback);
    }

    public void getListByDateReservation(int id, DateTime date, Callback<List<Reservation>> callback) throws Exception{
        String state = SessionUtils.getString(mContext, CodeDefinition.USER_STATE, CodeDefinition.CUSTOMER);
//        if(state.equals(CodeDefinition.CUSTOMER)) {
//            initCustomerService();
//            Call<List<Reservation>> reservations = customerService.getListByDate(id,date.toString("yyyy-MM-dd"));
//            reservations.enqueue(callback);
//        }else {
            initTrainerService();
            Call<List<Reservation>> reservations = trainerService.getListByDate(id,date.toString("yyyy-MM-dd"));
            reservations.enqueue(callback);
//        }
    }

    public void getCustomer(int id,Callback<Customer> callback) throws Exception{
        initCustomerService();
        Call<Customer> customers = customerService.getCustomer(id);
        customers.enqueue(callback);
    }
    public void getCustomers(int trainerId,Callback<List<Customer>> callback) throws Exception{
        initCustomerService();
        Call<List<Customer>> customers = customerService.getCustomers(trainerId);
        customers.enqueue(callback);
    }

    public void getReservation(int id,Callback<Reservation> callback) throws Exception {
        initReservationService();
        Call<Reservation> reservations = reservationService.getReservation(id);
        reservations.enqueue(callback);
    }

}
