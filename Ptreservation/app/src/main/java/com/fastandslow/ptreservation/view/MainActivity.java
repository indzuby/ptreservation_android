package com.fastandslow.ptreservation.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.view.adapter.ListAdapter;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.view.adapter.SchedulePagerAdapter;
import com.fastandslow.ptreservation.view.common.BaseActivity;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    ViewPager mSchedulePager;

    SchedulePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_schedule);

        init();
    }

    public  void init(){
        mSchedulePager = (ViewPager) findViewById(R.id.schedule_viewpager);
        pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager());

        mSchedulePager.setAdapter(pagerAdapter);
    }
}
