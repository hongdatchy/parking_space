package com.hongdatchy.entities.payload;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ContractPayload {

    private Integer id;
    private Integer userId;
    private Integer fieldId;
    private Timestamp timeInBook;
    private Timestamp timeOutBook;
    private String carNumber;
    private Timestamp dtCreate;

}
