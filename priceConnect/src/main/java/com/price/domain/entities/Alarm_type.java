package com.price.domain.entities;


import lombok.Data;
import javax.persistence.*;


@Data
@Entity(name = "ALARM_TYPE")
public class Alarm_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "xkey", nullable = false)
    private String xkey;

    @Column(name = "descripcion", nullable = false)
    private String description;


}
