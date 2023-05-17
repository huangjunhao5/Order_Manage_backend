package com.backend.orderservice;

import com.backend.orderservice.mapper.OrderMapper;
import com.backend.orderservice.service.OrderService;
import com.backend.pojo.client.UserClient;
import com.backend.pojo.client.Result;
import com.backend.pojo.client.StorehouseClient;
import com.backend.pojo.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private StorehouseClient storehouseClient;
    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderService orderService;
    @Test
    void contextLoads() {
        Result<List<User>> allUser = userClient.getAllUser();
        System.out.println(allUser);
        List<User> user = allUser.getData();
        System.out.println(user);
    }
    @Test
    void contextLoads2() {
        Result result = storehouseClient.getAllProduct();
        System.out.println(result);
    }
    @Test
    void addStorehouse(){
        Result result = storehouseClient.ChangeStoreNum(6, 4, StoreHouse.Add);
        System.out.println(result);
    }

    @Test
    void testSelectById(){
//        System.out.println(orderMapper.selectById(1));
    }

    @Test
    void TestCreateNewOrder(){
        Order order = new Order();
        OrderInfo orderInfo = new OrderInfo();
        List<OrderList> orderLists = new ArrayList<OrderList>();
        OrderList orderList1 = new OrderList();
        OrderList orderList2 = new OrderList();
        orderInfo.setCustomer("客户1");
        orderInfo.setFlag(0);
        Timestamp d = new Timestamp(System.currentTimeMillis());
        orderInfo.setCreateTime(d);
        order.setOrderInfo(orderInfo);
//        orderList1.setOrderId();
        orderList1.setNum(3);
        orderList1.setProductId(6);
        orderList2.setNum(4);
        orderList2.setProductId(7);
        orderLists.add(orderList1);
        orderLists.add(orderList2);
        order.setOrderLists(orderLists);
        System.out.println(order);
        orderService.createNewOrder(order);
    }
}
