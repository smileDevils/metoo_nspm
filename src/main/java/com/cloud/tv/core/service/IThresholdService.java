package com.cloud.tv.core.service;

import com.cloud.tv.entity.Threshold;

public interface IThresholdService {

    Threshold query();

    int update(Threshold instance);
}
