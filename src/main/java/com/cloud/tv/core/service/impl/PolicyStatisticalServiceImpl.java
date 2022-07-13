package com.cloud.tv.core.service.impl;

import com.cloud.tv.core.mapper.PolicyStatisticalMapper;
import com.cloud.tv.core.service.IPolicyStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PolicyStatisticalServiceImpl implements IPolicyStatisticalService {

    @Autowired
    private PolicyStatisticalMapper policyStatisticalMapper;

    @Override
    public Double getObjByCode(String code) {
        return this.policyStatisticalMapper.getObjByCode(code);
    }
}
