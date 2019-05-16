package com.example.demo.service.Impl;

import com.example.demo.entity.Roles;
import com.example.demo.service.definition.AUTHService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 自己实现的简易区分权限
 */

public class Auth implements AUTHService {

    public static List<Roles> rolesList;

    /**
     * 返回 权限角色 map
     * @return
     */
    public Map<String ,Object> getAuthMap(List<Roles> rolesList){

        Map<String ,Object> authMap = new HashMap<>();

        for (Roles role : rolesList){
            authMap.put(role.getRole(),role.getUrl());
        }

        return authMap;
    }


    /**
     * 匹配role-url  返回true
     * @param uri
     * @param role
     * @return
     */
    public boolean authMatch(String uri,String role){

        if (rolesList == null) {
            System.out.println("注入失败");
            return false;
        }

        String urls = getAuthMap(rolesList).get(role).toString();

        System.out.println("urls : "+urls);
        System.out.println("uri : "+uri);
        System.out.println("匹配规则 : "+regexURI(uri,urls));

        return regexURI(uri,urls);
    }


    /**
     * url 匹配
     * @param uri
     * @param urls
     * @return
     */
    public boolean regexURI(String uri,String urls) {

        String[] urlsArr = urls.split(",");

        /**
         * 若有一个匹配，则匹配成功
         */
        for (String url : urlsArr) {
            if (Pattern.compile(url).matcher(uri).matches()) return true;
        }
        return false;
    }

}