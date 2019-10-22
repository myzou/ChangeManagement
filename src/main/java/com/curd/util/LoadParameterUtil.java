package com.curd.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 参数配置类，用来放配置的参数
 *
 * @author op1768
 * @create 2019-09-23 11:53
 * @project light-attenuation
 */
@Configuration
@PropertySource(value = "classpath:param.properties")
public class LoadParameterUtil {
    public static String PROTOCOL;
    public static String DEBUG;
    public static String myselfSendHost;
    public static String myselfEmailPassword;
    public static String myselfFormEmail;
    public static String dcSendHost;
    public static String dcFormEmail;
    public static String monitorFormEmail;
    public static String dcEmailPassword;
    public static String addressee;
    public static String ggwUrl;
    public static String loginGgwUrl;
    public static String deviceGetUrl;

    public  String getPROTOCOL() {
        return PROTOCOL;
    }
    @Value("${protocol}")
    public  void setPROTOCOL( String PROTOCOL) {
        LoadParameterUtil.PROTOCOL = PROTOCOL;
    }

    public  String getDEBUG() {
        return DEBUG;
    }
    @Value("${debug}")
    public  void setDEBUG(String DEBUG) {
        LoadParameterUtil.DEBUG = DEBUG;
    }

    public  String getMyselfSendHost() {
        return myselfSendHost;
    }
    @Value("${myselfSendHost}")
    public  void setMyselfSendHost(String myselfSendHost) {
        LoadParameterUtil.myselfSendHost = myselfSendHost;
    }

    public  String getMyselfEmailPassword() {
        return myselfEmailPassword;
    }
    @Value("${myselfEmailPassword}")
    public  void setMyselfEmailPassword(String myselfEmailPassword) {
        LoadParameterUtil.myselfEmailPassword = myselfEmailPassword;
    }

    public  String getMyselfFormEmail() {
        return myselfFormEmail;
    }
    @Value("${myselfFormEmail}")
    public  void setMyselfFormEmail(String myselfFormEmail) {
        LoadParameterUtil.myselfFormEmail = myselfFormEmail;
    }

    public  String getDcSendHost() {
        return dcSendHost;
    }
    @Value("${dcSendHost}")
    public  void setDcSendHost(String dcSendHost) {
        LoadParameterUtil.dcSendHost = dcSendHost;
    }

    public  String getDcFormEmail() {
        return dcFormEmail;
    }
    @Value("${dcFormEmail}")
    public  void setDcFormEmail(String dcFormEmail) {
        LoadParameterUtil.dcFormEmail = dcFormEmail;
    }

    public  String getMonitorFormEmail() {
        return monitorFormEmail;
    }
    @Value("${monitorFormEmail}")
    public  void setMonitorFormEmail(String monitorFormEmail) {
        LoadParameterUtil.monitorFormEmail = monitorFormEmail;
    }

    public  String getDcEmailPassword() {
        return dcEmailPassword;
    }
    @Value("${dcEmailPassword}")
    public  void setDcEmailPassword(String dcEmailPassword) {
        LoadParameterUtil.dcEmailPassword = dcEmailPassword;
    }

    public  String getAddressee() {
        return addressee;
    }
    @Value("${addressee}")
    public  void setAddressee(String addressee) {
        LoadParameterUtil.addressee = addressee;
    }

    public static String getGgwUrl() {
        return ggwUrl;
    }
    @Value("${GGW_URL}")
    public static void setGgwUrl(String ggwUrl) {
        LoadParameterUtil.ggwUrl = ggwUrl;
    }

    public static String getLoginGgwUrl() {
        return loginGgwUrl;
    }

    @Value("${LOGIN_GGW_URL}")
    public static void setLoginGgwUrl(String loginGgwUrl) {
        LoadParameterUtil.loginGgwUrl = loginGgwUrl;
    }

    public static String getDeviceGetUrl() {
        return deviceGetUrl;
    }
    @Value("${DEVICE_GET_URL}")
    public static void setDeviceGetUrl(String deviceGetUrl) {
        LoadParameterUtil.deviceGetUrl = deviceGetUrl;
    }
}
