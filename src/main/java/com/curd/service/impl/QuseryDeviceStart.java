package com.curd.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.curd.dao.QueryDeviceDao;
import com.curd.model.QueryDevice;
import com.curd.util.GGWLoginApiUtil;
import com.curd.util.LoadParameterUtil;
import com.curd.util.TempDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author op1768
 * @create 2019-09-18 17:48
 * @project light-attenuation
 */
@Service
public class QuseryDeviceStart {

    @Autowired
    private QueryDeviceDao queryDeviceDao;

    /**
     * 开始查询方法
     */
    public void start(String paramDeviceName, String paramPort,Boolean sendEmail) {

        Map<String, Map<String, Object>> deviceMap = getDevices();
        Map<String, Object> systemParamMap = getSysParam();
        String[] tempArr = JSONUtil.parseObj(systemParamMap.get("OP帐号密码")).getStr("paramValue1").split("###");
        String opName = tempArr[0];
        String opPassword = tempArr[1];

        List<QueryDevice> list = queryDeviceDao.findAll();
        //输出光衰不正常的组装报告
        StringBuffer recoverBF = new StringBuffer();
        StringBuffer exceptionBF = new StringBuffer();
        recoverBF.append("<table><tr><th>设备</th><th>端口</th><th>状态</th><th>输出光功率</th><th>输入光功率</th></tr>");
        exceptionBF.append("<table><tr><th>设备</th><th>端口</th><th>状态</th><th>输出光功率</th><th>输入光功率</th></tr>");
        //判断是否有数据
        boolean isNullRecover=false;
        boolean isNullException=false;
        String toStr=LoadParameterUtil.addressee;
        String to[] = LoadParameterUtil.addressee.split(";");
        String cs[] = null;
        String ms[] = null;
        String subject = "上海冷备端口光衰监控";
        String content = "";
        String formEmail = LoadParameterUtil.monitorFormEmail;
        String fileList[] = null;
        String host = LoadParameterUtil.dcSendHost;
        String password = LoadParameterUtil.dcEmailPassword;
        if (StrUtil.isBlank(paramDeviceName) && StrUtil.isBlank(paramPort)) {
            for (int i = 0; list.size() > 0 && i < list.size(); i++) {
                QueryDevice queryDevice = list.get(i);
                String startStatus = queryDevice.getStatus();
                String deviceName = queryDevice.getDeviceName();
                String port = queryDevice.getPort();
                System.out.println("============" + deviceName + "\t" + port + "=======================");

                if (deviceMap.get(deviceName) == null || deviceMap.get(deviceName).get("ip") == null) {
                    queryDevice.setStatus("根据设备名称没有找到对应的ip");
                } else {
                    String ip = deviceMap.get(deviceName).get("ip").toString();
                    queryDevice.setIp(ip);
                    queryDevice.setIpUpdateTime(DateUtil.now());

                    String command = "show interfaces diagnostics optics " + port;
                    System.out.println(deviceName + ">" + command);
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("opName", opName);
                    paramMap.put("opPassword", opPassword);
                    paramMap.put("sign", "123456");
                    paramMap.put("command", command);
                    paramMap.put("ip", ip);
                    String loginReturnString = GGWLoginApiUtil.loginGGWAPI(paramMap);
                    //System.out.println("登录返回参数："+paramMap);
                    String getGGWAPIString = GGWLoginApiUtil.getGGWAPI(paramMap);
                    //System.out.println("返回的结果集："+getGGWAPIString);
                    String returnStr = JSONUtil.parseObj(getGGWAPIString).getStr("data");
                    System.out.println("returnStr:" + returnStr);
                    //if(deviceName.equals("CNSHHCJX1001E")){
                    //    System.out.println(123456);
                    //}
                    if (StrUtil.isBlank(returnStr)) {
                        queryDevice.setStatus("没有找到对应的光衰信息");
                    } else {
                        try {
                            boolean b = validateData(returnStr);
                            String tempDataArr[] = returnStr.split("\n");
                            queryDevice.setLaserOutputPower(tempDataArr[2].split(":")[1].split("/")[1].trim());
                            queryDevice.setReceiverOpticalPower(tempDataArr[5].split(":")[1].split("/")[1].trim());
                            if (b) {
                                queryDevice.setStatus("OK");
                            } else {
                                queryDevice.setStatus("光衰列不全为Off");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            queryDevice.setStatus(returnStr);
                        }
                    }

                }
                queryDevice.setCheckUpdateTime(DateUtil.now());
                String endStatus = queryDevice.getStatus();
                if (!StrUtil.isBlank(startStatus) && !StrUtil.isBlank(endStatus)) {
                    if ( endStatus.equals("OK")) {
                        isNullRecover=true;
                        recoverBF.append("<tr>");
                        //recoverBF.append("设备：" + deviceName + ",端口：" + port + ",状态：" + queryDevice.getStatus() + "<br>");
                        recoverBF.append("<td>"+deviceName+"</td>"+"<td>"+port+"</td>"+"<td>"+queryDevice.getStatus() +"</td>");
                        recoverBF.append("<td>"+queryDevice.getLaserOutputPower()+"</td>"+"<td>"+queryDevice.getReceiverOpticalPower()+"</td>");
                        recoverBF.append("</tr>");
                    } else if (!endStatus.equals("OK")) {
                        //exceptionBF.append("设备：" + deviceName + ",端口：" + port + ",状态：" + queryDevice.getStatus().replaceAll("\n", "  ") + "<br>");
                        isNullException=true;
                        exceptionBF.append("<tr>");
                        exceptionBF.append("<td>"+deviceName+"</td>"+"<td>"+port+"</td>"+"<td>"+queryDevice.getStatus().replaceAll("\n", "  ")  +"</td>");
                        exceptionBF.append("<td>"+queryDevice.getLaserOutputPower()+"</td>"+"<td>"+queryDevice.getReceiverOpticalPower()+"</td>");
                        exceptionBF.append("</tr>");


                    }
                }
                queryDeviceDao.save(queryDevice);
            }
        } else {
            QueryDevice temp = new QueryDevice();
            temp.setDeviceName(paramDeviceName);
            temp.setPort(paramPort);
            Example<QueryDevice> example = Example.of(temp);
            QueryDevice queryDevice = queryDeviceDao.findOne(example).get();
            String startStatus = queryDevice.getStatus();
            String deviceName = queryDevice.getDeviceName();
            String port = queryDevice.getPort();
            System.out.println("============" + deviceName + "\t" + port + "=======================");

            if (deviceMap.get(deviceName) == null || deviceMap.get(deviceName).get("ip") == null) {
                queryDevice.setStatus("根据设备名称没有找到对应的ip");
            } else {
                String ip = deviceMap.get(deviceName).get("ip").toString();
                queryDevice.setIp(ip);
                queryDevice.setIpUpdateTime(DateUtil.now());

                String command = "show interfaces diagnostics optics " + port;
                System.out.println(deviceName + ">" + command);
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("opName", opName);
                paramMap.put("opPassword", opPassword);
                paramMap.put("sign", "123456");
                paramMap.put("command", command);
                paramMap.put("ip", ip);
                String loginReturnString = GGWLoginApiUtil.loginGGWAPI(paramMap);
                //System.out.println("登录返回参数："+paramMap);
                String getGGWAPIString = GGWLoginApiUtil.getGGWAPI(paramMap);
                //System.out.println("返回的结果集："+getGGWAPIString);
                String returnStr = JSONUtil.parseObj(getGGWAPIString).getStr("data");
                System.out.println("returnStr:" + returnStr);
                //if(deviceName.equals("CNSHHCJX1001E")){
                //    System.out.println(123456);
                //}
                if (StrUtil.isBlank(returnStr)) {
                    queryDevice.setStatus("没有找到对应的光衰信息");
                } else {
                    try {
                        boolean b = validateData(returnStr);
                        String tempDataArr[] = returnStr.split("\n");
                        queryDevice.setLaserOutputPower(tempDataArr[2].split(":")[1].split("/")[1].trim());
                        queryDevice.setReceiverOpticalPower(tempDataArr[5].split(":")[1].split("/")[1].trim());
                        if (b) {
                            queryDevice.setStatus("OK");
                        } else {
                            queryDevice.setStatus("光衰列不全为Off");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        queryDevice.setStatus(returnStr);
                    }
                }

            }

            String endStatus = queryDevice.getStatus();
            if (!StrUtil.isBlank(startStatus) && !StrUtil.isBlank(endStatus)) {
                if ( endStatus.equals("OK")) {
                    isNullRecover=true;
                    recoverBF.append("<tr>");
                    //recoverBF.append("设备：" + deviceName + ",端口：" + port + ",状态：" + queryDevice.getStatus() + "<br>");
                    recoverBF.append("<td>"+deviceName+"</td>"+"<td>"+port+"</td>"+"<td>"+queryDevice.getStatus() +"</td>");
                    recoverBF.append("<td>"+queryDevice.getLaserOutputPower()+"</td>"+"<td>"+queryDevice.getReceiverOpticalPower()+"</td>");
                    recoverBF.append("</tr>");

                } else if (!endStatus.equals("OK")) {
                    //exceptionBF.append("设备：" + deviceName + ",端口：" + port + ",状态：" + queryDevice.getStatus().replaceAll("\n", "  ") + "<br>");
                    isNullException=true;
                    exceptionBF.append("<tr>");
                    exceptionBF.append("<td>"+deviceName+"</td>"+"<td>"+port+"</td>"+"<td>"+queryDevice.getStatus().replaceAll("\n", "  ")  +"</td>");
                    exceptionBF.append("<td>"+queryDevice.getLaserOutputPower()+"</td>"+"<td>"+queryDevice.getReceiverOpticalPower()+"</td>");
                    exceptionBF.append("</tr>");

                }
            }

            queryDevice.setCheckUpdateTime(DateUtil.now());
            queryDeviceDao.save(queryDevice);
        }
        recoverBF.append("</table>");
        exceptionBF.append("</table>");
        if(!isNullRecover){
            recoverBF=new StringBuffer();
        }
        if(!isNullException){
            exceptionBF=new StringBuffer();
        }
        if(sendEmail){
            sendEmail(to, cs, ms, subject, content, formEmail, fileList, host, password, exceptionBF.toString(), recoverBF.toString());
        }
    }

    private static void sendEmail(String to[], String cs[], String ms[], String subject, String content, String formEmail, String fileList[], String host, String password, String exceptionStr, String recoverStr) {
        if (!StrUtil.isBlank(exceptionStr) || !StrUtil.isBlank(recoverStr)) {
            recoverStr=(StrUtil.isBlank(recoverStr)?"无<br>":recoverStr);
            exceptionStr=(StrUtil.isBlank(exceptionStr)?"无<br>":exceptionStr);
            content="<html>" +
                    "<style type=\"text/css\">" +
                    "table {" +
                    "border-collapse: collapse;" +
                    "border: none;" +
                    "}" +
                    "table,table td,table th{" +
                    "border: solid #000 1px;" +
                    "}" +
                    "table td{" +
                    "border: solid #000 1px;" +
                    "}" +
                    "</style>" +
                    "<h3>状态正常的详细信息:</h3>" + recoverStr + "<h3>状态异常的详细信息:</h3>" + exceptionStr+
                    "</body>" +
                    "</html>";
            com.cter.util.SendMailUtil sendMailUtil = com.cter.util.SendMailUtil.getInstance();
            try {
                sendMailUtil.send(to, cs, ms, subject, content, formEmail, fileList, host, password);
                System.out.println("已经发送邮件");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("发送邮件失败");
            }
        }

    }


    /**
     * 查看光衰是否存在NO
     */
    private static boolean validateData(String data)throws Exception {
        String tempArr[] = data.split("\n");
        boolean notIndexOfOff = true;
        for (int i = 6; i >= 6 && i <= 25; i++) {
            if (tempArr[25].split(":")[1].trim().equals("On")) {
                return  notIndexOfOff = false;
            }
        }
        return notIndexOfOff;
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    public static Map<String, Map<String, Object>> getDevices() {
        String username = "nss_script";
        String password = "ZX4XdUpH";
        String url = "jdbc:log4jdbc:mysql://218.97.9.147:3306/nm_shared_info?characterEncoding=utf-8";
        String driver = "net.sf.log4jdbc.DriverSpy";
        TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);

        String sql = "select ip,full_name from devices  ";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
        List<Object> params = new ArrayList<Object>();
        list = tempDBUtils.executeQueryCE(sql, params);
        for (Map<String, Object> o : list) {
            map.put(o.get("full_name").toString(), o);
        }

        String[] testIpArr = new String[]{"202.76.80.210", "10.180.5.206"};
        //用来存放testlabIp
        Map<String, Object> tempMap = new HashMap<>();
        for (int i = 0; i < testIpArr.length; i++) {
            String teampIp = testIpArr[i];
            tempMap.put("full_name", teampIp);
            tempMap.put("ip", teampIp);
            map.put(teampIp, tempMap);
        }
        return map;
    }

    /**
     * 获取系统参数
     *
     * @return
     */
    public static Map<String, Object> getSysParam() {
        String username = "root";
        String password = "root";
        String url = "jdbc:log4jdbc:mysql://localhost:3306/light_attenuation?serverTimezone=UTC";
        String driver = "net.sf.log4jdbc.DriverSpy";
        TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);

        String sql = "select * from zq_data";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> params = new ArrayList<Object>();
        list = tempDBUtils.executeQueryCE(sql, params);
        for (Map<String, Object> o : list) {
            map.put(o.get("sysName").toString(), o);
        }
        return map;
    }

}
