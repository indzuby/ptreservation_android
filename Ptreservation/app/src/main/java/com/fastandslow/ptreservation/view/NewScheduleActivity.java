package com.fastandslow.ptreservation.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.view.common.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

/**
 * Created by hjan1 on 2016-05-11.
 */
public class NewScheduleActivity extends BaseActivity {
    TextView trainerView;
    TextView dateView;
    TextView startTime,endTime;
    TextView alarmView;
    EditText memoView;


    DateTime startDatetime, endDatetime;
    int trainerId;

    int mYear, mMonth, mDay, mHour;
    boolean isStartTime;

    DateTime mDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);


        //Intent intent = new Intent(this.getIntent());
        initActionbar();
        init();
    }

    public void initActionbar(){
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_new_schedule);
        v.findViewById(R.id.back).setOnClickListener(this);
        v.findViewById(R.id.save).setOnClickListener(this);
    }

    public void init(){

        String dateTimeString = getIntent().getStringExtra("DATE_TIME");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        mDateTime = formatter.parseDateTime(dateTimeString);

        trainerView = (TextView) findViewById(R.id.trainer_view);
        dateView = (TextView) findViewById(R.id.date);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        alarmView = (TextView) findViewById(R.id.alarm_view);
        memoView = (EditText) findViewById(R.id.memo);

        trainerView.setOnClickListener(this);
        dateView.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        alarmView.setOnClickListener(this);
        memoView.setOnClickListener(this);


        if(new DateTime().toString("yyyy-MM-dd").equals(dateTimeString))
            mHour = mDateTime.getHourOfDay();
        else
            mHour = 5;

        mYear = mDateTime.getYear();
        mMonth = mDateTime.getMonthOfYear();
        mDay = mDateTime.getDayOfMonth();

        DateTime time = new DateTime(mYear,mMonth,mDay+1,12,0);
        dateView.setText(time.toString("M월 d일 (E)"));

        startDatetime = new DateTime(mYear,mMonth,mDay+1,18,0);
        endDatetime = new DateTime(mYear,mMonth,mDay+1,18,30);

        startTime.setText(startDatetime.toString("a hh:mm"));
        endTime.setText(endDatetime.toString("a hh:mm"));

    }



    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear+1;
            mDay = dayOfMonth;
            DateTime time = new DateTime(mYear,mMonth,mDay,12,0);

            dateView.setText(time.toString("M월 d일 (E)"));
        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            DateTime time = new DateTime(mYear,mMonth,mDay,hourOfDay,minute);

            if(isStartTime) {
                startTime.setText(time.toString("a hh:mm"));
                startDatetime = time;
                endDatetime = startDatetime.plusMinutes(30);
                endTime.setText(endDatetime.toString("a hh:mm"));
            }else {
                endTime.setText(time.toString("a hh:mm"));
                endDatetime = time;

                if(time.getMillis()<startDatetime.getMillis()) {

                    startDatetime = endDatetime.minusHours(30);
                    startTime.setText(startDatetime.toString("a hh:mm"));
                }
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
            case R.id.date:
                DatePickerDialog dpd = DatePickerDialog.newInstance(dateSetListener,
                        mDateTime.getYear(),mDateTime.getMonthOfYear()-1, mDateTime.getDayOfMonth());
                dpd.show(getFragmentManager(), "Datepickerdialog");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new DateTime().plusDays(1).toDate());
                dpd.setMinDate(calendar);
                break;
            case R.id.start_time:
                isStartTime = true;
                tpd = TimePickerDialog.newInstance(timeSetListener,
                        startDatetime.getHourOfDay(),startDatetime.getMinuteOfHour(),false);
                tpd.setTimeInterval(1, 30);
                tpd.show(getFragmentManager(),"timePicerdialog");
                break;
            case R.id.end_time:
                isStartTime = false;
                tpd = TimePickerDialog.newInstance(timeSetListener,
                        endDatetime.getHourOfDay(),endDatetime.getMinuteOfHour(),false);
                tpd.setTimeInterval(1, 30);
                tpd.setMinTime(endDatetime.getHourOfDay(),endDatetime.getMinuteOfHour(),0);
                tpd.show(getFragmentManager(),"timePicerdialog");
                break;
        }
    }
}
