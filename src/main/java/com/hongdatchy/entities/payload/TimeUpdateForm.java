package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class TimeUpdateForm {

    int contractId;
    String timeCarIn;
    String timeCarOut;
    
}
