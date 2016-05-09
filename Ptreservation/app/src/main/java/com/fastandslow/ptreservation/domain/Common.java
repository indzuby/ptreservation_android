package com.fastandslow.ptreservation.domain;

import java.util.Date;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Common {
    int id;
    Date created_at;
    Date updated_at;
    boolean is_delete;
}
