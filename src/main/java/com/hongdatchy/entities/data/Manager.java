package com.hongdatchy.entities.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "last_time_access", nullable = false)
    private Date lastTimeAccess;

    @Column(name = "acp", nullable = false)
    private Boolean acp;

}
