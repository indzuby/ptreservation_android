package com.fastandslow.ptreservation.domain;

import lombok.Data;

/**
 * Created by zuby on 2016. 4. 30..
 */
@Data
public class Trainer extends Common{
    int user_id;
    int company_id;
    Company company;
    User user;
}
