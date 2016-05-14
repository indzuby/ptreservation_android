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


import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.utils.DateUtils;
import com.fastandslow.ptreservation.view.adapter.SchedulePagerAdapter;
import com.fastandslow.ptreservation.view.common.BaseActivity;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends BaseActivity {


    ViewPager mSchedulePager;
    SchedulePagerAdapter pagerAdapter;
    TextView mActionBarTitle ;
    int currentItem = - 1;
    List<DateTime> mDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_schedule);
        ImageButton btn  =(ImageButton) findViewById(R.id.plus_button);
        TextView textview = (TextView)findViewById(R.id.add_today_schedule);

        initActionBar();
        init();
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewScheduleActivity.class);
                startActivity(intent);
            }
        });


    }

    public void initActionBar(){
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_schedule);
        mActionBarTitle = (TextView) v.findViewById(R.id.title);

    }


    public void init() {

        mDateList = DateUtils.getTenDateList();


        mSchedulePager = (ViewPager) findViewById(R.id.schedule_viewpager);
        pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList);


        mSchedulePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(currentItem !=-1) {
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
                }else {
                    mActionBarTitle.setText(mDateList.get(position).getMonthOfYear()+"월");
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
