package com.price.application.usercases;

import com.price.infraestructure.vo.input.AlarmInputVO;
import com.price.infraestructure.vo.output.AlarmOutputVO;

import java.util.List;

public interface AlarmService {

    AlarmOutputVO addAlarm(AlarmInputVO alarmInputVO) throws Exception;
    List<AlarmOutputVO> getListAlarmsByParams(String key, String idTunnel, String dateInit, String dateFinal, String severity) throws Exception;
}
