package com.example.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/cookie.properties")
@ConfigurationProperties(prefix = "cookie.tsm")
@Data
public class CookieProperties {

    private String domain;

    private String path;

    private Integer maxAge;

    private String name;
}
