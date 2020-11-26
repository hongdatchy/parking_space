package com.hongdatchy.entities.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractKey implements Serializable {

    @Column(name = "slot_id", nullable = false)
    private Integer slotId;

    @Column(name = "invoice_id", nullable = false)
    private Integer invoiceId;

}
