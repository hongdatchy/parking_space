package com.hongdatchy.entities.data;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "field_gateway")
@Data
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
