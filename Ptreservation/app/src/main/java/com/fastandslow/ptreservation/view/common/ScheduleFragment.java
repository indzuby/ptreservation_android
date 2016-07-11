package com.fastandslow.ptreservation.view.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.Reservation;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.network.RestApi;
import com.fastandslow.ptreservation.utils.DateUtils;
import com.fastandslow.ptreservation.utils.StateUtils;
import com.fastandslow.ptreservation.view.adapter.ScheduleListAdapter;
import com.fastandslow.ptreservation.view.customer.CustomerNewScheduleActivity;
import com.fastandslow.ptreservation.view.trainer.TrainerNewScheduleActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class ScheduleFragment extends BaseFragment {

    private ScheduleListAdapter mAdapter;
    private ListView mList;

    List<TodaySchedule> time;

    DateTime mDateTime;

    Map<String, Integer> mReservationMap;
    Map<String, Integer> mTrainerReservationMap;
    int mId;
    int mTrainerId;
    boolean isTrainer;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_schedule) {
            Intent intent;
            if (isTrainer)
                intent = new Intent(getActivity(), TrainerNewScheduleActivity.class);
            else
                intent = new Intent(getActivity(), CustomerNewScheduleActivity.class);
            DateTime curr = mDateTime;
            if (curr.getMillis() < new DateTime().getMillis()) {
                intent.putExtra("DATE", new DateTime().toString("yyyy-MM-dd"));
            } else
                intent.putExtra("DATE", curr.toString("yyyy-MM-dd"));
            startActivityForResult(intent, 1001);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.element_schedule_body, container, false);

        mId = StateUtils.getUserId(getContext());
        mTrainerId = StateUtils.getTrainerId(getContext());
        isTrainer = StateUtils.isTrainer(getContext());
        init();
        return mView;
    }

    public void init() {
        View addSchedule = mView.findViewById(R.id.add_schedule);
        addSchedule.setOnClickListener(this);

        String dateTimeString = getArguments().getString("DATE_TIME");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        mDateTime = formatter.parseDateTime(dateTimeString);
        getReservation();

        TextView dayOfMonth = (TextView) mView.findViewById(R.id.day_of_month);
        TextView dayOfWeek = (TextView) mView.findViewById(R.id.day_of_week);
        TextView addScheduleText = (TextView) mView.findViewById(R.id.add_today_schedule);

        dayOfMonth.setText(mDateTime.getDayOfMonth() + "");
        dayOfWeek.setText(DateUtils.weekOfDate[mDateTime.getDayOfWeek() - 1]);
        addScheduleText.setText("일정 없음. 추가하려면 탭하세요.");

        time = DateUtils.getTodayList();

        mList = (ListView) mView.findViewById(R.id.today_schedule);

        if (DateUtils.isSameDate(mDateTime, new DateTime())) {
            dayOfMonth.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
            dayOfWeek.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
        } else {
            dayOfMonth.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
            dayOfWeek.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        }

        mAdapter = new ScheduleListAdapter(getActivity(), time, DateUtils.isSameDate(mDateTime, new DateTime()), mDateTime, new HashMap<String, Integer>(), new HashMap<String, Integer>());
        mList.setAdapter(mAdapter);
        setScroll(new DateTime());


    }

    public void getReservation() {
        try {
            RestApi.getInstance(getContext()).getListByDateReservation(mTrainerId, mDateTime, new Callback<List<Reservation>>() {
                @Override
                public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                    Log.e("Success", "!!!");
                    mReservationMap = new HashMap<>();
                    mTrainerReservationMap = new HashMap<>();
                    for (Reservation reservation : response.body()) {
                        DateTime startDatetime = new DateTime(reservation.getStartDatetime());
                        DateTime endDatetime = new DateTime(reservation.getEndDatetime());
                        while (startDatetime.getMillis() < endDatetime.getMillis()) {
                            if (isTrainer)
                                mReservationMap.put(startDatetime.toString("HH:mm"), reservation.getId());
                            else if (mId != reservation.getCustomerId())
                                mTrainerReservationMap.put(startDatetime.toString("HH:mm"), reservation.getId());
                            else
                                mReservationMap.put(startDatetime.toString("HH:mm"), reservation.getId());

                            startDatetime = startDatetime.plusMinutes(30);
                        }
                    }

                    mAdapter = new ScheduleListAdapter(getContext(), time, DateUtils.isSameDate(mDateTime, new DateTime()), mDateTime, mReservationMap,mTrainerReservationMap);
                    mList.setAdapter(mAdapter);
                    setScroll(new DateTime());
                }

                @Override
                public void onFailure(Call<List<Reservation>> call, Throwable t) {
                    Log.e("Failure", "!!!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                getReservation();
            }
        }
    }

    public void setScroll(DateTime dateTime) {
        mList.setSelection((dateTime.getHourOfDay() - 3 <= 0 ? 0 : dateTime.getHourOfDay() - 3));
    }
}
