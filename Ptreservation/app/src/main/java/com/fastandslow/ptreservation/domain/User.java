package com.fastandslow.ptreservation.domain;

import lombok.Data;

/**
 * Created by zuby on 2016. 5. 14..
 */
@Data
public class User extends Common {
    String name;
    String tel;
    String email;
    int sex;

}
