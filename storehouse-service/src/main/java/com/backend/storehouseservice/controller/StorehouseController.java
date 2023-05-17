package com.backend.storehouseservice.controller;

import com.backend.pojo.client.Result;
import com.backend.pojo.pojo.ProductStore;
import com.backend.pojo.pojo.Products;
import com.backend.storehouseservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storehouse")
public class StorehouseController {
    private final ProductService productService;

    public StorehouseController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Result createNewProduct(Products product){
        boolean flag = productService.createNewProduct(product);
        if(flag){
            return Result.ok("商品创建成功！",null);
        }else{
            return Result.err("商品创建失败，请检查名称和编号是否有重复！", null);
        }
    }
    @GetMapping
    public Result getAllProduct(){
        List<ProductStore> productStores = productService.selectAll();
        return Result.ok(productStores);
    }
    @GetMapping("/info")
    public Result getAllProductInfo(){
        return Result.ok(productService.selectAllProduct());
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        ProductStore productStore = productService.selectById(id);
        if(productStore != null){
            return Result.ok(productStore);
        }else{
            return Result.err(null);
        }
    }
    @PutMapping
    public Result updateProductInfo(@RequestBody Products product){
        boolean flag = productService.updateProductInfo(product);
        if(flag){
            return Result.ok("修改成功！", null);
        }else{
            return Result.err("修改失败，请检查是否有重名或出现非法字符。",null);
        }
    }
    @PutMapping(value = "/{id}")
    public Result ChangeStoreNum(@PathVariable Integer id, Integer num, Integer changeFlag){
        if(num < 0){
            return Result.err("输入数据格式错误：必须为正正整数！", null);
        }
//        System.out.println(id + " " + num + " " + changeFlag);
        boolean flag = productService.changeStoreNum(id, num, changeFlag);
        return Result.ok("修改成功！", null);
    }
}
