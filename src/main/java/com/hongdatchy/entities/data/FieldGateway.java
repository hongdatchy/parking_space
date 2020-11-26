package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "field_gateway")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldGateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "field_id", nullable = false)
    private Integer fieldId;

    @Column(name = "gateway_id", nullable = false)
    private Integer gatewayId;

    @Column(name = "last_time_setup", nullable = false)
    private Date lastTimeSetup;

}
