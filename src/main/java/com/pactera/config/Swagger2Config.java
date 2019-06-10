package com.pactera.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 类名称：Swagger2Config 类描述：Swagger2配置 创建人：lee 创建时间：2016年11月24日 下午11:47:58
 * 
 * @version 1.0.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private final String VERSION = "1.0";

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.desc}")
    private String desc;

    @Value("${swagger.url}")
    private String url;

    @Bean
    public Docket createRestApi() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.addAll(pars);
        return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(pars).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.pactera")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        try {
            title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
            desc = new String(desc.getBytes("ISO-8859-1"), "UTF-8");
            url = new String(url.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
        }
        return new ApiInfoBuilder().title(title).description(desc).termsOfServiceUrl(url).version(VERSION).build();
    }
}