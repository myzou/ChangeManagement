package com.curd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *  zq_data 系统参数类
 * @author luke 2019-09-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zq_data")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ZqData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参数名称
     */
    @Id
    private String sysName;

    /**
     * 参数值1
     */
    private String paramValue1;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;




}