package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verify_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "id_number", nullable = false)
    private Integer idNumber;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "last_time_access")
    private Date lastTimeAccess;

    @Column(name = "equipment", nullable = false)
    private String equipment;

    @Column(name = "address", nullable = false)
    private String address;

}
