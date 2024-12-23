package com.nsh.blog_rest_service.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
//    public static final String AUTHORIZATION_HEADER="Authorization";
//    private ApiKey apiKeys(){
//        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
//    }
//    @Bean
//    GroupedOpenApi publicApi(){
//        return GroupedOpenApi.builder().group("public-apis").pathsToMatch("/**").build();
//    }

    @Bean
    OpenAPI customOpenApi(){
        return new OpenAPI().info(new Info().title("API Title").version("version")).addSecurityItem(new SecurityRequirement().addList("bearerAuth")).components(new Components().addSecuritySchemes("bearerAuth",new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

//    private List<SecurityContext> securityContexts(){
//        return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
//    }

//    private List<SecurityReference> sf() {
//        AuthorizationScope authorizationScope=new AuthorizationScope("global","access everything");
//        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{ authorizationScope }));
//    }
//
//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2).securityContexts(securityContexts()).securitySchemes(Arrays.asList(apiKeys())).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).build();
//    }
//
//    private ApiInfo getInfo() {
//        return new ApiInfo(" Blog Application: backend Apis"," This project is made to learn Spring boot","1.0","Term of Services",
//                new Contact("Navneet","shaninavneet914071@gmail.com","shaninavneet914071@gmail.com"),"licence of APIs","License url", Collections.emptyList());
//    }


}
