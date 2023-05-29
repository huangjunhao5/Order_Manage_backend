package com.backend.pojo.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orderlist")
public class OrderList {
    private Integer id;
    @TableField("orderid")
    private Integer orderId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductid(){
        return this.productId;
    }

    public void setProductid(Integer productid){
        this.productId = productid;
    }

    @TableField("productid")
    private Integer productId;
    @TableField("productname")
    private String productName;
    private Integer num;

}
