package com.price.infraestructure.controllers;

import com.price.application.usercases.AlarmService;
import com.price.infraestructure.vo.output.AlarmOutputVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;


    @GetMapping(value = "/getList", produces = {
            "application/json"})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "OK", content = {
            @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad input parameter", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Registry not found", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "415", description = "Unsupported media type", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "503", description = "Service Unavailable", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = {
                    @Content(mediaType = "application/json")})})
    @Operation(summary = "getting list with params", tags = "", method = "getList")
    public ResponseEntity<List<AlarmOutputVO>> getList
            (@RequestParam(required = false) String key, @RequestParam(required = false) String idt
                    , @RequestParam(required = false) String dateInit, @RequestParam(required = false) String dateFinish
                    , @RequestParam(required = false) String severity) throws Exception {
        log.debug("FLUJO - getList");
        return new ResponseEntity<>(this.alarmService.getList(key, idt, dateInit, dateFinish, severity), HttpStatus.ACCEPTED);
    }


}
