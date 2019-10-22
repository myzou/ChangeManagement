package com.curd;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author op1768
 * @create 2019-09-26 15:46
 * @project light-attenuation
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {

    // 重写启动器，因为war是部署到Tomcat上用的是tomcat，springboot内置的tomcat会无效吧
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CurdApplication.class);
    }


}
