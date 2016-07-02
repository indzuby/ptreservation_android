package com.fastandslow.ptreservation.view.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.fastandslow.ptreservation.utils.StateUtils;
import com.fastandslow.ptreservation.view.customer.CustomerNewScheduleActivity;
import com.fastandslow.ptreservation.view.trainer.TrainerNewScheduleActivity;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

/**
 * Created by hyunjin on 2016-04-14.
 */
public class ScheduleListAdapter extends BaseAdapter implements View.OnClickListener {
    Context mContext;
    List<TodaySchedule> mList;
    @Setter
    Map<String, Integer> mReservationMap;
    @Setter
    Map<String, Integer> mTrainerReservationMap;
    private DateTime mDatetime;
    boolean isToday;
    boolean isTrainer;
    public ScheduleListAdapter(Context mContext, List<TodaySchedule> mList, boolean isToday, DateTime dateTime, Map<String, Integer> reservationMap,Map<String, Integer> trainerReservationsMap) {
        this.mContext = mContext;
        this.mList = mList;
        this.isToday = isToday;
        mDatetime = dateTime;
        mReservationMap = reservationMap;
        isTrainer = true;
        mTrainerReservationMap = trainerReservationsMap;
        isTrainer = false;
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
        firstTextView.setText("");
        secondTextView.setText("");
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
        if (mReservationMap.containsKey(beforeTime.toString("HH:mm"))) {
            firstTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_translate));
            firstTextView.setText(" * 예약");
        }else if(mTrainerReservationMap.containsKey(beforeTime.toString("HH:mm"))) {
            firstTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_translate));
            firstTextView.setText(" * 다른 회원 예약");
        }
        if (mReservationMap.containsKey(time.toString("HH:mm"))) {
            secondTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green_translate));
            secondTextView.setText(" * 예약");
        }else if(mTrainerReservationMap.containsKey(time.toString("HH:mm"))) {
            secondTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red_translate));
            secondTextView.setText(" * 다른 회원 예약");
        }
        firstTextView.setOnClickListener(new TimeListener(time.minusMinutes(30)));
        secondTextView.setOnClickListener(new TimeListener(time));
        return v;
    }


    private class TimeListener implements View.OnClickListener {
        LocalTime startTime;

        public TimeListener(LocalTime startTime) {
            this.startTime = startTime;
        }

        @Override
        public void onClick(View v) {
            boolean isTrainer = StateUtils.isTrainer(mContext);
            Intent intent;
            if (isTrainer)
                intent = new Intent(mContext, TrainerNewScheduleActivity.class);
            else
                intent = new Intent(mContext, CustomerNewScheduleActivity.class);

            if(!isTrainer && mTrainerReservationMap.containsKey(startTime.toString("HH:mm")))
                return;

            if (!isTrainer && isToday && startTime.getHourOfDay() <= new DateTime().getHourOfDay() && !mReservationMap.containsKey(startTime.toString("HH:mm"))) {
                Toast.makeText(mContext, "예약이 불가능한 시간입니다.", Toast.LENGTH_LONG).show();
                return;
            }
            intent.putExtra("DATE", mDatetime.toString("yyyy-MM-dd"));
            intent.putExtra("TIME", startTime.toString("HH:mm"));
            if (mReservationMap.containsKey(startTime.toString("HH:mm"))) {
                intent.putExtra("isEdit", true);
                intent.putExtra("id", mReservationMap.get(startTime.toString("HH:mm")));
            } else
                intent.putExtra("isEdit", false);
            ((Activity)mContext).startActivityForResult(intent,1001);

        }
    }
}
