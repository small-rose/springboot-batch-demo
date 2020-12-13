package cn.xiaocai.batch.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author Xiaocai.Zhang
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Spring Boot Batch Demo 项目的 Swagger 示例文档",
                "我的博客网站：https://zhangxiaocai.cn，欢迎大家访问。",
                "API V1.0",
                "Terms of service",
                new Contact("张小菜", "https://zhangxiaocai.cn", "small-rose@qq.com"),
                "Apache", "http://www.apache.org/", Collections.emptyList());
    }
}