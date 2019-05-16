package com.example.demo.config;


import com.example.demo.service.Impl.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AuthConfig {

    @Bean(name = "auth")
    public Auth getAuth(){

        return new Auth();
    }

}
