package com.fastandslow.ptreservation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.utils.DateUtils;
import com.fastandslow.ptreservation.view.adapter.SchedulePagerAdapter;
import com.fastandslow.ptreservation.view.common.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity {


    ViewPager mSchedulePager;
    SchedulePagerAdapter pagerAdapter;
    TextView mActionBarTitle;
    int currentItem = -1;
    List<DateTime> mDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_schedule);

        initActionBar();
        init();
    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

            DateTime now = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);

            if (Calendar.getInstance().get(Calendar.YEAR) != year)
                mActionBarTitle.setText(year + "년 " + (monthOfYear + 1) + "월");
            else
                mActionBarTitle.setText((monthOfYear + 1) + "월");
            mDateList = DateUtils.getTenDateList(now);

            pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList);
            currentItem = -1;
            mSchedulePager.setAdapter(pagerAdapter);
            mSchedulePager.setCurrentItem(5);

        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.month_layout) {
            DateTime now = mDateList.get(5);
            DatePickerDialog dpd = DatePickerDialog.newInstance(dateSetListener, now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
            dpd.show(getFragmentManager(), "Datepickerdialog");
        } else if (v.getId() == R.id.plus_button) {
            Intent intent = new Intent(MainActivity.this, NewScheduleActivity.class);

            DateTime curr = mDateList.get(5);
            if(curr.getMillis()<new DateTime().getMillis()) {
                intent.putExtra("DATE",new DateTime().plusDays(1).toString("yyyy-MM-dd"));
            }else
                intent.putExtra("DATE",curr.toString("yyyy-MM-dd"));
            startActivity(intent);

        }else if(v.getId() == R.id.today ){
            mDateList = DateUtils.getTenDateList();

            pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList);
            currentItem = -1;
            mSchedulePager.setAdapter(pagerAdapter);
            mSchedulePager.setCurrentItem(5);
        }
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_schedule);
        mActionBarTitle = (TextView) v.findViewById(R.id.title);
        findViewById(R.id.month_layout).setOnClickListener(this);
        findViewById(R.id.today).setOnClickListener(this);
    }


    public void init() {
        ImageButton btn = (ImageButton) findViewById(R.id.plus_button);
        btn.setOnClickListener(this);

        mDateList = DateUtils.getTenDateList();


        mSchedulePager = (ViewPager) findViewById(R.id.schedule_viewpager);
        pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList);


        mSchedulePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (currentItem != -1) {
                    if (position < currentItem) {
                        mDateList.add(0, DateUtils.getBeforeDate(mDateList.get(0)));
                        mDateList.remove(mDateList.size() - 1);
                    } else {
                        mDateList.add(DateUtils.getAfterDate(mDateList.get(mDateList.size() - 1)));
                        mDateList.remove(0);
                    }
                    currentItem = -1;
                    pagerAdapter.notifyDataSetChanged();
                    mSchedulePager.setCurrentItem(5);
                } else {
                    DateTime curr = mDateList.get(position);
                    if (new DateTime().getYear() != curr.getYear())
                        mActionBarTitle.setText(curr.getYear() + "년 " + (curr.getMonthOfYear() + 1) + "월");
                    else
                        mActionBarTitle.setText((curr.getMonthOfYear() + 1) + "월");

                    currentItem = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSchedulePager.setAdapter(pagerAdapter);
        mSchedulePager.setCurrentItem(5);
    }
}
