package com.fastandslow.ptreservation.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Reservation extends Common{

    @SerializedName("trainer_id")
    @Expose
    int trainerId;
    @SerializedName("customer_id")
    @Expose
    int customerId;
    @SerializedName("start_datetime")
    @Expose
    Date startDatetime;
    @SerializedName("end_datetime")
    @Expose
    Date endDatetime;
    @SerializedName("memo")
    @Expose
    String memo;

    public Date getStartDatetime(){
        return new DateTime(startDatetime).minusHours(9).toDate();
    }
    public Date getEndDatetime(){
        return new DateTime(endDatetime).minusHours(9).toDate();
    }
}
