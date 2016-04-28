package com.fastandslow.ptreservation.adapter;

import android.content.Context;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;

import org.joda.time.LocalTime;

import java.util.List;

/**
 * Created by hyunjin on 2016-04-14.
 */
public class ListAdapter extends BaseAdapter {
    Context mContext;
    List<TodaySchedule> mList;
    private LayoutInflater inflater = null;
    private RecyclerView.ViewHolder viewHolder = null;

    public ListAdapter(Context mContext, List<TodaySchedule> mList){
        this.mContext = mContext;
        this.mList = mList;
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

        if(v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.element_today_schedule_item,null);
        }

        TextView timeTextView = (TextView)v.findViewById(R.id.time);
        TextView firstTextView = (TextView)v.findViewById(R.id.first_pt);
        TextView secondTextView = (TextView)v.findViewById(R.id.second_pt);
        //TextView firstPt = (TextView)convertView.findViewById(R.id.first_pt);
        //TextView secondPt = (TextView)convertView.findViewById(R.id.second_pt);
        LocalTime time = schedule.getLocalTime();
        timeTextView.setText(time.toString("a hh:mm"));

        firstTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.translate));
        secondTextView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.translate));

        LocalTime nowTime = new LocalTime();

        LocalTime beforeTime = time.minusMinutes(30);
        LocalTime afterTime = time.plusMinutes(30);

        if(beforeTime.getHourOfDay() == nowTime.getHourOfDay() && time.getHourOfDay()>nowTime.getHourOfDay())
            firstTextView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray));
        else if(time.getHourOfDay()<=nowTime.getHourOfDay() && afterTime.getHourOfDay()>nowTime.getHourOfDay())
            secondTextView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray));


        //firstPt.setText(mList.get(position).getFirstSchedule());
        return v;
    }


}
