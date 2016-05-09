package com.fastandslow.ptreservation.utils;

import com.fastandslow.ptreservation.domain.TodaySchedule;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class DateUtils {

    public static List<TodaySchedule> getTodayList(){
        List<TodaySchedule> list = new ArrayList<>();
        for(int i = 0 ; i <=23;i++) {
            LocalTime time = new LocalTime(i,0);
            TodaySchedule schedule = new TodaySchedule();
            schedule.setLocalTime(time);
            list.add(schedule);
        }
        return list;
    }
}
