package com.hongdatchy.entities.payload;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookPayload {
    private Integer fieldId;
    private Date timeInBook;
    private Date timeOutBook;
}
