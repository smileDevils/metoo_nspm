package com.cloud.tv.core.service;

import com.cloud.tv.dto.CourseDto;
import com.cloud.tv.entity.Course;
import com.cloud.tv.vo.CourseVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ICourseService {

    Course getObjById(Long id);

    Page<Course> query(Map params);

    Object save(CourseDto instance);

    Object update(Course instance);

    boolean delete(Long id);

    List<Course> selectAll();

    /**
     * 条件查询
     * @param params
     * @return
     */
    List<Course> findBycondition(Map params);


    List<CourseVo> webCourseList();


}
