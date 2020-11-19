package com.hongdatchy.entities.data;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "manager_field")
public class ManagerField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "field_id", nullable = false)
    private Integer fieldId;

    @Column(name = "manager_id", nullable = false)
    private Integer managerId;

    @Column(name = "last_time_setup", nullable = false)
    private Date lastTimeSetup;

}
