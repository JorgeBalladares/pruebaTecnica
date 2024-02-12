package com.price.domain.repositories;

import com.price.domain.entities.Alarm;
import com.price.domain.entities.Alarm_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Alarm_typeRepo extends JpaRepository<Alarm_type, Integer>, JpaSpecificationExecutor<Alarm_type> {

    @Query(value = "SELECT id from Alarm_type where xkey = :xkey", nativeQuery = true)
    Integer getIdByKey(@Param("xkey") String xkey);
    @Query(value = "SELECT * from Alarm_type a where xkey = :xkey", nativeQuery = true)
    Alarm_type gettingTpeAlarm(@Param("xkey") String xkey);


}
