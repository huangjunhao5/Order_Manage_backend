package com.backend.orderservice.service;

import com.backend.pojo.pojo.Order;
import com.backend.pojo.pojo.OrderInfo;
import com.backend.pojo.pojo.OrderList;

import java.util.List;

public interface OrderService {
    public boolean createNewOrder(Order order);
    public OrderInfo getOrderInfoById(Integer id);

    public Order getOrderById(Integer id);
    public List<OrderList> getOrderListsByOrderId(Integer id);
    public boolean updateOrderInfo(OrderInfo orderInfo);
    public boolean addOrderList(OrderList addList);
    public boolean deleteList(OrderList deleteList);
    public boolean updateList(OrderList updateList);
    public List<OrderInfo> getAllOrderInfo();
    public List<OrderInfo> getOrderInfoByType(Integer type);
    public boolean cancerOrderById(Integer id);
    public boolean changeOrderInfo(Integer id, Integer type);
}
