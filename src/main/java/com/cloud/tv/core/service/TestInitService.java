package com.cloud.tv.core.service;

import org.springframework.stereotype.Service;

@Service
public class TestInitService {

    private String a;

    public void init(){
        this.a="123";
    }

    public String getA(){
        return this.a;
    }


}
