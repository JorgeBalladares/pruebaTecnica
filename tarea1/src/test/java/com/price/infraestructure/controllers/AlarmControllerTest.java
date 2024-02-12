package com.price.infraestructure.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.price.domain.entities.Alarm;
import com.price.domain.entities.Alarm_type;
import com.price.domain.repositories.AlarmRepo;
import com.price.domain.repositories.Alarm_typeRepo;
import com.price.infraestructure.vo.output.AlarmOutputVO;
import com.price.infraestructure.vo.output.AlarmTypeOutputVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AlarmControllerTest {

    @Mock
    private AlarmRepo alarmRepo;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private Alarm_typeRepo alarmTypeRepo;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void test1() {

        Alarm_type alarmType = alarmTypeRepo.gettingTpeAlarm("HUM");

        Alarm alarm1 = new Alarm();
        alarm1.setAlarm_tpe(alarmType);
        alarm1.setIdTunnel(14);
        alarm1.setAlarm(new Date(System.currentTimeMillis()));
        alarm1.setSeverity(5);
        alarm1.setDescription("Humedad al 10%");
        alarmRepo.save(alarm1);

        verify(alarmRepo, times(1)).save(alarm1);
    }

    @Test
    public void test2() throws Exception {

        String paramOne = "2024-01-02.22.21.00";
        String paramSecond = "2024-01-02.23.22.00";


        List<AlarmOutputVO> listOutAlarmProof = new ArrayList<>();
        listOutAlarmProof.add(this.alarmHum10());
        listOutAlarmProof.add(this.alarmHum60());

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList").param("dateInit", paramOne).param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listOutAlarmProof, listObj);
    }

    @Test
    public void test3() throws Exception {

        String paramOne = "2024-04-10.21.20.59";
        String paramSecond = "2024-04-10.21.50.59";
        String severity = "5";

        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpeHum());
        alarm1.setIdTunnel(104);
        alarm1.setAlarm(this.parseDate("2024-04-10.21.21.59"));
        alarm1.setSeverity(5);
        alarm1.setDescription("Humedad al 90 %");


        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList")
                .param("severity", severity)
                .param("dateInit", paramOne)
                .param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listObj.get(0), alarm1);
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

    @Test
    public void test5() throws Exception {

        String paramOne = "2024-01-02.22.21.00";
        String paramSecond = "2024-01-02.23.22.00";
        String severity = "3";
        String tunnel = "102";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList")
                .param("idt", tunnel)
                .param("severity", severity)
                .param("dateInit", paramOne)
                .param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listObj.get(0), this.alarmHum60());
    }

    @Test
    public void test6() throws Exception {

        String paramOne = "2024-01-02.22.21.00";
        String paramSecond = "2024-01-02.23.22.00";
        String severity = "3";
        String key = "hum";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList")
                .param("key", key)
                .param("severity", severity)
                .param("dateInit", paramOne)
                .param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listObj.get(0), this.alarmHum60());
    }

    @Test
    public void test7() throws Exception {

        String paramOne = "2024-01-02.22.21.00";
        String paramSecond = "2024-01-02.23.22.00";
        String key = "hum";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList")
                .param("key", key)
                .param("dateInit", paramOne)
                .param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        List<AlarmOutputVO> listOutAlarmProof = new ArrayList<>();
        listOutAlarmProof.add(this.alarmHum10());
        listOutAlarmProof.add(this.alarmHum60());

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listObj, listOutAlarmProof);
    }

    @Test
    public void test8() throws Exception {

        String paramOne = "2024-01-02.22.21.00";
        String paramSecond = "2024-01-02.23.22.00";
        String severity = "1";
        String tunnel = "101";
        String key = "hum";

        // MockMvc request
        MvcResult result = mockMvc.perform(get("/public/getList")
                .param("key", key)
                .param("idt", tunnel)
                .param("severity", severity)
                .param("dateInit", paramOne)
                .param("dateFinish", paramSecond)).andExpect(status().isAccepted()).andReturn();

        // Verificación de la respuesta
        List<AlarmOutputVO> listObj = this.getListObj(result);

        // Realizar las aserciones pertinentes
        Assertions.assertNotNull(listObj);
        assertEquals(listObj.get(0), this.alarmHum10());
    }

    private List<AlarmOutputVO> getListObj(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonResponse = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {
        });
    }

    private AlarmTypeOutputVO getTpeHum() {
        Alarm_type alarm = alarmTypeRepo.gettingTpeAlarm("HUM");
        return modelMapper.map(alarm, AlarmTypeOutputVO.class);
    }


    private Date parseDate(String dateToParse) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss");
        return format.parse(dateToParse);
    }

    private AlarmOutputVO alarmHum60() throws ParseException {
        AlarmOutputVO alarm2 = new AlarmOutputVO();
        alarm2.setAlarm_tpe(this.getTpeHum());
        alarm2.setIdTunnel(102);
        alarm2.setAlarm(this.parseDate("2024-01-02.22.21.59"));
        alarm2.setSeverity(3);
        alarm2.setDescription("Humedad al 60 %");
        return alarm2;
    }

    private AlarmOutputVO alarmHum10() throws ParseException {
        AlarmOutputVO alarm1 = new AlarmOutputVO();
        alarm1.setAlarm_tpe(this.getTpeHum());
        alarm1.setIdTunnel(101);
        alarm1.setAlarm(this.parseDate("2024-01-02.23.21.59"));
        alarm1.setSeverity(1);
        alarm1.setDescription("Humedad al 10 %");
        return alarm1;
    }

}