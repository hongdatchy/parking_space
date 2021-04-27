package com.hongdatchy.entities.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class BookPayload {
    private Integer fieldId;
    private String carNumber;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp timeInBook;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp timeOutBook;
}
