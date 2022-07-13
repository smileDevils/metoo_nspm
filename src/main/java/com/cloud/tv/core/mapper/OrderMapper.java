package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    Order getObjByOrderId(Long orderId);

    Order getObjByOrderNo(String orderNo);

    int save(Order instance);
}
