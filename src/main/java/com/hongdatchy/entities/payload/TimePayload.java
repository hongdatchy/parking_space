package com.hongdatchy.entities.payload;

import lombok.Data;

import java.util.Date;

@Data
public class TimePayload {
    private Date timeInBook;
    private Date timeOutBook;
}
