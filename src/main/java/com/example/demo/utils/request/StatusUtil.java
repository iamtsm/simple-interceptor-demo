package com.example.demo.utils.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AlgorithmParameters;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatusUtil {



    /**
     * 设置返回的结果集  --固定两个参数
     * @param status
     * @param message
     * @param args
     * @return
     */
    public static Map<String,Object> setResultMap(Integer status, Object message, Object ...args){

        Map<String,Object> resultMap = new HashMap<>();

        if (status == null) return null;

        resultMap.put("status",status);
        resultMap.put("message",message);

        for (int i = 0; i < args.length; i++){
            resultMap.put("obj"+i,args[i]);
        }

        return resultMap;
    }


    /**
     * json字符串转map
     * @param json
     * @return
     */
    public static Map jsonToMap(String json){

        ObjectMapper mapper = new ObjectMapper();
        Map resultMap = new HashMap();

        try {
            resultMap = mapper.readValue(json,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultMap;
    }


    /**
     * map 转json字符串
     * @param map
     * @return
     */
    public static String mapToJson(Map map){

        ObjectMapper mapper = new ObjectMapper();

        String resultJson = "{}";

        try {
            resultJson = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

}
