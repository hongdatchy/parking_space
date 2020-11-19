package com.hongdatchy.entities.data;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "slot_id", nullable = false)
    private Integer slotId;

    @Column(name = "invoice_id", nullable = false)
    private Integer invoiceId;

    @Column(name = "time_car_in", nullable = false)
    private Date timeCarIn;

    @Column(name = "time_car_out", nullable = false)
    private Date timeCarOut;

    @Column(name = "time_in_book", nullable = false)
    private Date timeInBook;

    @Column(name = "time_out_book", nullable = false)
    private Date timeOutBook;

    @Column(name = "duration", nullable = false)
    private Date duration;

}
