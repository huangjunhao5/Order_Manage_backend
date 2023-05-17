package com.backend.pojo.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("storehouse")
public class StoreHouse {
    private Integer productId;
    private Integer num;

    public static Integer Add = 1;
    public static Integer Sub = 0;
}
