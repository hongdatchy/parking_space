package com.hongdatchy.entities.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class UserPayload {

    private Integer id;
    private String password;
    private Integer idNumber;
    private String phone;
    private String email;
    private String equipment;
    private String image;
    private String address;
    private String sex;
    private Date birth;

}
