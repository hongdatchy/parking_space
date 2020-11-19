package com.hongdatchy.entities.json;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SlotJson {

    private int id;
    private int fieldId;
    private Boolean status; // false la free, true la busy
    private String AddressGateway;
    private String AddressDetector;
    private Date lastTimeUpdate;
    private Date lastTimeSetup;

}
