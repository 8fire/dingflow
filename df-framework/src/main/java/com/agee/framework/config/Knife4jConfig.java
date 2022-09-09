package com.agee.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @program: df
 * @description
 * @author: 没用的阿吉
 * @create: 2022-09-03 10:54
 **/
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket allApiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("全部接口")
                .apiInfo(dfApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.agee.admin.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo dfApiInfo(){
        return new ApiInfoBuilder()
                .title("DingFlow API")
                .description("DingFlow相关接口API")
                .contact(new Contact("Snow社区","","qimingjin@126.com"))
                .version("V1.0.0")
                .build();
    }
}
