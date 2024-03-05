package com.jobsys.system;


import com.jobsys.common.security.annotation.EnableCustomConfig;
import com.jobsys.common.security.annotation.EnableRyFeignClients;
import com.jobsys.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 *
 * @author PengFei Deng
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class JobSysSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysSystemApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  互联网招聘平台系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
