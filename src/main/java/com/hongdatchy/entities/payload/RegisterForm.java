package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class RegisterForm {

    private String phone;
    private String password;
    private String rePassword;
    private Integer idNumber;
    private String equipment;
    private Integer tag;
    private String address;

}
