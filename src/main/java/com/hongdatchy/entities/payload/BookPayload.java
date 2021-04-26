package com.hongdatchy.entities.payload;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class BookPayload {
    private Integer fieldId;
    private Timestamp timeInBook;
    private Timestamp timeOutBook;
}
