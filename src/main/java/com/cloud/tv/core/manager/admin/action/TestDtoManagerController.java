package com.cloud.tv.core.manager.admin.action;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.dto.GradeDto;
import com.cloud.tv.dto.UserDto;
import com.cloud.tv.entity.Accessory;
import com.cloud.tv.entity.Grade;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/test")
public class TestDtoManagerController {

    @RequestMapping("/dto")
    public Object dto(@Valid UserDto dto){
        return null;
    }

    public static void main(String[] args) {

        /*User user=new User();
        user.setId(Long.parseLong("1"));
        user.setUsername("name");
        user.setPassword("pwd");
        user.setSex(1);

        UserDto userDto = new UserDto();
        userDto.setId(Long.parseLong("1"));
        userDto.setUsername("p_name");
        userDto.setPassword("p_pwd");



        User target = new User();

        BeanUtils.copyProperties(userDto,target);

        SystemTest.out.println(target.getUsername() == userDto.getUsername());


        SystemTest.out.println(user.toString());
        SystemTest.out.println(target.toString());*/

        GradeDto gradeDto = new GradeDto();
        gradeDto.setName("grade_name");
        gradeDto.setType(1);

        Accessory accessory = new Accessory();
        accessory.setA_name("a_name");
        accessory.setA_path("a_path");

        gradeDto.setAccessory(accessory);

        Grade target = new Grade();

        BeanUtils.copyProperties(gradeDto, target);

        System.out.println(gradeDto.toString());
        System.out.println(target.toString());

    }

    @RequestMapping("test")
    public Object test(@RequestBody Grade grade){
        System.out.println(grade.toString());
        return grade.toString();
    }
}
