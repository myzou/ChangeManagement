package com.curd.util;

import com.curd.service.impl.QuseryDeviceStart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author op1768
 * @create 2019-09-24 19:29
 * @project light-attenuation
 */

@EnableScheduling
@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    private QuseryDeviceStart quseryDeviceStart;

    //@Scheduled(cron = "0 0 10 * * ?")
    public void everyDayQueryDevice() {
        quseryDeviceStart.start("","",true);
        log.info("每天执行一次");
    }


}