package com.example.demo.config;

import com.example.demo.config.properties.CookieProperties;
import com.example.demo.config.properties.InterceptorProperties;
import com.example.demo.service.Impl.Auth;
import com.example.demo.service.definition.ROLESService;
import com.example.demo.utils.interceptor.InterceptorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorProperties interceptorProperties;
    @Autowired
    private CookieProperties cookieProperties;
    @Autowired
    private ROLESService rolesService;


    @Override
    public void addInterceptors(InterceptorRegistry registry){

        //存放到静态变量中
        Auth.rolesList = rolesService.selectAll();

        //添加拦截路径
        registry.addInterceptor(new InterceptorH()).addPathPatterns(
                interceptorProperties.getAdmin(),
                interceptorProperties.getUser()
        );
    }



    private class InterceptorH implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            return InterceptorHandler.beforeDoRequest(request,response,cookieProperties);
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            InterceptorHandler.afterDoRequest(request,response);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            InterceptorHandler.afterDoShowView(request,response);
        }
    }

}
