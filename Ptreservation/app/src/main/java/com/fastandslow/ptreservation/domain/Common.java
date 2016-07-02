package com.fastandslow.ptreservation.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Common {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("created_at")
    @Expose
    Date created_at;
    Date updated_at;
    @SerializedName("is_delete")
    @Expose
    int isDelete;
}
