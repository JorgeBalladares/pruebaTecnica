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
    private static final String key_ = "xkey";
    private static final String idTunnel_ = "idTunel";
    private static final String alarmDate_ = "alarmDate";
    private static final String severity_ = "severity";

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
        String pointError = "";

        try {
            boolean dates = Objects.nonNull(dateInit) && Objects.nonNull(dateFinal);
            if (Objects.nonNull(key)) {
                if (Objects.nonNull(idTunnel)) {
                    if (dates) {
                        if (Objects.nonNull(severity)) {
                            pointError = key_ + ", " + idTunnel_ + ", " + alarmDate_ + ", " + severity_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmByAllParams(Integer.parseInt(idTunnel), dateInit, dateFinal, String.valueOf(idKey), Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = key_ + ", " + idTunnel_ + ", " + alarmDate_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmByKeyTunnelDate(Integer.parseInt(idTunnel), dateInit, dateFinal, String.valueOf(idKey)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    } else {
                        if (Objects.nonNull(severity)) {
                            pointError = key_ + ", " + idTunnel_ + ", " + severity_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmByKeyTunnelSev(Integer.parseInt(idTunnel), String.valueOf(idKey), Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = key_ + " and " + idTunnel_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmKeyTunnel(String.valueOf(idKey), idTunnel).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    }
                } else {
                    if (dates) {
                        if (Objects.nonNull(severity)) {
                            pointError = key_ + ", " + alarmDate_ + ", " + severity_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmByKeyDateSev(dateInit, dateFinal, String.valueOf(idKey), Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = alarmDate_ + " and " + key_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmKeyDates(String.valueOf(idKey), dateInit, dateFinal).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    } else {
                        if (Objects.nonNull(severity)) {
                            pointError = key_ + " and " + severity_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmBySevKey(Integer.parseInt(severity), String.valueOf(idKey)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = key_;
                            log.debug("getting list from BD");
                            int idKey = alarmTypeRepo.getIdByKey(key.toUpperCase());
                            return alarmRepo.listAlarmByXkey(idKey).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    }
                }
            }
            else {
                if (Objects.nonNull(idTunnel)) {
                    if (dates) {
                        if (Objects.nonNull(severity)) {
                            pointError = idTunnel_ + ", " + alarmDate_ + ", " + severity_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmByTunnelDateSev(Integer.parseInt(idTunnel), dateInit, dateFinal, Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = alarmDate_ + " and " + idTunnel_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmByIdTunnelDates(Integer.parseInt(idTunnel), dateInit, dateFinal).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    } else {
                        if (Objects.nonNull(severity)) {
                            pointError = idTunnel_ + ", " + severity_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmByTunnelSev(Integer.parseInt(idTunnel), Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = idTunnel_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmByIdTunnel(Integer.parseInt(idTunnel)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    }
                } else {
                    if (dates) {
                        if (Objects.nonNull(severity)) {
                            pointError = alarmDate_ + ", " + severity_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmByDateSev(dateInit, dateFinal, Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            pointError = alarmDate_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmBetweenDates(dateInit, dateFinal).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        }
                    } else {
                        if (Objects.nonNull(severity)) {
                            pointError = severity_;
                            log.debug("getting list from BD");
                            return alarmRepo.listAlarmBySev(Integer.parseInt(severity)).stream().map(alarm -> modelMapper.map(alarm, AlarmOutputVO.class)).collect(Collectors.toList());
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("It was not possible to get a list with param: " + pointError);

        }
    }

}
