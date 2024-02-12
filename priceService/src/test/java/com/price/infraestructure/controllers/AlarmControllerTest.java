package com.price.infraestructure.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.price.domain.entities.Alarm_type;
import com.price.infraestructure.vo.output.AlarmOutputVO;
import com.price.infraestructure.vo.output.AlarmTypeOutputVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AlarmControllerTest {


    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void test1() throws Exception {

        String paramOne = "2024-01-02.00.01.00";
        String paramSecond = "2024-04-11.00.01.00";


        List<AlarmOutputVO> listOutAlarmProof = new ArrayList<>();
        listOutAlarmProof.add(this.alarmHum10());
        listOutAlarmProof.add(this.alarmHum60());
        listOutAlarmProof.add(this.alarmHum90());
        listOutAlarmProof.add(this.alarmDio60());
        listOutAlarmProof.add(this.alarmDio80());
        listOutAlarmProof.add(this.alarmDio20());
        listOutAlarmProof.add(this.alarm60hum());
        listOutAlarmProof.add(this.alarmDio10());
        listOutAlarmProof.sort(Comparator.comparing(AlarmOutputVO::getSeverity).thenComparing(AlarmOutputVO::getAlarm).reversed());

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList").param("dateInit", paramOne).param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listOutAlarmProof, listObj);

    }

    @Test
    public void test2() throws Exception {

        String paramOne = "2024-03-10.00.01.00";
        String paramSecond = "2024-03-10.23.01.00";
        String tunnel = "107";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList").param("idt", tunnel).param("dateInit", paramOne).param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(this.alarmDio60(), listObj.get(0));
    }

    @Test
    public void test3() throws Exception {

        String paramOne = "2024-03-10.22.21.59";
        String paramSecond = "2024-03-10.22.21.59";
        String tunnel = "107";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList").param("idt", tunnel).param("dateInit", paramOne).param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(this.alarmDio60(), listObj.get(0));
    }

    @Test
    public void test4() throws Exception {

        String paramOne = "2024-01-02.22.21.00";
        String paramSecond = "2024-01-02.23.22.00";
        String severity = "3";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList")
                .param("severity", severity)
                .param("dateInit", paramOne)
                .param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listObj.get(0), this.alarmHum60());

    }

    private List<AlarmOutputVO> getListObj(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonResponse = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {
        });
    }

    private AlarmTypeOutputVO getTpe(String tpe) {
        Alarm_type a = new Alarm_type();
        if (tpe.equals("HUM")) {
            a.setId(2);
            a.setXkey("HUM");
            a.setDescription("Humedad");
            return modelMapper.map(a, AlarmTypeOutputVO.class);
        } else if (tpe.equals("DI")) {
            a.setId(3);
            a.setXkey("DI");
            a.setDescription("Dioxido de Carbono");
            return modelMapper.map(a, AlarmTypeOutputVO.class);

        } else {
            return null;
        }
    }


    private Date parseDate(String dateToParse) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss");
        return format.parse(dateToParse);
    }

    private AlarmOutputVO alarmHum60() throws ParseException {
        AlarmOutputVO alarm2 = new AlarmOutputVO();
        alarm2.setAlarm_tpe(this.getTpe("HUM"));
        alarm2.setIdTunnel(102);
        alarm2.setAlarm(this.parseDate("2024-01-02.22.21.59"));
        alarm2.setSeverity(3);
        alarm2.setDescription("Humedad al 60 %");
        return alarm2;
    }

    private AlarmOutputVO alarm60hum() throws ParseException {
        AlarmOutputVO alarm2 = new AlarmOutputVO();
        alarm2.setAlarm_tpe(this.getTpe("HUM"));
        alarm2.setIdTunnel(14);
        alarm2.setAlarm(this.parseDate("2024-02-07.22.21.59"));
        alarm2.setSeverity(3);
        alarm2.setDescription("Humedad al 60 %");
        return alarm2;
    }

    private AlarmOutputVO alarmHum10() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpe("HUM"));
        alarm1.setIdTunnel(101);
        alarm1.setAlarm(this.parseDate("2024-01-02.23.21.59"));
        alarm1.setSeverity(1);
        alarm1.setDescription("Humedad al 10 %");
        return alarm1;
    }

    private AlarmOutputVO alarmDio60() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpe("DI"));
        alarm1.setIdTunnel(107);
        alarm1.setAlarm(this.parseDate("2024-03-10.22.21.59"));
        alarm1.setSeverity(4);
        alarm1.setDescription("Dioxido de Carbono 60 %");
        return alarm1;
    }

    private AlarmOutputVO alarmHum90() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpe("HUM"));
        alarm1.setIdTunnel(104);
        alarm1.setAlarm(this.parseDate("2024-04-10.21.21.59"));
        alarm1.setSeverity(5);
        alarm1.setDescription("Humedad al 90 %");
        return alarm1;
    }

    private AlarmOutputVO alarmDio80() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpe("DI"));
        alarm1.setIdTunnel(240);
        alarm1.setAlarm(this.parseDate("2024-04-10.22.21.59"));
        alarm1.setSeverity(5);
        alarm1.setDescription("Dioxido de Carbono 80 %");
        return alarm1;
    }

    private AlarmOutputVO alarmDio20() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpe("DI"));
        alarm1.setIdTunnel(10);
        alarm1.setAlarm(this.parseDate("2024-02-07.22.21.59"));
        alarm1.setSeverity(1);
        alarm1.setDescription("Dioxido de Carbono 20 %");
        return alarm1;
    }

    private AlarmOutputVO alarmDio10() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpe("DI"));
        alarm1.setIdTunnel(107);
        alarm1.setAlarm(this.parseDate("2024-03-10.23.59.59"));
        alarm1.setSeverity(1);
        alarm1.setDescription("Dioxido de Carbono 10 %");
        return alarm1;
    }


}