package com.example.demo.utils.interceptor;

import com.example.demo.config.AuthConfig;
import com.example.demo.service.Impl.Auth;
import com.example.demo.config.properties.CookieProperties;
import com.example.demo.utils.request.SessionUtil;
import com.example.demo.utils.request.StatusUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截器工具类
 */
public class InterceptorHandler {


    /**
     * 这里在请求执行前会执行拦截，必须返回true，后面的拦截才会执行
     * */
    public static boolean beforeDoRequest(HttpServletRequest request, HttpServletResponse response,
                                          CookieProperties cookieProperties ) throws Exception{

        //验证状态
        Map map = SessionUtil.checkUserInfo(request,request.getSession(),cookieProperties.getDomain(), cookieProperties.getPath(),
                cookieProperties.getName());

        Map infoMap;

        if (!map.get("status").toString().equals("200")){
            System.out.println("身份失效  -- 拦截 : "+map.get("message"));

            response.setCharacterEncoding("utf-8");
            response.getWriter().write(StatusUtil.mapToJson(map));
            return false;
        }else {
            infoMap = StatusUtil.jsonToMap(map.get("message").toString());
            System.out.println("身份有效  -- 拦截 : "+map.get("message"));

            /**
             * 获取容器中的bean
             */
            ApplicationContext context = new AnnotationConfigApplicationContext(AuthConfig.class);
            Auth auth = (Auth) context.getBean("auth");


            /**
             * user-role匹配
             */
            String  currentUrI = request.getRequestURI();
            String currentRole = infoMap.get("role").toString();
            boolean hasRole = auth.authMatch(currentUrI,currentRole);
            if(hasRole)  return true; //鉴权成功

            response.setCharacterEncoding("utf-8");
            response.getWriter().write(StatusUtil.mapToJson(StatusUtil.setResultMap(403,"您没有权限")));
            return false;
        }
    }


    /**
     * 请求执行结束后执行
     */
    public static void afterDoRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{

    }



    /**
     * 渲染完视图后执行
     */
    public static void afterDoShowView(HttpServletRequest request, HttpServletResponse response) throws Exception{


    }





}