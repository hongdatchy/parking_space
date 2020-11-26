package com.hongdatchy.entities.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldGatewayPayload {

    private Integer id;
    private Integer fieldId;
    private Integer gatewayId;

}
