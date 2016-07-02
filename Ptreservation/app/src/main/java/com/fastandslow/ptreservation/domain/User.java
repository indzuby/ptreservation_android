package com.fastandslow.ptreservation.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by zuby on 2016. 5. 14..
 */
@Data
public class User extends Common {
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("tel")
    @Expose
    String tel;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("sex")
    @Expose
    int sex;

}
