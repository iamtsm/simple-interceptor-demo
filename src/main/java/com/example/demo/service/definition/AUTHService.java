package com.example.demo.service.definition;

import com.example.demo.entity.Roles;

import java.util.List;
import java.util.Map;

public interface AUTHService {

    public Map<String ,Object> getAuthMap(List<Roles> rolesList);

    public boolean authMatch(String uri,String role);

    public boolean regexURI(String uri,String urls);

}
