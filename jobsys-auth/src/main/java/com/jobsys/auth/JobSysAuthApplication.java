package com.jobsys.auth;

import com.jobsys.common.security.annotation.EnableRyFeignClients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 认证授权中心
 *
 * @author PengFei Deng
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JobSysAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysAuthApplication.class, args);
        System.out.println("\n\n(♥◠‿◠)ﾉﾞ  互联网招聘平台Auth认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  \n\n ");
    }
}
