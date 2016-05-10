package com.fastandslow.ptreservation.view.adapter;

import android.content.Context;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.utils.ContextUtils;

import org.joda.time.LocalTime;

import java.util.List;

/**
 * Created by hyunjin on 2016-04-14.
 */
public class ScheduleListAdapter extends BaseAdapter {
    Context mContext;
    List<TodaySchedule> mList;
    private LayoutInflater inflater = null;
    private RecyclerView.ViewHolder viewHolder = null;

    boolean isToday;

    public ScheduleListAdapter(Context mContext, List<TodaySchedule> mList, boolean isToday) {
        this.mContext = mContext;
        this.mList = mList;
        this.isToday = isToday;
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
        return v;
    }
}
