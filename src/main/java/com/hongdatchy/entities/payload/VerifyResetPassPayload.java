package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class VerifyResetPassPayload {

    private String email;
    private String code;
    private String pass;
}
