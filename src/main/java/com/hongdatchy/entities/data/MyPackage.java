package com.hongdatchy.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "package")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPackage {

    @Id
    @Column(name = "packet_number", nullable = false)
    private Integer packetNumber;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "battery_level", nullable = false)
    private String batteryLevel;


    @Column(name = "node_address", nullable = false)
    private String nodeAddress;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "communication_level", nullable = false)
    private String communicationLevel;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "location", nullable = false)
    private String location;

}
