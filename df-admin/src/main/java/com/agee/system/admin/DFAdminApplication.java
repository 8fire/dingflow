package com.agee.system.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author agee
 * @date 2022-09-02 10:39
 * @Description: DingFlow程序启动
 */
@SpringBootApplication
@ComponentScan({"springfox.documentation.schema","com.agee"})
public class DFAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DFAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  DingFlow启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
