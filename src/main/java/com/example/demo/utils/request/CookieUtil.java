package com.example.demo.utils.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    /**
     * 设置cookie
     * @param response
     * @param k
     * @param v
     * @param maxAge
     * @param httpOnly
     */
    public static void setCookie(HttpServletResponse response, String k, String v, String domain, String path,
                                 Integer maxAge, boolean httpOnly, boolean security){

        Cookie cookie = new Cookie(k, v);

        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setSecure(security);

        try {
            response.addCookie(cookie);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * 获取指定的cookie 值
     * @param request
     * @param domain
     * @param path
     * @param name
     * @return
     */
    public static String getCookieByKey(HttpServletRequest request, String domain , String path, String name){

        System.out.println(domain+" "+path+" "+name);

        if (request.getCookies() != null){

            for (Cookie cookie : request.getCookies()){

                if (cookie.getName().equals(name)) return cookie.getValue();

            }

        }
        return "no cookie";
    }



    /**
     * 删除 指定的cookie
     * @param request
     * @return
     */
    public static boolean dropCookie(HttpServletRequest request, HttpServletResponse response, String domain , String path, String name){

        System.out.println(domain+" "+path+" "+name);

        if (request.getCookies() != null){

            for (Cookie cookie : request.getCookies()){

                if (cookie.getName().equals(name)){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
        return false;
    }
}
