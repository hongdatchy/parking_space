package com.hongdatchy.entities.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Data
public class ContractPayload {

    private Integer id;

    private Integer userId;

    private Integer fieldId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp timeInBook;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp timeOutBook;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp timeCarIn;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp timeCarOut;

    private String carNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp dtCreate;

    private String status;
}
