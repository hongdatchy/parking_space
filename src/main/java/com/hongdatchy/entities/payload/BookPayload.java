package com.hongdatchy.entities.payload;

import lombok.Data;

import java.util.Date;

@Data
public class BookPayload {
    private Integer fieldId;
    private Date timeInBook;
    private Date timeOutBook;
}
