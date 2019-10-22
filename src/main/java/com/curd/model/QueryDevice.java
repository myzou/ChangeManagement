package com.curd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *  query_device
 * @author luke 2019-09-20
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "query_device")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class QueryDevice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    private String id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 端口
     */
    private String port;

    /**
     * 对应的设备ip
     */
    private String ip;

    /**
     * 状态
     */
    private String status;

    /**
     * ip更新时间
     */
    private String ipUpdateTime;

    /**
     * 检测状态时间
     */
    private String checkUpdateTime;

    /**
     * 输出光功率
     */
    private String  laserOutputPower  ;
    /**
     * 输入光功率
     */
    private String  receiverOpticalPower ;

    public QueryDevice() {
    }

}