package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class RegisterForm {

    private String email;
    private String password;
    private String rePassword;
    private Integer idNumber;
    private String equipment;
    private String address;

}
