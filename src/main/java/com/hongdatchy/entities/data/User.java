package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pass", nullable = false)
    private String password;

    @Column(name = "id_number", nullable = false)
    private Integer idNumber;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "last_time_access")
    private Date lastTimeAccess;

    @Column(name = "equipment", nullable = false)
    private String equipment;

    @Column(name = "address", nullable = false)
    private String address;


}
