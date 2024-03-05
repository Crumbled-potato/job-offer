package com.jobsys.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 *
 * @author PengFei Deng
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JobSysGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysGatewayApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  互联网招聘平台Gateway网关启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
