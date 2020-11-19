package com.hongdatchy.entities.json;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FieldJson {

    private int id;
    private String position;
    private int totalSlot;
    private int busySlot;

}
