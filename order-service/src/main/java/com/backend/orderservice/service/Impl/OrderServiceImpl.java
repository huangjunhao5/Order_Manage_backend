package com.backend.orderservice.service.Impl;

import com.backend.orderservice.mapper.OrderInfoMapper;
import com.backend.orderservice.mapper.OrderListMapper;
import com.backend.orderservice.mapper.OrderMapper;
import com.backend.orderservice.service.OrderService;
import com.backend.pojo.client.Result;
import com.backend.pojo.client.StorehouseClient;
import com.backend.pojo.pojo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderListMapper orderListMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StorehouseClient storehouseClient;

    @GlobalTransactional
    @Transactional
    @Override
    public boolean createNewOrder(Order order) {
        OrderInfo orderInfo = order.getOrderInfo();
        Timestamp d = new Timestamp(System.currentTimeMillis());
        orderInfo.setCreateTime(d);
        List<OrderList> orderLists = order.getOrderLists();
        for(OrderList list: orderLists){
            Result result = storehouseClient.getById(list.getProductId());
//            Class<?> aClass = result.getData().getClass();
//            System.out.println(aClass);
            System.out.println(result.getData());
            if(result.getCode() == Result.err
                    || ((ProductStore)result.getData()).getNum() < list.getNum()){
                return false;
            }
        }
        for(OrderList list: orderLists){
            Result result = storehouseClient.ChangeStoreNum(
                    list.getProductid(),
                    list.getNum(),
                    StoreHouse.Sub);
            System.out.println(result.getMsg());
        }
        orderInfoMapper.insert(orderInfo);
//        System.out.println(orderInfo.getId());
        orderListMapper.insertAll(orderLists,orderInfo.getId());
        return true;
    }

    @Override
    public OrderInfo getOrderInfoById(Integer id) {
        return orderInfoMapper.selectById(id);
    }

    @Transactional
    @Override
    public Order getOrderById(Integer id) {
        Order order = new Order();
        OrderInfo orderInfo = orderInfoMapper.selectById(id);
        order.setOrderInfo(orderInfo);
        List<OrderList> orderLists = orderListMapper.selectByOrderId(orderInfo.getId());
        order.setOrderLists(orderLists);
        return order;
    }

    @Override
    public List<OrderList> getOrderListsByOrderId(Integer id) {
        return orderListMapper.selectByOrderId(id);
    }

    @Override
    public boolean updateOrderInfo(OrderInfo orderInfo) {
        orderInfoMapper.updateById(orderInfo);
        return true;
    }

    @GlobalTransactional
    @Transactional
    @Override
    public boolean addOrderList(OrderList addList) {
        Result select = storehouseClient.getById(addList.getProductid());
        ProductStore data = (ProductStore)select.getData();
        Integer num = data.getNum();
        if(num < addList.getNum()){
            return false;
        }
        Result result = storehouseClient.ChangeStoreNum(
                addList.getProductid(),
                addList.getNum(),
                StoreHouse.Sub);
        if(Objects.equals(result.getCode(), Result.err)){
            return false;
        }
        int insert = orderListMapper.insert(addList);
        return insert == 1;
    }
    @GlobalTransactional
    @Transactional
    @Override
    public boolean deleteList(OrderList deleteList) {
        Result result = storehouseClient.ChangeStoreNum(
                deleteList.getProductid(),
                deleteList.getNum(),
                StoreHouse.Sub);
        if(Objects.equals(result.getCode(), Result.err)){
            return false;
        }
        int flag = orderListMapper.deleteById(deleteList);
        return flag == 1;
    }

    @GlobalTransactional
    @Transactional
    @Override
    public boolean updateList(OrderList updateList) {
        Integer nowNum = updateList.getNum();
        OrderList orderList = orderListMapper.selectById(updateList.getId());
        Integer oldNum = orderList.getNum();
        if(nowNum > oldNum){
            Result productStore = storehouseClient.getById(updateList.getProductid());
            ProductStore data = (ProductStore) productStore.getData();
            if(data.getNum() < nowNum - oldNum){
                return false;
            }
        }
        Result result = storehouseClient.ChangeStoreNum(
                updateList.getProductid(),
                Math.abs(nowNum - oldNum),
                nowNum > oldNum ? StoreHouse.Sub : StoreHouse.Add);
        if(result.getCode() == Result.err){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        int flag = orderListMapper.updateById(updateList);
        return flag == 1;
    }

    @Override
    public List<OrderInfo> getAllOrderInfo() {
        return orderInfoMapper.selectAll();
    }

    @Override
    public List<OrderInfo> getOrderInfoByType(Integer type) {
        return orderInfoMapper.selectByType(type);
    }

    @Transactional
    @GlobalTransactional
    @Override
    public boolean cancerOrderById(Integer id) {
        List<OrderList> orderLists = orderListMapper.selectByOrderId(id);
        for(OrderList orderList: orderLists){
            boolean flag = deleteList(orderList);
            if(flag == false){
                TransactionAspectSupport.currentTransactionStatus()
                        .setRollbackOnly();
                return false;
            }
        }
        OrderInfo orderInfo = orderInfoMapper.selectById(id);
        orderInfo.setFlag(OrderInfo.CanceledFlag);
        orderInfoMapper.updateById(orderInfo);
        return true;
    }

    @Override
    public boolean changeOrderInfo(Integer id, Integer type) {
        OrderInfo orderInfo = getOrderInfoById(id);
        if(orderInfo == null){
            return false;
        }
        orderInfo.setFlag(type);
        return updateOrderInfo(orderInfo);
    }
}
