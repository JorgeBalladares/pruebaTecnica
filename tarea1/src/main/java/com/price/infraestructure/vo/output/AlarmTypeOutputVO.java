package com.price.infraestructure.vo.output;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AlarmTypeOutputVO {

    @Column(name = "xkey", nullable = false)
    private String xkey;

    @Column(name = "descripcion", nullable = false)
    private String description;

}
