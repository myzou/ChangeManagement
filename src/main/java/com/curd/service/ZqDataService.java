package com.curd.service;

import com.curd.model.ZqData;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author luke
 * @date 2019-09-19 下午 9:45
 * @projectName curd
 */
public interface ZqDataService {

    /**
     * 获取所有列表数据
     * @return list集合
     */
    public List<ZqData> getAll();

    /**
     * 根据主键Id获取一条数据
     * @param id
     * @return 查询到的对象
     */
    public ZqData getOne(String id);

    /**
     * 保存一条数据
     * 如果Id为空时，jpa会新增一条数据，否则更新该对象
     * @param t
     * @return 保存成功后的对象
     */
    public ZqData save(ZqData t);

    /**
     * 删除一条数据
     * @param id 主键
     */
    public void remove(String id);

    /**
     * 获取分页数据
     * @param start 从第几页开始
     * @param count 显示多少行
     * @return 分页结果集
     */
    Page<ZqData> getPage(Integer start, Integer count);
}
