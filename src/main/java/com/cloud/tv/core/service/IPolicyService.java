package com.cloud.tv.core.service;

import com.cloud.tv.entity.Policy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IPolicyService {

    Policy getObjById(String id);
    List<Policy> getObjByParentId(String parentId);
    List<Policy> getObjOrderNo(String orderNo);
    List<Policy> getObjByMap(Policy instance);
    int save(Map policy);
    int delete(Policy policy);
    Double getGrade(String deviceUuid);
    int update(List<Policy> policies);
    Double HealthScore(String deviceUuid);
}
