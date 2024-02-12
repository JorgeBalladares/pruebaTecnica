package com.price.application.services;

import com.price.application.usercases.AlarmService;
import com.price.domain.entities.Alarm;
import com.price.domain.entities.Alarm_type;
import com.price.domain.repositories.AlarmRepo;
import com.price.domain.repositories.Alarm_typeRepo;
import com.price.infraestructure.vo.input.AlarmInputVO;
import com.price.infraestructure.vo.output.AlarmOutputVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlarmServiceImpl implements AlarmService {


    private final AlarmRepo alarmRepo;
    private final ModelMapper modelMapper;
    private final Alarm_typeRepo alarmTypeRepo;


    public AlarmServiceImpl(AlarmRepo alarmRepo, ModelMapper modelMapper, Alarm_typeRepo alarmTypeRepo) {
        this.alarmRepo = alarmRepo;
        this.modelMapper = modelMapper;
        this.alarmTypeRepo = alarmTypeRepo;
    }

    public AlarmOutputVO addAlarm(AlarmInputVO alarmInputVO) throws Exception {
        Alarm alarm = modelMapper.map(alarmInputVO, Alarm.class);
        Alarm_type alarmType;
        try {
            alarmType = alarmTypeRepo.gettingTpeAlarm(alarmInputVO.getAlarm_tpe());
        } catch (Exception e) {
            throw new Exception("It was not possible to get Alarm type which you try to insert");
        }
        try {
            alarm.setAlarm_tpe(alarmType);
            alarmRepo.saveAndFlush(alarm);
            log.debug("Alarm added");
        } catch (Exception e) {
            throw new Exception("It was not possible to add an alarm to the table");
        }
        return modelMapper.map(alarm, AlarmOutputVO.class);
    }

    public List<AlarmOutputVO> getListAlarmsByParams(String key, String idTunnel, String dateInit, String dateFinal, String severity) throws Exception {
        if (Objects.nonNull(key)){
            key = this.alarmTypeRepo.getIdByKey(key.toUpperCase()).toString();
        }
        try {
            log.debug("getting list from repository");
            return this.alarmRepo.getList(idTunnel, dateInit, dateFinal, key, severity)
                    .stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("It was not possible to get a list with params");

        }
    }

}
