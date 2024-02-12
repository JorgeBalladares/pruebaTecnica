package com.price.infraestructure.vo.output;

import lombok.Data;

import java.util.Date;

@Data
public class AlarmOutputVO {

    private Date alarm;
    private Integer idTunnel;
    private String description;
    private Integer severity;
    private AlarmTypeOutputVO alarm_tpe;


}
