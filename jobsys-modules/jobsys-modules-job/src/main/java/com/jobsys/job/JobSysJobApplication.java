package com.jobsys.job;


import com.jobsys.common.security.annotation.EnableCustomConfig;
import com.jobsys.common.security.annotation.EnableRyFeignClients;
import com.jobsys.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 定时任务
 *
 * @author PengFei Deng
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class JobSysJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysJobApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
