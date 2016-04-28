package com.fastandslow.ptreservation.domain;

import org.joda.time.LocalTime;

/**
 * Created by supernotebook on 2016-04-17.
 */
public class TodaySchedule {
    private LocalTime localTime;
    private boolean firstSchedule;
    private boolean secondSchedule;

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public boolean isFirstSchedule() {
        return firstSchedule;
    }

    public void setFirstSchedule(boolean firstSchedule) {
        this.firstSchedule = firstSchedule;
    }

    public boolean isSecondSchedule() {
        return secondSchedule;
    }

    public void setSecondSchedule(boolean secondSchedule) {
        this.secondSchedule = secondSchedule;
    }
}
