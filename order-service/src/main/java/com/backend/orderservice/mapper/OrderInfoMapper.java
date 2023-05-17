package com.backend.orderservice.mapper;

import com.backend.pojo.pojo.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    public OrderInfo selectById(Integer id);
    public List<OrderInfo> selectAll();
    public List<OrderInfo> selectByType(Integer type);
}
