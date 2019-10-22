package com.curd.dao;

import com.curd.model.ZqData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 系统参数类
 * @author luke
 * @date 2019-04-10 下午 9:08
 * @projectName curd
 */
@Repository
public interface ZqDataDao extends JpaRepository<ZqData,String>, JpaSpecificationExecutor<ZqData> {
}
