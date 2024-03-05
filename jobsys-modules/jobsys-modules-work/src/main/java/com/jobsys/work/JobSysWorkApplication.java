package com.jobsys.work;


import com.jobsys.common.security.annotation.EnableCustomConfig;
import com.jobsys.common.security.annotation.EnableRyFeignClients;
import com.jobsys.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
//@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class JobSysWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysWorkApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  互联网招聘平台工作职位模块启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
