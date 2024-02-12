package com.price.domain.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "ALARMS")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alarm_tpe")
    private Alarm_type alarm_tpe;

    @Column(name = "alarm", nullable = false)
    private Date alarm;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "severidad", nullable = false)
    private Integer severity;

    @Column(name = "idtunel", nullable = false)
    private Integer idTunnel;


}
