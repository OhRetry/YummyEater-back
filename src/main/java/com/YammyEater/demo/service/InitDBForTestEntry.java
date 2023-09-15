package com.YammyEater.demo.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class InitDBForTestEntry {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String DDL_AUTO;

    @Autowired
    InitDBForTest initDBForTest;

    @PostConstruct
    public void InitEntry() {
        if(DDL_AUTO.startsWith("create")) {
            initDBForTest.init();
        }
    }
}
