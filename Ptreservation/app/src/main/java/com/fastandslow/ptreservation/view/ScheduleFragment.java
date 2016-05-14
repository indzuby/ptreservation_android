package com.fastandslow.ptreservation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.utils.DateUtils;
import com.fastandslow.ptreservation.view.adapter.ScheduleListAdapter;
import com.fastandslow.ptreservation.view.common.BaseFragment;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class ScheduleFragment extends BaseFragment{

    private ScheduleListAdapter mAdapter;
    private ListView mList;

    List<TodaySchedule> time;

    DateTime mDateTime;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_schedule) {
            Intent intent = new Intent(getActivity(),NewScheduleActivity.class);
            DateTime curr = mDateTime;
            intent.putExtra("DATE_TIME",curr.toString("yyyy-MM-dd"));
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_schedule_body,container,false);

        init();
        return mView;
    }
    public void init(){

        View addSchedule = mView.findViewById(R.id.add_schedule);
        addSchedule.setOnClickListener(this);

        String dateTimeString = getArguments().getString("DATE_TIME");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        mDateTime = formatter.parseDateTime(dateTimeString);

        TextView dayOfMonth = (TextView)mView. findViewById(R.id.day_of_month);
        TextView dayOfWeek = (TextView) mView.findViewById(R.id.day_of_week);
        TextView addSchedule = (TextView)mView.findViewById(R.id.add_today_schedule);

        dayOfMonth.setText(mDateTime.getDayOfMonth()+"");
        dayOfWeek.setText(DateUtils.weekOfDate[mDateTime.getDayOfWeek()-1]);
        addSchedule.setText("일정 없음. 추가하려면 탭하세요.");

        time = DateUtils.getTodayList();

        mList = (ListView)mView.findViewById(R.id.today_schedule);

        if(DateUtils.isSameDate(mDateTime,new DateTime())) {
            dayOfMonth.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
            dayOfWeek.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
        }else {
            dayOfMonth.setTextColor(ContextCompat.getColor(getContext(),R.color.dark_gray));
            dayOfWeek.setTextColor(ContextCompat.getColor(getContext(),R.color.dark_gray));
        }

        ScheduleListAdapter adapter = new ScheduleListAdapter(getContext(),time,DateUtils.isSameDate(mDateTime,new DateTime()));

        mList.setAdapter(adapter);

        LocalTime nowTime = new LocalTime();

        mList.setSelection((nowTime.getHourOfDay()-3 <=0 ? 0 : nowTime.getHourOfDay()-3));

    }
}
