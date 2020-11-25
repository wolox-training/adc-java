package wolox.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
                .apiInfo(getApiEndPointsInfo());

    }

    private ApiInfo getApiEndPointsInfo() {
        return new ApiInfoBuilder().title("Training REST API")
                .description("REST API that has all the developmemt of the training")
                .contact(
                        new Contact(
                                "Alex Cudriz",
                                "https://github.com/wolox-training/adc-java",
                                "alex.cudriz@wolox.co"))
                .version("1.0.0")
                .build();
    }
}
