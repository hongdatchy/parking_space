package com.hongdatchy.entities.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ContractPayload {

    private Integer id;
    private Integer userId;
    private Integer fieldId;
    private Date timeInBook;
    private Date timeOutBook;

}
