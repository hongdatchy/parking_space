package com.hongdatchy.entities.json;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SlotJson {

    private Integer id;
    private Integer fieldId;
    private Boolean statusDetector; // false la free, true la busy
    private Boolean statusCam;
    private String AddressGateway;
    private String AddressDetector;
    private String lastTimeUpdate;
    private String lastTimeSetup;
    private Integer detectorId;

}
