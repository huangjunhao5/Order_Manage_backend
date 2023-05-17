package com.backend.pojo.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;


@Data
@TableName("Orderinfo")
public class OrderInfo {
    public static Integer CreatedFlag = 0;
    public static Integer EndFlag = 1;
    public static Integer CanceledFlag = -1;
    private Integer id;
    private Timestamp createTime;
    private String customer;

    private Integer flag;

    private Integer price;
}
