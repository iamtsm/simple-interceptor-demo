package com.example.demo.utils.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

public class SessionUtil {


    /**
     * 生成取值的key
     * @return
     */
    public static String genSessionKey(){

        return UUID.randomUUID().toString().toUpperCase();
    }


    /**
     * 设置session
     * @param session
     * @param v
     * @param maxAge
     * @return
     */
    public static String setSession(HttpSession session, Object v, Integer maxAge){

        String session_key = genSessionKey();

        session.setAttribute(session_key,v);

        session.setMaxInactiveInterval(maxAge);

        return session_key;
    }


    /**
     * 删除session
     * @param session
     */
    public static void dropSession(HttpSession session){

        session.invalidate();
    }


    /**
     * 删除session中的属性
     * @param session
     * @param key
     * @return
     */
    public static boolean deleteSessionByKey(HttpSession session, String key){

        Map map = (Map) session.getAttribute(key);

        if(map == null) return true;

        session.removeAttribute(key);

        return true;
    }


    /**
     * 获取session中的对应的值
     * @param session
     * @param key
     * @return
     */
    public static Object getSessionByKey(HttpSession session, String key){

        return session.getAttribute(key);
    }


    /**
     * 检查登录状态,返回存在对应的session数据
     * @param request
     * @param session
     * @return
     */
    public static Map checkUserInfo(HttpServletRequest request, HttpSession session, String domain,
                                    String path, String cookie_key){

        //获取cookie的值  用来从redis中取值对比验证码
        String uuid_k = CookieUtil.getCookieByKey(request,domain,path,cookie_key);

        //cookie失效
        if (uuid_k.equals("no cookie")) return StatusUtil.setResultMap(497,cookie_key+"对应的账户失效,重新登录");

        String userInfo = (String) SessionUtil.getSessionByKey(session,uuid_k);

        //对应的session失效
        if (userInfo == null) return StatusUtil.setResultMap(497,cookie_key+"对应的账户失效,重新登录");

        return StatusUtil.setResultMap(200,userInfo);
    }


    /**
     * 保存用户信息到session  有效期,  返回session_key到cookie中
     * @param response
     * @param v
     */
    public static void saveUserInfo(HttpSession session, HttpServletResponse response, String cookie_key, Object v,
                                    int time, String domain, String path){

        String m = StatusUtil.mapToJson((Map)v);

        String session_key = SessionUtil.setSession(session,m,time);

        CookieUtil.setCookie(response,cookie_key,session_key,domain,path,time,false,false);
    }


}
