package com.hongdatchy.entities.payload;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeUpdateForm {

    int contractId;
    String timeCarIn;
    String timeCarOut;


}
