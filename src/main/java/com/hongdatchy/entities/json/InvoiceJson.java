package com.hongdatchy.entities.json;

import com.hongdatchy.entities.data.Contract;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class InvoiceJson {
    private int id;
    List<Contract> contracts;
}
