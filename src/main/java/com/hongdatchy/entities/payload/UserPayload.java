package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class UserPayload {

    private Integer id;
    private String password;
    private Integer idNumber;
    private String email;
    private String equipment;
    private String address;

}
