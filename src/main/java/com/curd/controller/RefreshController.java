package com.curd.controller;

import com.curd.service.impl.QuseryDeviceStart;
import com.curd.service.impl.TempCheckDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author op1768
 * @create 2019-09-18 17:09
 * @project light-attenuation
 */

@RestController
@RequestMapping("/api/refresh")
public class RefreshController {

    @Autowired
    private QuseryDeviceStart quseryDeviceStart;
    @Autowired
    private TempCheckDevice tempCheckDevice;

    @GetMapping
    public boolean refresh(@RequestParam("paramDeviceName") String paramDeviceName,@RequestParam("paramPort") String paramPort,@RequestParam("sendEmail") Boolean sendEmail){
        quseryDeviceStart.start(paramDeviceName,paramPort,sendEmail);
        return true;
    }


    @GetMapping("/tempCheck")
    public String tempCheck(@RequestParam("sendEmail") Boolean sendEmail,@RequestParam("containDeviceName") String containDeviceName){
        return tempCheckDevice.start(sendEmail,containDeviceName);
    }

    @GetMapping("/tempCheckAll")
    public String tempCheckAll(@RequestParam("sendEmail") Boolean sendEmail,@RequestParam("containDeviceName") String containDeviceName){
        return tempCheckDevice.tempCheckAll(sendEmail,containDeviceName);
    }


}
