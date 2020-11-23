package com.hongdatchy.entities.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ContractPayload {

    private Integer id;
    private Integer slotId;
    private Integer invoiceId;
    private Date timeInBook;
    private Date timeOutBook;

}
