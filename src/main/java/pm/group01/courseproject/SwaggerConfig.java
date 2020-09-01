package pm.group01.courseproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.*;

/*
 * @author Battuguldur Ganbold (986874)
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * localhost:8090/v2/api-docs
     * localhost:8090/swagger-ui.html
     * @return
     */

    @Primary
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Team1")
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo buildApiInfo() {
        Contact contact = new Contact("April 2020 CS490 Project Management Course", "https://eteam1.com", "mail@eteam1.com");
        return new ApiInfoBuilder()
                .title("Shopping Cart System")
                .description("Shopping Cart System API")
                .license("license")
                .version("1.0")
                .contact(contact)
                .build();
    }

}
