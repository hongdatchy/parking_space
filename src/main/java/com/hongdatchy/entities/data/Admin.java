package com.hongdatchy.entities.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "pass", nullable = false)
    private String pass;

}
