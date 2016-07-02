package com.fastandslow.ptreservation.view.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.Customer;
import com.fastandslow.ptreservation.domain.Reservation;
import com.fastandslow.ptreservation.network.RestApi;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.utils.StateUtils;
import com.fastandslow.ptreservation.view.common.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hjan1 on 2016-05-11.
 */
public class CustomerNewScheduleActivity extends BaseActivity {
    TextView dateView;
    TextView startTime, endTime;
    TextView alarmView;
    TextView nameView;
    EditText memoView;

    Reservation reservation;


    DateTime startDatetime, endDatetime;
    int mId;
    Customer mCustomer;

    int mYear, mMonth, mDay;
    boolean isStartTime;
    boolean isTrainer;
    boolean isEdit;
    DateTime mDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        mId = StateUtils.getUserId(this);
        isTrainer = StateUtils.isTrainer(this);
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        //Intent intent = new Intent(this.getIntent());
        initActionbar();
        init();
    }

    public void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_new_schedule);
        v.findViewById(R.id.back).setOnClickListener(this);
        v.findViewById(R.id.save).setOnClickListener(this);
        if(isEdit) {
            ((TextView)v.findViewById(R.id.title)).setText("일정 수정");
        }
    }

    public void init() {
        if (!isTrainer) getCustomerInfo();
        reservation = new Reservation();

        String dateTimeString = getIntent().getStringExtra("DATE");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        mDateTime = formatter.parseDateTime(dateTimeString);

        nameView = (TextView) findViewById(R.id.name_view);
        dateView = (TextView) findViewById(R.id.date);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        alarmView = (TextView) findViewById(R.id.alarm_view);
        memoView = (EditText) findViewById(R.id.memo);

        nameView.setOnClickListener(this);
        dateView.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        alarmView.setOnClickListener(this);
        memoView.setOnClickListener(this);


        mYear = mDateTime.getYear();
        mMonth = mDateTime.getMonthOfYear();
        mDay = mDateTime.getDayOfMonth();

        dateView.setText(new DateTime(mYear, mMonth, mDay, 12, 0).toString("M월 d일 (E)"));

        LocalTime time;
        if (getIntent().hasExtra("TIME")) {
            String timeString = getIntent().getStringExtra("TIME");
            formatter = DateTimeFormat.forPattern("HH:mm");
            time = formatter.parseLocalTime(timeString);
        } else
            time = new LocalTime(18, 0);


        startDatetime = new DateTime(mYear, mMonth, mDay, time.getHourOfDay(), time.getMinuteOfHour());
        LocalTime eTime = time.plusMinutes(30);
        endDatetime = new DateTime(mYear, mMonth, mDay, eTime.getHourOfDay(), eTime.getMinuteOfHour());

        startTime.setText(startDatetime.toString("a hh:mm"));
        endTime.setText(endDatetime.toString("a hh:mm"));
        reservation.setStartDatetime(startDatetime.toDate());
        reservation.setEndDatetime(endDatetime.toDate());

        if (isEdit) {
            findViewById(R.id.delete).setVisibility(View.VISIBLE);
            findViewById(R.id.delete).setOnClickListener(this);
        }

    }

    public void initData() {
        reservation.setId(getIntent().getIntExtra("id", 0));
        try {
            RestApi.getInstance(this).getReservation(reservation.getId(), new Callback<Reservation>() {
                @Override
                public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                    reservation = response.body();

                    dateView.setText(new DateTime(reservation.getStartDatetime()).toString("M월 d일 (E)"));
                    startDatetime = new DateTime(reservation.getStartDatetime());
                    endDatetime = new DateTime(reservation.getEndDatetime());

                    reservation.setStartDatetime(startDatetime.toDate());
                    reservation.setEndDatetime(endDatetime.toDate());

                    startTime.setText(startDatetime.toString("a hh:mm"));
                    endTime.setText(endDatetime.toString("a hh:mm"));
                    memoView.setText(reservation.getMemo());
                }

                @Override
                public void onFailure(Call<Reservation> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCustomerInfo() {
        try {
            RestApi.getInstance(this).getCustomer(mId, new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    if (response.code() == 200) {
                        mCustomer = response.body();
                        nameView.setText(mCustomer.getTrainer().getUser().getName() + " 트레이너 (" + mCustomer.getTrainer().getUser().getTel() + ","+mCustomer.getPtCount()+"회)");
                        if (isEdit)
                            initData();
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    Log.e("FAILURE", "FAILURE");
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addReservation() {
        try {
            RestApi.getInstance(this).addReservation(reservation, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 201) {
                        Toast.makeText(getBaseContext(), "예약하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("DATE", new DateTime(reservation.getLocalStartDatetime()).toString("yyyy-MM-dd"));
                        setResult(RESULT_OK, intent);
                        finish();
                    } else
                        Toast.makeText(getBaseContext(), "예약 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "예약 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void editReservation(){
        try {
            RestApi.getInstance(this).editReservation(reservation, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getBaseContext(), "수정하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("DATE", new DateTime(reservation.getLocalStartDatetime()).toString("yyyy-MM-dd"));
                        setResult(RESULT_OK, intent);
                        finish();
                    } else
                        Toast.makeText(getBaseContext(), "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            DateTime time = new DateTime(mYear, mMonth, mDay, 12, 0);

            dateView.setText(time.toString("M월 d일 (E)"));
        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            DateTime time = new DateTime(mYear, mMonth, mDay, hourOfDay, minute);

            if (isStartTime) {
                startTime.setText(time.toString("a hh:mm"));


                startDatetime = time;
                endDatetime = startDatetime.plusMinutes(30);
                endTime.setText(endDatetime.toString("a hh:mm"));
                reservation.setStartDatetime(startDatetime.toDate());
                reservation.setEndDatetime(endDatetime.toDate());
            } else {
                endTime.setText(time.toString("a hh:mm"));
                endDatetime = time;

                if (time.getMillis() < startDatetime.getMillis()) {

                    startDatetime = endDatetime.minusHours(30);
                    startTime.setText(startDatetime.toString("a hh:mm"));
                    reservation.setStartDatetime(startDatetime.toDate());
                }
                reservation.setEndDatetime(endDatetime.toDate());
            }
        }
    };

    @Override
    public void onClick(View v) {
        TimePickerDialog tpd;
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.name_view:
                // telephone
                break;
            case R.id.date:
                DatePickerDialog dpd = DatePickerDialog.newInstance(dateSetListener,
                        mDateTime.getYear(), mDateTime.getMonthOfYear() - 1, mDateTime.getDayOfMonth());
                dpd.show(getFragmentManager(), "Datepickerdialog");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new DateTime().plusDays(1).toDate());
                dpd.setMinDate(calendar);
                break;
            case R.id.start_time:
                isStartTime = true;
                tpd = TimePickerDialog.newInstance(timeSetListener,
                        startDatetime.getHourOfDay(), startDatetime.getMinuteOfHour(), false);
                tpd.setTimeInterval(1, 30);
                tpd.show(getFragmentManager(), "timePicerdialog");
                break;
            case R.id.end_time:
                isStartTime = false;
                tpd = TimePickerDialog.newInstance(timeSetListener,
                        endDatetime.getHourOfDay(), endDatetime.getMinuteOfHour(), false);
                tpd.setTimeInterval(1, 30);
                tpd.setMinTime(endDatetime.getHourOfDay(), endDatetime.getMinuteOfHour(), 0);
                tpd.show(getFragmentManager(), "timePicerdialog");
                break;
            case R.id.delete:

                break;
            case R.id.save:
                reservation.setMemo(memoView.getText().toString());
                reservation.setCustomerId(mCustomer.getId());
                reservation.setTrainerId(mCustomer.getTrainer().getId());
                if (isEdit)
                    editReservation();
                else

                    addReservation();
                break;
        }
    }
}
