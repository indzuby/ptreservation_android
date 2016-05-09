package com.fastandslow.ptreservation.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fastandslow.ptreservation.view.ScheduleFragment;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class SchedulePagerAdapter extends FragmentStatePagerAdapter {

    List<DateTime> mTimeList;
    public SchedulePagerAdapter(FragmentManager fm,List<DateTime> mList) {
        super(fm);
        mTimeList = mList;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATE_TIME", mTimeList.get(position).toString("yyyy-MM-dd"));
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return mTimeList.size();
    }
    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }
}
