package com.agee.admin;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


/**
 * @author agee
 * @date 2022-09-02 10:39
 * @Description: DingFlow程序启动
 */
@SpringBootApplication
@ComponentScan({"com.agee.*"})
@Import({SpringUtil.class})
public class DFAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DFAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  DingFlow启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}
