package com.cloud.tv.core.manager.admin.action;

import com.cloud.tv.core.service.TestInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestInitAction {

    @Autowired
    private TestInitService testInitService;

    @Value("asd")
    private String client;

    private String b;

    public void init(){
        this.b="456";
    }

    public static void main(String[] args) {
        System.out.println(System.getenv());
    }

    @RequestMapping("/admin/testInit")
    public String test(){
        System.out.println(client);
        System.out.println(b);
        return this.testInitService.getA();
    }
}
