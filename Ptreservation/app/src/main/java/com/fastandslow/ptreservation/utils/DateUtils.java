package com.fastandslow.ptreservation.utils;

import com.fastandslow.ptreservation.domain.TodaySchedule;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class DateUtils {

    final static public String[] weekOfDate = {"월","화","수","목","금","토","일"};

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

    public static List<DateTime> getTenDateList(DateTime now){
        List<DateTime> dateList = new ArrayList<>();
        for(int i = -5;i<=4;i++)
            dateList.add(now.plusDays(i));
        return dateList;
    }

    public static List<DateTime> getTenDateList(){
        return getTenDateList(new DateTime());
    }

    public static boolean isSameDate(DateTime a,DateTime b) {
        return a.toString("yyyyMMdd").equals(b.toString("yyyyMMdd"));
    }

    public static DateTime getBeforeDate(DateTime dateTime){
        return dateTime.minusDays(1);
    }

    public static DateTime getAfterDate(DateTime dateTime){
        return dateTime.plusDays(1);
    }
}
