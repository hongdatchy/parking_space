package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class VerifyPayload {
    private String mail;
    private String code;
}
