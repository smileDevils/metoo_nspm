package com.cloud.tv.core.service;

import com.cloud.tv.entity.Order;

public interface IOrderService {

    Order getObjByOrderId(Long orderId);

    Order getObjByOrderNo(String orderNo);

    int save(Order instance);
}
