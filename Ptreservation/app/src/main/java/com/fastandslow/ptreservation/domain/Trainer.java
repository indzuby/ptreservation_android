package com.fastandslow.ptreservation.domain;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Trainer extends User{
    int user_id;
    int company_id;
    String company_name;
    String company_tel;
    String company_address;
}
