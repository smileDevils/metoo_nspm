package com.cloud.tv.core.service;

import com.cloud.tv.entity.Policy;
import com.cloud.tv.entity.Task;

import java.util.List;

public interface IssuedService {

    void pushtaskstatuslist();

    List<Task> query();

    void queryTask(String invisibleName, String type, List<Policy> policysNew, String command);

    void createOrder(String userName, String type, List<Policy> policysNew);
}
