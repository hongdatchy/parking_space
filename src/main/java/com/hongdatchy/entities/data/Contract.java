package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "contract")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "slot_id", nullable = false)
    private Integer slotId;

    @Column(name = "invoice_id", nullable = false)
    private Integer invoiceId;

    @Column(name = "time_car_in")
    private Date timeCarIn;

    @Column(name = "time_car_out")
    private Date timeCarOut;

    @Column(name = "time_in_book", nullable = false)
    private Date timeInBook;

    @Column(name = "time_out_book", nullable = false)
    private Date timeOutBook;


}
