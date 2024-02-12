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

    @Query(value = "SELECT * from alarms where alarm_tpe = :idKey", nativeQuery = true)
    List<Alarm> listAlarmByXkey(@Param("idKey") int idKey);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel", nativeQuery = true)
    List<Alarm> listAlarmByIdTunnel(@Param("idTunnel") int idTunnel);

    @Query(value = "SELECT * from alarms " +
            "where alarm between PARSEDATETIME(:dateInit,'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish, 'yyyy-MM-dd.hh.mm.ss')", nativeQuery = true)
    List<Alarm> listAlarmBetweenDates(@Param("dateInit") String dateInit, @Param("dateFinish") String dateFinish);

    @Query(value = "SELECT * from alarms where alarm_tpe = :idKey and idTunel = :tunnel ", nativeQuery = true)
    List<Alarm> listAlarmKeyTunnel(@Param("idKey") String idKey, @Param("tunnel") String tunnel);

    @Query(value = "SELECT * from alarms " +
            "where alarm_tpe = :idKey " +
            "and (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss'))", nativeQuery = true)
    List<Alarm> listAlarmKeyDates(@Param("idKey") String idKey, @Param("dateInit") String dateInit, @Param("dateFinish") String dateFinish);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel " +
            "and (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss'))", nativeQuery = true)
    List<Alarm> listAlarmByIdTunnelDates(@Param("idTunnel") int idTunnel, @Param("dateInit") String dateInit, @Param("dateFinish") String dateFinish);

    @Query(value = "SELECT * from alarms where severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmBySev(@Param("severity") int severity);

    @Query(value = "SELECT * from alarms where severidad = :severity " +
            "and alarm_tpe = :idKey", nativeQuery = true)
    List<Alarm> listAlarmBySevKey(@Param("severity") int severity, @Param("idKey") String idKey);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel " +
            "and (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss')) " +
            "and alarm_tpe = :idKey " +
            "and severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmByAllParams(@Param("idTunnel") int idTunnel, @Param("dateInit") String dateInit,
                                     @Param("dateFinish") String dateFinish, @Param("idKey") String idKey,
                                     @Param("severity") int severity);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel " +
            "and (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss')) " +
            "and alarm_tpe = :idKey ", nativeQuery = true)
    List<Alarm> listAlarmByKeyTunnelDate(@Param("idTunnel") int idTunnel, @Param("dateInit") String dateInit,
                                         @Param("dateFinish") String dateFinish, @Param("idKey") String idKey);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel " +
            "and alarm_tpe = :idKey " +
            "and severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmByKeyTunnelSev(@Param("idTunnel") int idTunnel, @Param("idKey") String idKey,
                                        @Param("severity") int severity);

    @Query(value = "SELECT * from alarms where (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss')) " +
            "and alarm_tpe = :idKey " +
            "and severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmByKeyDateSev(@Param("dateInit") String dateInit,
                                      @Param("dateFinish") String dateFinish, @Param("idKey") String idKey,
                                      @Param("severity") int severity);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel " +
            "and (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss')) " +
            "and severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmByTunnelDateSev(@Param("idTunnel") int idTunnel, @Param("dateInit") String dateInit,
                                     @Param("dateFinish") String dateFinish,
                                     @Param("severity") int severity);

    @Query(value = "SELECT * from alarms where IDTUNEL = :idTunnel " +
            "and severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmByTunnelSev(@Param("idTunnel") int idTunnel,
                                         @Param("severity") int severity);

    @Query(value = "SELECT * from alarms where (alarm between PARSEDATETIME(:dateInit, 'yyyy-MM-dd.hh.mm.ss') " +
            "and PARSEDATETIME(:dateFinish,'yyyy-MM-dd.hh.mm.ss')) " +
            "and severidad = :severity", nativeQuery = true)
    List<Alarm> listAlarmByDateSev(@Param("dateInit") String dateInit,
                                         @Param("dateFinish") String dateFinish,
                                         @Param("severity") int severity);

}
