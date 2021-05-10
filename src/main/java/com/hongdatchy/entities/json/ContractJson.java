package com.hongdatchy.entities.json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ContractJson {

    private Integer id;

    private Integer fieldId;

    private Integer userId;

    private Timestamp timeCarIn;

    private Timestamp timeCarOut;

    private Timestamp timeInBook;

    private Timestamp timeOutBook;

    private String carNumber;

    private Timestamp dtCreate;

    private String status;

    private String cost;
}
