package com.fastandslow.ptreservation.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.utils.CodeDefinition;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.utils.DateUtils;
import com.fastandslow.ptreservation.utils.SessionUtils;
import com.fastandslow.ptreservation.utils.StateUtils;
import com.fastandslow.ptreservation.view.adapter.SchedulePagerAdapter;
import com.fastandslow.ptreservation.view.adapter.SideMenuAdapter;
import com.fastandslow.ptreservation.view.customer.CustomerNewScheduleActivity;
import com.fastandslow.ptreservation.view.trainer.TrainerNewScheduleActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity {


    ViewPager mSchedulePager;
    SchedulePagerAdapter pagerAdapter;
    TextView mActionBarTitle;
    int currentItem = -1;
    List<DateTime> mDateList;
    DrawerLayout mDrawerLayout;
    ListView mSideMenu;
    boolean isTrainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_schedule);
        isTrainer = StateUtils.isTrainer(this);
        initActionBar();
        init();

    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

            DateTime now = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);

            if (Calendar.getInstance().get(Calendar.YEAR) != year)
                mActionBarTitle.setText(year + "년 " + (monthOfYear) + "월");
            else
                mActionBarTitle.setText((monthOfYear) + "월");
            mDateList = DateUtils.getTenDateList(now);

            pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList,isTrainer);
            currentItem = -1;
            mSchedulePager.setAdapter(pagerAdapter);
            mSchedulePager.setCurrentItem(5);

        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.month_layout) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) mDrawerLayout.closeDrawers();
            DateTime now = mDateList.get(5);
            DatePickerDialog dpd = DatePickerDialog.newInstance(dateSetListener, now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
            dpd.show(getFragmentManager(), "Datepickerdialog");
        } else if (v.getId() == R.id.plus_button) {
            Intent intent;
            if(isTrainer)
                intent = new Intent(MainActivity.this, TrainerNewScheduleActivity.class);
            else
                intent = new Intent(MainActivity.this, CustomerNewScheduleActivity.class);
            DateTime curr = mDateList.get(5);
            intent.putExtra("DATE",curr.toString("yyyy-MM-dd"));
            if(curr.getMillis()<new DateTime().getMillis())
                intent.putExtra("DATE",new DateTime().plusDays(1).toString("yyyy-MM-dd"));
            startActivityForResult(intent,1001);

        }else if(v.getId() == R.id.today ){
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) mDrawerLayout.closeDrawers();
            mDateList = DateUtils.getTenDateList();

            pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList,isTrainer);
            currentItem = -1;
            mSchedulePager.setAdapter(pagerAdapter);
            mSchedulePager.setCurrentItem(5);
        }else if(v.getId() == R.id.menu) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) mDrawerLayout.closeDrawers();
            else mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        View v = ContextUtils.getActionBar(this, actionBar, R.layout.actionbar_schedule);
        mActionBarTitle = (TextView) v.findViewById(R.id.title);
        findViewById(R.id.month_layout).setOnClickListener(this);
        findViewById(R.id.today).setOnClickListener(this);
        findViewById(R.id.menu).setOnClickListener(this);
    }


    public void init() {
        findViewById(R.id.plus_button).setOnClickListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mSideMenu = (ListView) findViewById(R.id.side_list_view);
        mSideMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2) {
                    Toast.makeText(getBaseContext(),"로그아웃 하였습니다.",Toast.LENGTH_SHORT).show();
                    SessionUtils.putBoolean(getBaseContext(), CodeDefinition.AUTO_LOGIN, false);
                    SessionUtils.putString(getBaseContext(), CodeDefinition.EMAIL, "");
                    SessionUtils.putString(getBaseContext(), CodeDefinition.PASSWORD, "");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        SideMenuAdapter sideAdapter = new SideMenuAdapter(this);
        mSideMenu.setAdapter(sideAdapter);
        mDateList = DateUtils.getTenDateList();


        mSchedulePager = (ViewPager) findViewById(R.id.schedule_viewpager);
        pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList,isTrainer);


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
                        mActionBarTitle.setText(curr.getYear() + "년 " + (curr.getMonthOfYear()) + "월");
                    else
                        mActionBarTitle.setText((curr.getMonthOfYear()) + "월");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == 1001) {

                String dateTimeString = data.getStringExtra("DATE");
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime date = formatter.parseDateTime(dateTimeString);

                mDateList = DateUtils.getTenDateList(date);
                pagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager(), mDateList,isTrainer);
                currentItem = -1;
                mSchedulePager.setAdapter(pagerAdapter);
                mSchedulePager.setCurrentItem(5);
            }
        }
    }
}
