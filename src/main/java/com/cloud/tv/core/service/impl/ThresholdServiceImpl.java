package com.cloud.tv.core.service.impl;

import com.cloud.tv.core.mapper.ThresholdMapper;
import com.cloud.tv.core.service.IThresholdService;
import com.cloud.tv.entity.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ThresholdServiceImpl implements IThresholdService {

    @Autowired
    private ThresholdMapper thresholdMapper;

    @Override
    public Threshold query() {
        return this.thresholdMapper.query();
    }

    @Override
    public int update(Threshold instance) {
        return this.thresholdMapper.update(instance);
    }
}
