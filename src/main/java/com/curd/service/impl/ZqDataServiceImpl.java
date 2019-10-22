package com.curd.service.impl;

import com.curd.dao.ZqDataDao;
import com.curd.model.ZqData;
import com.curd.service.ZqDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author luke
 * @date 2019-09-19 下午 9:46
 * @projectName curd
 */
@Service
public class ZqDataServiceImpl implements ZqDataService {

    @Autowired
    private ZqDataDao ZqDataDao;

    @Override
    public List<ZqData> getAll() {
        return ZqDataDao.findAll();
    }

    @Override
    public ZqData getOne(String id) {
            Optional<ZqData> OptionalZqData =ZqDataDao.findById(id);
            if(OptionalZqData.isPresent()){
                return OptionalZqData.get();
            }
            return null;
    }

    @Override
    public ZqData save(ZqData ZqData) {
        return ZqDataDao.save(ZqData);
    }

    @Override
    public void remove(String id) {
        ZqDataDao.deleteById(id);
    }

    @Override
    public Page<ZqData> getPage(Integer pageNumber, Integer sizeNumber) {
        if (pageNumber == 0 || null == pageNumber || sizeNumber == 0 || null == sizeNumber) {
            pageNumber = 0;
            sizeNumber = 20;
        } else {
            --pageNumber;
        }
        //替代方法
        Pageable pageable = PageRequest.of(pageNumber,sizeNumber);
        Page<ZqData> page = ZqDataDao.findAll(pageable);
        return page;
    }
}
