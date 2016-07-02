package com.fastandslow.ptreservation.domain;

import org.joda.time.LocalTime;

import lombok.Data;

/**
 * Created by supernotebook on 2016-04-17.
 */
@Data
public class TodaySchedule {
    private LocalTime localTime;
    private boolean firstSchedule;
    private boolean secondSchedule;
    boolean hasSchedule;
    int id;
}
