package com.curd.controller;

import cn.hutool.core.date.DateUtil;
import com.curd.model.ZqData;
import com.curd.service.impl.ZqDataServiceImpl;
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
@RequestMapping("/api/zq_data")
public class ZqDataController {

    @Autowired
    private ZqDataServiceImpl ZqDataService;

    @PostMapping("/save")
    public ZqData save(@RequestBody ZqData ZqData) {
        ZqData tempZqData=ZqDataService.getOne(ZqData.getSysName());
        if(null==tempZqData){
            ZqData.setCreateTime(DateUtil.now());
            ZqData.setUpdateTime(DateUtil.now());
            tempZqData=null;
            return ZqDataService.save(ZqData);
        }else{
            tempZqData.setParamValue1(ZqData.getParamValue1());
            tempZqData.setUpdateTime(DateUtil.now());
            ZqData=null;
            return ZqDataService.save(tempZqData);
        }
    }

    @GetMapping("/all")
    public List<ZqData> all() {
        return ZqDataService.getAll();
    }

    @RequestMapping("/one")
    public ZqData one(@RequestParam("id") String id) {
        return ZqDataService.getOne(id);
    }

    @RequestMapping("/del")
    public boolean delete(@RequestParam("id") String id) {
        try {
            ZqDataService.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/page")
    public Page<ZqData> getPage(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("sizeNumber") Integer sizeNumber) {
        Page<ZqData> result = ZqDataService.getPage(pageNumber, sizeNumber);
        return result;
    }

}
