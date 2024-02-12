package com.price.application.services;

import com.price.application.usercases.AlarmService;
import com.price.infraestructure.vo.output.AlarmOutputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlarmServiceImpl implements AlarmService {

    @Value("${url}")
    private String url;


    public List<AlarmOutputVO> getList(String key, String idt, String dateInit, String dateFinish, String severity) throws Exception {

        this.modUrl(key, idt, dateInit, dateFinish, severity);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<AlarmOutputVO>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
            url = "http://localhost:8080/public/getList";

            log.debug("OK connection, getting list of getBody");
            return Objects.requireNonNull(responseEntity.getBody()).stream()
                    .sorted(Comparator.comparing(AlarmOutputVO::getSeverity).thenComparing(AlarmOutputVO::getAlarm).reversed())
                    .collect(Collectors.toList());


        } catch (Exception e) {
            throw new Exception("Error: not possible connect to micro");
        }
    }

    private void modUrl(String key, String idt, String dateInit, String dateFinish, String severity) {
        boolean dates = Objects.nonNull(dateInit) && Objects.nonNull(dateFinish);
        if (Objects.nonNull(key)) {
            if (Objects.nonNull(idt)) {
                if (dates) {
                    if (Objects.nonNull(severity)) {
                        url = url + "?key=" + key + "&&idt=" + idt + "&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish + "&&severity=" + severity;
                    } else {
                        url = url + "?key=" + key + "&&idt=" + idt + "&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish;
                    }
                } else {
                    if (Objects.nonNull(severity)) {
                        url = url + "?key=" + key + "&&idt=" + idt + "&&severity=" + severity;
                    } else {
                        url = url + "?key=" + key + "&&idt=" + idt;
                    }
                }
            } else {
                if (dates) {
                    if (Objects.nonNull(severity)) {
                        url = url + "?key=" + key + "&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish + "&&severity=" + severity;
                    } else {
                        url = url + "?key=" + key + "&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish;
                    }
                } else {
                    if (Objects.nonNull(severity)) {
                        url = url + "?key=" + key + "&&severity=" + severity;
                    } else {
                        url = url + "?key=" + key;
                    }
                }
            }
        } else {
            if (Objects.nonNull(idt)) {
                if (dates) {
                    if (Objects.nonNull(severity)) {
                        url = url + "?idt=" + idt + "&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish + "&&severity=" + severity;
                    } else {
                        url = url + "?idt=" + idt + "&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish;
                    }
                } else {
                    if (Objects.nonNull(severity)) {
                        url = url + "?idt=" + idt + "&&severity=" + severity;
                    } else {
                        url = url + "?idt=" + idt;
                    }
                }
            } else {
                if (dates) {
                    if (Objects.nonNull(severity)) {
                        url = url + "?&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish + "&&severity=" + severity;
                    } else {
                        url = url + "?&&dateInit=" + dateInit + "&&dateFinish=" + dateFinish;
                    }
                } else {
                    if (Objects.nonNull(severity)) {
                        url = url + "?severity=" + severity;
                    }
                }
            }
        }
    }

}
