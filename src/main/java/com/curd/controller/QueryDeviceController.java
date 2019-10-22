package com.curd.controller;

import com.curd.model.QueryDevice;
import com.curd.service.QueryDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author op1768
 * @create 2019-09-19 11:25
 * @project light-attenuation
 */
@RestController
@RequestMapping("/api/qd")
public class QueryDeviceController {

    @Autowired
    private QueryDeviceService queryDeviceService;

    @PostMapping("/save")
    public QueryDevice save(@RequestBody QueryDevice QueryDevice) {
        return queryDeviceService.save(QueryDevice);
    }

    @GetMapping("/all")
    public List<QueryDevice> all() {
        return queryDeviceService.getAll();
    }

    @RequestMapping("/one")
    public QueryDevice one(@RequestParam("id") String id) {
        return queryDeviceService.getOne(id);
    }

    @RequestMapping("/del")
    public boolean delete(@RequestParam("id") String id) {
        try {
            queryDeviceService.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/page")
    public Page<QueryDevice> getPage(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("sizeNumber") Integer sizeNumber) {
        Page<QueryDevice> result = queryDeviceService.getPage(pageNumber, sizeNumber);
        return result;
    }

}
