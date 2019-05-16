package com.example.demo.web;

import com.example.demo.config.properties.CookieProperties;
import com.example.demo.utils.request.CookieUtil;
import com.example.demo.utils.request.SessionUtil;
import com.example.demo.utils.request.StatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class TestController {

    @Autowired
    private CookieProperties cookieProperties;


    /**
     * admin接口
     * @return
     */
    @GetMapping(path = "/admin/test")
    public Map<String ,Object> AdminTest(){
        return StatusUtil.setResultMap(200,"访问admin成功");
    }


    /**
     * user接口
     * @return
     */
    @GetMapping(path = "/user/test")
    public Map<String ,Object> UserTest(){
        return StatusUtil.setResultMap(200,"访问user成功");
    }


    /**
     * 设置登录状态接口
     * @param session
     * @param response
     * @param name
     * @param pwd
     * @param role
     * @return
     */
    @GetMapping(path = "/set")
    public Map<String ,Object> set(HttpSession session, HttpServletResponse response,String name ,String pwd,String role){

        Map map = new HashMap<String ,String >(){{
            put("name",name);
            put("pwd",pwd);
            put("role",role);
        }};

        SessionUtil.saveUserInfo(session,response,
                cookieProperties.getName(),map,
                cookieProperties.getMaxAge(), cookieProperties.getDomain(),cookieProperties.getPath());

        return StatusUtil.setResultMap(200,"设置cookie/session成功");
    }


    /**
     * 退出接口
     * @param session
     * @param request
     * @param response
     * @return
     */
    @GetMapping(path = "/drop")
    public Map<String ,Object> drop(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        SessionUtil.dropSession(session);
        CookieUtil.dropCookie(request,response,
                cookieProperties.getDomain(),cookieProperties.getPath(),cookieProperties.getName());

        return StatusUtil.setResultMap(200,"清除cookie/session成功");
    }


    /**
     * 获取登录信息接口
     * @param session
     * @param request
     * @return
     */
    @GetMapping(path = "/get")
    public Map get(HttpSession session, HttpServletRequest request){

        return SessionUtil.checkUserInfo(request,session,
                cookieProperties.getDomain(),cookieProperties.getPath(),cookieProperties.getName());
    }


    /**
     * 界面
     * @return
     */
    @GetMapping(path = "/")
    public ModelAndView page(){
        return new ModelAndView("/index.html");
    }


}
