package com.example.idus_exam.config;

import com.example.idus_exam.config.filter.JwtLogoutFilter;
import com.example.idus_exam.config.filter.LoginFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Idus Exam API")
                .description("Idus Exam API")
                .version("1.0.0");

    }

    @Bean
    OpenApiCustomizer springSecurityLoginEndpointCustomizer(ApplicationContext applicationContext) {
        FilterChainProxy springSecurityFilterChain =
                applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);


        return (openApi) -> {
            //스프링 필터체인에서 필터들을 반복문을 돌린다.
            for(SecurityFilterChain filterChain : springSecurityFilterChain.getFilterChains()) {
                Optional<LoginFilter> loginFilter = filterChain.getFilters()
                        .stream()
                        .filter(LoginFilter.class::isInstance)
                        .map(LoginFilter.class::cast)
                        .findAny();
                Optional<JwtLogoutFilter> logoutFilter = filterChain.getFilters()
                        .stream()
                        .filter(JwtLogoutFilter.class::isInstance)
                        .map(JwtLogoutFilter.class::cast)
                        .findAny();
                if(loginFilter.isPresent()) {
                    Operation operation = new Operation();

                    //로그인 입력값 스키마 설정
                    Schema<?> schema = new ObjectSchema()
                            .addProperty("email", new StringSchema())
                            .addProperty("password", new StringSchema());
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType("application/json", new MediaType().schema(schema))
                    );

                    operation.setRequestBody(requestBody);

                    //로그인 반환 코드 설정
                    ApiResponses response  =new ApiResponses();
                    response.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    response.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));

                    operation.setResponses(response);

                    operation.addTagsItem("유저 기능");
                    operation.summary("로그인");
                    PathItem pathItem = new PathItem().post(operation);

                    openApi.getPaths().addPathItem("/login", pathItem);
                }

                if(logoutFilter.isPresent()) {
                    Operation operation = new Operation();


                    //로그아웃 반환 코드 설정
                    ApiResponses response  =new ApiResponses();
                    response.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    response.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));

                    operation.setResponses(response);

                    operation.addTagsItem("유저 기능");
                    operation.summary("로그아웃");
                    PathItem pathItem = new PathItem().get(operation);

                    openApi.getPaths().addPathItem("/logout", pathItem);
                }
            }

        };
    }
}
