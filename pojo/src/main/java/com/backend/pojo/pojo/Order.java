package com.backend.pojo.pojo;


import lombok.Data;

import java.util.List;

@Data
public class Order {
    private OrderInfo orderInfo;
    private List<OrderList> orderLists;
}
