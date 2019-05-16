package com.example.demo.service.Impl;

import com.example.demo.dao.ROLESDao;
import com.example.demo.entity.Roles;
import com.example.demo.service.definition.ROLESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ROLESServiceImpl implements ROLESService {

    @Autowired
    private ROLESDao rolesDao;

    @Override
    public List<Roles> selectAll() {

        return rolesDao.selectAll();
    }
}
