package com.fastandslow.ptreservation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.utils.DateUtils;
import com.fastandslow.ptreservation.view.adapter.ListAdapter;
import com.fastandslow.ptreservation.view.common.BaseFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class ScheduleFragment extends BaseFragment{

    private ListAdapter mAdapter;

    private ListView mList;

    List<TodaySchedule> time;

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_schedule_body,container,false);
        init();
        return mView;
    }
    public void init(){
        DateTime dateTime = new DateTime();


        String dayOfMonth = Integer.toString(dateTime.getDayOfMonth());


        TextView textview = (TextView)mView. findViewById(R.id.day_of_month);

        textview.setText(dayOfMonth);

        time = DateUtils.getTodayList();

        mList = (ListView)mView.findViewById(R.id.today_schedule);
        ListAdapter adapter = new ListAdapter(getContext(),time);

        mList.setAdapter(adapter);

        LocalTime nowTime = new LocalTime();

        mList.setSelection((nowTime.getHourOfDay()-3 <=0 ? 0 : nowTime.getHourOfDay()-3));
    }
}
