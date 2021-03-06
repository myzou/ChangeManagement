package com.curd.dao;

import com.curd.model.SystemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 系统信息数据操作类
 * @author Max
 * @date 2019-04-10 下午 9:08
 * @projectName curd
 */
@Repository
public interface SystemInfoDao extends JpaRepository<SystemInfo,String>, JpaSpecificationExecutor<SystemInfo> {
}
