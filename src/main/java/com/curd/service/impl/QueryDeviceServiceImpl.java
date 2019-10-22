package com.curd.service.impl;

import com.curd.dao.QueryDeviceDao;
import com.curd.model.QueryDevice;
import com.curd.service.QueryDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author op1768
 * @create 2019-09-20 11:36
 * @project light-attenuation
 */
@Service
public class QueryDeviceServiceImpl implements QueryDeviceService {

    @Autowired
    private QueryDeviceDao QueryDeviceDao;

    @Override
    public List<QueryDevice> getAll() {
        return QueryDeviceDao.findAll();
    }

    @Override
    public QueryDevice getOne(String id) {
        try {
            QueryDevice QueryDevice = QueryDeviceDao.getOne(id);
            return QueryDevice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryDevice save(QueryDevice QueryDevice) {
        return QueryDeviceDao.save(QueryDevice);
    }

    @Override
    public void remove(String id) {
        QueryDeviceDao.deleteById(id);
    }

    @Override
    public Page<QueryDevice> getPage(Integer pageNumber, Integer sizeNumber) {
        if (pageNumber == 0 || null == pageNumber || sizeNumber == 0 || null == sizeNumber) {
            pageNumber = 0;
            sizeNumber = 20;
        } else {
            --pageNumber;
        }
        //替代方法
        Pageable pageable = new PageRequest(pageNumber, sizeNumber);
        Page<QueryDevice> page = QueryDeviceDao.findAll(pageable);
        return page;
    }
}
