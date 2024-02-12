package com.price.domain.repositories;


import com.price.domain.entities.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AlarmRepo extends JpaRepository<Alarm, Integer>, JpaSpecificationExecutor<Alarm> {

    @Query(value = "SELECT * FROM ALARMS  " +
            "WHERE (:idTunnel IS NULL OR idTunel = :idTunnel) " +
            "AND (:idKey IS NULL OR alarm_tpe = :idKey) " +
            "AND (:dateInit IS NULL OR :dateFinish IS NULL OR alarm " +
            "BETWEEN PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "AND PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss')) " +
            "AND (:severity IS NULL OR severidad = :severity)", nativeQuery = true)
    List<Alarm> getList(
            @Param("idTunnel") String idTunnel, @Param("dateInit") String dateInit,
            @Param("dateFinish") String dateFinish, @Param("idKey") String idKey,
            @Param("severity") String severity
    );


}
