package com.fastandslow.ptreservation.domain;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Reservation extends Common{

    int trainer_id;
    int customer_id;
    Date start_datetime;
    Date end_datetime;

    boolean is_delete;

    String memo;

}
