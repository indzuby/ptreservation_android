package com.fastandslow.ptreservation.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Customer extends Common{
    @SerializedName("pt_count")
    @Expose
    int ptCount;
    @SerializedName("user")
    @Expose
    User user;

    @SerializedName("trainer")
    @Expose
    Trainer trainer;



}
