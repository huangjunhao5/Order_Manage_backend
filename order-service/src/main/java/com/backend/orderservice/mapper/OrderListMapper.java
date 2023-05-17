package com.backend.orderservice.mapper;

import com.backend.pojo.pojo.OrderList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderListMapper extends BaseMapper<OrderList> {
    public Integer insertAll(List<OrderList> orderLists, Integer orderId);
    public List<OrderList> selectByOrderId(Integer id);
}
