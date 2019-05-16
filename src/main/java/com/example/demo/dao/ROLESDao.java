package com.example.demo.dao;


import com.example.demo.entity.Roles;
import org.beetl.sql.core.annotatoin.SqlResource;

import java.util.List;

@SqlResource("roles")
public interface ROLESDao {

    /**
     * 查询所有的角色及权限
     * @return
     */
    public List<Roles> selectAll();


}
