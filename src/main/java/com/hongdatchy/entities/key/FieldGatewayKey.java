package com.hongdatchy.entities.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldGatewayKey implements Serializable {

    @Column(name = "field_id", nullable = false)
    private Integer fieldId;

    @Column(name = "gateway_id", nullable = false)
    private Integer gatewayId;

}
