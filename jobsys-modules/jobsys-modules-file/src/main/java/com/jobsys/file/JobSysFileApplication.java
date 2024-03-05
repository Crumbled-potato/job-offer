package com.jobsys.file;


import com.jobsys.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 文件服务
 *
 * @author PengFei Deng
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JobSysFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysFileApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  文件服务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
