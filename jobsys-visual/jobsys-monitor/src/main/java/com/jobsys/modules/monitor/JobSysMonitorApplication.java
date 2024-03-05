package com.jobsys.modules.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 监控中心
 *
 * @author PengFei Deng
 */
@EnableAdminServer
@SpringBootApplication
public class JobSysMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSysMonitorApplication.class, args);
        System.out.println("\n\n\n(♥◠‿◠)ﾉﾞ  互联网招聘平台监控中心启动成功   ლ(´ڡ`ლ)ﾞ  \n\n\n");
    }
}
