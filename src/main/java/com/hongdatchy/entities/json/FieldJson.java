package com.hongdatchy.entities.json;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FieldJson {

    private int id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private int totalBook;
    private int totalSlot;
    private int busySlot;

}
