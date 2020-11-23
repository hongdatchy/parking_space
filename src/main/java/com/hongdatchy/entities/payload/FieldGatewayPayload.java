package com.hongdatchy.entities.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldGatewayPayload {

    private Integer id;
    private Integer fieldId;
    private Integer gatewayId;

    FieldGatewayPayload(Integer fieldId,Integer gatewayId){
        this.id = null;
        this.fieldId =fieldId;
        this.gatewayId = gatewayId;
    }
}
