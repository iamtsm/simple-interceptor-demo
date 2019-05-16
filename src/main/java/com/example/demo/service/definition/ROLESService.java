package com.example.demo.service.definition;

import com.example.demo.entity.Roles;

import java.util.List;

public interface ROLESService {

    /**
     * 查询所有的角色及权限
     * @return
     */
    public List<Roles> selectAll();


}
