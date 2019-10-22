package com.curd.test;

import cn.hutool.http.HttpUtil;

/**
 * @author op1768
 * @create 2019-09-20 18:52
 * @project light-attenuation
 */
public class Test {


    public static void main(String[] args) {
        String url ="http://10.180.5.135:48888/ExecuteCommand/RSA?ip=202.76.8.226&&command=show%20interfaces%20descriptions&&crypto_sign=dBvxRM+aKE1N2GK2WxfTaIJUIOKzfvnm++HDvhcS2tjzkA32NRSWbDbXdUYnMybRZyFUwD7j/GsBckiLdB+z0/tYmFul7LxOPEiBq0iQ44q7PmS/zXBJJ7o7lmQtTdEOecNGR5rChvEv3TXodiWGZqBjCF96CNaolnerM5w9UB+xih/ybSjX2os2e6RLZbk+mWEj0NtPzDVk6uHRZe07uF2XLDn47LS30yhkQMdzNOr0/rhhkcKfGOe41ljDdtmvv0GsVXl2fmpoxwAa4q2yiV8bzdj5lSpOonjZ0clULTjXsBLiKpZi2fd5Dkk4BszColz7SIaUGUrwtsry4bloHw==";
        System.out.println(HttpUtil.get(url));;
    }

}
