package com.fastandslow.ptreservation.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fastandslow.ptreservation.view.ScheduleFragment;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class SchedulePagerAdapter extends FragmentStatePagerAdapter {


    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new ScheduleFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
