package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class VerifyPayload {
    private String email;
    private String code;
}
