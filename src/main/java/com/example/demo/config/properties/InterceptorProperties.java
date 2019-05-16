package com.example.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/interceptor.properties")
@ConfigurationProperties(prefix = "interceptor.auth")
@Data
public class InterceptorProperties {


    private String admin;

    private String user;

}
