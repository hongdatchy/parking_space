package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "manager")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "last_time_access", nullable = false)
    private Date lastTimeAccess;

    @Column(name = "acp", nullable = false)
    private Boolean acp;

}
