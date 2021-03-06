package com.cloud.tv.core.manager.integrated.policy;

import com.cloud.tv.core.utils.freemarker.FreemarkerUtil;
import com.cloud.tv.entity.User;
import com.cloud.tv.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestHtmlComtroller {

    @Autowired
    private FreemarkerUtil freemarkerUtil;

    @RequestMapping("/html")
    public String html(Model model){
        Result result = new Result();
        result.setCode(100);
        model.addAttribute("result", result);
        return "index";
    }

    @RequestMapping("/ftl")
    public String ftl(Model model){
        Result result = new Result();
        result.setCode(100);
        model.addAttribute("result", result);
        return "test";
    }

    @RequestMapping("/createHtml")
    public void createHtml(Model model){
        Result result = new Result();
        result.setCode(100);
        User user = new User();
        user.setUsername("hkk");
//        result.setUser(user);
        model.addAttribute("result", result);
        this.freemarkerUtil.createHtml("test.ftl", "testq.html", result);
    }

}
