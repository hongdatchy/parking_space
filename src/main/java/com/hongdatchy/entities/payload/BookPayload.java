package com.hongdatchy.entities.payload;
import lombok.Data;

import java.util.Date;

@Data
public class BookPayload {
    private Integer slotId;
    private Date timeInBook;
    private Date timeOutBook;
}
