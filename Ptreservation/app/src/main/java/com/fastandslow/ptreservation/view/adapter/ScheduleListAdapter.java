package com.fastandslow.ptreservation.view.adapter;

import android.content.Context;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.utils.ContextUtils;
import com.fastandslow.ptreservation.view.MainActivity;
import com.fastandslow.ptreservation.view.NewScheduleActivity;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Created by hyunjin on 2016-04-14.
 */
public class ScheduleListAdapter extends BaseAdapter implements View.OnClickListener {
    Context mContext;
    List<TodaySchedule> mList;
    private LayoutInflater inflater = null;
    private RecyclerView.ViewHolder viewHolder = null;

    private DateTime mDatetime;
    boolean isToday;

    public ScheduleListAdapter(Context mContext, List<TodaySchedule> mList, boolean isToday,DateTime dateTime) {
        this.mContext = mContext;
        this.mList = mList;
        this.isToday = isToday;
        mDatetime = dateTime;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TodaySchedule schedule = mList.get(position);

        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.element_today_schedule_item, null);
        }

        TextView timeTextView = (TextView) v.findViewById(R.id.time);
        TextView firstTextView = (TextView) v.findViewById(R.id.first_pt);
        TextView secondTextView = (TextView) v.findViewById(R.id.second_pt);
        //TextView firstPt = (TextView)convertView.findViewById(R.id.first_pt);
        //TextView secondPt = (TextView)convertView.findViewById(R.id.second_pt);
        LocalTime time = schedule.getLocalTime();
        timeTextView.setText(time.toString("a hh:mm"));


        firstTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.translate));
        secondTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.translate));

        LocalTime nowTime = new LocalTime();

        LocalTime beforeTime = time.minusMinutes(30);
        LocalTime afterTime = time.plusMinutes(30);

        LinearLayout timeIndicator = (LinearLayout) v.findViewById(R.id.time_indicator);
        timeIndicator.setVisibility(View.GONE);
        if (isToday)
            if (beforeTime.getMillisOfDay() <= nowTime.getMillisOfDay() && afterTime.getMillisOfDay() >= nowTime.getMillisOfDay()) {
                timeIndicator.setVisibility(View.VISIBLE);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) timeIndicator.getLayoutParams();
                int marginTop;
                if (nowTime.getMinuteOfHour() >= 30) {
                    float percent = (nowTime.getMinuteOfHour() - 30) / 30f;
                    marginTop = Math.round(ContextUtils.pxFromDp(mContext, 32) * percent);
                } else {
                    float percent = nowTime.getMinuteOfHour() / 30f;
                    percent += 1;
                    marginTop = Math.round(ContextUtils.pxFromDp(mContext, 32) * percent);
                }
                params.setMargins(0, marginTop, ContextUtils.pxFromDp(mContext, 16), 0);
                timeIndicator.setLayoutParams(params);
            }

        firstTextView.setOnClickListener(new TimeListener(time.minusMinutes(30)));
        secondTextView.setOnClickListener(new TimeListener(time));
        return v;
    }



    private class TimeListener implements View.OnClickListener{
        LocalTime startTime;

        public TimeListener(LocalTime startTime) {
            this.startTime = startTime;
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, NewScheduleActivity.class);

            if(mDatetime.getMillis()<new DateTime().getMillis()) {
                Toast.makeText(mContext,"24시간 뒤 예약만 가능합니다",Toast.LENGTH_LONG).show();
            }else {
                intent.putExtra("DATE",mDatetime.toString("yyyy-MM-dd"));
                intent.putExtra("TIME",startTime.toString("hh:mm"));
                mContext.startActivity(intent);
            }
        }
    }
}
