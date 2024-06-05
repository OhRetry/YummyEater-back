package com.YammyEater.demo.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class InitDBForTestEntry {
    @Autowired
    InitDBForTest initDBForTest;

    @Value("${flag_init_dummy_data}")
    boolean FLAG_INIT_DUMMY_DATA;

    @PostConstruct
    public void InitEntry() {
        if(FLAG_INIT_DUMMY_DATA) {
            initDBForTest.init();
        }
    }
}
