package com.backend.orderservice.controller;

import com.backend.orderservice.service.OrderService;
import com.backend.pojo.client.Result;
import com.backend.pojo.pojo.Order;
import com.backend.pojo.pojo.OrderInfo;
import com.backend.pojo.pojo.OrderList;
import com.backend.pojo.pojo.ProductStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Result createNewOrder(@RequestBody Order order){
        log.info(order.toString());
        boolean flag = orderService.createNewOrder(order);
        if(flag){
            return Result.ok("订单新建成功！", null);
        }else{
            return Result.err("订单新建失败，请检查输入是否正确！", null);
        }
    }
    @GetMapping("/type/{type}")
    public Result getOrderByType(@PathVariable Integer type){
        List<OrderInfo> orderInfoByType = orderService.getOrderInfoByType(type);
        return Result.ok(orderInfoByType);
    }
    @GetMapping
    public Result getAllOrder(){
        List<OrderInfo> allOrderInfo = orderService.getAllOrderInfo();
        return Result.ok(allOrderInfo);
    }
    @GetMapping("/{id}")
    public Result getOrderById(@PathVariable Integer id){
        Order order = orderService.getOrderById(id);
        Result result = new Result(
                order,
                order == null ? Result.err : Result.ok
        );
        return result;
    }
    @DeleteMapping("/{id}")
    public Result cancerOrder(@PathVariable Integer id){
        boolean flag = orderService.cancerOrderById(id);
        if(flag){
            return Result.ok("订单取消成功！", null);
        }else{
            return Result.err("取消失败！", null);
        }
    }
    @PutMapping("/{id}")
    public Result changeOrderType(@PathVariable Integer id, Integer type){
        boolean flag = orderService.changeOrderInfo(id, type);
        if(flag){
            return Result.ok("处理完成！", null);
        }else{
            return Result.err("处理失败！", null);
        }
    }
    @PutMapping
    public Result updateOrderInfo(@RequestBody OrderInfo order){
        boolean flag = orderService.updateOrderInfo(order);
        if(flag){
            return Result.ok("修改成功！", null);
        }else{
            return Result.err("修改失败，请检查数据是否合法！", null);
        }
    }
    @PostMapping("/orderlist")
    public Result addOrderList(@RequestBody OrderList orderList){
        boolean flag = orderService.addOrderList(orderList);
        if(flag){
            return Result.ok("添加成功！", null);
        }else{
            return Result.err("添加失败！", null);
        }
    }
    @PutMapping("/orderlist")
    public Result updateOrderList(@RequestBody OrderList orderList){
        boolean flag = orderService.updateList(orderList);
        if(flag){
            return Result.ok("修改成功！", null);
        }else{
            return Result.err("修改失败，请检查输入数据是否合法！", null);
        }
    }
    @DeleteMapping("/orderlist")
    public Result deleteOrderList(@RequestBody OrderList orderList){
        boolean flag = orderService.deleteList(orderList);
        if(flag){
            return Result.ok("删除成功！", null);
        }else{
            return Result.err("删除失败，请检查数据是否合法！", null);
        }
    }
}
