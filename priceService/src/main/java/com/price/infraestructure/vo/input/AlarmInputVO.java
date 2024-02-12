package com.price.infraestructure.vo.input;

import lombok.Data;

import java.util.Date;

@Data
public class AlarmInputVO {

    private String alarm_tpe;
    private Date alarm;
    private String description;
    private Integer severity;
    private Integer idTunnel;

}
