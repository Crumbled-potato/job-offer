package com.jobsys.gen;


import com.jobsys.common.security.annotation.EnableCustomConfig;
import com.jobsys.common.security.annotation.EnableRyFeignClients;
import com.jobsys.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成
 *
 * @author PengFei Deng
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class JobSysGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysGenApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  互联网招聘平台代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
