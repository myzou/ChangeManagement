package com.curd.dao;

import com.curd.model.QueryDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author op1768
 * @create 2019-09-20 11:33
 * @project light-attenuation
 */
@Repository
public interface QueryDeviceDao extends JpaRepository<QueryDevice,String>, JpaSpecificationExecutor<QueryDevice> {
}
