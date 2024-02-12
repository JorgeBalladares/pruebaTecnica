package com.price.application.usercases;

import com.price.infraestructure.vo.output.AlarmOutputVO;

import java.util.List;

public interface AlarmService {

    List<AlarmOutputVO> getList(String key, String idt, String dateInit, String dateFinish, String severity) throws Exception;
}