package com.fastandslow.ptreservation.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.view.common.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by hjan1 on 2016-05-11.
 */
public class NewScheduleActivity extends BaseActivity {
    TextView trainerView;
    TextView dateView;
    TextView startTime,endTime;
    TextView alarmView;
    EditText memoView;

    int trainerId;

    int year, month, day, hour;
    boolean isStartTime;

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


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
    }



    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

            String msg = String.format("%d / %d / %d", year, monthOfYear + 1, dayOfMonth);
            Toast.makeText(NewScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);
            Toast.makeText(NewScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();

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
                DatePickerDialog dpd = DatePickerDialog.newInstance(dateSetListener, year, month, day);
                dpd.show(getFragmentManager(), "Datepickerdialog");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new DateTime().plusDays(1).toDate());
                dpd.setMinDate(calendar);
                break;
            case R.id.start_time:
                isStartTime = true;
                tpd = TimePickerDialog.newInstance(timeSetListener,hour+1,0,false);
                tpd.setTimeInterval(1, 30);
                tpd.show(getFragmentManager(),"timePicerdialog");
                break;
            case R.id.end_time:
                isStartTime = false;
                tpd = TimePickerDialog.newInstance(timeSetListener,hour+1,30,false);
                tpd.setTimeInterval(1, 30);
                tpd.show(getFragmentManager(),"timePicerdialog");
                break;
        }
    }
}
